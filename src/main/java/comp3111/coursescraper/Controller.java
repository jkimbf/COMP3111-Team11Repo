package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
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
