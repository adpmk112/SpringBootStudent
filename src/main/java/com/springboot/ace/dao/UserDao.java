package com.springboot.ace.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.ace.dto.RequestUserDto;
import com.springboot.ace.dto.ResponseUserDto;

@Repository
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unused")
	private ResponseUserDto mapRowToUser(ResultSet row, int rowNum) throws SQLException{
		ResponseUserDto responseUserDto = new ResponseUserDto();
		responseUserDto.setId(row.getInt("id"));
		responseUserDto.setEmail(row.getString("email"));
		responseUserDto.setPassword(row.getString("password"));
		return responseUserDto;
	}
	
	public void createUser(RequestUserDto requestUserDto) {
		// TODO Auto-generated method stub
		String sql = "insert into `user` (`id`,`email`,`password`) values (?,?,?)";
		this.jdbcTemplate.update(sql,
				requestUserDto.getId(),
				requestUserDto.getEmail(),
				requestUserDto.getPassword());
	}
	
	public void updateByUserId(RequestUserDto requestUserDto) {
		// TODO Auto-generated method stub
		String sql = "update `user` " + "set email=?,password=? where id=?";
		this.jdbcTemplate.update(sql,
				requestUserDto.getEmail(),
				requestUserDto.getPassword(),requestUserDto.getId());
	}

	public void deleteByUserId(RequestUserDto requestUserDto) {
		String sql = "delete from user where id=?";
		this.jdbcTemplate.update(sql,requestUserDto.getId());
	}

	public ResponseUserDto selectOneById(RequestUserDto requestUserDto) {
		
		ResponseUserDto responseUserDto = new ResponseUserDto();
		
		String sql = "select * from user where id=?";
		
		List<ResponseUserDto> results = this.jdbcTemplate.query(sql, 
				this::mapRowToUser,requestUserDto.getId());
		
		if(results.size()>0) {
			responseUserDto = results.get(0);
		}
		
		return responseUserDto;
	}

	public ResponseUserDto selectOneByEmail(RequestUserDto requestUserDto) {
		
		ResponseUserDto responseUserDto = new ResponseUserDto();
		
		String sql = "select * from user where email=?";
		
		List<ResponseUserDto> results = this.jdbcTemplate.query(sql, 
				this::mapRowToUser,requestUserDto.getEmail());
		
		if(results.size()>0) {
			responseUserDto = results.get(0);
		}
		return responseUserDto;
	}

	public List<ResponseUserDto> selectAll() {
		String sql = "select * from user";
		return this.jdbcTemplate.query(sql, this::mapRowToUser);
	}
}
