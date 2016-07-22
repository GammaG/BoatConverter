package de.init.boatconverter.converter;

import java.util.ArrayList;

import de.init.boatconverter.pojos.CallHolder;

public class CallLengthConverter {

	private ArrayList<CallHolder> callHolders = new ArrayList<CallHolder>();

	public CallLengthConverter(ArrayList<CallHolder> callHolders) {
		this.callHolders = callHolders;
	}

	/**
	 * Changes the length of the breaks and splits tasks into max 6 hours
	 * 
	 * @return
	 */
	public ArrayList<CallHolder> changeCallLenght() {
		ArrayList<CallHolder> localList = new ArrayList<CallHolder>();
		for (CallHolder holder : callHolders) {

			if (holder.getTimeBreak() < 1 && holder.getTimeEffort() >= 6) {
				double originalBreakTime = holder.getTimeBreak();
				double toAdd = 1 - originalBreakTime;
				holder.setTimeBreak(1.0);
				holder.setTimeTo(holder.getTimeTo() + toAdd);
			}

			if (holder.getTimeEffort() > 6) {
				CallHolder localHolder = new CallHolder();
				localHolder.setDate(holder.getDate());
				localHolder.setWorkDescription(holder.getWorkDescription());
				localHolder.setTimeBreak(0);

				double oldToValue = holder.getTimeTo();
				double toValue = oldToValue;
				while ((toValue - holder.getTimeFrom()) >= 6) {
					toValue--;
				}

				holder.setTimeTo(toValue);
				holder.setTimeEffort(holder.getTimeTo() - holder.getTimeFrom());
				localHolder.setTimeFrom(toValue + holder.getTimeBreak());
				localHolder.setTimeTo(oldToValue);
				localHolder.setTimeEffort(oldToValue - (toValue + holder.getTimeBreak()));
				localHolder.setPerson(holder.getPerson());
				localHolder.setPriceclass(holder.getPriceclass());

				String message = "Buchung > 6 Stunden -> Aufgesplitted in zwei Buchungen, Zeiten fuer Pause angepasst";
				holder.setInternalNote(message);
				localHolder.setInternalNote(message);

				localList.add(holder);
				localList.add(localHolder);

			} else {
				localList.add(holder);
			}
		}
		return localList;
	}

}
