/**
 * 
 */
package easyRemember;

import java.util.*;


/**
 * @author Maciej Kasprzyk
 *
 */
public class Model {

	private WordDataBase wordDataBase = new WordDataBase();
	private String processedNumber;
	private List<WordRecord> candidates;
	private List<WordRecord> story;

	public Model() {
		super();
		//WordDataBase.readFile("in.txt");
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
		addCandidatesOfGivenLenght(5);
		addCandidatesOfGivenLenght(4);
		addCandidatesOfGivenLenght(3);
		addCandidatesOfGivenLenght(2);
		addCandidatesOfGivenLenght(1);
		return;
	}

	public void chooseCandidate(int n) {
		try {
			WordRecord choosen = candidates.get(n);
			story.add(choosen);

			int begin = choosen.getKey().length();
			int end = processedNumber.length();
			processedNumber = processedNumber.substring(begin, end);
			calculateCandidates();
		} catch (IndexOutOfBoundsException e) {
			// TODO: handle exception
		}

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
	}
	
	public WordDataBase getWordDataBase() {
		return wordDataBase;
	}

}