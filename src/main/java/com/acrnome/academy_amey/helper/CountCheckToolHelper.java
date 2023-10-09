package com.acrnome.academy_amey.helper;

import java.time.Instant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acrnome.academy_amey.utils.ByteConversion;

public class CountCheckToolHelper {
	private static final Logger log= LogManager.getLogger(CountCheckToolHelper.class);
	/*
	 * Main records of protocol for particular timestamp
	 */
	public static int noOfErrorProtocolData(byte[] fileData, int posKopf, long protocolDataLen,
			LocalDate starttargetTimeStamp, LocalDate endtargetTimeStamp) {
		int countRecord = 0;
		try {
			int telegrammVersion = ByteConversion.readByte(fileData, 9);
			int kennung = ByteConversion.readBit0ToBit6(fileData, posKopf);
			int headerVerschiebung = 0;
			if (telegrammVersion == 8) {
				headerVerschiebung = 2;
			}
			if (kennung == 2) {

				int currentPosKopf = posKopf + 3 + headerVerschiebung;
				for (int i = 0; i < protocolDataLen; i++) {
					LocalDate recordDateTimeStamp = extractTimestamp(fileData, currentPosKopf + 5).plusHours(2)
							.toLocalDate();

					if (isBetween(recordDateTimeStamp, starttargetTimeStamp, endtargetTimeStamp)) {
						countRecord += 1;
					}
					currentPosKopf += 11;
				}

			}

			return countRecord;
		} catch (Exception e) {
			return 0;
		}
	}

	/*
	 * Main records of failure for particular timestamp
	 */

	public static int noOfErrorFailuerData(byte[] fileData, int posKopf, long failureDataLen,
			LocalDate starttargetTimeStamp, LocalDate endtargetTimeStamp) {
		int countRecord = 0;
		try {
			int headerVerschiebung = 0;
			int telegrammVersion = ByteConversion.readByte(fileData, 9);

			if (telegrammVersion == 8) {

				headerVerschiebung = 2;
			}

			int currentEventKopfPos = posKopf + 3 + headerVerschiebung;
			for (long i = 0; i < failureDataLen; i++) {
				int GesamtlaengeEntrag = extractGesamtlaengeEntrag(fileData, currentEventKopfPos);

				LocalDate recordDateTimeStamp = extractTimestamp(fileData, currentEventKopfPos + 5).plusHours(2)
						.toLocalDate();

				if (isBetween(recordDateTimeStamp, starttargetTimeStamp, endtargetTimeStamp)) {
					countRecord += 1;
				}
				currentEventKopfPos += GesamtlaengeEntrag;

			}

			return countRecord;
		} catch (Exception e) {
			return 0;
		}
	}

	/*
	 * Helpers
	 */

	public static long getTotalDataLen(byte[] fileData, int posKopf) {
		int dWordKennung = 0;
		return ((dWordKennung == 0) ? ByteConversion.readInt16(fileData, posKopf + 1)
				: ByteConversion.readInt32(fileData, posKopf + 1));
	}

	/*
	 * Local time decoder helper
	 */
	public static LocalDateTime extractTimestamp(byte[] fileData, int position) {
		try {
			return LocalDateTime.ofInstant(Instant.ofEpochSecond(ByteConversion.readInt32(fileData, position)),
					ZoneId.of("UTC"));
		} catch (Exception e) {
			return LocalDateTime.of(1970, 1, 1, 0, 0);
		}
	}

	public static int extractGesamtlaengeEntrag(byte[] fileData, int posKopf) {
		try {
			return ByteConversion.readByte(fileData, posKopf + 1);
		} catch (Exception e) {
			log.error("Error occurred extractGesamtlaengeEntrag: ", e);
			return 0;
		}
	}

	public static boolean isBetween(LocalDate target, LocalDate min, LocalDate max) {
		if (target == null || min == null || max == null) {
			throw new IllegalArgumentException("parametres can't be null");
		}

		return target.compareTo(min) >= 0 && target.compareTo(max) <= 0;
	}

	public static String extractEreignisCode(byte[] fileData, int posKopf) {
		try {
			return ByteConversion.readString16BigEndian(fileData, posKopf + 3);
		} catch (Exception e) {
			log.error("Error on extractEreignisCode: ", e);
			return "";
		}
	}
}
