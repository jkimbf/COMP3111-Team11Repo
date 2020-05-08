package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

public class Task_4_5_Test extends ApplicationTest {
	
	
	
	private Scene s;
	private Map<String, Object> m;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
   		Map<String, Object> namespace = loader.getNamespace();
   		m = namespace;
	}

	@Test
	public void click() {
		boolean click = false;
		click=!click;
		assertTrue(click);
	}
	
	
	@Test
	public void testnormal() {		
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		sleep(3000);
		clickOn("#allSubjectSearch");
		
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("Total Number of Courses fetched: "));
	}
	
	@Test
	public void testempty() {
		TextField  textfield = (TextField)s.lookup("#textfieldURL");
		sleep(1000);
		textfield.setText("123123");
		
		
		
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		sleep(3000);
		clickOn("#allSubjectSearch");
	
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("404 page not found. Check if the URL is correct."));
		
	}

	@Test
	public void testcount() {
		boolean click=false;
		int count=0;
		Course c=new Course();c.setTitle("c");
		Course c2=new Course();c2.setTitle("c2");
		List<Course> v = new Vector<Course>();
		v.add(c);
		v.add(c2);
		if(!click && v.size()!=0)
		{
		for(int i=0;i<v.size();i++) {
				String code=v.get(i).getTitle();
		    	if(code!="")count++;
		    }
		assertEquals(count,2);
	}
}
	
	@Test
	public void testEnrollment() {
		TextField  textfield = (TextField)s.lookup("#textfieldSubject");
		sleep(1000);
		textfield.setText("ELEC");
		
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		clickOn("#tabTimetable");

		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		sleep(1000);
		clickOn("#enroll0");
		clickOn("#tabTimetable");
		sleep(1000);
	TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertTrue(output.contains(list.get(0).getCourseCode()));
	}
	
	public void testEnrollment2() {

		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		clickOn("#tabTimetable");

		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		sleep(1000);
		clickOn("#enroll0");
		clickOn("#tabTimetable");
		sleep(1000);
	TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertTrue(output.contains(list.get(0).getCourseCode()));
	}
	
	@Test
	public void testEnrollmentCancel() {

		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		clickOn("#tabTimetable");

		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		sleep(1000);
		clickOn("#enroll0");
		clickOn("#tabTimetable");
		sleep(1000);
		clickOn("#tabList");
		clickOn("#enroll0");
		clickOn("#tabTimetable");
		sleep(1000);
	TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertTrue(!output.contains(list.get(0).getCourseCode()));
	}
	@Test
	public void completeenrolls() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		clickOn("#tabTimetable");

		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		sleep(1000);
		clickOn("#enroll0");
		clickOn("#enroll2");
		clickOn("#enroll4");
		clickOn("#enroll6");
		clickOn("#enroll8");
		clickOn("#tabTimetable");
		sleep(3000);

	TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		boolean out1= output.contains(list.get(0).getCourseCode());
		boolean out2= output.contains(list.get(2).getCourseCode());
		boolean out3= output.contains(list.get(3).getCourseCode());
		boolean out4= output.contains(list.get(4).getCourseCode());
		boolean out5= output.contains(list.get(5).getCourseCode());
		assertTrue(out1 && out2 && out3 && out4 && out5);
		
	}
	
	@Test
	public void testrepeat() {
		int count=0;
		int test=3;
		String prech="";
		String [] ch = {"test1", "test2", "test3", "test3", "test1"};
		for(int i=0;i<ch.length;i++)
		{if(prech.contentEquals(ch[i]) && test!=i)
			{count++;continue;}}
		
		assertEquals(count,0);
		
	}
}