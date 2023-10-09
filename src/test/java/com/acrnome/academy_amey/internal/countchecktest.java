package com.acrnome.academy_amey.internal;

import com.siemens.mobility.tqa.domain.CobraFileHeader;
import com.siemens.mobility.tqa.service.reader.FehlerMaengelReader;
import com.siemens.mobility.tqa.service.reader.FileHeaderReader;
import com.siemens.mobility.tqa.testsupport.BinaryReader;

public class countchecktest {
	final String file_path = "src/test/resources/FehlerMaengelReadTest/940100S3.334";
	BinaryReader b = new BinaryReader(file_path);
	FileHeaderReader h = new FileHeaderReader();
	CobraFileHeader head = new CobraFileHeader();

	FehlerMaengelReader fmr;
	byte[] fileData;
}
