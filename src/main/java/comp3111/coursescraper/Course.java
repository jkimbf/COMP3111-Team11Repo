package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	private static final int DEFAULT_MAX_SECTION = 20;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private Slot [] slots;
	private int numSlots;
	private Section[] sections;
	private int numSections;
	private int actualNumSec;
	
	// SFQ variables
	private String CourseOverallMean;
	private String InstructorOverallMean;
	private String Enrolment;
	private String ResponseRate;
	
	
	public Course() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) slots[i] = null;
		numSlots = 0;
		
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSections = 0;
		actualNumSec = 0;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;		
		sections[numSections++] = s.clone();
		++actualNumSec;
		if (numSections > 1 && sections[numSections-1].getID() == sections[numSections-2].getID())
			--actualNumSec;
	}
	
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSlots() {
		return numSlots;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	// get the actual number of Sections
	public int getNumSections() {
		return actualNumSec;
	}
	
	// set the Course Overall Mean
	public void setCourseOverallMean(String CourseOverallMean) {
		this.CourseOverallMean = CourseOverallMean;
	}
	
	// get the Course Overall Mean
	public String getCourseOverallMean() {
		return CourseOverallMean;
	}
	
	// set the Instructor Overall Mean
	public void setInstructorOverallMean(String InstructorOverallMean) {
		this.InstructorOverallMean = InstructorOverallMean;
	}
	
	// get the Instructor Overall Mean
	public String getInstructorOverallMean() {
		return InstructorOverallMean;
	}
	
	// set the Response Rate
	public void setResponseRate(String ResponseRate) {
		this.ResponseRate = ResponseRate;
	}
	
	// get the Response Rate
	public String getResponseRate() {
		return ResponseRate;
	}
	

}
