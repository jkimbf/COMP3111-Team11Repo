package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

public class Task_2_3_Test extends ApplicationTest {
	
	@Test
	public void testSetTitle() {
		Course i = new Course();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	@Test
	public void testSetExclusion() {
		Course i = new Course();
		i.setExclusion("ABC");
		assertEquals(i.getExclusion(), "ABC");
	}
	
	@Test
	public void testSetCC4Y() {
		Course i = new Course();
		i.setCC4Y("null");
		assertEquals(i.getCC4Y(), "null");
	}
	
	@Test
	public void testGetNumSlotsForSec() {
		Course i = new Course();
		Section s1 = new Section(); s1.setCode("L1");
		Section s2 = new Section(); s2.setCode("L1");
		Section s3 = new Section(); s3.setCode("L2");
		i.addSection(s1);
		i.addSection(s2);
		i.addSection(s3);
		assertEquals(i.getNumSlotsForSec("L1"), 2);
	}
	
	@Test
	public void testGetSectionIndex() {
		Course i = new Course();
		Section s1 = new Section(); s1.setCode("L1");
		Section s2 = new Section(); s2.setCode("L2");
		Section s3 = new Section(); s3.setCode("L1");
		i.addSection(s1);
		i.addSection(s2);
		i.addSection(s3);
		int[] output = new int[2];
		i.getSectionIndex("L1", output);
		assertEquals(output[0], 0);
		assertEquals(output[1], 2);
	}
	
	@Test
	public void testGetDaysOfSection() {
		Course i = new Course();
		Section s1 = new Section(); s1.setCode("L2");
		Section s2 = new Section(); s2.setCode("L1");
		Section s3 = new Section(); s3.setCode("L1");
		Slot sl1 = new Slot(); sl1.setDay(3);
		Slot sl2 = new Slot(); sl2.setDay(2);
		Slot sl3 = new Slot(); sl3.setDay(4);
		i.addSection(s1); i.addSlot(sl1);
		i.addSection(s2); i.addSlot(sl2);
		i.addSection(s3); i.addSlot(sl3);
		
		int[] output = new int[2];
		i.getDaysOfSection("L1", output);
		assertEquals(output[0], 2);
		assertEquals(output[1], 4);
	}
	
	@Test
	public void testSetCourseCode_courseData() {
		courseData i = new courseData();
		i.setCourseCode("COMP3111");
		assertEquals(i.getCourseCode(), "COMP3111");
	}
	
	@Test
	public void testSetSection_courseData() {
		courseData i = new courseData();
		i.setSection("L2");
		assertEquals(i.getSection(), "L2");
	}
	
	@Test
	public void testSetName_courseData() {
		courseData i = new courseData();
		i.setName("Software Engineering");
		assertEquals(i.getName(), "Software Engineering");
	}
	
	@Test
	public void testSetInstructor_courseData() {
		courseData i = new courseData();
		i.setInstructor("Kenneth Wai-Ting LEUNG");
		assertEquals(i.getInstructor(), "Kenneth Wai-Ting LEUNG");
	}
	
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
	public void testSetEnroll_courseData() {
		courseData i = new courseData();
		CheckBox tempBox = new CheckBox();
		tempBox.setSelected(true);
		i.setEnroll(tempBox);
		assertEquals(i.getEnroll().isSelected(), true);
	}
	
	@Test
	public void testSelectAll_button() {
		clickOn("#tabFilter");
		clickOn("#selectAllButton");
		Button b = (Button)s.lookup("#selectAllButton");
		sleep(3000);
		assertEquals(b.getText(), "De-select All");
	}
	
	@Test
	public void testSelectAll_checkboxes() {
		clickOn("#tabFilter");
		clickOn("#selectAllButton");
		CheckBox checkAM = (CheckBox)s.lookup("#checkAM");
		CheckBox checkPM = (CheckBox)s.lookup("#checkPM");
		CheckBox checkMon = (CheckBox)s.lookup("#checkMon");
		CheckBox checkTue = (CheckBox)s.lookup("#checkTue");
		CheckBox checkWed = (CheckBox)s.lookup("#checkWed");
		CheckBox checkThu = (CheckBox)s.lookup("#checkThu");
		CheckBox checkFri = (CheckBox)s.lookup("#checkFri");
		CheckBox checkSat = (CheckBox)s.lookup("#checkSat");
		CheckBox checkCC = (CheckBox)s.lookup("#checkCC");
		CheckBox checkNoEx = (CheckBox)s.lookup("#checkNoEx");
		CheckBox checkLAT = (CheckBox)s.lookup("#checkLAT");
		sleep(3000);
		assertTrue(checkAM.isSelected() && checkPM.isSelected() && checkMon.isSelected() 
				&& checkTue.isSelected() && checkWed.isSelected() && checkThu.isSelected()
				&& checkFri.isSelected() && checkSat.isSelected() && checkCC.isSelected() 
				&& checkNoEx.isSelected() && checkLAT.isSelected());
	}
	
	@Test
	public void testDeSelectAll_button() {
		clickOn("#tabFilter");
		clickOn("#selectAllButton");
		clickOn("#selectAllButton");
		Button b = (Button)s.lookup("#selectAllButton");
		sleep(3000);
		assertEquals(b.getText(), "Select All");
	}
	
	@Test
	public void testDeSelectAll_checkboxes() {
		clickOn("#tabFilter");
		clickOn("#selectAllButton");
		clickOn("#selectAllButton");
		CheckBox checkAM = (CheckBox)s.lookup("#checkAM");
		CheckBox checkPM = (CheckBox)s.lookup("#checkPM");
		CheckBox checkMon = (CheckBox)s.lookup("#checkMon");
		CheckBox checkTue = (CheckBox)s.lookup("#checkTue");
		CheckBox checkWed = (CheckBox)s.lookup("#checkWed");
		CheckBox checkThu = (CheckBox)s.lookup("#checkThu");
		CheckBox checkFri = (CheckBox)s.lookup("#checkFri");
		CheckBox checkSat = (CheckBox)s.lookup("#checkSat");
		CheckBox checkCC = (CheckBox)s.lookup("#checkCC");
		CheckBox checkNoEx = (CheckBox)s.lookup("#checkNoEx");
		CheckBox checkLAT = (CheckBox)s.lookup("#checkLAT");
		sleep(3000);
		assertFalse(checkAM.isSelected() || checkPM.isSelected() || checkMon.isSelected() 
				|| checkTue.isSelected() || checkWed.isSelected() || checkThu.isSelected()
				|| checkFri.isSelected() || checkSat.isSelected() || checkCC.isSelected() 
				|| checkNoEx.isSelected() || checkLAT.isSelected());
	}
	
	@Test
	public void testAMCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains("COMP 1001"));
	}
	
	@Test
	public void testPMCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkPM");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1001"));
	}
	
	@Test
	public void testAMPMCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkPM");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("11:30-13:20"));
		assertTrue(output.contains("COMP 2611"));
	}
	
	@Test
	public void testMondayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkMon");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Tu") || output.contains(":We") 
				|| output.contains(":Th") || output.contains(":Fr")
				|| output.contains(":Sa"));
	}
	
	@Test
	public void testTuesdayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkTue");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Mo") || output.contains(":We") 
				|| output.contains(":Th") || output.contains(":Fr")
				|| output.contains(":Sa"));
	}
	
	@Test
	public void testWednesdayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkWed");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Mo") || output.contains(":Tu") 
				|| output.contains(":Th") || output.contains(":Fr")
				|| output.contains(":Sa"));
	}
	
	@Test
	public void testThursdayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkThu");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Mo") || output.contains(":Tu") 
				|| output.contains(":We") || output.contains(":Fr")
				|| output.contains(":Sa"));
	}
	
	@Test
	public void testFridayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkFri");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Mo") || output.contains(":Tu") 
				|| output.contains(":We") || output.contains(":Th")
				|| output.contains(":Sa"));
	}
	
	@Test
	public void testSaturdayCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkSat");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Mo") || output.contains(":Tu") 
				|| output.contains(":We") || output.contains(":Th")
				|| output.contains(":Fr"));
	}
	
	@Test
	public void testCommonCoreCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkCC");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1001"));
		assertFalse(output.contains("COMP 2011"));
	}
	
	@Test
	public void testNoExclusionCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkNoEx");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1943"));
		assertFalse(output.contains("COMP 1021"));
	}
	
	@Test
	public void testLabTutorialCheckBox() {
		clickOn("#tabFilter");
		clickOn("#checkLAT");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1001"));
		assertFalse(output.contains("COMP 3632"));
	}
	
	@Test
	public void testFilterSearch1() {
		clickOn("#tabFilter");
		clickOn("#checkMon");
		clickOn("#checkWed");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains(":Tu") || output.contains(":Th") 
				|| output.contains(":Fr") || output.contains(":Sa"));
	}
	
	@Test
	public void testFilterSearch2() {
		clickOn("#tabFilter");
		clickOn("#checkNoEx");
		clickOn("#checkCC");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1943"));
		assertFalse(output.contains("COMP 2011"));
	}
	
	@Test
	public void testFilterSearch3() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkPM");
		clickOn("#checkFri");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertTrue(output.contains("COMP 1021"));
		assertFalse(output.contains("COMP 2611"));
	}
	
	@Test
	public void testFilterSearch4() {
		clickOn("#tabFilter");
		clickOn("#checkMon");
		clickOn("#checkTue");
		clickOn("#checkWed");
		clickOn("#checkThu");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		sleep(3000);
		String output = textAreaConsole.getText();
		assertFalse(output.contains("COMP"));
	}
	
	@Test
	public void testInitializeCourseSet() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		assertTrue(list.get(0).getCourseCode().contains("COMP"));
	}
	
	@Test
	public void testNewCourseSet() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		assertTrue(list.get(0).getCourseCode().contains("COMP"));
		clickOn("#tabMain");
		TextField subjectBox = (TextField)m.get("textfieldSubject");
		subjectBox.clear();
		subjectBox.setText("ELEC");
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TableView<courseData> courseTable2 = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list2 = courseTable2.getItems();
		assertTrue(list2.get(0).getCourseCode().contains("ELEC"));
	}
	
	@Test
	public void testEnrollConsistency() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		list.get(0).getEnroll().setSelected(true);
		
		clickOn("#checkAM");
		courseTable = (TableView<courseData>)m.get("courseTable");
		list = courseTable.getItems();
		boolean result = false;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getEnroll().isSelected())
				result = true;
		}
		assertTrue(result);
	}
	
	@Test
	public void testEnrollmentOutput() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		
		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		
		clickOn("#enroll0");
		clickOn("#enroll2");
		sleep(1000);
		
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertTrue(output.contains(list.get(0).getCourseCode()));
		assertTrue(output.contains(list.get(2).getCourseCode()));
	}
	
	@Test
	public void testEnrollmentCancel() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		
		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		
		clickOn("#enroll0");
		clickOn("#enroll2");
		clickOn("#enroll0");
		sleep(1000);
		
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertFalse(output.contains(list.get(0).getCourseCode()));
		assertTrue(output.contains(list.get(2).getCourseCode()));
	}
	
	@Test
	public void testInitializeCourseSet_allSub() {
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		clickOn("#allSubjectSearch");
		clickOn("#tabFilter");
		clickOn("#checkAM");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		assertTrue(list.get(0).getCourseCode().contains("AESF"));
		assertTrue(list.get(5).getCourseCode().contains("BIBU"));
		assertTrue(list.get(6).getCourseCode().contains("BIEN"));
	}
	
	@Test
	public void testEnrollmentOutput_allSub() {
		clickOn("#tabAllSubject");
		clickOn("#allSubjectSearch");
		clickOn("#allSubjectSearch");
		
		clickOn("#tabFilter");
		clickOn("#checkAM");
		clickOn("#checkAM");
		
		clickOn("#tabList");
		TableView<courseData> courseTable = (TableView<courseData>)m.get("courseTable");
		ObservableList<courseData> list = courseTable.getItems();
		
		clickOn("#enroll0");
		clickOn("#enroll2");
		sleep(1000);
		
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		
		String output = textAreaConsole.getText();
		assertTrue(output.contains(list.get(0).getCourseCode()));
		assertTrue(output.contains(list.get(2).getCourseCode()));
	}
}
