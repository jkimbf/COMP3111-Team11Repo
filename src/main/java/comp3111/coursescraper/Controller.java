package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
public class Controller {

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;
    
    @FXML
    private Button allSubjectSearch;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    private Scraper scraper = new Scraper();
    
    private Scraper scraperSFQ = new Scraper();
    
    @FXML
    private Button selectAllButton;
    
    @FXML
    private CheckBox checkAM;
    
    @FXML
    private CheckBox checkPM;
    
    @FXML
    private CheckBox checkMon;
    
    @FXML
    private CheckBox checkTue;
    
    @FXML
    private CheckBox checkWed;
    
    @FXML
    private CheckBox checkThu;
    
    @FXML
    private CheckBox checkFri;
    
    @FXML
    private CheckBox checkSat;
    
    @FXML
    private CheckBox checkCC;
    
    @FXML
    private CheckBox checkNoEx;
    
    @FXML
    private CheckBox checkLAT;
    
    @FXML
    private TableView<courseData> courseTable;
    
    @FXML
    private TableColumn<courseData, String> codeCol;
    
    @FXML
    private TableColumn<courseData, String> sectionCol;
    
    @FXML
    private TableColumn<courseData, String> nameCol;
    
    @FXML
    private TableColumn<courseData, String> instructorCol;
    
    @FXML
    private TableColumn<courseData, CheckBox> enrollCol;
    
    @FXML
    private ObservableList<courseData> list = FXCollections.observableArrayList();
    
    @FXML
    private ObservableList<courseData> listAll = FXCollections.observableArrayList();
    
    @FXML
    private List<Course> allCourses;
    
    @FXML
    private ObservableList<courseData> listForPrint = FXCollections.observableArrayList();
    
    private String prevURL;
    
    private String prevTerm;
    
    private String prevSubject;
    
    private boolean filterAllSubjects = false;
    
    private boolean allInitialized = false;

    private boolean click=false; //detect in Allsubjectsearch
    
    private int counteri=0;

 

	/**
	 * Check the URL validity
	 * 
	 * @param 	error contains error statement only if there is an error
	 * @return	returns 404 page not found if there is error, no error otherwise
	 */
    public String errorCheck(String error) {
    	if (error.equals("404error"))
    		return "404 page not found. Check if the URL is correct.";
    	else
    		return "No error";
    }
    
    // Should be true
	/**
	 * Set to true before search or subject search is clicked
	 * SFQ button is disabled when true.
	 */
	private boolean DISABLED = true;
    
    //public List<courseData> courseDataSet;
    /**
     * Initialize the list of courses scraped and the variables storing the previous URL, previous Term and previous Subject when program starts
     */
    @FXML
    protected void initialize() {
    	initializeCourseSet();
    	prevURL = textfieldURL.getText();
	    prevTerm = textfieldTerm.getText();
	    prevSubject = textfieldSubject.getText();
    }
    
    /**
     * For the Select-All button
     */
    @FXML
    protected void selectAll() {
    	boolean ind;
    	if(selectAllButton.getText().equals("Select All")) {
    		ind = true;
    		selectAllButton.setText("De-select All");
    	}
    	else {
    		ind = false;
    		selectAllButton.setText("Select All");
    	}
    	
    	checkAM.setSelected(ind);
    	checkPM.setSelected(ind);
    	checkMon.setSelected(ind);
    	checkTue.setSelected(ind);
    	checkWed.setSelected(ind);
    	checkThu.setSelected(ind);
    	checkFri.setSelected(ind);
    	checkSat.setSelected(ind);
    	checkCC.setSelected(ind);
    	checkNoEx.setSelected(ind);
    	checkLAT.setSelected(ind);
    	
    	filterSearch();
    }
    
    // Need to be changed
    /**
     * Searches based on the filters applied and print in the console
     */
    @FXML
    protected void filterSearch() {
    	List<Course> v;
    	if(!filterAllSubjects) {
	    	if(!prevURL.equals(textfieldURL.getText()) || !prevTerm.equals(textfieldTerm.getText()) || !prevSubject.equals(textfieldSubject.getText())) {
	    		initializeCourseSet();
	    		if(allInitialized)
	        		allToSingle();
	    		prevURL = textfieldURL.getText();
	    	    prevTerm = textfieldTerm.getText();
	    	    prevSubject = textfieldSubject.getText();
	    	}
	    	textAreaConsole.setText("");
	    	v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	}
    	else {
    		textAreaConsole.setText("");
	    	v = new ArrayList<Course>(allCourses);
    	}
    	// Check for 404 page not found
    	if (!v.isEmpty()) {
        	String error = errorCheck(v.get(0).getTitle());
        	if (error != "No error") {
        		textAreaConsole.setText(error);
        		return;
        	}
    	}
    	
    	// With Labs or Tutorial
    	if(checkLAT.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			temp.setCC4Y(v.get(i).getCC4Y());
    			
    			boolean containsLAT = false;
    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    				if(v.get(i).getSection(j).getCode().contains("LA")
    						|| v.get(i).getSection(j).getCode().charAt(0) == 'T') {
    					containsLAT = true;
    				}
    			}
    			
    			
	    		for(int j = 0; j < v.get(i).getNumSlots(); j++) {
	    			if(containsLAT) {	
	    				temp.addSlot(v.get(i).getSlot(j));
	    				temp.addSection(v.get(i).getSection(j));
	    			}
    			}
    			v.remove(i);
    			v.add(i, temp);
    		}
    	}
    	
    	// Days
    	if(checkMon.isSelected() || checkTue.isSelected() || checkWed.isSelected() 
    			|| checkThu.isSelected() || checkFri.isSelected() || checkSat.isSelected()) {
    		int intMon = (checkMon.isSelected())? 1: 0;
    		int intTue = (checkTue.isSelected())? 1: 0;
    		int intWed = (checkWed.isSelected())? 1: 0;
    		int intThu = (checkThu.isSelected())? 1: 0;
    		int intFri = (checkFri.isSelected())? 1: 0;
    		int intSat = (checkSat.isSelected())? 1: 0;
    		int counter = intMon + intTue + intWed + intThu + intFri + intSat;
    		// if one of the Days buttons is clicked
    		if(counter == 1) {
    			int buttonNum = -1;
    			if(intMon == 1) buttonNum = 0;
    			if(intTue == 1) buttonNum = 1;
    			if(intWed == 1) buttonNum = 2;
    			if(intThu == 1) buttonNum = 3;
    			if(intFri == 1) buttonNum = 4;
    			if(intSat == 1) buttonNum = 5;
    			
    			for(int i = 0; i < v.size(); i++) {
	    			Course temp = new Course();
	    			temp.setTitle(v.get(i).getTitle());
	    			temp.setDescription(v.get(i).getDescription());
	    			temp.setExclusion(v.get(i).getExclusion());
	    			temp.setCC4Y(v.get(i).getCC4Y());
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
	    				if(v.get(i).getSlot(j).getDay() == buttonNum) {
	    					temp.addSlot(v.get(i).getSlot(j));
	    					temp.addSection(v.get(i).getSection(j));
	    				}
	    			}
	    			v.remove(i);
	    			v.add(i, temp);
	    		}
    		}
    		// A section may have zero, one, two, or three slots
    		else if(counter >= 2 && counter <= 3) {
    			int[] buttonNums = new int[counter];
    			for(int i = 0; i < counter; i++)
    			{
	    			if(intMon == 1)	{ buttonNums[i] = 0; intMon-=1; continue; }
	        		if(intTue == 1) { buttonNums[i] = 1; intTue-=1; continue; }
	        		if(intWed == 1) { buttonNums[i] = 2; intWed-=1; continue; }
	        		if(intThu == 1) { buttonNums[i] = 3; intThu-=1; continue; }
	        		if(intFri == 1) { buttonNums[i] = 4; intFri-=1; continue; }
	        		if(intSat == 1) { buttonNums[i] = 5; intSat-=1; continue; }
    			}
    			
    			for(int i = 0; i < v.size(); i++) {
    				String prevCode = "";
					Course temp = new Course();
	    			temp.setTitle(v.get(i).getTitle());
	    			temp.setDescription(v.get(i).getDescription());
	    			temp.setExclusion(v.get(i).getExclusion());
	    			temp.setCC4Y(v.get(i).getCC4Y());
	    			
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
	    				Section tempSection = v.get(i).getSection(j);
	    				if(prevCode.equals(tempSection.getCode()))
	    					continue;
	    				//textAreaConsole.setText(textAreaConsole.getText() + "\n" 
						//		+ tempSection.getCode());
	    				int numSlots = v.get(i).getNumSlotsForSec(tempSection.getCode());
	    				int[] indices = new int[numSlots];
	    				v.get(i).getDaysOfSection(tempSection.getCode(), indices);
	    				
	    				int[] outputIndices = new int[counter];
	    				int index = 0;
	    				boolean test = true;
	    				for(int k = 0; k < numSlots; k++) {
	    					boolean tempTest = false;
	    					for(int p = 0; p < counter; p++) {
	    						if(indices[k] == buttonNums[p]) {
	    							tempTest = true;
	    							outputIndices[index++] = j+k;
	    						}
	    					}
	    					if(!tempTest)
	    						test = false;
	    				}
	    				if(numSlots < counter)
	    					test = false;
	    				
	    				if(test) {
	    					for(int k = 0; k < counter; k++) {
	    						temp.addSlot(v.get(i).getSlot(outputIndices[k]));
		    					temp.addSection(v.get(i).getSection(outputIndices[k]));
	    					}
	    				}
	    				prevCode = tempSection.getCode();
	    			}
	    			
	    			v.remove(i);
	    			v.add(i, temp);
				}	
    		}
    		// More than or equal to 4 buttons will always give no result
    		else {
    			for(int i = 0; i < v.size(); i++) {
        			Course temp = new Course();
        			temp.setTitle(v.get(i).getTitle());
        			temp.setDescription(v.get(i).getDescription());
        			temp.setExclusion(v.get(i).getExclusion());
        			temp.setCC4Y(v.get(i).getCC4Y());
        			
        			// deleting all slots
        			
        			v.remove(i);
        			v.add(i, temp);
        		}
    		}
    	}
    	
    	
    	// AM & PM Check boxes
    	if(checkAM.isSelected() && checkPM.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			temp.setCC4Y(v.get(i).getCC4Y());
    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    				int slotsNum = v.get(i).getNumSlotsForSec(v.get(i).getSection(j).getCode());
    				int[] indices = new int[slotsNum];
    				v.get(i).getSectionIndex(v.get(i).getSection(j).getCode(), indices);
    				boolean isBoth = false, isAM = false, isPM = false;
    				for(int k = 0; k < slotsNum; k++) {
    					if((v.get(i).getSlot(indices[k]).getStartHour() >= 0 && v.get(i).getSlot(indices[k]).getStartHour() < 12) || 
	    						(v.get(i).getSlot(indices[k]).getEndHour() >= 0 && v.get(i).getSlot(indices[k]).getEndHour() < 12))
    						isAM = true;
    					if((v.get(i).getSlot(indices[k]).getStartHour() >= 12 && v.get(i).getSlot(indices[k]).getStartHour() < 24) || 
	    						(v.get(i).getSlot(indices[k]).getEndHour() >= 12 && v.get(i).getSlot(indices[k]).getEndHour() < 24))
    						isPM = true;
    				}
    				isBoth = (isAM && isPM);
    				
    				if((v.get(i).getSlot(j).getStartHour() >= 0 && v.get(i).getSlot(j).getStartHour() < 12) && 
    						(v.get(i).getSlot(j).getEndHour() >= 12 && v.get(i).getSlot(j).getEndHour() < 24)) 
    					isBoth = true;
    				
    				if(isBoth) {
    					temp.addSlot(v.get(i).getSlot(j));
    					temp.addSection(v.get(i).getSection(j));
    				}
    			}
    			v.remove(i);
    			v.add(i, temp);
    		}
    	}
    	
    	else {
	    	if(checkAM.isSelected()) {
	    		for(int i = 0; i < v.size(); i++) {
	    			Course temp = new Course();
	    			temp.setTitle(v.get(i).getTitle());
	    			temp.setDescription(v.get(i).getDescription());
	    			temp.setExclusion(v.get(i).getExclusion());
	    			temp.setCC4Y(v.get(i).getCC4Y());
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
	    				if((v.get(i).getSlot(j).getStartHour() >= 0 && v.get(i).getSlot(j).getStartHour() < 12) || 
	    						(v.get(i).getSlot(j).getEndHour() >= 0 && v.get(i).getSlot(j).getEndHour() < 12)) {
	    					temp.addSlot(v.get(i).getSlot(j));
	    					temp.addSection(v.get(i).getSection(j));
	    				}
	    			}
	    			v.remove(i);
	    			v.add(i, temp);
	    		}
	    	}
	    	
	    	if(checkPM.isSelected()) {
	    		for(int i = 0; i < v.size(); i++) {
	    			Course temp = new Course();
	    			temp.setTitle(v.get(i).getTitle());
	    			temp.setDescription(v.get(i).getDescription());
	    			temp.setExclusion(v.get(i).getExclusion());
	    			temp.setCC4Y(v.get(i).getCC4Y());
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
	    				if((v.get(i).getSlot(j).getStartHour() >= 12 && v.get(i).getSlot(j).getStartHour() < 24) || 
	    						(v.get(i).getSlot(j).getEndHour() >= 12 && v.get(i).getSlot(j).getEndHour() < 24)) {
	    					temp.addSlot(v.get(i).getSlot(j));
	    					temp.addSection(v.get(i).getSection(j));
	    				}
	    			}
	    			v.remove(i);
	    			v.add(i, temp);
	    		}
	    	}
    	}
    	
    	
    	// Common Core
    	if(checkCC.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			temp.setCC4Y(v.get(i).getCC4Y());
    			
    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    				if(v.get(i).getCC4Y().contains("4Y")) {
    					temp.addSlot(v.get(i).getSlot(j));
    					temp.addSection(v.get(i).getSection(j));
    				}
    			}
    			v.remove(i);
    			v.add(i, temp);
    		}
    	}
    	
    	
    	// No Exclusion
    	if(checkNoEx.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			temp.setCC4Y(v.get(i).getCC4Y());
    			
    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    				if(v.get(i).getExclusion() == "null") {
    					temp.addSlot(v.get(i).getSlot(j));
    					temp.addSection(v.get(i).getSection(j));
    				}
    			}
    			v.remove(i);
    			v.add(i, temp);
    		}
    	}
    	
    	for (Course c : v) {
    		String newline = "";
    		
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			Section x = c.getSection(i);
    			newline += "Slot " + i + ":" + t + " Section " + x.toString() + "\n";
    		}
    		
    		//check whether slots exist after filtering
    		if(c.getNumSlots() != 0) {
    			String newTitle = c.getTitle() + "\n"; // + "Number of sections: " + c.getNumSections() + "\n";
    			newTitle += newline;
    			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newTitle);
    		}
    	}
    	
    	listFilteredCourses(v);
    }
    
    /**
     * To keep the enrollment status, list of all courses from the inputs are saved as courseData objects
     */
    @FXML
    protected void initializeCourseSet() {
    	if(!filterAllSubjects) {
    		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    		// Check for 404 page not found
        	if(!v.isEmpty()) {
            	String error = errorCheck(v.get(0).getTitle());
            	if (error != "No error") {
            		textAreaConsole.setText(error);
            		return;
            	}
        	}

        	
        	list.clear();
        	int id = 0;
        	for(int i = 0; i < v.size(); i++) {
        		String[] courseInfo = v.get(i).getTitle().split("-", 2);
        		String courseCode = courseInfo[0];
        		String name = courseInfo[1].substring(0, courseInfo[1].indexOf('(')-1);
        		
        		String prevSection = "";
        		for(int j = 0; j < v.get(i).getNumSlots(); j++) {
        			String section = v.get(i).getSection(j).getCode();
        			if(prevSection.equals(section)) {
        				list.add(list.get(list.size()-1));
        				continue;
        			}
        			String instructor = "";
        			int k = 0;
        			while(k < v.get(i).getSlot(j).getInstNum()-1)
        				instructor += (v.get(i).getSlot(j).getInstructor(k++)+" / ");
        			instructor += v.get(i).getSlot(j).getInstructor(k);
        			CheckBox ch = new CheckBox();
        			ch.setOnAction(event -> {
        				enrollSection();
        				updateTimetable();
        			});
        			ch.setId("enroll"+ String.valueOf(id++));
        			courseData temp = new courseData(courseCode, section, name, instructor, ch);
        			list.add(temp);
        			prevSection = section;
        		}
        	}
    	}
    	else {
    		List<Course> v = new ArrayList<Course>(allCourses);
    		if(!v.isEmpty()) {
            	String error = errorCheck(v.get(0).getTitle());
            	if (error != "No error") {
            		textAreaConsole.setText(error);
            		return;
            	}
        	}

        	
        	listAll.clear();
        	int id = 0;
        	for(int i = 0; i < v.size(); i++) {
        		String[] courseInfo = v.get(i).getTitle().split("-", 2);
        		String courseCode = courseInfo[0];
        		String name = courseInfo[1].substring(0, courseInfo[1].indexOf('(')-1);
        		
        		String prevSection = "";
        		for(int j = 0; j < v.get(i).getNumSlots(); j++) {
        			String section = v.get(i).getSection(j).getCode();
        			if(prevSection.equals(section)) {
        				listAll.add(listAll.get(listAll.size()-1));
        				continue;
        			}
        			String instructor = "";
        			int k = 0;
        			while(k < v.get(i).getSlot(j).getInstNum()-1)
        				instructor += (v.get(i).getSlot(j).getInstructor(k++)+" / ");
        			instructor += v.get(i).getSlot(j).getInstructor(k);
        			CheckBox ch = new CheckBox();
        			ch.setOnAction(event -> {
        				enrollSection();
        				updateTimetable();
        			});
        			ch.setId("enroll"+ String.valueOf(id++));
        			courseData temp = new courseData(courseCode, section, name, instructor, ch);
        			listAll.add(temp);
        			prevSection = section;
        		}
        	}
    	}
    }
    
    /**
     * Add the course with clicked buttons to the enrolled course list and print in the console
     */
    @FXML
    protected void enrollSection() {
    	textAreaConsole.clear();
    	String output = "The following sections are enrolled:\n";
    	String prevSection = "", prevCourseCode = "";
    	if(!filterAllSubjects) {
	    	for(int i = 0; i < list.size(); i++) {
	    		if(list.get(i).getEnroll().isSelected()) {
	    			if(prevSection.equals(list.get(i).getSection()) && prevCourseCode.equals(list.get(i).getCourseCode()))
	    				continue;
	    			output += list.get(i).getCourseCode()+" - "+list.get(i).getSection()+" - "+list.get(i).getName()+" - "+list.get(i).getInstructor()+"\n";
	    			prevSection = list.get(i).getSection();
	    			prevCourseCode = list.get(i).getCourseCode();
	    		}
	    	}
    	}
    	else {
    		for(int i = 0; i < listAll.size(); i++) {
	    		if(listAll.get(i).getEnroll().isSelected()) {
	    			if(prevSection.equals(listAll.get(i).getSection()) && prevCourseCode.equals(listAll.get(i).getCourseCode()))
	    				continue;
	    			output += listAll.get(i).getCourseCode()+" - "+listAll.get(i).getSection()+" - "+listAll.get(i).getName()+" - "+listAll.get(i).getInstructor()+"\n";
	    			prevSection = listAll.get(i).getSection();
	    			prevCourseCode = listAll.get(i).getCourseCode();
	    		}
	    	}
    	}
    	textAreaConsole.setText(output);
    }
    
    /**
     * Put courses from the Filter search result to the TableView in the List tab
     * @param v list of courses from the filtering search
     */
    protected void listFilteredCourses(List<Course> v) {
    	// Need to separate for search and allsearch
    	listForPrint.clear();
    	int indexV = 0;
    	int indexSection = 0;
    	String prevSection = "";
    	if(!filterAllSubjects) {
	    	for(int i = 0; i < list.size(); i++) {
	    		if(indexV >= v.size())
	    			break;
	    		if(v.get(indexV).getNumSlots() == 0) {
	    			indexV++;
	    			i--;
	    			continue;
	    		}
	    		
	    		// get slot info in v
	    		String[] courseInfo = v.get(indexV).getTitle().split("-", 2);
	    		String courseCodeV = courseInfo[0];
	    		
	    		if(listForPrint.size() != 0 
	    				&& (listForPrint.get(listForPrint.size()-1).getSection().equals(v.get(indexV).getSection(indexSection).getCode())
	    				&& listForPrint.get(listForPrint.size()-1).getCourseCode().equals(courseCodeV))) {
	    			if(indexSection >= v.get(indexV).getNumSlots()-1) {
		    			indexSection = 0;
		    			indexV++;
		    		}
		    		else
		    			indexSection++;
	    			continue;
	    		}
	    			
	    		if(prevSection.equals(list.get(i).getSection())
	    				&& listForPrint.get(listForPrint.size()-1).getCourseCode().equals(list.get(i).getCourseCode()))
	    			continue;
	    		
	    		
	    		String nameV = v.get(indexV).getSection(indexSection).getCode();
	    		// Add slots that are in result of filtering only
	    		
	        	/*textAreaConsole.setText(textAreaConsole.getText()
	        			+courseCodeV+"\t"+nameV+"\t"
	        			+list.get(i).getCourseCode()+"\t"+list.get(i).getSection()+"\t"
	        			+courseCodeV.equals(list.get(i).getCourseCode())+"\t"
	        			+nameV.equals(list.get(i).getSection())
	        			+"\n");*/
	    		if(courseCodeV.equals(list.get(i).getCourseCode()) && nameV.equals(list.get(i).getSection())) {
		    		listForPrint.add(list.get(i));
		    		prevSection = list.get(i).getSection();
		    		if(indexSection >= v.get(indexV).getNumSlots()-1) {
		    			indexSection = 0;
		    			indexV++;
		    		}
		    		else
		    			indexSection++;
	    		}
	    	}
    	}
    	else {
    		for(int i = 0; i < listAll.size(); i++) {
	    		if(indexV >= v.size())
	    			break;
	    		if(v.get(indexV).getNumSlots() == 0) {
	    			indexV++;
	    			i--;
	    			continue;
	    		}
	    		
	    		// get slot info in v
	    		String[] courseInfo = v.get(indexV).getTitle().split("-", 2);
	    		String courseCodeV = courseInfo[0];
	    		
	    		if(listForPrint.size() != 0 
	    				&& (listForPrint.get(listForPrint.size()-1).getSection().equals(v.get(indexV).getSection(indexSection).getCode())
	    				&& listForPrint.get(listForPrint.size()-1).getCourseCode().equals(courseCodeV))) {
	    			if(indexSection >= v.get(indexV).getNumSlots()-1) {
		    			indexSection = 0;
		    			indexV++;
		    		}
		    		else
		    			indexSection++;
	    			continue;
	    		}
	    			
	    		if(prevSection.equals(listAll.get(i).getSection())
	    				&& listForPrint.get(listForPrint.size()-1).getCourseCode().equals(listAll.get(i).getCourseCode()))
	    			continue;
	    		
	    		
	    		String nameV = v.get(indexV).getSection(indexSection).getCode();
	    		// Add slots that are in result of filtering only
	    		
	        	/*textAreaConsole.setText(textAreaConsole.getText()
	        			+courseCodeV+"\t"+nameV+"\t"
	        			+list.get(i).getCourseCode()+"\t"+list.get(i).getSection()+"\t"
	        			+courseCodeV.equals(list.get(i).getCourseCode())+"\t"
	        			+nameV.equals(list.get(i).getSection())
	        			+"\n");*/
	    		if(courseCodeV.equals(listAll.get(i).getCourseCode()) && nameV.equals(listAll.get(i).getSection())) {
		    		listForPrint.add(listAll.get(i));
		    		prevSection = listAll.get(i).getSection();
		    		if(indexSection >= v.get(indexV).getNumSlots()-1) {
		    			indexSection = 0;
		    			indexV++;
		    		}
		    		else
		    			indexSection++;
	    		}
	    	}
    	}
    	
    	courseTable.setItems(listForPrint);
    	codeCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("courseCode"));
    	sectionCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("section"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("name"));
    	instructorCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("instructor"));
    	enrollCol.setCellValueFactory(new PropertyValueFactory<courseData,CheckBox>("enroll"));
    }
    
    @FXML
    void updateTimetable() {
    	//timetable change start
    	
    	int test=0;
    	String prech="";
    	AnchorPane ap= (AnchorPane)tabTimetable.getContent();

    	//get original table index. 
    	if(counteri==0)
    	counteri=ap.getChildren().size()-1;
    	
    	//initialize table
    	ap.getChildren().remove(counteri, ap.getChildren().size());
    	 

    	
   	 
    	
    	for(int i = 0; i < list.size(); i++) {
    			
    	    if(list.get(i).getEnroll().isSelected()) {
    	    	
    	    	Random rand = new Random();
    	    	int r = rand.nextInt(255);
    	    	int g = rand.nextInt(255);
    	    	int b = rand.nextInt(255);
    	    	Color randomColor = Color.rgb(r, g, b, 0.1);


    	    	
    	    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	    	double start=0,end=0,day=0;
    	    	for(int j=0;j<v.size();j++)
    	    	{
    	    		String[] courseInfo = v.get(j).getTitle().split("-", 2);
    	    					String code=courseInfo[0];
    		
    	    		if(code.equals(list.get(i).getCourseCode()))
    	    		{
    	    			
    	    			
    	    			for(int k=0;k<v.get(j).getNumSlots();k++)
    	    			{
    	    				String ch;
    	    				
    	    				ch =v.get(j).getSection(k).getCode();
    	    				//check case : System.out.println("ch: " + ch);
    	    				//check case: System.out.println("real ch: " + list.get(i).getSection());
    	    				if(ch.equals(list.get(i).getSection()))
    	    				{	
    	    					if(prech.contentEquals(code+ch) && test!=i)continue;
    	    					
    	    					//check case: System.out.println(code + ch + " Day: " + v.get(j).getSlot(k).getDay() + " Time: " + v.get(j).getSlot(k).getStartHour()+v.get(j).getSlot(k).getStartMinute() + " - " + v.get(j).getSlot(k).getEndHour()+v.get(j).getSlot(k).getEndMinute());
    	    					prech=code+ch;test=i;
    	    					
    	    					
    	    					
    	    					start = ((float)v.get(j).getSlot(k).getStartHour()-9)*20;
    	    					start +=((float)v.get(j).getSlot(k).getStartMinute()/3);
    	    					
    	    					end = (float) (v.get(j).getSlot(k).getEndHour()-v.get(j).getSlot(k).getStartHour())*20;
    	    					end +=(float)(v.get(j).getSlot(k).getEndMinute()-v.get(j).getSlot(k).getStartMinute())/3;
    	    					
    	    					day= (float)v.get(j).getSlot(k).getDay()*100+102; //102 is initial value, *100 is interval
    	    					
    	    	    	    	Label randomLabel = new Label(list.get(i).getCourseCode() + "\n" + list.get(i).getSection());

    	    	    	    	
    	    	    	    	randomLabel.setBackground(new Background(new BackgroundFill(randomColor , CornerRadii.EMPTY, Insets.EMPTY)));
    	    	    	    	randomLabel.setLayoutX(day);
    	    	    	    	randomLabel.setLayoutY(start+45); // 65-45= y+20= 1 hour ,45 is initial value, 20 is interval of 1 hour
    	    	    	    	randomLabel.setMinWidth(100.0);
    	    	    	    	randomLabel.setMaxWidth(100.0);
    	    	    	    	randomLabel.setMinHeight(end);
    	    	    	    	randomLabel.setMaxHeight(end+5);
    	    	    	    	ap.getChildren().addAll(randomLabel);
    	    					
    	    						
    	    				}
    	    			}
    	    		}
    	    	}
    	

    	   	  }
    	    }
    }
   //timetable end
    @FXML
    void allSubjectSearch() {
    	// For disabling SFQ button until Search button is clicked
       	DISABLED = false;
    	buttonSfqEnrollCourse.setDisable(DISABLED);
    	click=!click;
    	int count=0;
    	int subcount=0;
    	//List<Course> v= new LinkedList<>();
    	List<Course> v = new Vector<Course>();
    	List<Course> temp = new Vector<Course>();
    	String []sub= {"ACCT", "AESF", "BIBU","BIEN","BIPH","BTEC", "CBME", "CENG", "CHEM","CHMS", "CIEM", "CIVL", "COMP","CSIC", "CSIT", "ECON", "EEMT", "EESM","ELEC","EMBA","ENEG","ENGG", "ENTR", "ENVR", "ENVS", "EVNG","EVSM", "FINA","GBUS","GFIN","GNED", "HART", "HHMS", "HLTH","HMMA","HUMA", "IBTM","IDPO","IEDA","IIMP","IMBA", "ISDN", "ISOM", "JEVE","LABU","LANG", "LIFS", "MAED", "MAFS", "MARK", "MATH", "MECH","MESF","MFIT", "MGCS", "MGMT","MILE", "MIMT", "MSBD","MSDM","NANO", "OCES","PDEV", "PHYS","PPOL","RMBI", "SBMT","SCIE","SHSS", "SOSC","SSMA","SUST","TEMG", "UROP","WBBA"};
    	for(int i=0;i<sub.length;i++)
    	{
    		textfieldSubject.setText(sub[i]);
    		temp.clear();
    		temp = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    		subcount+=temp.size();
    		for(int j=0;j<temp.size();j++)
    		{
    			if (temp.get(j).getTitle().equals("404error"))
    				continue;
    			
    			v.add(temp.get(j));
    		}
    	
    	}
    	
    		// Check for 404 page not found
    	if (v.isEmpty()) {
    		textAreaConsole.setText("404 page not found. Check if the URL is correct.");
    		return;
    	}
    	if (!v.isEmpty()) {
    		String error = errorCheck(v.get(0).getTitle());
    		if (error != "No error") {
    			textAreaConsole.setText(error);
    			return;
    		}
    	}
       	if(click)
        {
        	textAreaConsole.setText("Total Number of Categories/Code Prefix: " + subcount);
        	double progress=0.0f;
        	progressbar.setProgress(progress);
       	}
       
        if(!click && v.size()!=0)
        {
        	double progress=0.0f;
        	for(int i=0;i<v.size();i++) {

        	   	System.out.println(v.get(i).getTitle() + " is done");
        		progress=(float)i/v.size() ;

        		progressbar.setProgress(progress);
        		    	
        		String[] courseInfo = v.get(i).getTitle().split("-", 2);
        		String code=courseInfo[0];
        		if(code!="")count++;
        	}
        	textAreaConsole.setText("Total Number of Courses fetched: " + count);
        	
        	// Indicator for filterSearch()
        	filterAllSubjects = true;
        	allInitialized = true;
        	allCourses = v;
        	initializeCourseSet();
        	singleToAll();
        }
    	
    }
    
    /**
     * Enrollment status update from single subject search to All subjects
     */
    protected void singleToAll() {
    	// list: single scraped courses
    	// listAll: all scraped courses
    	
    	for(int i = 0; i < list.size(); i++) {
    		// if one course is selected
    		if(list.get(i).getEnroll().isSelected()) {
    			// Traverse all list
    			for(int j = 0; j < listAll.size(); j++) {
        			if(list.get(i).getCourseCode().equals(listAll.get(j).getCourseCode()) 
        					&& list.get(i).getSection().equals(listAll.get(j).getSection())) {
        				CheckBox temp = listAll.get(j).getEnroll();
        				temp.setSelected(true);
        				listAll.get(j).setEnroll(temp);
        			}
        		}
    		}
    		
    	}
    }

	/**
	 * Scrape and prints instructors' SFQ data
	 * <p>
	 * This function scrape data from the SFQ web site and 
	 * get the average SFQ data for each instructor on the
	 * web site.
	 * </p>
	 */
    @FXML
    void findInstructorSfq() {
    	textAreaConsole.setText("");
    	List<SFQinstructor> v = scraperSFQ.scrapeSFQinst(textfieldSfqUrl.getText());
    	
    	
    	// Check for 404 page not found
    	if (v.isEmpty()) {
    		textAreaConsole.setText("404 page not found. Check if the URL is correct.");
    		return;
    	}
    	if (!v.isEmpty()) {
        	String error = errorCheck(v.get(0).getName());
        	if (error != "No error") {
        		textAreaConsole.setText(error);
        		return;
        	}
    	}

    	
    	for (SFQinstructor inst : v) {
    		String newline = inst.getName() + " Course Overall Mean: " + inst.getCourseOverallMean() +
    				" Instructor Overall Mean: " + inst.getInstructorOverallMean() + 
    				" Response Rate: " + inst.getResponseRate() + "%";
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    }
    


	/**
	 * Scrape and print SFQ data for each course.
	 * <p>
	 * This function scrape data from the SFQ web site and 
	 * get the average SFQ data for each course on the web 
	 * site.
	 * </p>
	 */
    @FXML
    void findSfqEnrollCourse() {
    	textAreaConsole.setText("");
    	buttonSfqEnrollCourse.setDisable(DISABLED);
    	if (DISABLED)
    		return;
    	List<Course> v = scraperSFQ.scrapeSFQ(textfieldSfqUrl.getText());
    	
    	// Check for 404 page not found
    	if (v.isEmpty()) {
    		textAreaConsole.setText("404 page not found. Check if the URL is correct.");
    		return;
    	}
    	if (!v.isEmpty()) {
        	String error = errorCheck(v.get(0).getTitle());
        	if (error != "No error") {
        		textAreaConsole.setText(error);
        		return;
        	}
    	}

    	
    	String prev = "";
    	for (courseData d : list) {
    		if (!d.getEnroll().isSelected() || prev == d.getCourseCode())
    			continue;
    		for (Course c : v) {
    			if (d.getCourseCode().contentEquals(c.getTitle().substring(1))) {
    	    		prev = d.getCourseCode();
    				String newline = c.getTitle().substring(1) + " Course Overall Mean: " + c.getCourseOverallMean() +
    	    				" Instructor Overall Mean: " + c.getInstructorOverallMean() + 
    	    				" Response Rate: " + c.getResponseRate();
    	    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	    		break;
    			}
    		}
    	}    	
    }
    
    /**
     * Updating enrollment status from all subjects list to single subject list
     */
    protected void allToSingle() {
    	// list: single scraped courses
    	// listAll: all scraped courses
    	
    	for(int i = 0; i < listAll.size(); i++) {
    		// if one course is selected
    		if(listAll.get(i).getEnroll().isSelected()) {
    			// Traverse all list
    			for(int j = 0; j < list.size(); j++) {
        			if(listAll.get(i).getCourseCode().equals(list.get(j).getCourseCode()) 
        					&& listAll.get(i).getSection().equals(list.get(j).getSection())) {
        				CheckBox temp = list.get(j).getEnroll();
        				temp.setSelected(true);
        				list.get(j).setEnroll(temp);
        			}
        		}
    		}
    	}
    }
    
	/**
	 * Scrape and print data for each course
	 * <p>
	 * This function scrape data from the course catalog 
	 * web site to get the information for each section. 
	 * </p>
	 */
    @FXML
    void search() {
    	textAreaConsole.setText("");
    	// For disabling SFQ button until Search button is clicked
    	DISABLED = false;
    	buttonSfqEnrollCourse.setDisable(DISABLED);
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	// indicator for filterSearch()
    	filterAllSubjects = false;
    	initializeCourseSet();
    	if(allInitialized)
    		allToSingle();
    	
    	// Check for 404 page not found
    	if (!v.isEmpty()) {
        	String error = errorCheck(v.get(0).getTitle());
        	if (error != "No error") {
        		textAreaConsole.setText(error);
        		return;
        	}
    	}
    	
    	PriorityQueue<String> instructors = new PriorityQueue<String>();
    	
    	// Add all instructors to the list
    	for (Course c : v) {
    		for(int i = 0; i < c.getNumSlots(); ++i) {
    			for(int j = 0; j < c.getSlot(i).getInstNum(); ++j) {
    				if(instructors.contains(c.getSlot(i).getInstructor(j))) {
    					continue;
    				}
    				else {
    					instructors.add(c.getSlot(i).getInstructor(j));
    				}
    			}
    		}
    	}
    	    	
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			Section x = c.getSection(i);
    			newline += "Slot " + i + ":" + t + " Section " + x.toString() + "\n";
    			
    			// Display only the instructors who does not have teaching assignment at Tu 3:10 pm
    			LocalTime time = LocalTime.of(15, 10);
    			if(t.getDay() == 1 && t.getStart().isBefore(time) && t.getEnd().isAfter(time)) {
    				// remove from the instructor list
    				for(int j = 0; j < t.getInstNum(); ++j) {
    					instructors.remove(t.getInstructor(j));
    				}
    			}
    			
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
    	// Calculates total number of courses and sections
    	int numCourses = 0;
    	int numSections = 0;
    	for (Course c : v) {
    		numSections += c.getNumSections();
    		for(int i = 0; i < c.getNumSlots(); ++i) {
    			if (c.getSection(i).getCode().charAt(0) == 'L' ||
    					c.getSection(i).getCode().charAt(0) == 'T' ||
    					c.getSection(i).getCode().substring(0,1) == "LA") {
    				++numCourses;
    				break;
    			}
    		}
    	}
    	
    	// Print total number of sections
    	String secNum = "Total Number of different sections in this search: " + Integer.toString(numSections) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + secNum);
    	
    	// Print total number of courses
    	String num = "Total Number of Courses in this search: " + Integer.toString(numCourses) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + num);
    	
    	// Print instructors on the console
    	String line = "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: \n";
    	while(!instructors.isEmpty()) {
    		line += "\"" + instructors.remove() + "\", ";
    	}
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + line);
    	
    	
    	
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	
    	
    	
    }

}
