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

/**
 * Stores word records, allows basic data manipulation: adding, deleting,
 * searching. Allows to read and save to file.
 * 
 * @author Maciej Kasprzyk
 *
 */
public class WordDataBase {

	/**
	 * Stores WordRecords as map, where key is the key generated for a word. Value
	 * is a list, because different words can have the same key. <key , list of word
	 * records(word,key)>
	 */
	private Map<String, List<WordRecord>> map;

	public WordDataBase() {
		super();
		map = new HashMap<>();
	}

	/**
	 * Adds new word to the database.
	 * 
	 * @param word
	 *            Word to be added to the data base.
	 */
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

	/**
	 * Deletes given word from a database.
	 * 
	 * @param toDelete
	 *            Word to be deleted.
	 */
	public void deleteWord(String toDelete) {
		String key = WordRecord.wordToKey(toDelete);
		map.get(key).remove(new WordRecord(toDelete));
	}

	/**
	 * Check if database contains the given word.
	 * 
	 * @param word
	 *            Word to look for.
	 * @return True or false whether the word is in the data base.
	 */
	public boolean contains(String word) {

		WordRecord wordRecord = new WordRecord(word);
		List<WordRecord> list = map.get(wordRecord.getKey());
		if (list != null) {
			if (list.contains(wordRecord))
				return true;
		}
		return false;
	}

	/**
	 * Returns all records from database.
	 * 
	 * @return List of all records currently in database.
	 */
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

	/**
	 * Return records from database which have the given key.
	 * 
	 * @param key
	 *            Key to look by.
	 * @return List of all records which have the the given key.
	 */
	public List<WordRecord> getRecordsByKey(String key) {
		if (map.get(key) == null)
			return null;
		List<WordRecord> result = new ArrayList<>(map.get(key));
		Collections.sort(result);
		return result;
	}

	/**
	 * Function reads from a file in the folder database in the project folder.
	 * 
	 * @param fileName
	 *            Filename to read from. Example "EasyWords.txt"
	 */
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

	/**
	 * Function save a file in the folder database in the project folder.
	 * 
	 * @param fileName
	 *            Filename to save the file as. Example "EasyWords.txt"
	 */
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