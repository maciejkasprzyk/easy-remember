package easyRemember;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WordRecordTest {

	@Test
	void givenWord_whenGettingHash_itIsReturned() {
		Assertions.assertEquals("0011223456778899", new WordRecord("zstdnńmrljkgfwpb").getKey());
		Assertions.assertEquals("", new WordRecord("qeyuioaxv").getKey());
		Assertions.assertEquals("00", new WordRecord("zz").getKey());
	}

}
