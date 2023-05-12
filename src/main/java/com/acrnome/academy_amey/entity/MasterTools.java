package com.acrnome.academy_amey.entity;

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
public class MasterTools {
	private String itemId;
	private String description;
	private String globalDescription;
	private String searchA;
	private String searchB;
	private String supplier;
	private String location;
	private String group;
	private String orderingCode;
	
}
