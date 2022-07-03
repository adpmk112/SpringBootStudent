package com.springboot.ace.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String email;
	
	private String password;
	
	private String confirmPassword;

}
