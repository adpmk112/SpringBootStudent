package com.springboot.ace.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class CourseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long id;
	String name;
}
