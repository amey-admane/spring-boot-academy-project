package com.acrnome.academy_amey.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acrnome.academy_amey.utils.ByteConversion;



public class FileReader extends AbstractReader {
	private static final Logger log = LogManager.getLogger(FileReader.class);
	

	@Override
	public void read() {
		ByteConversion.readInt32(fileData,63);
	}
}
