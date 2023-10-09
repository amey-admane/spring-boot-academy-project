package com.acrnome.academy_amey.utils;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ByteConversion {

	private static final Logger log = LogManager.getLogger(ByteConversion.class);

	public static int readInt16(byte[] bytes, int pos) {
		if (!checkArray(bytes, 2))
			return 0;
		try {
			byte[] data = Arrays.copyOfRange(bytes, pos, 2 + pos);
			return (((data[1] & 0xFF) << 8) | ((data[0] & 0xFF) << 0));
		} catch (Exception e) {
			log.error("Error on convertierung: ", e);
			return 0;
		}
	}

	public static String readString16BigEndian(byte[] bytes, int pos) {
		if (!checkArray(bytes, 2))
			return "";
		byte[] data = Arrays.copyOfRange(bytes, pos, 2 + pos);
		// String part1 = Integer.toHexString(data[0]);
		String part1 = String.format("%02X", data[0]);
		// String part2 = Integer.toHexString(data[1]);
		String part2 = String.format("%02X", data[1]);
		String result = part1 + part2;
		return result.toUpperCase();
	}

	public static boolean checkArray(byte[] bytes, int length) {
		if (ArrayUtils.isNotEmpty(bytes) && bytes.length >= length) {
			return true;
		}
		return false;
	}

	/**
	 * DWord - 4bytes
	 *
	 * @param
	 * @param pos
	 * @return
	 */
	public static int readInt32(byte[] bytes, int pos) {
		if (!checkArray(bytes, 4))
			return 0;
		try {
			byte[] data = Arrays.copyOfRange(bytes, pos, 4 + pos);
			return (((data[3] & 0xFF) << 24) | ((data[2] & 0xFF) << 16) | ((data[1] & 0xFF) << 8)
					| ((data[0] & 0xFF) << 0));
		} catch (Exception e) {
			log.error("Error on convertierung: ", e);
			return 0;
		}
	}

	public static String readInt24BCDStr(byte[] bytes, int pos) {
		if (!checkArray(bytes, 3))
			return "0";
		byte[] data = Arrays.copyOfRange(bytes, pos, 3 + pos);

		// String part1 = Integer.toHexString(data[0]);
		String part1 = String.format("%02X", data[0]);
		// String part2 = Integer.toHexString(data[1]);
		String part2 = String.format("%02X", data[1]);
		String part3 = String.format("%02X", data[2]);
		String result = part1 + part2 + part3;
		return result.toUpperCase();

	}

	public static int readBit0ToBit6(byte[] data, int i) {
		if (!checkArray(data, 1))
			return 0;
		String string = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

		return Integer.parseInt(string.substring(1, 8), 2);
	}

	public static int readByte(byte[] data, int i) {
		if (!checkArray(data, 1))
			return 0;
		try {
			return data[i];
		} catch (Exception e) {
			log.error("Error on convertierung: ", e);
			return 0;
		}
	}
}
