package comp3111.coursescraper;

public class Section {
	
	private String sectionID;
	private String sectionCode;
	
	public Section() {
		
	}
	
	// Copy this
	@Override
	public Section clone() {
		Section s = new Section();
		s.sectionID = this.sectionID;
		s.sectionCode = this.sectionCode;
		return s;
	}
	
	// Modify ID
	public void setID(String id) {
		sectionID = id;
	}
	
	// Modify Code
	public void setCode(String code) {
		sectionCode = code;
	}
	
	// For printing section code and ID at once
	public String toString() {
		return sectionCode + ' ' + sectionID;
	}
	
	// return the section ID (four digit int)
	public String getID() {
		return sectionID;
	}
	
	// return the section code (L1,L2,LA1,T2...)
	public String getCode() {
		return sectionCode;
	}
	
}
