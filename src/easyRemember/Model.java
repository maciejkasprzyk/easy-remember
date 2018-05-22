/**
 * 
 */
package easyRemember;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class which changes the given number into a story of words. It uses
 * WordDataBase class to get the words.
 * 
 * @author Maciej Kasprzyk
 *
 */
public class Model {

	private WordDataBase wordDataBase = new WordDataBase();

	public WordDataBase getWordDataBase() {
		return wordDataBase;
	}

	/**
	 * Number for which the story is being generated.
	 */
	private String processedNumber;
	/**
	 * Which match next digits in the processed number
	 */
	private List<WordRecord> candidates = new ArrayList<>();

	/**
	 * Returns list of words which match next digits in the processed number.
	 * 
	 * @return List of wordRecors that match next digits in the processed number.
	 */
	public List<WordRecord> getCandidates() {
		return candidates;
	}

	private List<WordRecord> story = new ArrayList<>();

	/**
	 * Returns chosen part of the story.
	 * 
	 * @return List of WordRecords in the story.
	 */
	public List<WordRecord> getCurrentStory() {
		return story;
	}

	private Controller controller;

	public Model(Controller view) {
		super();
		this.controller = view;

	}

	/**
	 * Use it to pick a next word in a story. Words can be chosen among candidates
	 * on the list candidates list.
	 * 
	 * @param index
	 *            Index of chosen candidate on the candidates list.
	 * @throws IndexOutOfBoundsException
	 *             Throws when there's no candidate with given index.
	 */
	public void chooseCandidate(int index) throws IndexOutOfBoundsException {
		try {
			if (candidates == null)
				throw new IndexOutOfBoundsException();
			WordRecord choosen = candidates.get(index);
			story.add(choosen);

			int begin = choosen.getKey().length();
			int end = processedNumber.length();
			processedNumber = processedNumber.substring(begin, end);
			calculateCandidates();
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
		controller.refresh();
		return;
	}

	/**
	 * Starts new story.
	 * 
	 * @param newNumberToLearn
	 *            Number which you want to remember.
	 */
	public void startNewStory(String newNumberToLearn) {

		this.processedNumber = newNumberToLearn;
		story = new ArrayList<>();
		calculateCandidates();
		controller.refresh();
	}

	/**
	 * Converts List of words to ObervableList of strings.
	 * 
	 * @param in
	 *            List of word records to be converted.
	 * @return Converted list of ObservableList type.
	 */
	public static ObservableList<String> recordsToStringObservableList(List<WordRecord> in) {
		ObservableList<String> out = FXCollections.observableArrayList();
		for (WordRecord item : in) {
			out.add(item.getWord() + " " + item.getKey());
		}
		return out;

	}

	/**
	 * Add candidates of given key length to the list of candidates.
	 * 
	 * @param length
	 *            Length of the key.
	 */
	private void addCandidatesOfGivenLength(int length) {
		String key;
		if (processedNumber.length() >= length) {
			key = processedNumber.substring(0, length);
		} else
			return;
		List<WordRecord> list = wordDataBase.getRecordsByKey(key);

		if (list == null)
			return;

		for (WordRecord item : list) {
			candidates.add(item);
		}
		return;
	}

	/**
	 * Calculates list of candidates for the number stored.
	 */
	private void calculateCandidates() {
		candidates = new ArrayList<>();
		addCandidatesOfGivenLength(7);
		addCandidatesOfGivenLength(6);
		addCandidatesOfGivenLength(5);
		addCandidatesOfGivenLength(4);
		addCandidatesOfGivenLength(3);
		addCandidatesOfGivenLength(2);
		addCandidatesOfGivenLength(1);

		controller.refresh();
		return;
	}

}