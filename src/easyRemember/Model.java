/**
 * 
 */
package easyRemember;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Maciej Kasprzyk
 *
 */
public class Model {

	private WordDataBase wordDataBase = new WordDataBase();
	private String processedNumber;
	private List<WordRecord> candidates = new ArrayList<>();
	private List<WordRecord> story = new ArrayList<>();
	private Controller view;

	public Model(Controller view) {
		super();
		this.view = view;

	}

	public void chooseCandidate(int n) throws IndexOutOfBoundsException {
		try {
			if (candidates == null)
				throw new IndexOutOfBoundsException();
			WordRecord choosen = candidates.get(n);
			story.add(choosen);

			int begin = choosen.getKey().length();
			int end = processedNumber.length();
			processedNumber = processedNumber.substring(begin, end);
			calculateCandidates();
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
		view.refresh();
		return;
	}

	public List<WordRecord> getCandidates() {
		return candidates;
	}

	public List<WordRecord> getCurrentStory() {
		return story;
	}

	public void startNewStory(String processed) {

		this.processedNumber = processed;
		story = new ArrayList<>();
		calculateCandidates();
		view.refresh();
	}

	public WordDataBase getWordDataBase() {
		return wordDataBase;
	}

	public static ObservableList<String> recordsToStringObservableList(List<WordRecord> in) {
		ObservableList<String> out = FXCollections.observableArrayList();
		for (WordRecord item : in) {
			out.add(item.getWord() + " " + item.getKey());
		}
		return out;

	}

	private void addCandidatesOfGivenLenght(int n) {
		String key;
		if (processedNumber.length() >= n) {
			key = processedNumber.substring(0, n);
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

	private void calculateCandidates() {
		candidates = new ArrayList<>();
		addCandidatesOfGivenLenght(7);
		addCandidatesOfGivenLenght(6);
		addCandidatesOfGivenLenght(5);
		addCandidatesOfGivenLenght(4);
		addCandidatesOfGivenLenght(3);
		addCandidatesOfGivenLenght(2);
		addCandidatesOfGivenLenght(1);
	
		view.refresh();
		return;
	}

}