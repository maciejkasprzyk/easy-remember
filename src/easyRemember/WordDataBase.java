package easyRemember;

import java.io.*;
import java.util.*;

public class WordDataBase {
	private Map<String, List<WordRecord>> map;

	public WordDataBase() {
		super();
		map = new HashMap<String, List<WordRecord>>();
	}
	
	public void addNewWord(String word) {
		WordRecord wordRecord = new WordRecord(word);

		List<WordRecord> list = map.get(wordRecord.getKey());
		if (list != null) {
			list.add(wordRecord);
		} else {
			list = new ArrayList<>();
			list.add(wordRecord);
			map.put(wordRecord.getKey(), list);
		}
	}

	public List<WordRecord> getAllRecords() {
		List<WordRecord> result = new ArrayList<>();
		for (List<WordRecord> list : map.values()) {
			for (WordRecord item : list) {
				result.add(item);
			}
		}
		Collections.sort(result);
		return result;
	}

	
	public List<WordRecord> getRecordsByKey(String key) {
		if (map.get(key) == null)
			return null;
		List<WordRecord> result = new ArrayList<>(map.get(key));
		Collections.sort(result);
		return result;
	}

	public void readFile(String fileName) {

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String privious = "";
			while ((line = bufferedReader.readLine()) != null) {
				line = line.replaceAll(" .*", "");

				// don't read duplications
				if (!line.equals(privious))
					addNewWord(line);
				privious = line;

			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.err.println(ex);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public void saveFile(String fileName) {
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter("dataBase/" + fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			List<WordRecord> list = getAllRecords();

			for (WordRecord item : list) {
				bufferedWriter.write(item.getWord());
				bufferedWriter.write(" ");
				bufferedWriter.write(item.getKey());
				bufferedWriter.newLine();
			}

			bufferedWriter.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}