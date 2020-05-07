package comp3111.coursescraper;

/**
 * <p>
 * Class Section represents a section for a course. It contains a unique ID and a code.
 * </p>
 * 
 * @author HWANG, Junyeol
 * 
 */
public class Section {

	private String sectionID;
	private String sectionCode;
	
	/**
	 * a constructor
	 */
	public Section() {
		
	}
	
	/**
	 * Copy the contents of an object
	 * 
	 * @return a new section with data copied
	 */
	@Override
	public Section clone() {
		Section s = new Section();
		s.sectionID = this.sectionID;
		s.sectionCode = this.sectionCode;
		return s;
	}
	
	/**
	 * Set the ID
	 * @param id is to be set as the sectionID
	 */
	public void setID(String id) {
		sectionID = id;
	}
	
	/**
	 * Set the code
	 * @param code is to be set as the sectionCode
	 */
	public void setCode(String code) {
		sectionCode = code;
	}
	
	/**
	 * Get the code and the ID
	 * @return the sectionCode and the sectionID in String type
	 */
	public String toString() {
		return sectionCode + ' ' + sectionID;
	}
	
	/**
	 * Get the ID
	 * @return the section ID
	 */
	public String getID() {
		return sectionID;
	}
	
	/**
	 * Get the code
	 * @return the section code
	 */
	public String getCode() {
		return sectionCode;
	}
	
}
