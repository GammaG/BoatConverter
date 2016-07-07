package de.init.boatconverter.pojos;

public class CallHolder {

	private String date;
	private double timeFrom;
	private double timeTo;
	private double timeEffort;
	private double timeBreak = 0;
	private String workDescription;
	private String person;
	private String priceclass;
	private String internalNote = "";

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getPriceclass() {
		return priceclass;
	}

	public void setPriceclass(String priceclass) {
		this.priceclass = priceclass;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		if (this.internalNote.equals("")) {
			this.internalNote = internalNote;
		} else {
			this.internalNote += "; " + internalNote;
		}

	}

	@Override
	public String toString() {
		return "CallHolder [date=" + date + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + ", timeEffort=" + timeEffort + ", timeBreak=" + timeBreak
				+ ", workDescription=" + workDescription + ", person=" + person + ", priceclass=" + priceclass + ", internalNote=" + internalNote + "]";
	}

}
