package app.model;

import javafx.beans.property.*;

public class Rating {
	private StringProperty username;
	private DoubleProperty rating;
	private StringProperty comments;
	
	public Rating(String username, double rating, String comments){
		this.username=new SimpleStringProperty(username);
		this.rating=new SimpleDoubleProperty(rating);
		this.comments=new SimpleStringProperty(comments);
	}
	
	public String getUsername() {
		return username.get();
	}
	public Double getRating() {
		return rating.get();
	}
	public String getComments() {
		return comments.get();
	}
	
	
}
