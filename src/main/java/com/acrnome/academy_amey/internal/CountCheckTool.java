package com.acrnome.academy_amey.internal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acrnome.academy_amey.helper.CountCheckToolHelper;
import com.acrnome.academy_amey.utils.ByteConversion;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Checker for Failure and Protocol Data
 *
 * @author ameyadmane
 */

public class CountCheckTool {
	private static final Logger log = LogManager.getLogger(CountCheckTool.class);
	private static LocalDate startTimeToCheck = LocalDate.of(2023, 8, 17);
	private static LocalDate endTimeToCheck = LocalDate.of(2023, 8, 15);
	private static String outputFilePath = "./src/main/resources/check-tool/results";
	private static String cobraFileFolder = "./src/main/resources/check-tool/cobra-files";
	private static String inputCSVFile = "./src/main/resources/check-tool/input-aws-is-counts/input_event.csv";
	private static String configFile = "./src/main/resources/check-tool/CountCheckConfig.json";

	public static void main(String[] args) throws IOException {

		try {
			if (Files.exists(Path.of(configFile)) && Files.exists(Path.of(inputCSVFile))
					&& Files.isDirectory(Path.of(cobraFileFolder)) && Files.isDirectory(Path.of(outputFilePath))) {
				log.info("Folder Structure Exists.. ");

				Map<String, String> scriptData = new HashMap<>();

				try {
					ObjectMapper mapper = new ObjectMapper();

					
					
					Path configfileName = Path.of(configFile);
					String inputConfigFileData = Files.readString(configfileName);
					JsonNode jsonFileData = mapper.readTree(inputConfigFileData);
					startTimeToCheck = LocalDate.parse(jsonFileData.get("check_period_start").asText());
					endTimeToCheck = LocalDate.parse(jsonFileData.get("target_date").asText());
				} catch (Exception e) {

					e.printStackTrace();
				}

				/*
				 * Reading the Input file from aws
				 */
				Map<String, String> inputFileData = readBooksFromCSV(Paths.get(inputCSVFile));

				/*
				 * Reading Cobara Files from the resource folder
				 */

				Files.list(Paths.get(cobraFileFolder)).forEach(path -> {

					try {
						if (path.getFileName().toString().contains("H")) {
							long error = readCobraFile(Files.readAllBytes(path), startTimeToCheck, endTimeToCheck);

							scriptData.put(path.getFileName().toString(), String.valueOf(error));

						}
					} catch (IOException e) {

						e.printStackTrace();
					}
				});

				/*
				 * comparing the input file data with script data
				 */
				List<List<String>> outputData = new ArrayList<>();
				scriptData.forEach((filename, errorcount) -> {

					ArrayList<String> rowData = new ArrayList<>();
					rowData.add(filename);
					if (Integer.valueOf(inputFileData.getOrDefault(filename,"0")) > Integer.valueOf( errorcount)) {
						rowData.add("true");
					} else {
						rowData.add("false");
					}
					rowData.add(errorcount);
					rowData.add(inputFileData.getOrDefault(filename,"0"));
					outputData.add(rowData);
				});
				/*
				 * Writting Output to CSV File
				 */

				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath + "/output.csv", false));
					writer.write("filename");
					writer.write(",");
					writer.write("success");
					writer.write(",");
					writer.write("count by script");
					writer.write(",");
					writer.write("count from input file");
					writer.write("\n");
					outputData.forEach(row -> {

						try {
							writer.write(row.get(0));
							writer.write(",");
							writer.write(row.get(1));
							writer.write(",");
							writer.write(row.get(2));
							writer.write(",");
							writer.write(row.get(3));
							writer.write("\n");
						} catch (IOException e) {
							e.printStackTrace();
						}

					});
					writer.close();

				} catch (Exception e) {
					log.error("Error occurred while writing the output csv" + e);
				}
			} else {
				log.info("Check Folder Structure.. ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long readCobraFile(byte[] fileData, LocalDate startTimeToCheck, LocalDate endTimeToCheck) {

		int protocolDataBytes = ByteConversion.readInt32(fileData, 71);
		int failureDataBytes = ByteConversion.readInt32(fileData, 63);

		long failuerDataLen = CountCheckToolHelper.getTotalDataLen(fileData, ByteConversion.readInt32(fileData, 63));
		long protocolDataLen = CountCheckToolHelper.getTotalDataLen(fileData, ByteConversion.readInt32(fileData, 71));

		var errorFailureData = CountCheckToolHelper.noOfErrorFailuerData(fileData, failureDataBytes, failuerDataLen,
				startTimeToCheck, endTimeToCheck);
		var errorProtocolData = CountCheckToolHelper.noOfErrorProtocolData(fileData, protocolDataBytes, protocolDataLen,
				startTimeToCheck, endTimeToCheck);

		return errorFailureData + errorProtocolData;

	}

	private static Map<String, String> readBooksFromCSV(Path pathToFile) {
		Map<String, String> inputCodes = new HashMap<>();
		try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
			String line = br.readLine();
			line = br.readLine();
			while (line != null) {
				String[] lineSplit = line.split(",");
				String filename = lineSplit[0].substring(1, lineSplit[0].length() - 1);
				String errorcount = lineSplit[1].substring(1, lineSplit[1].length() - 1);
				inputCodes.put(filename, errorcount);
				line = br.readLine();
			}
		} catch (IOException ioe) {
			log.error("Error occurred while reading the Input File " + ioe);
		}

		return inputCodes;
	}

}
