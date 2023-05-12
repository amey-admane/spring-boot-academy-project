package com.acrnome.academy_amey.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.acrnome.academy_amey.entity.MasterTools;
import com.acrnome.academy_amey.exceptions.MasterToolsException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExcelFileHelper {

	

	private static String excelFormat = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	

	public boolean checkExcelFormat(MultipartFile multipartFile) {
		String contentType = multipartFile.getContentType();

		return excelFormat.equals(contentType);
	}
	
	public boolean validateExcelHeader(Row row) {
		return (row.getCell(0).toString().equals("Item Id") && row.getCell(1).toString().equals("Description")
				&& row.getCell(2).toString().equals("Global Description")
				&& row.getCell(3).toString().equals("Location") && row.getCell(4).toString().equals("Group")
				&& row.getCell(5).toString().equals("Search A") && row.getCell(6).toString().equals("Search B")
				&& row.getCell(7).toString().equals("Supplier") && row.getCell(8).toString().equals("Ordering Code")
				&& row.getCell(9).toString().equals("Combinable Number Link")
				&& row.getCell(10).toString().equals("Global Tool No.") && row.getCell(11).toString()
						.equals("Suitable for Wire Size,Minimum Bending Radius,Min Tensile Strength"));

	}
	
	public boolean validateExcelHeaderColumns(Row row) {
		
		return row.getLastCellNum() == 12 ;
	}

	public List<Integer> validateDataUnForamttedRows(InputStream stream)  {

		
		try (XSSFWorkbook workbook = new XSSFWorkbook(stream)) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItrator = sheet.iterator();

			List<Integer> unFormatedRows = new ArrayList<>();

			while (rowItrator.hasNext()) {

				Row row = rowItrator.next();

				int rowNum = row.getRowNum();

				if(rowNum ==0 && !validateExcelHeaderColumns(row)) {
					throw new MasterToolsException("Error : Headers are Incomplete, Check the given Document Format","104",HttpStatus.NOT_ACCEPTABLE);
					
				}
				if (rowNum == 0 && !validateExcelHeader(row)) {
					
					throw new MasterToolsException("Error : Headers not present in Proper Format","102",HttpStatus.NOT_ACCEPTABLE);
				}
				
				if(rowNum > 0 && (row.getCell(0) == null || !row.getCell(0).toString().matches("\\d+")|| row.getCell(10) == null)) {
						unFormatedRows.add(rowNum + 1);
				}

			}
			
			return unFormatedRows;

		} catch (IOException e) {
			log.error("Error : Input Stream Error,  Unable to read the Excel File.");
			throw new MasterToolsException("Error :  Input Stream Error,  Unable to read the Excel File.","105",HttpStatus.BAD_REQUEST);
		}
	}

	public List<MasterTools> parseExcelDataForTools(InputStream stream){

		List<MasterTools> toolsDataList = new ArrayList<>();
		try (XSSFWorkbook workbook = new XSSFWorkbook(stream)) {

			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowItrator = sheet.iterator();

			List<Integer> unFormatedRows = new ArrayList<>();

			while (rowItrator.hasNext()) {

				Row row = rowItrator.next();

				int rowNum = row.getRowNum();

				if (rowNum == 0 && !validateExcelHeader(row)) {
					
					throw new MasterToolsException("Error : Headers not present in Proper Format","102",HttpStatus.NOT_ACCEPTABLE);
				}
				if (rowNum > 0) {
					MasterTools refrenceRow = new MasterTools();

					refrenceRow.setDescription("");
					refrenceRow.setGlobalDescription("");
					refrenceRow.setLocation("");
					refrenceRow.setGroup("");
					refrenceRow.setSearchA("");
					refrenceRow.setSearchB("");
					refrenceRow.setSupplier("");
					refrenceRow.setOrderingCode("");
					
					
					if ((row.getCell(0) != null && row.getCell(0).toString().matches("\\d+"))
						&& (row.getCell(10) != null && !"".equals(row.getCell(10).toString()))){
						refrenceRow.setItemId(String.valueOf(row.getCell(0).toString()));
						

					}else{
						unFormatedRows.add(rowNum + 1);
						continue;
					}

					if (row.getCell(1) != null && !"".equals(row.getCell(1).toString())) {
						refrenceRow.setDescription(row.getCell(1).toString());
					}
					if (row.getCell(2) != null && !"".equals(row.getCell(2).toString())) {
						refrenceRow.setGlobalDescription(row.getCell(2).toString());
					}

					if (row.getCell(3) != null && !"".equals(row.getCell(3).toString())) {
						refrenceRow.setLocation(row.getCell(3).toString());
					}
					

					if (row.getCell(4) != null && !"".equals(row.getCell(4).toString())) {
						refrenceRow.setGroup(row.getCell(4).toString());
					}

					if (row.getCell(5) != null && !"".equals(row.getCell(5).toString())) {
						refrenceRow.setSearchA(row.getCell(5).toString());
					}

					if (row.getCell(6) != null && !"".equals(row.getCell(6).toString())) {
						refrenceRow.setSearchB(row.getCell(6).toString());
					}

					if (row.getCell(7) != null && !"".equals(row.getCell(7).toString())) {
						refrenceRow.setSupplier(row.getCell(7).toString());
					}

					if (row.getCell(8) != null && !"".equals(row.getCell(8).toString())) {
						refrenceRow.setOrderingCode(row.getCell(8).toString());
					}

				

					toolsDataList.add(refrenceRow);
				}

			}
		
		} catch (IOException e) {
			log.error("Error : Input Stream Error,  Unable to read the Excel File.");
			throw new MasterToolsException("Error :  Input Stream Error,  Unable to read the Excel File.","105",HttpStatus.BAD_REQUEST);
		}
		
		toolsDataList.forEach(op -> log.info("toolsDataList : {}", op));
		if(toolsDataList.isEmpty()) {
			throw new MasterToolsException("Data List is Empty", "103", HttpStatus.NOT_FOUND);
		}
		return toolsDataList;
	}
}
