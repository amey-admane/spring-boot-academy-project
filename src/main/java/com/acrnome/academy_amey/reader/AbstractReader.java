package com.acrnome.academy_amey.reader;

import lombok.NoArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.acrnome.academy_amey.utils.ByteConversion;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
public abstract class AbstractReader {

  private static final Logger log = LogManager.getLogger(AbstractReader.class);
  protected byte[]            fileData; 

  public AbstractReader(byte[] fileData) {
    this.fileData = fileData;
  }

  public LocalDateTime extractTimestamp(byte[] fileData, int position) {
    try { 
      return LocalDateTime.ofInstant(Instant.ofEpochSecond(ByteConversion.readInt32(fileData, position)), ZoneId.of("UTC")); 
    } catch (Exception e) {
      log.error("Error occurred extractTimestamp: ", e);
      return LocalDateTime.of(1970, 1, 1, 0, 0);
    }
  }

  abstract void read();
}
