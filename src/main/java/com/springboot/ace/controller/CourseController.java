package com.springboot.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.ace.dao.CourseDao;
import com.springboot.ace.dto.RequestCourseDto;
import com.springboot.ace.dto.ResponseCourseDto;
import com.springboot.ace.model.CourseBean;

@Controller
@RequestMapping("/course")
public class CourseController {
	
	 Long courseId = (long) 0;
	  
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private RequestCourseDto requestCourseDto;
	@Autowired
	private ResponseCourseDto responseCourseDto;
	
	@GetMapping("/view")
	public String courseView(Model model) {
		responseCourseDto = courseDao.selectLastRow();
		courseId = responseCourseDto.getId()+1;
		model.addAttribute("courseId",courseId);
		return "courseRegister";
	}
	
	@PostMapping("/add")
	public String addCourse(CourseBean courseBean) {
		responseCourseDto = courseDao.selectLastRow();
		courseId = responseCourseDto.getId()+1;
		
		requestCourseDto.setName(courseBean.getName());
		courseDao.createCourse(requestCourseDto);
		return "redirect:view";
	}
}
