package mvcapp.controller;

import mvcapp.service.CommentsServiceClass;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class BasicController {

	@Autowired
	private CommentsServiceClass service;

	@RequestMapping("/")
	public String index() {
		return "Greetings, please enter to /getComments.";
	}

	@RequestMapping("/getComments")
	public String getComments() {
		try {
			service.Write_Comments(service.Fetch_Comments("https://my-json-server.typicode.com/typicode/demo/comments"),"./comments_output.txt");
		} catch (IOException e) {
			System.out.println("Exception Raised service.Write_Comments");
			e.printStackTrace();
			return "Some IO Error Happened";
		}
		return "Comments Are Successfully ADDED!";
	}
}
