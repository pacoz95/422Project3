/* WORD LADDER Main.java  
 * EE422C Project 3 submission by  
 * Paul Cozzi  
 * pac2472  
 * 16450
 * Alexander Doria
 * aed2395
 * 16450
 * Slip days used: <0>  
 * Git URL:  https://github.com/pacoz95/422Project3
 * Fall 2016  */
package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	static HashSet<String> visited = new HashSet<String>();
	static Set<String> dict = null;
// static variables and constants only here. 
	/**
	 * Class that contains String that has the word and parent that has what the word branched from.
	 */
	private static class WordNode {
		String word = null;
		WordNode parent = null;
		
		public WordNode(String word){
			this.word = word;
		}
		public WordNode(String word, WordNode parent){
			this.word = word;
			this.parent = parent;
		}
	}
	public static void main(String[] args) throws Exception {
		Scanner kb; // input Scanner for commands
		kb = new Scanner(System.in);// default from Stdin 
		initialize();
		//parse for input
		ArrayList<String> arguments;
		ArrayList<String> result;
		arguments = parse(kb);
		if(arguments.size() == 0){
			return;
		}
		//print result
		else{
			result = getWordLadderBFS(arguments.get(0),arguments.get(1));
		}
		//print the results
		if(result.size() < 2){
			System.out.println("no word ladder can be found between " + 
								arguments.get(0).toLowerCase() + " and " + arguments.get(1).toLowerCase() + ".");
		}
		else{
			System.out.println("a " + (result.size() - 2) + "-rung ladder exists between " +
								arguments.get(0).toLowerCase() + " and " + arguments.get(1).toLowerCase() + ".");
			printLadder(result);
		}
	}
	
	/**
	 * initializes the dictionary for calls
	 * @param none
	 * @return none
	 */
	public static void initialize() {
	// initialize your static variables or constants here. 
	// We will call this method before running our JUNIT tests.  So call it
	// only once at the start of main.
		dict = makeDictionary();
	}
	/**  
	 * @param keyboard Scanner connected to System.in  
	 * @return ArrayList of 2 Strings containing start word and end word.  
	 * If command is /quit, return empty ArrayList.  
	 */ 
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> wordladder = new ArrayList<String>();
		String str = keyboard.next();
		if(str.equals("/quit")){
			System.exit(0);
		}
		
		wordladder.add(str.toUpperCase());
		wordladder.add(keyboard.next().toUpperCase());
		return wordladder;
	} 
	/**
	 * Uses depth first search to find any word ladder
	 * @param start a String, the start word
	 * @param end, a String, the end word
	 * @return The populated word ladder, including the start and end, empty if no word ladder
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end){
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayList<String> ladder = new ArrayList<String>();
		//even though DFS is very optimized, it still stack overflows in special cases
		try{
			visited.clear();
			ladder = getWordLadderDFShelper(start, end, -1);
			Collections.reverse(ladder);
		}
		catch(StackOverflowError e){
			visited.clear();
			ladder = getWordLadderDFShelper(end, start, -1);
		}
		visited = new HashSet<String>(); //cleanup
		return ladder;
	}
	/**
	 * Helper function for DFS
	 * @param start - a String - the start string
	 * @param end - a String - the end string
	 * @param lastIndex - the last index of a word that was changed, will be skipped
	 * @return ArrayList<String> with word ladder, or empty if no word ladder
	 */
	private static ArrayList<String> getWordLadderDFShelper(String start, String end, int lastIndex){
		// Returned list should be ordered start to end.  Include start and end.
		
		ArrayList<String> ret = new ArrayList<String>();
		if(visited.contains(start)){
			return ret;
		}
		visited.add(start);					//now, we've visited start
		// Base case, we're at the end
		if(start.equals(end)){
			ret.add(end);
			return ret;
		}
		if(!dict.contains(start)){			//not even a word, bad case
			return ret;
		}
		char[] optimal = end.toCharArray();
		String testStr;
		//first, try letter changes that bring you closer to the solution
		for(int k = 0; k < start.length(); ++k){
			if(k == lastIndex){
				continue;
			}
			testStr = start.substring(0, k) + optimal[k] + start.substring(k+1);
			ret = getWordLadderDFShelper(testStr, end, k);
			if(ret.size() > 0){
				ret.add(start);
				return ret;
			}
		}
		//try random letters because the ones that bring you closer to the solution failed
		for(char k = 'A'; k <= 'Z'; ++k){
			for(int j = 0; j < start.length(); ++j){
				if(j == lastIndex){
					continue;
				}
				testStr = start.substring(0, j) + k + start.substring(j+1);
				ret = getWordLadderDFShelper(testStr, end, j);
				if(ret.size() > 0){
					ret.add(start);
					return ret;
				}
			}
		}
		// Return empty list if no ladder.
		return ret; // made it through all letters, no solution
	}
    /**
     * Finds if there is a word ladder between two words and returns the wordladder.
     * @param start string that contains the start word of the word ladder
     * @param end string that contains the end word of the word ladder
     * @return ArrayList<String> contains word ladder.
     */
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayDeque<WordNode> perms = new ArrayDeque<WordNode>();
		ArrayList<String> wordLadder = new ArrayList<String>();
		visited.add(start);
		WordNode sword = new WordNode(start);
		WordNode current = sword;
		while(true){
			if(!current.word.equals(end)){
				for(int i = 0; i < start.length(); i++){ // checks permutations of start with end letters
					String bfsTry = current.word.substring(0, i) + end.charAt(i) + current.word.substring(i+1);
					if(dict.contains(bfsTry) == true){
						if(!visited.contains(bfsTry)){
						WordNode word = new WordNode(bfsTry, current);
						perms.add(word);
						visited.add(current.word);
						}
					}
				}
				for(int k = 0; k < start.length(); k++){// checks permutaions using all letters of the dictionary
					for(char lt = 'A'; lt <= 'Z'; lt++){
						String bfsRandTry = current.word.substring(0, k) + lt + current.word.substring(k+1 );
						if(dict.contains(bfsRandTry) == true){
							if(!visited.contains(bfsRandTry)){
								WordNode word = new WordNode(bfsRandTry, current);
								perms.add(word);
								visited.add(current.word);
								
							}
						}
					}
				}
			}	
			if(current.word.equals(end)){ //checks if the current word is end
				wordLadder = makeWordLadder(current, wordLadder, start);
				return wordLadder;
			}
			if(perms.isEmpty()){ // checks if the queue is empty
				return wordLadder;
			}
			current = perms.remove(); //sets the next word as current from the queue
			
		} // replace this line later with real return
}
	/**
	 * Returns an ArrayList<String> that contains the word ladder.
	 * @param nodeWord NodeWord that contains the last word in the word ladder.
	 * @param wordLadder ArrayList<String> will contain the wordladder.
	 * @param start String contains start word
	 * @return ArrayList<String> that contains the word ladder.
	 */
	public static ArrayList<String> makeWordLadder(WordNode nodeWord, ArrayList<String> wordLadder, String start){
		while(nodeWord.parent != null){
			wordLadder.add(nodeWord.word);
			nodeWord = nodeWord.parent;
		}
		wordLadder.add(start);
		Collections.reverse(wordLadder);
		return wordLadder;
	}
	/**
	 * Pulls dictionary from file
	 * @return a HashSet<String> containing all words in the dictionary
	 */
	public static Set<String>  makeDictionary () { 
		Set<String> words = new HashSet<String>();
		Scanner infile = null; 
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} 
		catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!"); e.printStackTrace(); System.exit(1);
		} 
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		} 
		return words;
	} 
	public static void printLadder(ArrayList<String> ladder) {
		for(int k = 0; k < ladder.size(); k++){
			System.out.println(ladder.get(k).toLowerCase());
		}
	}
}