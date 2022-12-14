package com.pension.entity;

import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter
@Setter
@AllArgsConstructor
public class ExceptionModel {
	private String message;
	private String solution;
	private String timeStamp;
	private boolean error;
}
