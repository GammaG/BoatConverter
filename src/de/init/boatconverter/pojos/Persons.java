package de.init.boatconverter.pojos;

import java.util.ArrayList;

public class Persons {

	private static ArrayList<String[]> namesModifierList = new ArrayList<String[]>();

	public static void addElement(String[] list) {
		namesModifierList.add(list);
	}

	public static String getReplacementName(String orgName) {
		for (String[] list : namesModifierList) {
			if (list[0].equals(orgName)) {
				return list[1];
			}
		}
		return orgName;
	}

	public static boolean nameAlreadyInTheList(String orgName) {
		for (String[] list : namesModifierList) {
			if (list[0].equals(orgName)) {
				return true;
			}
		}
		return false;
	}

}
