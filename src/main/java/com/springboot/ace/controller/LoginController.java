package com.springboot.ace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.ace.dto.RequestUserDto;
import com.springboot.ace.dto.ResponseUserDto;
import com.springboot.ace.dao.UserDao;
import com.springboot.ace.model.UserBean;

@Controller
public class LoginController {
	    
	   @Autowired
	   private UserDao userDao;
	   @Autowired
	   private RequestUserDto requestUserDto;
	   @Autowired
	   private ResponseUserDto responseUserDto;
	   
	   @GetMapping("/login")
	   public String loginView() {
		   return "login";
	   }
	   
	   @PostMapping("/login")
	   public String login(UserBean userBean, HttpSession session,ModelMap model) {

		   requestUserDto.setEmail(userBean.getEmail());
		   responseUserDto = userDao.selectOneByEmail(requestUserDto);

			if (userBean.getEmail().equals(responseUserDto.getEmail())
					&& userBean.getPassword().equals(responseUserDto.getPassword())) {
				session.setAttribute("userSession", responseUserDto);
				return "menu";
			} 
			
			else {
				model.addAttribute("error","The account name or password that you have entered is incorrect");
				return "login";
			}
	   }
	   
	   @GetMapping("/logOut")
	   public String logout(ModelMap model,HttpSession session) {
		   session.removeAttribute("userSession");
		   session.invalidate();
		   return "redirect:/login";
	   }
	   
		@RequestMapping(value="/menu",method= RequestMethod.GET)
		public String menu() {
			return "menu";
		}
}
