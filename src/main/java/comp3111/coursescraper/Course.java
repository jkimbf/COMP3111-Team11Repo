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
	 * @param section - section ID
	 * @return number of slots for the input section ID
	 */
	public int getNumSlotsForSec(String section) {
		int counter = 0;
		for(int i = 0; i < numSections; i++)
			if(sections[i].getCode().equals(section))
				counter++;
		return counter;
	}
	
	public void getSectionIndex(String section, int[] output) {
		int index = 0;
		for(int i = 0; i < numSections; i++) {
			if(sections[i].getCode().equals(section)) {
				output[index++] = i;
			}
		}
	}
	
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
	

}
