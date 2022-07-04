package com.springboot.ace.controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.ace.dao.UserDao;
import com.springboot.ace.dto.RequestUserDto;
import com.springboot.ace.dto.ResponseUserDto;
import com.springboot.ace.model.UserBean;

@Controller
@RequestMapping("/user")
public class UserController {

	List<ResponseUserDto> resUserDtoList = new ArrayList<>();
	
	String confirmPassword;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RequestUserDto requestUserDto;
	@Autowired
	private ResponseUserDto responseUserDto;
	
	@GetMapping("/view")
	public String userView(UserBean user,Model model) {
		resUserDtoList = userDao.selectAll();
		model.addAttribute("resUserDtoList", resUserDtoList);
		return "userView";
	}
	
	@GetMapping("/register")
	public String registerView() {
		return "userRegister";
	}
	
	@PostMapping("/add")
	public String addUser(UserBean userBean, ModelMap model) {
		requestUserDto.setEmail(userBean.getEmail());
		requestUserDto.setPassword(userBean.getPassword());
		confirmPassword = userBean.getConfirmPassword();
		
		if(requestUserDto.getPassword().equals(confirmPassword)) {
			userDao.createUser(requestUserDto);
			return "redirect:userView";
		}
		else {
			model.addAttribute("error","Something is wrong in email and password.");
			return "userRegister";
		}
	}
	
	@GetMapping("/updateView/{updateId}")
	public ModelAndView fetchUserToUpdate(@PathVariable("updateId") String updateId, Model model) {
		requestUserDto.setId(Integer.valueOf(updateId));
		responseUserDto = userDao.selectOneById(requestUserDto);
		model.addAttribute("fetchedUserData",responseUserDto);
		return new ModelAndView("userUpdate","userUpdateData",new UserBean());
	}
	
	@PostMapping("/update")
	public String updateUser(UserBean userBean, ModelMap model,
												HttpSession session){
		
		requestUserDto.setId(Integer.valueOf(userBean.getId()));
		requestUserDto.setEmail(userBean.getEmail());
		requestUserDto.setPassword(userBean.getPassword());
		userDao.updateByUserId(requestUserDto);
		return "redirect:userView";
	}
	
	@GetMapping("/delete/{deleteId}")
	public String deleteUser(@PathVariable("deleteId")String deleteId) {
		requestUserDto.setId(Integer.valueOf(deleteId));
		userDao.deleteByUserId(requestUserDto);
		return "redirect:userView";
	}
	
	@GetMapping("/search")
	public String searchUser(UserBean userBean, ModelMap model) {
		ResponseUserDto responseUserDto = null;
		if(userBean.getId()!="") {
			requestUserDto.setId(Integer.valueOf(userBean.getId()));
			responseUserDto = userDao.selectOneById(requestUserDto);
			model.addAttribute("searchedUserDto", responseUserDto);
			}
		
		else if(userBean.getEmail()!=""){
			requestUserDto.setEmail(userBean.getEmail());
			responseUserDto = userDao.selectOneByEmail(requestUserDto);
			model.addAttribute("searchedUserDto", responseUserDto);
			}
		
		else {
			model.addAttribute("searchNull", "Fill the blank to search");
		}
		return "userSearched";
	}
}
