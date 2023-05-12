package com.acrnome.academy_amey.dto;

import java.util.List;


import com.acrnome.academy_amey.entity.MasterTools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ToolsMasterDataResponseDTO {
	List<MasterTools> dataList = null;
	MasterTools data = null;
	String metaData = null;
	
}
