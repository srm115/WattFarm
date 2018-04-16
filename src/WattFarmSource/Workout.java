package WattFarmSource;

import java.sql.Date;

public class Workout {
	//all variables are arrays to handle interval workout.
	private double[] distance;
	private double[] time;
	private double[] split;
	private double[] avgWatt;
	private int[] spm;
	private Date datePerformed; //year, month, date format
	private int userID; //ID of the athlete who performed workout
	private int ID;
	private String tag; //what kind of workout it is
	
	//justForTesting
	public Workout() {
		
	}
	
	public Workout(double[] distance, double[] time, double[] split, double[] avgWatt, int[] spm, Date datePerformed, int userID, int ID) {
		this.distance = distance;
		this.time = time;
		this.split = split;
		this.avgWatt = avgWatt;
		this.spm = spm;
		this.datePerformed = datePerformed;
		this.userID = userID;
		this.ID = ID;
	}

	public double[] getDistance() {
		return distance;
	}

	public void setDistance(double[] distance) {
		this.distance = distance;
	}

	public double[] getTime() {
		return time;
	}

	public void setTime(double[] time) {
		this.time = time;
	}

	public double[] getSplit() {
		return split;
	}

	public void setSplit(double[] split) {
		this.split = split;
	}

	public double[] getAvgWatt() {
		return avgWatt;
	}

	public void setAvgWatt(double[] avgWatt) {
		this.avgWatt = avgWatt;
	}

	public int[] getSpm() {
		return spm;
	}

	public void setSpm(int[] spm) {
		this.spm = spm;
	}

	public Date getDatePerformed() {
		return datePerformed;
	}

	public void setDatePerformed(Date datePerformed) {
		this.datePerformed = datePerformed;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
