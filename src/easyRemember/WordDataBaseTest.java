package easyRemember;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordDataBaseTest {

	@Test
	void WhenAddingWords_TheyAreAdded() {

		WordDataBase baza = new WordDataBase();
		baza.addNewWord("kiełbasa");
		baza.addNewWord("mleko");
		baza.addNewWord("banan");
		baza.addNewWord("zz");
		baza.addNewWord("ss");

		List<WordRecord> expected = new ArrayList<>();
		expected.add(new WordRecord("kiełbasa"));
		expected.add(new WordRecord("mleko"));
		expected.add(new WordRecord("banan"));
		expected.add(new WordRecord("zz"));
		expected.add(new WordRecord("ss"));
		Collections.sort(expected);

		Assertions.assertEquals(expected, baza.getAllRecords());
	}

	@Test
	void WhenSavingToFile_ItCanBeRead() {

		WordDataBase baza = new WordDataBase();
		baza.addNewWord("kiełbasa");
		baza.addNewWord("mleko");
		baza.addNewWord("banan");
		baza.addNewWord("zz");
		baza.addNewWord("ss");
		baza.saveFile("Tests.txt");

		WordDataBase baza2 = new WordDataBase();
		baza2.readFile("Tests.txt");

		Assertions.assertEquals(baza2.getAllRecords(), baza.getAllRecords());
	}

	@Test
	void WhenGetingWordsByKey_TheyAreReturned() {
		WordDataBase baza = new WordDataBase();
		baza.addNewWord("kiełbasa");
		baza.addNewWord("mleko");
		baza.addNewWord("banan");
		baza.addNewWord("zz");
		baza.addNewWord("ss");

		List<WordRecord> expected = new ArrayList<>();
		expected.add(new WordRecord("ss"));
		expected.add(new WordRecord("zz"));
		Collections.sort(expected);

		Assertions.assertEquals(expected, baza.getRecordsByKey("00"));
	}
}