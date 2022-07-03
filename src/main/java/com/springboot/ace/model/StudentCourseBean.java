package com.springboot.ace.model;

import lombok.Data;

@Data
public class StudentCourseBean {
		private Long studentId, courseId;
		private String studentName, birth, gender, phone, education, courseName;
}
