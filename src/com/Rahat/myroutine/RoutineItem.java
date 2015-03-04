package com.Rahat.myroutine;

import java.io.Serializable;

import android.graphics.Color;

public class RoutineItem implements Serializable{
	String courseID;
	String courseName;
	String teachers;
	int colorConstant;
	static final int defaultColorConstant=Color.GREEN;
	
	public RoutineItem() {
		clear();
	}
	public RoutineItem(String courseID, String courseName, String teachers, int colorConstant) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.teachers = teachers;
		this.colorConstant = colorConstant;
	}
	
	public void clear()
	{
		courseID="";
		courseName="";
		teachers="";
		colorConstant=defaultColorConstant;
	}
	
	public String getCourseID() {
		return courseID;
	}
	public String getCourseName() {
		return courseName;
	}
	public String getTeachers() {
		return teachers;
	}
	public int getColorConstant() {
		return colorConstant;
	}
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}
	public void setColorConstant(int colorConstant) {
		this.colorConstant=colorConstant;
	}
}
