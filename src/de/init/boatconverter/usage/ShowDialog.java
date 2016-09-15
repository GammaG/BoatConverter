package de.init.boatconverter.usage;

import java.awt.Frame;

import javax.swing.JOptionPane;

import de.init.boatconverter.pojos.CallHolder;

public class ShowDialog {

	public String showDescriptionDialog(CallHolder callHolder) {

		String s = (String) JOptionPane.showInputDialog(new Frame(), createCallDescription(callHolder), "Check the Workdescription.",
				JOptionPane.PLAIN_MESSAGE, null, null, callHolder.getWorkDescription());

		// If a string was returned set it as new Workdescription
		if ((s != null) && (s.length() > 0)) {
			return s;
		}
		return callHolder.getWorkDescription();
	}

	private String createCallDescription(CallHolder holder) {
		StringBuilder builder = new StringBuilder();
		builder.append("The given entry was longer than 6 hours, before the split happens you may want to check the workdescription for the second entry.\n");
		builder.append("Person: " + holder.getPerson() + "\n");
		builder.append("Date: " + holder.getDate() + "\n");
		builder.append("Effort: " + ("" + holder.getTimeEffort()).replace(".", ",") + "\n");
		builder.append("TimeFrom: " + Constants.getTimeValueAsString(holder.getTimeFrom()) + "\n");
		builder.append("TimeTo: " + Constants.getTimeValueAsString(holder.getTimeTo()) + "\n");
		return builder.toString();

	}
}
