package com.springboot.ace.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ace.dto.ResponseCourseDto;
import com.springboot.ace.dao.CourseDao;
import com.springboot.ace.dao.StudentCourseDao;
import com.springboot.ace.dao.StudentDao;
import com.springboot.ace.dto.RequestCourseDto;
import com.springboot.ace.dto.RequestStudentCourseDto;
import com.springboot.ace.dto.RequestStudentDto;
import com.springboot.ace.dto.ResponseStudentCourseDto;
import com.springboot.ace.dto.ResponseStudentDto;
import com.springboot.ace.model.StudentBean;
import com.springboot.ace.model.StudentCourseBean;

@Controller
public class StudentController {
		Long studId;
		@Autowired
		private StudentDao studentDao;
		@Autowired
		private CourseDao courseDao;
		@Autowired
		private StudentCourseDao studentCourseDao;
		@Autowired
		private RequestStudentDto requestStudentDto;
		@Autowired
		private ResponseStudentDto responseStudentDto;
		@Autowired
		private RequestCourseDto requestCourseDto;
		@Autowired
		private RequestStudentCourseDto requestStudentCourseDto;
		
		@GetMapping("/studentRegisterView")
		public String studentRegisterView(ModelMap model) {
			List<ResponseCourseDto>courseList = courseDao.selectAll();
			model.addAttribute("courseList", courseList);
			responseStudentDto = studentDao.selectLastRow();
			studId = responseStudentDto.getId()+1;
			model.addAttribute("studId", "STU-"+studId);
			model.addAttribute("studentRegisterData",new StudentBean());
			return "studentRegister";
		}
		
		@PostMapping("/addStudent")
		public String addStudent(StudentBean studentBean,
													ModelMap model,HttpServletRequest request) {
			responseStudentDto = studentDao.selectLastRow();
			studId = responseStudentDto.getId()+1;
			requestStudentDto.setId(studId);
			requestStudentDto.setName(studentBean.getName());
			requestStudentDto.setBirth(studentBean.getBirth());
			requestStudentDto.setGender(studentBean.getGender());
			requestStudentDto.setPhone(studentBean.getPhone());
			requestStudentDto.setEducation(studentBean.getEducation());
			studentDao.createStudent(requestStudentDto);
			String[]attend = request.getParameterValues("course");
			for(int i=0;i<attend.length;i++) {
				requestCourseDto.setId(attend[i]);
				studentCourseDao.createStudent_course(requestStudentDto, requestCourseDto);
			}
			return "redirect:studentView";	
		}
		
		@GetMapping("/deleteStudent/{deleteId}")
		public String deleteStudent(@PathVariable("deleteId")String deleteId) {
				requestStudentDto.setId(Long.valueOf(deleteId));
				studentDao.deleteByStudentId(requestStudentDto);
				return "redirect:studentView";
		}
		
		@GetMapping("/studentView")
		public ModelAndView viewStudent() {
			List<ResponseStudentCourseDto>resStudCourseDtoList = 
					studentCourseDao.selectAllStudentwithCourseName();
			return new ModelAndView("studentView","studWithCourse",resStudCourseDtoList);
		}
		
		@GetMapping("/fetchStudent/{fetchId}")
		public String fetchStudent(@PathVariable("fetchId")String fetchId,ModelMap model,
									@ModelAttribute("studentUpdateData")StudentCourseBean studentCourseBean) {
			requestStudentCourseDto.setStudentId(Integer.valueOf(fetchId));
			
		/*	studentCourseBean.setCourseId(responseStudentCourseDto.getCourseId());
			studentCourseBean.setCourseName(responseStudentCourseDto.getCourseName());
			studentCourseBean.setStudentId(responseStudentCourseDto.getStudentId());
			studentCourseBean.setStudentName(responseStudentCourseDto.getStudentName());
			studentCourseBean.setBirth(responseStudentCourseDto.getBirth());
			studentCourseBean.setGender(responseStudentCourseDto.getGender());
			studentCourseBean.setPhone(responseStudentCourseDto.getPhone());
			studentCourseBean.setEducation(responseStudentCourseDto.getEducation()); */
			
			return "studentUpdate";
		}
		
		
}
