package com.springboot.ace.dto;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("requestStudentDto")
public class RequestStudentDto {
	
	private Long id;
	private String name, birth, gender, phone, education;
}
