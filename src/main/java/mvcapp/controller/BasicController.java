package mvcapp.controller;

import mvcapp.model.Comments_List;
import mvcapp.service.ServiceClass;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BasicController {

	@Autowired
	private ServiceClass service;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping("/getComments")
	public String getComments() {
		Comments_List list = service.Fetch_Comments("https://my-json-server.typicode.com/typicode/demo/comments");
		try {
			service.Write_Comments(list);
		} catch (IOException e) {
			System.out.println("Exception Raised service.Write_Comments");
			e.printStackTrace();
		}
		return "Get Coments!";
	}
}
