package com.acrnome.academy_amey.services;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.acrnome.academy_amey.entity.MasterTools;
import com.acrnome.academy_amey.exceptions.MasterToolsException;
import com.acrnome.academy_amey.helper.ExcelFileHelper;
import com.acrnome.academy_amey.repository.MasterToolsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MasterToolsService {

	@Autowired
	private ExcelFileHelper excelHelper;

	@Autowired
	private MasterToolsRepository masterToolsRepository;

	private final List<String> defaultColumnsForSorting = Arrays.asList("itemid", "supplier", "globaldescription",
			"description", "searcha", "searchb", "location", "globaltoolno", "combinable_numberlink",
			"dimensionparameters");

	public List<Integer> uploadExcelDataForTools(MultipartFile file) {

		if (excelHelper.checkExcelFormat(file)) {

			try {
				List<Integer> dataRowsNotAdded = excelHelper.validateDataUnForamttedRows(file.getInputStream());
				List<MasterTools> toolsDataList = excelHelper.parseExcelDataForTools(file.getInputStream());
				masterToolsRepository.uploadExcelDataForTools(toolsDataList);
				return dataRowsNotAdded;

			} catch (IOException e) {
				throw new MasterToolsException("Input Stream Error,  Unable to read the Excel File.", "105",
						HttpStatus.BAD_REQUEST);
			}

		} else {
			log.error("Error : File Type Not Supported, Upload Excel File");
			throw new MasterToolsException("Error : File Type Not Supported, Upload Excel File", "101",
					HttpStatus.UNSUPPORTED_MEDIA_TYPE);

		}
	}

	public List<MasterTools> getToolsMasterData(int offSet, int limit, String orderBy, String columnToSort) {
		return masterToolsRepository.getToolsMasterData(offSet, limit, orderBy, columnToSort);
	}

	public List<MasterTools> getToolsMasterDataById(String itemID) {
		return masterToolsRepository.getToolsMasterDataById(itemID);
	}

	public void addToolsMasterData(MasterTools toolsData) {
		if (toolsData.getItemId() == null || toolsData.getOrderingCode() == null) {
			throw new MasterToolsException("Mandatory Fields Not Present ", "114", HttpStatus.PRECONDITION_REQUIRED);

		} else if (!toolsData.getItemId().matches("\\d+") || ("".equals(toolsData.getItemId()))
				|| ("".equals(toolsData.getOrderingCode())) || !(toolsData.getOrderingCode() instanceof String)) {
			throw new MasterToolsException("Mandatory Fields are Present But Not in Proper Format", "109",
					HttpStatus.PRECONDITION_FAILED);

		} else if (!masterToolsRepository.getToolsMasterDataById(toolsData.getItemId()).isEmpty()) {
			throw new MasterToolsException("Data Already present, Insertion of Data Not Allowed", "110",
					HttpStatus.CONFLICT);

		} else {
			masterToolsRepository.addToolsMasterData(toolsData);
		}
	}

	public void editToolsMasterData(MasterTools toolsData) {
		if (toolsData.getItemId() == null || toolsData.getOrderingCode() == null) {
			throw new MasterToolsException("Mandatory Fields Not Present ", "115", HttpStatus.PRECONDITION_REQUIRED);

		} else if (!toolsData.getItemId().matches("\\d+") || ("".equals(toolsData.getItemId()))
				|| ("".equals(toolsData.getOrderingCode())) || !(toolsData.getOrderingCode() instanceof String)) {
			throw new MasterToolsException("Mandatory Fields are Present But Not in Proper Format", "116",
					HttpStatus.PRECONDITION_FAILED);

		} else {
			masterToolsRepository.editToolsMasterData(toolsData);
		}
	}

	public int deleteToolsMasterData(List<String> itemIdDeleted) {
		return masterToolsRepository.deleteToolsMasterData(itemIdDeleted);
	}

	public List<MasterTools> searchedData(List<String> columnsToSearch, String searchTerm, int offset, int limit,
			String orderby, String columnToSort) {
		return masterToolsRepository.searchedData(columnsToSearch, searchTerm, offset, limit, orderby, columnToSort);
	}

	public int getSearchedDataLength(List<String> columnsToSearch, String searchTerm) {
		return masterToolsRepository.getSearchedDataLength(columnsToSearch, searchTerm);
	}

	public int getStoredDataLength() {
		return masterToolsRepository.getStoredDataLength();

	}

	public List<MasterTools> searchToolsMasterData(int offSet, int limit, String orderBy, String columnToSort,
			String searchTerm) {
		return masterToolsRepository.searchedData(defaultColumnsForSorting, searchTerm, offSet, limit, orderBy,
				columnToSort);
	}

}
