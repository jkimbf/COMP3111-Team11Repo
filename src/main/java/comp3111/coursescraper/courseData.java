package comp3111.coursescraper;

import java.util.List;

import javafx.scene.control.CheckBox;

/**
 * A class for the each raw of the TableView in the List tab
 * 
 * @author KIM, Jaehyeok
 * 
 */
public class courseData {
	/**
	 * Course Code of the course
	 * 
	 * @see #getCourseCode()
	 * @see #setCourseCode(String)
	 */
	private String courseCode;
	
	/**
	 * Section of the data
	 * 
	 * @see #getSection()
	 * @see #setSection(String)
	 */
	private String section;
	
	/**
	 * Title of the course
	 * 
	 * @see #getName()
	 * @see #setName(String)
	 */
	private String name;
	
	/**
	 * Instructor name of the course
	 * 
	 * @see #getInstructor()
	 * @see #setInstructor(String)
	 */
	private String instructor;
	
	/**
	 * Enrollment status of the section
	 * 
	 * @see #getEnroll()
	 * @see #setEnroll(CheckBox)
	 */
	private CheckBox enroll;
	
	/**
	 * Constructor with no arguments
	 */
	public courseData() {
		
	}
	
	/**
	 * Constructor with arguments for fields
	 * 
	 * @param courseCode the code of course
	 * @param section the section of the data
	 * @param name the title of the course
	 * @param instructor the instructor of the course
	 * @param enroll enrollment status
	 */
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
	 * @return the title of the course
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the title to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the instructor name of the section
	 */
	public String getInstructor() {
		return instructor;
	}
	
	/**
	 * @param instructor the instructor name to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	
	/**
	 * @return the CheckBox for enrollment status
	 */
	public CheckBox getEnroll() {
		return enroll;
	}
	
	/**
	 * @param enroll the CheckBox(enrollment status) to set
	 */
	public void setEnroll(CheckBox enroll) {
		this.enroll = enroll;
	}
	
}
