package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class Feedback {
	@Id
    private String id;
    private String username;
    private Integer rating;
	private String comment;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Feedback(String id, String username, Integer rating, String comment) {
		super();
		this.id = id;
		this.username = username;
		this.rating = rating;
		this.comment = comment;
	}
	public Feedback() {
		super();
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", username=" + username + ", rating=" + rating + ", comment=" + comment + "]";
	}
	
	
	
}
