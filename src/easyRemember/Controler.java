package easyRemember;

import java.time.LocalDateTime;
import java.util.*;

public class Controler {

	public static void main(String[] args) {
		Model model = new Model();
		
		//model.getWordBase().addNewWord(word);
		
		model.getWordDataBase().readFile("in.txt");
		model.getWordDataBase().saveFile("/backups/" + LocalDateTime.now().toString() + ".txt");
		model.getWordDataBase().saveFile("EasyWords.txt");
		
		model.startNewStory("451507539");
		
		Scanner S = new Scanner(System.in);
		while(!model.getCandidates().isEmpty()) {
			List<WordRecord> candidates = model.getCandidates();
			for(WordRecord item : candidates) {
				System.out.println(item.getWord());
			}
			
			int x;
			x = S.nextInt();
			model.chooseCandidate(x);
		}
		
		List<WordRecord> story = model.getCurrentStory();
		for(WordRecord item : story) {
			System.out.println(item.getWord());
		}
		
		S.close();
		
		return;

	}
}
