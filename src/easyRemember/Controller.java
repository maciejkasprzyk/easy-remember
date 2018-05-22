package easyRemember;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Class responsible for refreshing the view and interpreting user input.
 * 
 * @author Maciej Kasprzyk
 *
 */
public class Controller {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private ListView<String> candidatesListView;
	@FXML
	private ListView<String> storyListView;
	@FXML
	private ListView<String> allWordsListView;
	@FXML
	private TextField processedTextField;
	@FXML
	private TextField addNewWordTextField;
	@FXML
	private Text actionText;
	@FXML
	private Text processedNumberText;

	/**
	 * @param word
	 *            Word to be checked
	 * @return True of false whether the word consists only from letters.
	 */
	private static boolean isWord(String word) {
		word.toLowerCase();
		for (int i = 0, n = word.length(); i < n; i++) {
			char c = word.charAt(i);
			if ((c < 'a' || c > 'z') && c != 'ą' && c != 'ć' && c != 'ę' && c != 'ł' && c != 'ń' && c != 'ó' && c != 'ś'
					&& c != 'ź' && c != 'ż') {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param number
	 *            Number to be checked
	 * @return True of false whether the number consists only from digits.
	 */
	private static boolean isNumber(String number) {
		for (int i = 0, n = number.length(); i < n; i++) {
			char c = number.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}

		return true;
	}

	/**
	 * Refresh the view with data from the model.
	 */
	public void refresh() {

		// update candidates
		ObservableList<String> candidates = Model.recordsToStringObservableList(model.getCandidates());
		candidatesListView.setItems(candidates);

		// update story
		ObservableList<String> story = Model.recordsToStringObservableList(model.getCurrentStory());
		storyListView.setItems(story);

		// update all word list
		ObservableList<String> words = Model.recordsToStringObservableList(model.getWordDataBase().getAllRecords());
		allWordsListView.setItems(words);

		return;
	}

	/**
	 * Handles the choose of next word in the story. Triggered by pressing a word on
	 * candidates list.
	 */
	public void candidatesListView_OnMouseClicked() {
		int selectedIndex = candidatesListView.getSelectionModel().getSelectedIndex();
		try {
			model.chooseCandidate(selectedIndex);
			refresh();
		} catch (IndexOutOfBoundsException e) {
			// user didn't click on any item in list
		}

		return;
	}

	/**
	 * Handles the start of new story. Triggered by pressing enter in the
	 * processedTextField.
	 */
	public void processedTextField_OnAction() {
		String processed = processedTextField.getText();
		processed = processed.replaceAll(" ", "");
		if (isNumber(processed)) {
			processedTextField.setText("");
			processedNumberText.setText("Nauczana liczba: \n" + processed);
			model.startNewStory(processed);
			refresh();
		} else {
			actionText.setText(processed + " nie jest liczbą.");
			processedNumberText.setText("");
			processedTextField.setText("");
		}
	}

	/**
	 * Handles the adding a new word to the database. Triggered by pressing enter in
	 * addNewWordTextField.
	 */
	public void addNewWordTextField_OnAction() {
		String newWord = addNewWordTextField.getText();
		System.out.println(newWord);
		if (isWord(newWord)) {
			if (!model.getWordDataBase().contains(newWord)) {
				addNewWordTextField.setText("");
				model.startNewStory(newWord);
				actionText.setText("Dodałeś słowo: " + newWord + " " + WordRecord.wordToKey(newWord));
				model.getWordDataBase().addNewWord(newWord);
				refresh();
			} else {
				actionText.setText("Słowo " + newWord + " znajduję sie w bazie.");
				addNewWordTextField.setText("");
			}
		} else {
			actionText.setText("Słowo " + newWord + " nie jest poprawne.");
			addNewWordTextField.setText("");
		}

		return;
	}

	/**
	 * Handles the delete of word in the data base. Triggered by clicking on delete
	 * button.
	 */
	public void deleteButton_OnAction() {

		String toDelete = allWordsListView.getSelectionModel().getSelectedItem();
		toDelete = toDelete.replaceAll(" .*", "");

		model.getWordDataBase().deleteWord(toDelete);
		refresh();
		actionText.setText("Słowo " + toDelete + " zostało usunięte.");

		return;
	}
}
