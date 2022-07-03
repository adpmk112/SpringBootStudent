package com.springboot.ace.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.ace.dto.RequestCourseDto;
import com.springboot.ace.dto.RequestStudentCourseDto;
import com.springboot.ace.dto.RequestStudentDto;
import com.springboot.ace.dto.ResponseStudentCourseDto;

@Repository
public class StudentCourseDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unused")
	private ResponseStudentCourseDto mapRowToStudentCourse(ResultSet row, int rowNum) throws SQLException{
		ResponseStudentCourseDto responseStudentCourseDto = new ResponseStudentCourseDto();
		responseStudentCourseDto.setStudentId(row.getLong("student_id"));
		responseStudentCourseDto.setCourseId(row.getLong("course_id"));
		responseStudentCourseDto.setStudentName(row.getString("student_name"));
		responseStudentCourseDto.setBirth(row.getString("birth"));
		responseStudentCourseDto.setGender(row.getString("gender"));
		responseStudentCourseDto.setEducation(row.getString("education"));
		responseStudentCourseDto.setCourseName(row.getString("course_name"));
		return responseStudentCourseDto;
	}	
	
	public void createStudent_course(RequestStudentDto requestStudentDto, RequestCourseDto requestCourseDto) {
		String sql = "insert into student_course (student_id, course_id) values(?,?)";
		this.jdbcTemplate.update(sql,requestStudentDto.getId(),requestCourseDto.getId());
	}

	public List<ResponseStudentCourseDto> selectOneByStudentId(RequestStudentCourseDto requestStudentCourseDto) {
		String sql = "select s.`id` AS `student_id`, s.`name` AS `student_name`, s.`birth`, s.`gender`, "
				+ "s.`phone`, s.`education`, c.`id` AS `course_id`, c.`name` AS `course_name` from `student` "
				+ "s join `student_course` sc join `course` c on s.`id`=sc.`student_id` and c.id=sc.`course_id` "
				+ "where s.`id`=?";
		
		List<ResponseStudentCourseDto> results = this.jdbcTemplate.query(sql, 
				this::mapRowToStudentCourse,
				requestStudentCourseDto.getStudentId());
		
		return results;
	}

	public List<ResponseStudentCourseDto> selectAllStudentwithCourseName() {

		String sql = "select s.`id` AS `student_id` ,s.`name` AS `student_name`,"
				+ "s.`birth`, s.`gender`, s.`phone`, s.`education`,"
				+ "c.`id` AS `course_id`,  c.`name` AS `course_name` from "
				+ "`student` s join `student_course` sc join `course` c on"
				+ " s.`id`=sc.`student_id` and c.`id`=sc.`course_id`;";
		
		return this.jdbcTemplate.query(sql, 
				this::mapRowToStudentCourse);
	}
}
