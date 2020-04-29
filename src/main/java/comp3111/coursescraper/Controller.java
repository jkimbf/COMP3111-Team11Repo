package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.Random;
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
    
    /*public List<courseData> courseDataSet = null;
    
    @FXML
    public void initializeCourseSet() {
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	int index = 0;
    	for(int i = 0; i < v.size(); i++) {
    		String[] courseInfo = v.get(i).getTitle().split("-");
    		String courseCode = courseInfo[0];
    		String name = courseInfo[1];
    		
    		for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    			String section = v.get(i).getSection(j).getCode();
    			String instructor = "Steve Kim";
    			CheckBox ch = new CheckBox();
    			courseDataSet.add(index++, new courseData(courseCode, section, name, instructor, ch));
    		}
    		
    	}
    }*/
    
    @FXML
    void selectAll() {
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
    @FXML
    void filterSearch() {
    	textAreaConsole.setText("");
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	
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
    			
    			String prevCode = "";
    			for(int i = 0; i < v.size(); i++) {
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
    			
    			if(containsLAT) {
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++) {
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
    	
    	// Calculates total number of courses and sections
    	int numSections = 0;
    	for (Course c : v) {
    		numSections += c.getNumSections();
    	}
    	
    	// Print total number of sections
    	String secNum = "Number of sections: " + Integer.toString(numSections) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + secNum);
    	
    	// Print total number of courses
    	String num = "Number of courses: " + Integer.toString(v.size()) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + num);
    	
    	listFilteredCourses(v);
    }
    
    public void listFilteredCourses(List<Course> v) {
    	list.clear();
    	for(int i = 0; i < v.size(); i++) {
    		String[] courseInfo = v.get(i).getTitle().split("-", 2);
    		String courseCode = courseInfo[0];
    		String name = courseInfo[1].substring(0, courseInfo[1].indexOf('(')-1);
    		
    		for(int j = 0; j < v.get(i).getNumSlots(); j++) {
    			String section = v.get(i).getSection(j).getCode();
    			String instructor = "";
    			int k = 0;
    			while(k < v.get(i).getSlot(j).getInstNum()-1)
    				instructor += (v.get(i).getSlot(j).getInstructor(k++)+" / ");
    			instructor += v.get(i).getSlot(j).getInstructor(k);
    			CheckBox ch = new CheckBox();
    			list.add(new courseData(courseCode, section, name, instructor, ch));
    		}
    		
    	}
    	/*for(int i = 0; i < courseDataSet.size(); i++) {
    		list.add(courseDataSet.get(i));
    	}*/
    	
    	courseTable.setItems(list);
    	codeCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("courseCode"));
    	sectionCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("section"));
    	nameCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("name"));
    	instructorCol.setCellValueFactory(new PropertyValueFactory<courseData,String>("instructor"));
    	enrollCol.setCellValueFactory(new PropertyValueFactory<courseData,CheckBox>("enroll"));
    }
    
    @FXML
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }
    
    // Should be true
	private boolean DISABLED = true;

    @FXML
    void findSfqEnrollCourse() {
    	// AllsubjectSearch condition to be implemented
    	buttonSfqEnrollCourse.setDisable(DISABLED);
    	if (DISABLED)
    		return;
    	List<Course> v = scraperSFQ.scrapeSFQ(textfieldSfqUrl.getText());
    	for (Course c: v) {
    		String newline = c.getTitle() + " Course Overall Mean: " + c.getCourseOverallMean() +
    				" Instructor Overall Mean: " + c.getInstructorOverallMean() + 
    				" Response Rate: " + c.getResponseRate();
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}    	
    }

    @FXML
    void search() {
    	textAreaConsole.setText("");
    	// For diabling SFQ button until Search button is clicked
    	// AllsubjectSearch condition to be implemented
    	DISABLED = false;
    	buttonSfqEnrollCourse.setDisable(DISABLED);
    	
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	PriorityQueue<String> instructors = new PriorityQueue<String>();
    	
    	// Add all instructors to the list
    	for (Course c : v) {
    		for(int i = 0; i < c.getNumSlots(); ++i) {
    			for(int j = 0; j < c.getSlot(i).getInstNum(); ++j) {
    				if(instructors.contains(c.getSlot(i).getInstructor(j))) {
//    					System.out.println("exists " + c.getSlot(i).getInstructor(j));
    					continue;
    				}
    				else {
    					instructors.add(c.getSlot(i).getInstructor(j));
//    					System.out.println("added " + c.getSlot(i).getInstructor(j));
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
    			
    			// Modify instructors
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
    	String secNum = "Number of sections: " + Integer.toString(numSections) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + secNum);
    	
    	// Print total number of courses
    	String num = "Number of courses: " + Integer.toString(numCourses) + "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + "\n" + num);
    	
    	// Print instructors on the console
    	String line = "Instructors free: ";
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
