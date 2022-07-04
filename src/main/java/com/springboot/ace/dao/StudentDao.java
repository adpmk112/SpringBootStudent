package com.springboot.ace.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.ace.dto.RequestStudentDto;
import com.springboot.ace.dto.ResponseStudentDto;

@Repository
public class StudentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unused")
	private ResponseStudentDto mapRowToStudent(ResultSet row, int rowNum) throws SQLException{
		ResponseStudentDto responseStudentDto = new ResponseStudentDto();
		responseStudentDto.setId(row.getInt("id"));
		responseStudentDto.setName(row.getString("name"));
		responseStudentDto.setBirth(row.getString("birth"));
		responseStudentDto.setGender(row.getString("gender"));
		responseStudentDto.setPhone(row.getString("phone"));
		responseStudentDto.setEducation(row.getString("education"));
		return responseStudentDto;
	}
	
	@SuppressWarnings("unused")
	private ResponseStudentDto mapRowToStudentId(ResultSet row, int rowNum) throws SQLException{
		ResponseStudentDto responseStudentDto = new ResponseStudentDto();
		responseStudentDto.setId(row.getInt("id"));
		return responseStudentDto;
	}
	
	public void createStudent(RequestStudentDto requestStudentDto) {
		String sql = "insert into `student` "
				+ "(`id`,`name`,`birth`,`gender`,`phone`,`education`) "
				+ "values (?,?,?,?,?,?)";
		
		this.jdbcTemplate.update(sql,
				requestStudentDto.getId(),
				requestStudentDto.getName(),
				requestStudentDto.getBirth(),
				requestStudentDto.getGender(),
				requestStudentDto.getPhone(),
				requestStudentDto.getEducation());
	}
	
	public void updateByStudentId(RequestStudentDto requestStudentDto) {
		String sql = "update `student` set name=?,birth=?,gender=?,"
				+ "phone=?,education=? where id=?";
		
		this.jdbcTemplate.update(sql,
				requestStudentDto.getName(),
				requestStudentDto.getBirth(),
				requestStudentDto.getGender(),
				requestStudentDto.getPhone(),
				requestStudentDto.getEducation(),
				requestStudentDto.getId());
	}
	
	public void deleteByStudentId(RequestStudentDto requestStudentDto) {
		String sql = "delete from student where id=?";
		this.jdbcTemplate.update(sql,requestStudentDto.getId());
	}
	
	public ResponseStudentDto selectOneById(RequestStudentDto requestStudentDto) {
	    
		ResponseStudentDto responseStudentDto = new ResponseStudentDto();
		
		String sql = "select * from student where id=?";
	    
	    List<ResponseStudentDto> results = this.jdbcTemplate.query(sql, 
				this::mapRowToStudent,requestStudentDto.getId());
		
	    if(results.size()>0) {
	    	responseStudentDto = results.get(0);
	    }
		return responseStudentDto;
	}
	
	public List<ResponseStudentDto> selectOneByName(RequestStudentDto requestStudentDto) {
	    String sql = "select * from student where name=?";
	    
	    List<ResponseStudentDto> results = this.jdbcTemplate.query(sql, 
				this::mapRowToStudent,requestStudentDto.getName());
		
		return results;
	}
	
	public List<ResponseStudentDto> selectAll() {
	    
	    String sql = "select * from student";
	    return this.jdbcTemplate.query(sql, this::mapRowToStudent);
	}
	
	public ResponseStudentDto selectLastRow() {
		ResponseStudentDto responseStudentDto = new ResponseStudentDto();
		
		String sql = "SELECT `id` FROM `student` ORDER BY `id` DESC LIMIT 1";
		
		List<ResponseStudentDto> results = this.jdbcTemplate.query(sql, this::mapRowToStudentId);
		
		if(results.size()>0) {
			responseStudentDto = results.get(0);
		}
		return responseStudentDto;
	}
}
