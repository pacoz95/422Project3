package assignment3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MainTest {
	@Before
	public void initializeTests(){
		Main.initialize();
	}

	@Test
	public void testNoDFSLoopsDFS() {
		ArrayList<String> dictionary = new ArrayList<String>(Main.dict);
		ArrayList<String> ladder;
		HashSet<String> result;
		Random rand = new Random();
		while(true){
				int i = rand.nextInt(31) % dictionary.size();
				int k = rand.nextInt(31) % dictionary.size();
				if(i == k){continue;}
				String word1 = dictionary.get(i);
				String word2 = dictionary.get(k);
				System.out.println(word1 + " " + word2);
				ladder = Main.getWordLadderDFS(word1, word2);
				result = new HashSet<String>(ladder);
				assertEquals(result.size(),ladder.size());
		}
	}
	@Ignore
	@Test
	public void testNoDFSCrashDFS() {
		ArrayList<String> dictionary = new ArrayList<String>(Main.dict);
		Random rand = new Random();
		while(true){
		int i = rand.nextInt(31) % dictionary.size();
		int k = rand.nextInt(31) % dictionary.size();
				if(i == k){continue;}
				String word1 = dictionary.get(i);
				String word2 = dictionary.get(k);
				System.out.println(word1 + " " + word2);
				Main.getWordLadderDFS(word1, word2);
		}
	}
}
