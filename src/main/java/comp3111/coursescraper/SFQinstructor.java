package comp3111.coursescraper;

// This class is merely used for storing SFQ data of the instructors.
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
	
	public SFQinstructor(String name) {
		this.courseOverallMean = new double[DEFAULT_MAX_SECTION];
		this.instructorOverallMean = new double[DEFAULT_MAX_SECTION];
		this.responseRate = new double[DEFAULT_MAX_SECTION];
		
		this.numCOM = 0;
		this.numIOM = 0;
		this.numRR = 0;

		this.name = name;
	}
	
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
	
	public void COMaverage() {
		double totalCOM = 0;
		for(int i = 0; i < numCOM; ++i) {
			totalCOM +=courseOverallMean[i];
		}
		totalCourseOverallMean = totalCOM / numCOM;
	}
	
	public void IOMaverage() {
		double totalIOM = 0;
		for(int i = 0; i < numIOM; ++i) {
			totalIOM += instructorOverallMean[i];
		}
		totalInstructorOverallMean = totalIOM / numIOM;
	}
	
	public void RRaverage() {
		double totalRR = 0;
		for(int i = 0; i < numRR; ++i) {
			totalRR += responseRate[i];
		}
		totalResponseRate = totalRR / numRR;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCourseOverallMean() {
		if(numCOM == 0)
			return "-";
		else
			return String.format("%.1f", totalCourseOverallMean);
//			return Double.toString(totalCourseOverallMean);
	}
	
	public String getInstructorOverallMean() {
		if(numIOM == 0)
			return "-";
		else
			return String.format("%.1f", totalInstructorOverallMean);
	}
	
	public String getResponseRate() {
		if(numRR == 0)
			return "-";
		else
			return String.format("%.1f", totalResponseRate);
	}
	
}
