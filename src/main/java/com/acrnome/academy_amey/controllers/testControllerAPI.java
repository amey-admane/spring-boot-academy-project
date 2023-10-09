package com.acrnome.academy_amey.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acrnome.academy_amey.dto.ToolsMasterDataResponseDTO;
import com.acrnome.academy_amey.internal.CountCheckTool;
import com.acrnome.academy_amey.services.MasterToolsService;

@RestController
@CrossOrigin(origins = "*")
public class testControllerAPI {
	
//	@Autowired
//	private CountCheckTool countCheckTool;
	
	@GetMapping(value = "/countCheck")
	public void startCountCheck() throws IOException{
		CountCheckTool.main(null);
	}

}
