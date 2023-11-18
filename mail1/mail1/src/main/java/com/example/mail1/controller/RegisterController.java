package com.example.mail1.controller;

import java.io.UnsupportedEncodingException;



import javax.mail.MessagingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mail1.model.Register;
import com.example.mail1.service.RegisterService;

@Controller
public class RegisterController {
@Autowired
RegisterService regService;


public void setRegService(RegisterService regService) {
	this.regService = regService;
}


@GetMapping("register")
public String register (Model model) {
	model.addAttribute("register", new Register());
	return "form";
	
}
@PostMapping("send")
public String send(Register reg, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
	regService.register(reg,getsiteURL(request));
	return "success";
	
}
private String getsiteURL(HttpServletRequest request) {
    String siteURL = request.getRequestURL().toString();
    return siteURL.replace(request.getServletPath(), "");
}

@GetMapping("verify")
public String verify (@Param ("code")String code) {
	if(regService.verify(code)) {
		return "success";
	}else {
		return "fail";
	}
}
}
