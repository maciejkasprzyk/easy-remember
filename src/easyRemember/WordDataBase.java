package easyRemember;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDataBase {

	// <key , list of word records(word,key)>
	private Map<String, List<WordRecord>> map;

	public WordDataBase() {
		super();
		map = new HashMap<>();
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

	public boolean contains(String word) {

		WordRecord wordRecord = new WordRecord(word);
		List<WordRecord> list = map.get(wordRecord.getKey());
		if (list != null) {
			if (list.contains(wordRecord))
				return true;
		}
		return false;
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
			FileReader fileReader = new FileReader("dataBase/" + fileName);

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

	public void deleteWord(String toDelete) {
		String key = WordRecord.wordToKey(toDelete);
		map.get(key).remove(new WordRecord(toDelete));
	}
}