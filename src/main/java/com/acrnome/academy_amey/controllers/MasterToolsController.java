package com.acrnome.academy_amey.controllers;

import java.util.ArrayList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.acrnome.academy_amey.dto.SearchingDTO;
import com.acrnome.academy_amey.dto.ToolsMasterDataResponseDTO;
import com.acrnome.academy_amey.entity.MasterTools;
import com.acrnome.academy_amey.exceptions.MasterToolsException;
import com.acrnome.academy_amey.services.MasterToolsService;




@RestController
@CrossOrigin(origins = "*")
public class MasterToolsController {

	@Autowired
	private MasterToolsService masterToolsService;

//	public String getUsername() {
//		SecurityContext context = SecurityContextHolder.getContext();
//		Authentication authentication = context.getAuthentication();
//
//		return authentication.getName();
//
//	}

	@PostMapping(value = "/masterTools", consumes = { "multipart/form-data" })
	public ResponseEntity<ToolsMasterDataResponseDTO> uploadExcelDataForTools(
			@RequestParam("file") MultipartFile file) {

		var dataRowNotDeleted = masterToolsService.uploadExcelDataForTools(file);

		ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(
				masterToolsService.getToolsMasterData(0, 0, "ASC","itemid"), null, dataRowNotDeleted.toString());

		return ResponseEntity.status(HttpStatus.OK).body(returningObj);

	}

	@GetMapping(value = "/masterTools")
	public ResponseEntity<ToolsMasterDataResponseDTO> getToolsMasterData(
			@RequestParam(name = "offset", defaultValue = "1") @Valid int offSet,
			@RequestParam(name = "limit", defaultValue = "10") @Valid int limit,
			@RequestParam(name = "orderBy", defaultValue = "ASC") @Valid String orderBy,
			@RequestParam(name = "column", defaultValue = "itemid") @Valid String columnToSort,
			@RequestParam(name = "search", defaultValue = "") @Valid String searchTerm) {
		
		if(!("ASC".equals(orderBy) || "DESC".equals(orderBy))) {
			throw new MasterToolsException("Ordering code is Incorrect ", "119", HttpStatus.PRECONDITION_FAILED);	
		}
		if(!(offSet>0 && limit>2)) {
			throw new MasterToolsException("Offset or Limit is Incorrect ", "120", HttpStatus.PRECONDITION_FAILED);	
			
		}
		List<MasterTools> dataReturend = null;
//		search on all columns
		if((!searchTerm.equals("")) && searchTerm.length()>3) {
			System.out.print("Entered for search "+searchTerm);
			dataReturend = masterToolsService.searchToolsMasterData(offSet, limit, orderBy,columnToSort,searchTerm);
		}else {
			dataReturend = masterToolsService.getToolsMasterData(offSet, limit, orderBy,columnToSort);
			if (dataReturend.isEmpty()) {
				throw new MasterToolsException("Data Not Present in Database ", "106", HttpStatus.NOT_FOUND);
			}
		}
		ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(dataReturend, null, String.valueOf(getStoredDataLength()));

		return ResponseEntity.status(HttpStatus.OK).body(returningObj);
	}

	@GetMapping(value = "/masterTools/{masterToolId}")
	public ResponseEntity<ToolsMasterDataResponseDTO> getToolsMasterDataById(
			@PathVariable(name = "masterToolId") String masterToolId) {
		if (!masterToolId.matches("\\d+")) {
			throw new MasterToolsException("Item Id is invalid  , not in proper format", "107",
					HttpStatus.NOT_ACCEPTABLE);

		}
		var dataReturend = masterToolsService.getToolsMasterDataById(masterToolId);
		if (dataReturend.isEmpty()) {
			throw new MasterToolsException("Data For this Item Id is not present in Database", "108",
					HttpStatus.NOT_FOUND);
		}
		ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(null, dataReturend.get(0), null);

		return ResponseEntity.status(HttpStatus.OK).body(returningObj);
	}

	@PostMapping(value = "/masterTools")
	public ResponseEntity<ToolsMasterDataResponseDTO> addToolsMasterData(@RequestBody MasterTools toolsData) {

		masterToolsService.addToolsMasterData(toolsData);

		return getToolsMasterDataById(toolsData.getItemId());
	}

	@PutMapping(value = "/masterTools/{masterToolId}")
	public ResponseEntity<ToolsMasterDataResponseDTO> editToolsMasterData(
			@PathVariable(name = "masterToolId") String masterToolId, @RequestBody MasterTools toolsData) {
		if (!masterToolId.matches("\\d+")) {
			throw new MasterToolsException("Item Id is invalid  , not in proper format", "117",
					HttpStatus.NOT_ACCEPTABLE);

		}
		if (!masterToolId.equals(toolsData.getItemId())) {
			throw new MasterToolsException("Item Id in Header and Object Does Not matches, Operation Canceled", "118",
					HttpStatus.CONFLICT);
		}
		masterToolsService.editToolsMasterData(toolsData);
		return getToolsMasterDataById(masterToolId);
	}

	@DeleteMapping(value = "/masterTools")
	public ResponseEntity<ToolsMasterDataResponseDTO> deleteToolsMasterData(@RequestBody List<String> itemIdDeleted) {

		if (itemIdDeleted.isEmpty()) {
			throw new MasterToolsException("Tools Data List is Empty ,Deletion Operation Cancelled.", "111",
					HttpStatus.UNPROCESSABLE_ENTITY);
		}

		List<String> rowsNotDeleted = new ArrayList<>();
		List<String> filteredIds = new ArrayList<>();
		for (int i = 0; i < itemIdDeleted.size(); i++) {
			if (itemIdDeleted.get(i).matches("\\d+")) {
				var dataInDatabase = masterToolsService.getToolsMasterDataById(itemIdDeleted.get(i));
				if (dataInDatabase.isEmpty()) {
					rowsNotDeleted.add(itemIdDeleted.get(i));
				} else {
					filteredIds.add(itemIdDeleted.get(i));
				}
			} else {
				rowsNotDeleted.add(itemIdDeleted.get(i));
			}
		}

		if (filteredIds.isEmpty()) {
			throw new MasterToolsException(
					"Tools itemIds passed are not valid,Error Integer Value is Only Accepted and Should be present in Database",
					"112", HttpStatus.UNPROCESSABLE_ENTITY);

		} else {

			int rowsDeleted = masterToolsService.deleteToolsMasterData(filteredIds);

			if (rowsNotDeleted.isEmpty() && rowsDeleted == itemIdDeleted.size()) {
				ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(null, null,
						String.valueOf(rowsDeleted));

				return ResponseEntity.status(HttpStatus.OK).body(returningObj);

			} else if (rowsDeleted == 0) {
				throw new MasterToolsException(
						"Tools data Deletetion UnSuccessfully as ItemId in List are not present in the Database  ",
						"113", HttpStatus.NOT_FOUND);

			} else {

				ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(null, null,
						String.valueOf(rowsNotDeleted));

				return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(returningObj);

			}
		}
	}

//	search on particular columns
	@PostMapping(value = "/masterTools/search")
	public ResponseEntity<ToolsMasterDataResponseDTO> searchedData(@RequestBody SearchingDTO searchingDto,
			@RequestParam(name = "offset", defaultValue = "1") @Valid  int offSet,
			@RequestParam(name = "limit", defaultValue = "10") @Valid  int limit,
			@RequestParam(name = "orderBy", defaultValue = "ASC") @Valid String orderBy,
			@RequestParam(name = "column", defaultValue = "itemid") @Valid String columnToSort) {
		if (searchingDto.getColumnsToSearch().isEmpty()) {
			throw new MasterToolsException("Columns List is Empty, Data required for searching", "123", HttpStatus.BAD_REQUEST);
		}
		if(!("ASC".equals(orderBy) || "DESC".equals(orderBy))) {
			throw new MasterToolsException("Ordering code is Incorrect ..", "121", HttpStatus.PRECONDITION_FAILED);	
		}
		if(!(offSet>0 && limit>2)) {
			throw new MasterToolsException("Offset or Limit is Incorrect ..", "122", HttpStatus.PRECONDITION_FAILED);	
			
		}

		ToolsMasterDataResponseDTO returningObj = new ToolsMasterDataResponseDTO(
				masterToolsService.searchedData(searchingDto.getColumnsToSearch(), searchingDto.getSearchTerm(),
						offSet,limit,orderBy,columnToSort), null,
				String.valueOf( getSearchedDataLength(searchingDto)));

		return ResponseEntity.status(HttpStatus.OK).body(returningObj);
	}

	public int  getSearchedDataLength(SearchingDTO searchingDto) {
		return masterToolsService.getSearchedDataLength(searchingDto.getColumnsToSearch(), searchingDto.getSearchTerm());
	}

	public int getStoredDataLength() {
		return masterToolsService.getStoredDataLength();
	}
}
