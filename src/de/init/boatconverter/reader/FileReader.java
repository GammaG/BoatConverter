package de.init.boatconverter.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader {

	private volatile InputStream is;
	private volatile InputStreamReader isr;
	private volatile BufferedReader br;

	public ArrayList<ArrayList<String>> readFile(String path) throws IOException {

		ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();

		try {
			// open input stream test.txt for reading purpose.
			is = new FileInputStream(path);

			// create new input stream reader
			isr = new InputStreamReader(is);

			// create new buffered reader
			br = new BufferedReader(isr);

			String currentLine;

			// reads to the end of the stream
			while ((currentLine = br.readLine()) != null) {
				ArrayList<String> line = new ArrayList<>();
				String[] elements = currentLine.split("[;]+");
				for (String s : elements) {
					line.add(s);
				}
				lines.add(line);
			}

			return lines;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// releases resources associated with the streams
			if (is != null)
				is.close();
			if (isr != null)
				isr.close();
			if (br != null)
				br.close();
		}
		return null;
	}
}
