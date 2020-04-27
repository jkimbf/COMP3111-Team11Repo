package comp3111.coursescraper;

import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
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
import java.util.List;
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
    	
    	// AM & PM Check boxes
    	if(checkAM.isSelected() && checkPM.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			for(int j = 0; j < v.get(i).getNumSlots(); j++)
    				if((v.get(i).getSlot(j).getStartHour() >= 0 && v.get(i).getSlot(j).getStartHour() < 12) && 
    						(v.get(i).getSlot(j).getEndHour() >= 12 && v.get(i).getSlot(j).getEndHour() < 24))
    					temp.addSlot(v.get(i).getSlot(j));
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
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++)
	    				if((v.get(i).getSlot(j).getStartHour() >= 0 && v.get(i).getSlot(j).getStartHour() < 12) || 
	    						(v.get(i).getSlot(j).getEndHour() >= 0 && v.get(i).getSlot(j).getEndHour() < 12))
	    					temp.addSlot(v.get(i).getSlot(j));
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
	    			for(int j = 0; j < v.get(i).getNumSlots(); j++)
	    				if((v.get(i).getSlot(j).getStartHour() >= 12 && v.get(i).getSlot(j).getStartHour() < 24) || 
	    						(v.get(i).getSlot(j).getEndHour() >= 12 && v.get(i).getSlot(j).getEndHour() < 24))
	    					temp.addSlot(v.get(i).getSlot(j));
	    			v.remove(i);
	    			v.add(i, temp);
	    		}
	    	}
    	}
    	
    	if(checkNoEx.isSelected()) {
    		for(int i = 0; i < v.size(); i++) {
    			Course temp = new Course();
    			temp.setTitle(v.get(i).getTitle());
    			temp.setDescription(v.get(i).getDescription());
    			temp.setExclusion(v.get(i).getExclusion());
    			for(int j = 0; j < v.get(i).getNumSlots(); j++)
    				if(v.get(i).getExclusion() == "null")
    					temp.addSlot(v.get(i).getSlot(j));
    			v.remove(i);
    			v.add(i, temp);
    		}
    	}
    	
    	for (Course c : v) {
    		String newline = "";
    		int count = 0;
    		
    		//Need to figure out how to handle all conditions
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			//if(t.getDay() == 2) {
    				newline += "Slot " + i + ":" + t + "\n";
    				count++;
    			//}
    		}
    		
    		//check whether slots exist after filtering
    		if(count != 0) {
    			String newTitle = c.getTitle() + "\n";
    			newTitle += newline;
    			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newTitle);
    		}
    	}
    }
    
    @FXML
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

    @FXML
    void search() {
    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	for (Course c : v) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSlots(); i++) {
    			Slot t = c.getSlot(i);
    			newline += "Slot " + i + ":" + t + "\n";
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
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
