package de.init.boatconverter.pojos;

import de.init.boatconverter.usage.Constants;

public class CallHolder {

	private String date;
	private double timeFrom = 0;
	private double timeTo = 0;
	private double timeEffort;
	private double timeBreak = 0;
	private String workDescription;
	private String person;
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

	public boolean checkIfValuesAreValid() {
		if (workDescription.equals("Portalanaylse mit Bewertungsmatrix")) {
			System.out.println("stuff");
		}
		if (person == null | timeFrom == 0 | timeTo == 0 || Constants.getTimeValueAsString(timeFrom).equals("")
				|| Constants.getTimeValueAsString(timeTo).equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "CallHolder [date=" + date + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + ", timeEffort=" + timeEffort + ", timeBreak=" + timeBreak
				+ ", workDescription=" + workDescription + ", person=" + person + "]";
	}

}
