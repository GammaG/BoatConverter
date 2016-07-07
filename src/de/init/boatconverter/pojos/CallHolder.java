package de.init.boatconverter.pojos;

import java.util.Date;

public class CallHolder {

	private Date date;
	private double timeFrom;
	private double timeTo;
	private double timeEffort;
	private double timeBreak;
	private String workDescription;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(double timeFrom) {
		this.timeFrom = timeFrom;
	}

	public double getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(double timeTo) {
		this.timeTo = timeTo;
	}

	public double getTimeEffort() {
		return timeEffort;
	}

	public void setTimeEffort(double timeEffort) {
		this.timeEffort = timeEffort;
	}

	public double getTimeBreak() {
		return timeBreak;
	}

	public void setTimeBreak(double timeBreak) {
		this.timeBreak = timeBreak;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

}
