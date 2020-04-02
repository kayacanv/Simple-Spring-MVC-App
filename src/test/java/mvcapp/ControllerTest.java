package mvcapp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import mvcapp.service.CommentsServiceClass;
import mvcapp.model.*;

import kong.unirest.json.JSONObject;
import kong.unirest.json.JSONArray;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

	@Autowired
	private CommentsServiceClass service;

	@Autowired
	private MockMvc mvc;

	@Autowired
	CommentsServiceClass CommentsServices;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Greetings, please enter to /getComments.")));
	}
	@Test
	public void getComments() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/getComments").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Comments Are Successfully ADDED!")));
	}

	@Test
	public void GetComments() throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("id", 0);
		jo.put("body", "test");
		jo.put("postId", 0);
		JSONArray ja = new JSONArray();
		ja.put(jo);
		Comments_List test= new Comments_List();
		test.addComment(0, "test", 0);

		Comments_List CommentsServicesRes= CommentsServices.JsonCommentsList(ja);

		ArrayList<String> List1 = CommentsServicesRes.getComments();
		ArrayList<String> List2 = test.getComments();
		assertEquals(List1.size(),List2.size());
		for(int i=0;i<List1.size();i++)
			assertEquals(List1.get(i),List2.get(i));
		  
	}
	@Test
	public void WriteComments1() throws Exception {
	
		Comments_List test= new Comments_List();
		test.addComment(0, "test", 0);
		CommentsServices.Write_Comments(test,"temporary_test.txt");
	
		File myObj = new File("temporary_test.txt");
        Scanner myReader = new Scanner(myObj);
		ArrayList<String>  items= new ArrayList<String>();
		while (myReader.hasNextLine()) {
     	    String data = myReader.nextLine();
			items = new ArrayList<String>(Arrays.asList(data.split(",")));
		}
		assertEquals("test",items.get(0), "first element is not; test");
		myReader.close();		
	}	
	@Test
	public void WriteComments10() throws Exception {
	
		Comments_List test= new Comments_List();
		for(int i=0;i<10;i++)
			test.addComment(i, "test"+i, i);
		CommentsServices.Write_Comments(test,"temporary_test.txt");
		File myObj = new File("temporary_test.txt");
        Scanner myReader = new Scanner(myObj);
		ArrayList<String>  items= new ArrayList<String>();
		while (myReader.hasNextLine()) {
     	    String data = myReader.nextLine();
			items = new ArrayList<String>(Arrays.asList(data.split(",")));
		}
		for(int i=0;i<items.size();i++)
			assertEquals("test"+i,items.get(i), "first element is not; test");
		myReader.close();		
	}
	@Test
	public void FetchData() throws Exception {	
		Comments_List test= new Comments_List();
		test.addComment(1, "some comment", 1);
		test.addComment(2, "some comment", 1);
		 
		Comments_List FetchedComments = service.Fetch_Comments("https://my-json-server.typicode.com/typicode/demo/comments");


		ArrayList<String> List1 = test.getComments();
		ArrayList<String> List2 = FetchedComments.getComments();
		
		assertEquals(List1.size(),List2.size());
		for(int i=0;i<List1.size();i++)
			assertEquals(List1.get(i),List2.get(i));

		ArrayList<Integer> ListId1 = test.getIds();
		ArrayList<Integer> ListId2 = FetchedComments.getIds();
		
		assertEquals(ListId1.size(),ListId2.size());
		for(int i=0;i<ListId1.size();i++)
			assertEquals(ListId1.get(i),ListId2.get(i));
	
	}
}
