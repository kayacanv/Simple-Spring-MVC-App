package mvcapp.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import org.springframework.stereotype.Service;

import mvcapp.model.Comments_List;

import kong.unirest.*;
import kong.unirest.json.JSONObject;
import kong.unirest.json.JSONArray;
@Service
public class CommentsServiceClass {


	public Comments_List JsonCommentsList(JSONArray jsonResponse){
		Comments_List res = new Comments_List();
		for(int i=0;i<jsonResponse.length();i++)
		{
			JSONObject it= (JSONObject)jsonResponse.get(i);
			System.out.println(it);
			res.addComment(it.getInt("id"),it.getString("body"),it.getInt("postId"));
		}
		return res;
	}

	public Comments_List Fetch_Comments(String url){
		HttpResponse<JsonNode> request = Unirest.get(url).asJson();
		return JsonCommentsList(request.getBody().getArray());
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
