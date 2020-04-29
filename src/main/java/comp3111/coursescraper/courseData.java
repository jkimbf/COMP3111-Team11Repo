package comp3111.coursescraper;

import java.util.List;

import javafx.scene.control.CheckBox;

public class courseData {
	private String courseCode;
	private String section;
	private String name;
	private String instructor;
	private CheckBox enroll;
	
	public courseData(String courseCode, String section, String name, String instructor, CheckBox enroll) {
		this.courseCode = courseCode;
		this.section = section;
		this.name = name;
		this.instructor = instructor;
		this.enroll = enroll;
	}

	public String getCourseCode() {
		return courseCode;
	}
	
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	public CheckBox getEnroll() {
		return enroll;
	}
	
	public void setEnroll(CheckBox enroll) {
		this.enroll = enroll;
	}
	
}
