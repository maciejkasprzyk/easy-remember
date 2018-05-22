package easyRemember;

/**
 * Class which represent a record of word and a key of this word.
 * 
 * @author Maciej Kasprzyk
 */
public class WordRecord implements Comparable<WordRecord> {

	private String key;
	private String word;

	WordRecord(String word) {
		this.word = word.replaceAll(" .*", "");
		this.word.toLowerCase();
		key = wordToKey(this.word);
	}

	/**
	 * @param word
	 *            Word to for which the key will be generated.
	 * @return The key generated for the given word.
	 */
	public static String wordToKey(String word) {

		String key = new String();
		char previous = 'a';
		for (int i = 0, n = word.length(); i < n; i++) {
			char c = word.charAt(i);

			char next;
			if (i + 1 < n)
				next = word.charAt(i + 1);
			else
				next = 'a';

			switch (c) {

			case 'z':
				if (previous == 's' || previous == 'c' || previous == 'd' || previous == 'r')
					break;
				key = key + '0';
				break;
			case 's':
				if (next == 'z')
					break;
				key = key + "0";
				break;

			case 'd':
				if (next == 'z' || next == 'ź' || next == 'ż')
					break;
			case 't':
				key = key + "1";
				break;

			case 'n':
			case 'ń':
				key = key + "2";
				break;

			case 'm':
			case 'h':
				key = key + "3";
				break;

			case 'r':
				if (next == 'z')
					break;
				key = key + "4";
				break;

			case 'l':
				key = key + "5";
				break;

			case 'c':
				if (next == 'z' || next == 'h')
					break;
			case 'j':
				key = key + "6";
				break;

			case 'k':
			case 'g':
				key = key + "7";
				break;

			case 'f':
			case 'w':
				key = key + "8";
				break;

			case 'p':
			case 'b':
				key = key + "9";
				break;
			}
			previous = c;
		}
		return key;
	}

	@Override
	public int compareTo(WordRecord other) {
		if (key.length() < other.key.length())
			return -1;
		if (key.length() > other.key.length())
			return 1;
		int keyComp = key.compareTo(other.key);
		if (keyComp != 0)
			return keyComp;
		else
			return word.compareTo(other.word);
	}

	public String getKey() {
		return key;
	}

	public String getWord() {
		return word;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordRecord other = (WordRecord) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return word + " " + key;
	}
}
