package com.springboot.ace.dto;

import org.springframework.stereotype.Service;
import lombok.Getter;
import lombok.Setter;

@Service("requestCourseDto")
@Getter
@Setter
public class RequestCourseDto {
	String id;
	String name;
}
