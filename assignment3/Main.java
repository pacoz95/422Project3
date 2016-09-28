/* WORD LADDER Main.java  
 * EE422C Project 3 submission by  
 * Paul Cozzi  
 * pac2472  
 * 16450
 * Alexander Doria
 * aed2395
 * 16450
 * Slip days used: <0>  
 * Git URL:  
 * Fall 2016  */
package assignment3;
import java.util.*; 
import java.io.*;

public class Main {
	HashSet<String> visited = new HashSet<String>();
	static Set<String> dict = null;
// static variables and constants only here. 
	public static void main(String[] args) throws Exception {
		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file // If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin 
			ps = System.out; // default to Stdout
		} initialize();
		// TODO methods to read in words, output ladder
		getWordLadderBFS("String", "aring");
		} 
	
	public static void initialize() {
		// initialize your static variables or constants here. // We will call this method before running our JUNIT tests.  So call it
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
		wordladder.add(keyboard.next());
		wordladder.add(keyboard.next());
		return wordladder;
	} 

	public static ArrayList<String> getWordLadderDFS(String start, String end){
		// Returned list should be ordered start to end.  Include start and end.
		
		// Return empty list if no ladder. // TODO some code Set<String> dict = makeDictionary(); // TODO more code
		return null; // replace this line later with real return
	}
    
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		// TODO more code
		PriorityQueue<String> perms = new PriorityQueue<String>();
		ArrayList<String> wordLadder = new ArrayList<String>();
		String strStrg = start;
		while(!perms.isEmpty()){
			for(int i = 1; i < start.length(); i++){ // checks permutations of start with end letters
				for(int j = 0; i < end.length(); j++){
					String bfsTry = end.substring(j, j + 1) + strStrg.substring(i);
					if(dict.contains(bfsTry) == true){
						if(bfsTry.equals(end) == true){
							
						}
						perms.add(bfsTry);
					}
				}
			}
			for(int k = 1; k < start.length(); k++){
				for(char lt = 'A'; lt < 'Z'; lt++){
					String bfsRandTry = lt + strStrg.substring(k);
					if(dict.contains(bfsRandTry) == true){
						if(bfsRandTry.equals(end) == true){
							
						}
						perms.add(bfsRandTry);
						
					}
				}
			}
			strStrg = perms.remove();
			
		} 
	return null;// replace this line later with real return
}
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
			System.out.println(ladder.get(k));
		}
	}
// TODO // Other private static methods here
}