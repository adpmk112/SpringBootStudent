package com.springboot.ace.dto;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("responseCourseDto")
public class ResponseCourseDto {
	Long id;
	String name;
}
