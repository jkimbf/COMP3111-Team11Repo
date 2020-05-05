package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private String instructors[];
	private int instNum;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}

	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.instructors = this.instructors;
		s.instNum = this.instNum;
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		return s;
	}
	public String toString() {
		return DAYS[day] + start.toString() + "-" + end.toString() + ":" + venue;
	}
	public int getStartHour() {
		return start.getHour();
	}
	public int getStartMinute() {
		return start.getMinute();
	}
	public int getEndHour() {
		return end.getHour();
	}
	public int getEndMinute() {
		return end.getMinute();
	}
	/**
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	// initialize instructors
	public void setInstructor(int size) {
		instructors = new String[size];
		instNum = 0;
	}
	
	// Add instructor
	public void addInstructor(String x) {
		instructors[instNum] = x;
		++instNum;
	}
	
	public String getInstructor(int i) {
		return instructors[i];
	}
	
	public int getInstNum() {
		return instNum;
	}
	
	// Print instructors for debugging. Should be deleted later
	public void printInst() {
//		for(String inst : instructors) {
//			System.out.println(inst);
//			System.out.println('\n');
//		}
		for (int i = 0; i < instructors.length; ++i) {
			System.out.println(getInstructor(i));
			System.out.println('\n');
		}
	}
	

}
