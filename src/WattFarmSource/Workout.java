package WattFarmSource;

import java.text.ParseException;


public class Workout {
	private int distance;
	private int time;
	private int split;
	private int avgWatt;
	private int spm;
	private String datePerformed; //yyyy-MM-dd
	private String tag; //what kind of workout it is
	
	public Workout(int distance, int time, int split, int avgWatt, int spm,
			String datePerformed, String tag) throws ParseException {
		this.distance = distance;
		this.time = time;
		this.split = split;
		this.avgWatt = avgWatt;
		this.spm = spm;
		this.datePerformed = datePerformed;
		this.tag = tag;
	}
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSplit() {
		return split;
	}
	public void setSplit(int split) {
		this.split = split;
	}
	public int getAvgWatt() {
		return avgWatt;
	}
	public void setAvgWatt(int avgWatt) {
		this.avgWatt = avgWatt;
	}
	public int getSpm() {
		return spm;
	}
	public void setSpm(int spm) {
		this.spm = spm;
	}
	public String getDatePerformed() {
		return datePerformed;
	}
	public void setDatePerformed(String datePerformed) {
		this.datePerformed = datePerformed;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	
}
