package com.momenthere.fragment.trackmap;

import java.sql.Date;

/**
 * @author Xiuming XU (gracexuxiuming@gmail.com)
 */
public class TrackmapNode {
	String username;
	Date trackDate; 
	String Location; 
	int PostcardNum;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getTrackDate() {
		return trackDate;
	}
	public void setTrackDate(Date trackDate) {
		this.trackDate = trackDate;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getPostcardNum() {
		return PostcardNum;
	}
	public void setPostcardNum(int postcardNum) {
		PostcardNum = postcardNum;
	}

	public TrackmapNode(String username,Date trackDate, String Location, int PostcardNum) {
	}

}
