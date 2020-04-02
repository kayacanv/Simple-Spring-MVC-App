package mvcapp.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


import org.springframework.stereotype.Service;

import mvcapp.model.Comments_List;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
@Service
public class ServiceClass {

	public Comments_List Fetch_Comments(String url){
		System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEE");

		System.out.println(url);
		Comments_List res = new Comments_List();
		HttpResponse<JsonNode> request = Unirest.get(url).asJson();
		System.out.println(request.getBody().toString());
		for(int i=0;i<request.getBody().getArray().length();i++)
		{
			JSONObject it= (JSONObject)request.getBody().getArray().get(i);
			System.out.println(it);
			res.addComment(it.getInt("id"),it.getString("body"),it.getInt("postId"));
		}
		return res;
	}
	public int Write_Comments(Comments_List List,String filePath)
		throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		int first=0;
		for (String it: List.getComments()) {
			if(first==1)
				writer.append(',');
			first=1;
			writer.write(it);
		}
		writer.close();
		return 0;
	}
}
