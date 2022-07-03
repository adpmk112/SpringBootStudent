package com.springboot.ace.dto;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service("responseUserDto")
public class ResponseUserDto {
	private Long id;
	private String email;
	private String password;
}
