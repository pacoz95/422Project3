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
	public static void main(String[] args) throws Exception {
		Scanner kb; // input Scanner for commands
		//PrintStream ps; // output file // If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
		//	ps = new PrintStream(new File(args[1]));
		//	System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin 
		//	ps = System.out; // default to Stdout
		} 
		initialize();
		// TODO methods to read in words, output ladder
		ArrayList<String> arguments;
		arguments = parse(kb);
		if(arguments.size() == 0){
			return;
		}
		else 
			arguments = getWordLadderBFS(arguments.get(0),arguments.get(1));
		printLadder(arguments);
		
		//getWordLadderBFS("String", "aring");
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
		String str = keyboard.next();
		if(str.equals("/quit")){
			return wordladder;
		}
		
		wordladder.add(str.toUpperCase());
		wordladder.add(keyboard.next().toUpperCase());
		return wordladder;
	} 
	static HashSet<String> visited1;
	public static ArrayList<String> getWordLadderDFS(String start, String end){
		start = start.toUpperCase();
		end = end.toUpperCase();
		visited1 = new HashSet<String>();
		return getWordLadderDFShelper(start, end, -1);
	}
	private static ArrayList<String> getWordLadderDFShelper(String start, String end, int lastIndex){
		// Returned list should be ordered start to end.  Include start and end.
		
		ArrayList<String> ret = new ArrayList<String>();
		if(visited1.contains(start)){
			return ret;
		}
		visited1.add(start);					//now, we've visited start
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
				ret.add(0,start);
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
					ret.add(0,start);
					return ret;
				}
			}
		}
		// Return empty list if no ladder.
		return ret; // made it through all letters, no solution
	}
    
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		start = start.toUpperCase();
		end = end.toUpperCase();
		ArrayDeque<String> perms = new ArrayDeque<String>();
		ArrayList<String> wordLadder = new ArrayList<String>();
		String strStrg = start;
		wordLadder.add(start);
		visited.add(start);
		while(true){
			if(!strStrg.equals(end)){
				for(int i = 1; i < start.length(); i++){ // checks permutations of start with end letters
					String bfsTry = strStrg.substring(0, i) + end.charAt(i) + strStrg.substring(i+1);
					if(dict.contains(bfsTry) == true){
						if(!visited.contains(bfsTry)){
						perms.add(bfsTry);
						}
					}
				}
				for(int k = 1; k < start.length(); k++){
					for(char lt = 'A'; lt <= 'Z'; lt++){
						String bfsRandTry = lt + strStrg.substring(k);
						if(dict.contains(bfsRandTry) == true){
							if(!visited.contains(bfsRandTry)){
								perms.add(bfsRandTry);
							}
						}
					}
				}
			}	
			if(strStrg.equals(end)){
				return wordLadder;
			}
			if(perms.isEmpty()){
				return wordLadder;
			}
			strStrg = perms.remove();
			visited.add(strStrg);
			wordLadder.add(strStrg);
			
		} // replace this line later with real return
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
			System.out.println(ladder.get(k).toLowerCase());
		}
	}
// TODO // Other private static methods here
}