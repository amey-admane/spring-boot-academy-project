package com.acrnome.academy_amey.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DTO for search request payloads
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchingDTO {

	// Column names to search within
	private List<String> columnsToSearch;
	
	// Term to search for
	private String searchTerm;
}
