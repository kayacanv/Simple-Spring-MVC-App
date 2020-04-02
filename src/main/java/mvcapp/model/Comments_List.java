package mvcapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class Comments_List {
  private class Comment{
    private int id;
    private String comment;
    private int postId;
    public Comment(int id,String comment,int postId) {
      this.id=id;
      this.comment=comment;
      this.postId=postId;
    }
    public int getId() {
      return id;
    }
    public String getComment() {
      return comment;
    }
    public int getPostId() {
      return postId; 
    }
  }
  
  private ArrayList<Comment> List;
  public Comments_List() {
    List = new ArrayList<Comment>();
  }
  public int addComment(int i, String string, int j){
     List.add(new Comment(i,string,j));
    return 0;
  }
  public ArrayList<String> getComments()
  {
    ArrayList<String> res = new ArrayList<String>();
    for(int i=0;i<List.size();i++)
      res.add(List.get(i).getComment());
    return res;
  }
}

