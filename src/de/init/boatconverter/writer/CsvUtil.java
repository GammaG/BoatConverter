package de.init.boatconverter.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CsvUtil {

	public static String emptyColumn() {
		return "\"\";";
	}

	public static String quotedColumn(String content) {
		return "\"" + content + "\";";
	}

	public static String quotedLastColumn(String content) {
		return "\"" + content + "\"\n";
	}

	public static String removeInvalidCharacters(String content) {
		if (content != null)
			return content.replaceAll("\n", " ").replaceAll(";", ",").replaceAll("\"", "'");
		return "";
	}

	public static void saveFile(String name, String content) {
		GregorianCalendar calNow = new GregorianCalendar();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileTimestamp = "-" + sdf2.format(calNow.getTime());

		try {
			File file = new File(name + fileTimestamp + ".csv");
			file.getParentFile().mkdirs();
			file.createNewFile();

			BufferedWriter out = null;
			FileOutputStream fos = new FileOutputStream(file);
			out = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
			out.write('\ufeff');

			out.write(content);
			out.close();

			System.out.println(file.getName() + " has been created.");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Date getDate(Calendar cal) {
		if (cal != null) {
			return cal.getTime();
		}
		return null;
	}

}
