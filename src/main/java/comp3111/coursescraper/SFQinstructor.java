package comp3111.coursescraper;

/**
 * <p>
 * A class for storing SFQ data for each instructor. SFQ data includes course overall mean, 
 * instructor overall mean, response rate.
 * </p>
 * 
 * @author HWANG, Junyeol
 * 
 */
public class SFQinstructor {
	private static final int DEFAULT_MAX_SECTION = 40;
	
	private double [] courseOverallMean;
	private double [] instructorOverallMean;
	private double [] responseRate;
	private double totalCourseOverallMean;
	private double totalInstructorOverallMean;
	private double totalResponseRate;
	private int numCOM;
	private int numIOM;
	private int numRR;
	private String name;
	
	/**
	 * @param name is the name of the instructor and stored as name.
	 */
	public SFQinstructor(String name) {
		this.courseOverallMean = new double[DEFAULT_MAX_SECTION];
		this.instructorOverallMean = new double[DEFAULT_MAX_SECTION];
		this.responseRate = new double[DEFAULT_MAX_SECTION];
		
		this.numCOM = 0;
		this.numIOM = 0;
		this.numRR = 0;

		this.name = name;
	}
	
	/**
	 * Add a course overall mean value for the instructor
	 * @param COM is the Course overall mean to be added for this instructor
	 */
	public void SFQaddCOM(String COM) {
		double dCOM;
		if(COM.contains("(")) {
			dCOM = Double.valueOf(COM.substring(0,3));
		}
		else {
			dCOM = Double.valueOf(COM.substring(0,4));
		}
		this.courseOverallMean[numCOM] = dCOM;
		++numCOM;
		COMaverage();
	}
	
	/**
	 * Add an instructor overall mean value for the instructor
	 * @param IOM is the Instructor overall mean to be added for this instructor
	 */
	public void SFQaddIOM(String IOM) {
		double dIOM;
		if(IOM.contains("(")) {
			dIOM = Double.valueOf(IOM.substring(0,3));
		}
		else {
			dIOM = Double.valueOf(IOM.substring(0,4));
		}
		this.instructorOverallMean[numIOM] = dIOM;
		++numIOM;
		IOMaverage();
	}
	
	/**
	 * Add a response rate value for the instructor
	 * @param RR is the Response rate to be added for this instructor
	 */
	public void SFQaddRR(String RR) {
		double dRR;
		if(RR.contains("%")) {
			dRR = Double.valueOf(RR.substring(0,3));
		}
		else {
			dRR = Double.valueOf(RR.substring(0,4));
		}
		this.responseRate[numRR] = dRR;
		++numRR;
		RRaverage();
	}
	
	/**
	 * Calculate the average of the Course overall mean and save it as totalCourseOverallMean
	 */
	public void COMaverage() {
		double totalCOM = 0;
		for(int i = 0; i < numCOM; ++i) {
			totalCOM +=courseOverallMean[i];
		}
		totalCourseOverallMean = totalCOM / numCOM;
	}
	
	/**
	 * Calculate the average of the Instructor overall mean and save it as totalInstructorOverallMean
	 */
	public void IOMaverage() {
		double totalIOM = 0;
		for(int i = 0; i < numIOM; ++i) {
			totalIOM += instructorOverallMean[i];
		}
		totalInstructorOverallMean = totalIOM / numIOM;
	}
	
	/**
	 * Calculate the average of the Response rate and save it as totalResponseRate
	 */
	public void RRaverage() {
		double totalRR = 0;
		for(int i = 0; i < numRR; ++i) {
			totalRR += responseRate[i];
		}
		totalResponseRate = totalRR / numRR;
	}
	
	/**
	 * Get the name
	 * @return the name of the instructor
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the average course overall mean
	 * 
	 * @return the average course overall mean
	 */
	public String getCourseOverallMean() {
		if(numCOM == 0)
			return "-";
		else
			return String.format("%.1f", totalCourseOverallMean);
//			return Double.toString(totalCourseOverallMean);
	}
	
	/**
	 * Get the average instructor overall mean
	 * @return the average instructor overall mean
	 */
	public String getInstructorOverallMean() {
		if(numIOM == 0)
			return "-";
		else
			return String.format("%.1f", totalInstructorOverallMean);
	}
	
	/**
	 * Get the average response rate
	 * @return the average response rate
	 */
	public String getResponseRate() {
		if(numRR == 0)
			return "-";
		else
			return String.format("%.1f", totalResponseRate);
	}
	
}
