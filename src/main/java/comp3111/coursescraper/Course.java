package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SLOT = 20;
	private static final int DEFAULT_MAX_SECTION = 40;
	
	private String title ; 
	private String description;
	private String exclusion;
	private String cc4Y;
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
	
	/**
	 * This function adds a section to the course.
	 * 
	 * @param s is a Section object to be added.
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;		
		sections[numSections++] = s.clone();
		++actualNumSec;
		if (numSections > 1 && sections[numSections-1].getID() == sections[numSections-2].getID())
			--actualNumSec;
	}
	
	/**
	 * Get the section at the index i
	 * 
	 * @param i is an index for a section in the course.
	 * @return the Section stored at i
	 */
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	
	/**
	 * Calculates the number of slots having the input section as their section
	 * 
	 * @param section the section ID
	 * @return number of slots for the input section ID
	 */
	public int getNumSlotsForSec(String section) {
		int counter = 0;
		for(int i = 0; i < numSections; i++)
			if(sections[i].getCode().equals(section))
				counter++;
		return counter;
	}
	
	/**
	 * Put indices of slots with the input section String for their sections in the "output" array that is an input
	 * 
	 * @param section the section wants to search for
	 * @param output array for slot indices match the input section
	 */
	public void getSectionIndex(String section, int[] output) {
		int index = 0;
		for(int i = 0; i < numSections; i++) {
			if(sections[i].getCode().equals(section)) {
				output[index++] = i;
			}
		}
	}

	/**
	 * Put days of the slots with the input section String for their sections
	 * 
	 * @param section - section wants to search for
	 * @param output - days of slots matching the input section
	 */
	public void getDaysOfSection(String section, int[] output) {
		int index = 0;
		for(int i = 0; i < numSections; i++) {
			if(sections[i].getCode().equals(section)) {
				output[index++] = slots[i].getDay();
			}
		}
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
	 * @return the Common Core Information
	 */
	public String getCC4Y() {
		return cc4Y;
	}

	/**
	 * @param ccInfo: the Common Core information to set
	 */
	public void setCC4Y(String ccInfo) {
		this.cc4Y = ccInfo;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion: the exclusion to set
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
	
	/**
	 * @return the actual number of Sections
	 */
	public int getNumSections() {
		return actualNumSec;
	}
	
	/**
	 * @param CourseOverallMean becomes CourseOverallMean of the course
	 */
	public void setCourseOverallMean(String CourseOverallMean) {
		this.CourseOverallMean = CourseOverallMean;
	}
	
	/**
	 * @return the Course Overall Mean
	 */
	public String getCourseOverallMean() {
		return CourseOverallMean;
	}
	
	/**
	 * @param InstructorOverallMean becomes InstructorOverallMean of the course
	 */
	public void setInstructorOverallMean(String InstructorOverallMean) {
		this.InstructorOverallMean = InstructorOverallMean;
	}
	
	/**
	 * @return the Instructor Overall Mean
	 */
	public String getInstructorOverallMean() {
		return InstructorOverallMean;
	}
	
	/**
	 * @param ResponseRate becomes ResponseRate of the course
	 */
	public void setResponseRate(String ResponseRate) {
		this.ResponseRate = ResponseRate;
	}
	
	/**
	 * @return the Response Rate
	 */
	public String getResponseRate() {
		return ResponseRate;
	}
	
	/**
	 * Add one to the actual number of sections
	 */
	public void addOneToActualNumSec() {
		++actualNumSec;
	}
	
	/**
	 * Subtract one to the actual number of sections
	 */
	public void subtractOneToActualNumSec() {
		--actualNumSec;
	}
	

}
