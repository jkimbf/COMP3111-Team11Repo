package comp3111.coursescraper;

import java.util.List;

import javafx.scene.control.CheckBox;

public class courseData {
	private String courseCode;
	private String section;
	private String name;
	private String instructor;
	private CheckBox enroll;
	
	public courseData() {
		
	}
	
	public courseData(String courseCode, String section, String name, String instructor, CheckBox enroll) {
		this.courseCode = courseCode;
		this.section = section;
		this.name = name;
		this.instructor = instructor;
		this.enroll = enroll;
	}
	
	/**
	 * @return the course code
	 */
	public String getCourseCode() {
		return courseCode;
	}
	
	/**
	 * @param courseCode the course code to set
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}
	
	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	/**
	 * @return the enrollment status
	 */
	public CheckBox getEnroll() {
		return enroll;
	}
	
	/**
	 * @param enroll the enrollment status to set
	 */
	public void setEnroll(CheckBox enroll) {
		this.enroll = enroll;
	}
	
}
