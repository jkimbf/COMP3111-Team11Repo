package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;


public class Task_1_and_6 extends ApplicationTest {
	

	private Scene s;
	
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
	}
	
	// Task 1
	
	@Test
	public void testSectionID() {
		Section s = new Section();
		s.setID("sampleID");
		assertEquals(s.getID(), "sampleID");
	}
	
	@Test
	public void testSectionCode() {
		Section s = new Section();
		s.setCode("sampleCode");
		assertEquals(s.getCode(), "sampleCode");
	}
	
	@Test
	public void testSectionToString() {
		Section s = new Section();
		s.setID("sampleID");
		s.setCode("sampleCode");
		assertEquals(s.toString(), "sampleCode sampleID");
	}
	
	@Test
	public void testSectionClone() {
		Section s = new Section();
		s.setID("sampleID");
		s.setCode("sampleCode");
		Section x = s.clone();
		assertEquals(s.toString(), x.toString());
	}
	
	@Test
	public void testCourseAddSection1() {
		Course c = new Course();
		Section s = new Section();
		s.setCode("sampleCode");
		c.addSection(s);
		assertEquals(c.getSection(0).getCode(), s.getCode());
	}
	
	@Test
	public void testCourseAddSection2() {
		Course c = new Course();
		for (int i = 0; i < 50; ++i) {
			Section s = new Section();
			s.setID(Integer.toString(i));
			c.addSection(s);
		}

		assertEquals(c.getNumSections(), 40);
	}
	
	@Test
	public void testCourseSetSectionNumber() {
		Course c = new Course();
		Section s = new Section();
		c.addSection(s);
		c.addOneToActualNumSec();
		c.addOneToActualNumSec();
		c.subtractOneToActualNumSec();

		assertEquals(c.getNumSections(), 2);
	}
	
	@Test
	public void testCourseGetSection() {
		Course c = new Course();
		Section s1 = new Section();
		Section s2 = new Section();
		s1.setID("ID1");
		s2.setID("ID2");
		c.addSection(s1);
		c.addSection(s2);

		assertNotEquals(c.getSection(0).getID(), c.getSection(1).getID());
	}
	
	@Test
	public void testCourseOverallMean() {
		Course c = new Course();
		c.setCourseOverallMean("100");

		assertEquals(c.getCourseOverallMean(), "100");
	}
	
	@Test
	public void testCourseInstructorOverallMean() {
		Course c = new Course();
		c.setInstructorOverallMean("100");

		assertEquals(c.getInstructorOverallMean(), "100");
	}
	
	@Test
	public void testCourseResponseRate() {
		Course c = new Course();
		c.setResponseRate("100");

		assertEquals(c.getResponseRate(), "100");
	}
	
	
	// Task 2
	
	@Test
	public void testSFQinstructorConstructor() {
		SFQinstructor inst = new SFQinstructor("Junyeol");

		assertEquals(inst.getName(), "Junyeol");
	}
	
	@Test
	public void testSFQinstructorCourseOverallMean1() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddCOM("31.6");

		assertEquals(inst.getCourseOverallMean(), "31.6");
	}
	
	@Test
	public void testSFQinstructorCourseOverallMean2() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddCOM("1.6(");

		assertEquals(inst.getCourseOverallMean(), "1.6");
	}
	
	@Test
	public void testSFQinstructorCourseOverallMean3() {
		SFQinstructor inst = new SFQinstructor("Junyeol");

		assertEquals(inst.getCourseOverallMean(), "-");
	}
	
	@Test
	public void testSFQinstructorOverallMean() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddIOM("31.6");

		assertEquals(inst.getInstructorOverallMean(), "31.6");
	}
	
	@Test
	public void testSFQinstructorOverallMean2() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddIOM("1.6(");

		assertEquals(inst.getInstructorOverallMean(), "1.6");
	}
	
	@Test
	public void testSFQinstructorOverallMean3() {
		SFQinstructor inst = new SFQinstructor("Junyeol");

		assertEquals(inst.getInstructorOverallMean(), "-");
	}
	
	@Test
	public void testSFQinstructorResponseRate() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddRR("31.6");

		assertEquals(inst.getResponseRate(), "31.6");
	}
	
	@Test
	public void testSFQinstructorResponseRate2() {
		SFQinstructor inst = new SFQinstructor("Junyeol");
		inst.SFQaddRR("1.6%");

		assertEquals(inst.getResponseRate(), "1.6");
	}
	
	@Test
	public void testSFQinstructorResponseRate3() {
		SFQinstructor inst = new SFQinstructor("Junyeol");

		assertEquals(inst.getResponseRate(), "-");
	}
	
	@Test
	public void testScrapeSFQ() {
		clickOn("#buttonSearch");
		clickOn("#tabSfq");
		clickOn("#buttonSfqEnrollCourse");
		
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		
		
		assertFalse(b.isDisabled());
	}
	
	@Test
	public void testScrapeSFQinstructors() {
		clickOn("#tabSfq");
		clickOn("#buttonInstructorSfq");
		
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		TextArea textAreaConsole = (TextArea)s.lookup("#textAreaConsole");
		String output = textAreaConsole.getText();
		
		
		assertNotEquals(output, "404 page not found. Check if the URL is correct.");
	}	
	
	
	
	
	


}
