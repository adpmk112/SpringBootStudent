package com.springboot.ace.controller;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.springboot.ace.dto.ResponseUserDto;
import com.springboot.ace.model.StudentBean;
import com.springboot.ace.model.StudentCourseBean;
import com.springboot.ace.model.UserBean;

@Controller
public class StudentController {
		Integer studId;
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
		@Autowired
		private ResponseStudentCourseDto responseStudentCourseDto;
		
		@GetMapping("/studentRegisterView")
		public String studentRegisterView(ModelMap model) {
			List<ResponseCourseDto>courseList = courseDao.selectAll();
			model.addAttribute("courseList", courseList);
			
			responseStudentDto = studentDao.selectLastRow();
			studId = responseStudentDto.getId()+1;
			StudentBean studentBean = new StudentBean();
			studentBean.setId(studId);
			model.addAttribute("studId", "STU-"+studId);
			model.addAttribute("studentBean",studentBean);
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
			for(int i=0;i<studentBean.getAttend().length;i++) {
				requestCourseDto.setId(Integer.valueOf(studentBean.getAttend()[i]));
				studentCourseDao.createStudent_course(requestStudentDto, requestCourseDto);
			}
			return "redirect:studentView";	
		}
		
		@PostMapping("/studentUpdate")
		public String updateStudent(@ModelAttribute("studentBean") StudentBean studentBean, 
				ModelMap model) {
			requestStudentDto.setId(Integer.valueOf(studentBean.getId()));
			requestStudentDto.setName(studentBean.getName());
			requestStudentDto.setBirth(studentBean.getBirth());
			requestStudentDto.setGender(studentBean.getGender());
			requestStudentDto.setPhone(studentBean.getPhone());
			requestStudentDto.setEducation(studentBean.getEducation());			
			studentDao.updateByStudentId(requestStudentDto);
			
			for(int i=0;i<studentBean.getAttend().length;i++) {
				requestCourseDto.setId(Integer.valueOf(studentBean.getAttend()[i]));
				studentCourseDao.deleteStudentCourseByCourseId(requestCourseDto);
				studentCourseDao.createStudent_course(requestStudentDto, requestCourseDto);
			}
			
			return "redirect:studentView";	
		}
		
		@GetMapping("/deleteStudent/{deleteId}")
		public String deleteStudent(@PathVariable("deleteId")String deleteId) {
				requestStudentDto.setId(Integer.valueOf(deleteId));
				studentDao.deleteByStudentId(requestStudentDto);
				return "redirect:/studentView";
		}
		
		@GetMapping("/studentView")
		public ModelAndView viewStudent() {
			List<ResponseStudentCourseDto>resStudCourseDtoList = 
					studentCourseDao.selectAllStudentwithCourseName();
			return new ModelAndView("studentView","studWithCourse",resStudCourseDtoList);
		}
		
		@GetMapping("/fetchStudentView/{fetchId}")
		public ModelAndView fetchStudent(@PathVariable("fetchId")String fetchId,ModelMap model) {
			requestStudentCourseDto.setStudentId(Integer.valueOf(fetchId));
			
			StudentBean studentBean = new StudentBean();
			List<ResponseStudentCourseDto> responseStudentCourseDtoList =studentCourseDao
																			.selectOneById(requestStudentCourseDto);
			
			List<ResponseStudentCourseDto>attendCourseList = new ArrayList<>(); 
			
			Iterator<ResponseStudentCourseDto>it = responseStudentCourseDtoList.iterator();
	    	while(it.hasNext()) {
	    		 responseStudentCourseDto = it.next();
	    		studentBean.setId(responseStudentCourseDto.getStudentId());
	 			studentBean.setName(responseStudentCourseDto.getStudentName());
	 			studentBean.setBirth(responseStudentCourseDto.getBirth());
	 			studentBean.setGender(responseStudentCourseDto.getGender());
	 			studentBean.setPhone(responseStudentCourseDto.getPhone());
	 			studentBean.setEducation(responseStudentCourseDto.getEducation());
	 			
	 			attendCourseList.add(responseStudentCourseDto);
	    	}
	    	
	    	String[]attendCourse = new String[50];
	    	
	    	for(int i=0;i<attendCourseList.size();i++) {
				requestCourseDto.setId(attendCourseList.get(i).getCourseId());
				attendCourse[i] = courseDao.selectById(requestCourseDto).getName();
			}
	    	
	    	studentBean.setAttend(attendCourse);
	    	
			List<ResponseCourseDto> courseList = courseDao.selectAll();
			model.addAttribute("courseList",courseList);
			return new ModelAndView("studentUpdate","studentBean",studentBean);
		}
		
		@GetMapping("/searchStudentId")
		public String searchUser(@RequestParam("studentId") String id, ModelMap model) {
			if(id!="") {
				requestStudentCourseDto.setStudentId(Integer.valueOf(id));
				List<ResponseStudentCourseDto>responseStudentCourseDtoList = 
						studentCourseDao.selectOneById(requestStudentCourseDto);
				model.addAttribute("searchedStudentDto", responseStudentCourseDtoList);
				}
			
			else {
				model.addAttribute("error", "Fill the blank to search");
			}
			return"studentSearched";
		}
}
