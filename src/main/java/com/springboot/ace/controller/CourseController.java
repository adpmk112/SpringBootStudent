package com.springboot.ace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ace.dao.CourseDao;
import com.springboot.ace.dto.RequestCourseDto;
import com.springboot.ace.dto.ResponseCourseDto;
import com.springboot.ace.model.CourseBean;

@Controller
@RequestMapping("/course")
public class CourseController {
	  
	int courseId = 0;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private RequestCourseDto requestCourseDto;
	@Autowired
	private ResponseCourseDto responseCourseDto;
	
	@GetMapping("/view")
	public ModelAndView courseView(Model model) {
		CourseBean courseBean = new CourseBean();
		responseCourseDto = courseDao.selectLastRow();
		courseBean.setId(responseCourseDto.getId()+1);
		return new ModelAndView("courseRegister","courseBean",courseBean);
	}
	
	@PostMapping("/add")
	public String addCourse(CourseBean courseBean) {
		requestCourseDto.setName(courseBean.getName());
		courseDao.createCourse(requestCourseDto);
		return "redirect:view";
	}
}
