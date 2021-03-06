package com.springboot.ace.dto;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("requestStudentCourseDto")
public class RequestStudentCourseDto {
	private int studentId, courseId;
	private String studentName, birth, gender, phone, education, courseName;
}
