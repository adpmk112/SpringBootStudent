package com.springboot.ace.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.ace.dto.RequestCourseDto;
import com.springboot.ace.dto.ResponseCourseDto;
import com.springboot.ace.dto.ResponseStudentDto;

@Repository
public class CourseDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private ResponseCourseDto mapRowToCourse(ResultSet row, int rowNum) throws SQLException{
		ResponseCourseDto responseCourseDto = new ResponseCourseDto();
		responseCourseDto.setId(row.getInt("id"));
		responseCourseDto.setName(row.getString("name"));
		return responseCourseDto;
	}
	
	private ResponseCourseDto mapRowToCourseForId(ResultSet row, int rowNum) throws SQLException{
		ResponseCourseDto responseCourseDto = new ResponseCourseDto();
		responseCourseDto.setId(row.getInt("id"));
		return responseCourseDto;
	}
	
	private ResponseCourseDto mapRowToCourseForName(ResultSet row, int rowNum) throws SQLException{
		ResponseCourseDto responseCourseDto = new ResponseCourseDto();
		responseCourseDto.setName(row.getString("name"));
		return responseCourseDto;
	}
	
	public void createCourse(RequestCourseDto requestCourseDto) {
		// TODO Auto-generated method stub
		String sql = "insert into `course` (`name`) values (?)";
		this.jdbcTemplate.update(sql,requestCourseDto.getName());
	}
	
	public List<ResponseCourseDto> selectAll() {
		// TODO Auto-generated method stub
		return this.jdbcTemplate.query("SELECT * FROM `course`", 
															this::mapRowToCourse);
	}
	
	public ResponseCourseDto selectById(RequestCourseDto requestCourseDto) {
		ResponseCourseDto responseCourseDto = new ResponseCourseDto();
		String sql = "select `name` from `course` where `id`=?";
		
		List<ResponseCourseDto> results =
				this.jdbcTemplate.query(sql, this::mapRowToCourseForName,requestCourseDto.getId());
		
		if(results.size()>0) {
		    	responseCourseDto = results.get(0);
		    }
		return responseCourseDto;
	}
	
	public ResponseCourseDto selectLastRow() {
		// TODO Auto-generated method stub
		ResponseCourseDto responseCourseDto = new ResponseCourseDto();
		String sql = "SELECT `id` FROM `course` ORDER BY `id` DESC LIMIT 1";
		List<ResponseCourseDto> lastId = this.jdbcTemplate.query(sql, this::mapRowToCourseForId);
		System.out.println(lastId.size());
		responseCourseDto = lastId.get(0);
		return responseCourseDto;
	}
}
