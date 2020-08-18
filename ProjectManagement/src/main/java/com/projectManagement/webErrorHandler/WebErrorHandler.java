package com.projectManagement.webErrorHandler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebErrorHandler implements ErrorController {

	@RequestMapping("/error")
	public String handleError() {
		// do something like logging
		return "blank";
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
}
