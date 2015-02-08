package com.example.momentherelogin;

public class Message {
	public String name;
	public String message;
	public String location;
	public String time;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Message(){
		
	}
	
	public Message(String name, String message, String location, String time) {
		super();
		this.name = name;
		this.message = message;
		this.location = location;
		this.time = time;
	}
}
