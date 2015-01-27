/*
  An abstract implementation of a Lexicon that uses an OrderedStructure.

  This abstract class contains everything needed except a constructor,
  in which the appropriate ordered structure is constructed.

  Jim Teresco, Mount Holyoke College, November 2009

  $Id: LexiconOrderedStructure.java 1007 2009-11-06 04:41:23Z terescoj $
  
 */

import structure5.*;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;

public abstract class LexiconOrderedStructure implements Lexicon {
 
    /** the OrderedVector that contains our lexicon */
    protected OrderedStructure<String> lexicon;

    /**
       This method adds the specified word to this lexicon.  It returns
       true if the word was added (i.e. previously did not appear in
       this lexicon) and false otherwise. The word is expected to
       contain only lower/uppercase letters (you choose).
       
       @param word the word to be added to the lexicon
       @post the word is added to the lexicon if necessary
       @return whether it was necessary to add the word
    */
    public boolean addWord(String word) {
	
	word = word.toLowerCase();
	if (!lexicon.contains(word)) {
	    lexicon.add(word);
	    return true;
	}
	return false;
    }
    
    
    /**
       This method opens the specified file, expecting a 
       text file containing one word per line, and adds each word  
       to this lexicon. The file must be in the same folder as 
       the executable to be found. The value returned is the count of
       new words that were added. If the file doesn't exist or was unable
       to be opened, the function returns 0.
       
       @param filename the name of the file containing the words
       @post the words from the file are inserted into the lexicon and
       the number of words added is returned
       @return the number of words read in and successfully added to the
       lexicon
    */
    
    public int addWordsFromFile(String filename) {
	Scanner s;
	
	try {
	    s = new Scanner(new File(filename));
	}
	catch (Exception e) {
	    System.err.println(e);
	    return 0;
	}
	
	int added = 0;
	while (s.hasNext()) {
	    if (addWord(s.next())) added++;
	}
	
	return added;
    }
    
    
    /**
       This method attempts to remove a specified word from 
       this lexicon. If the word appears in the lexicon, it is removed,
       and true is returned. If the word was not contained in the lexicon, 
       the lexicon is unchanged and false is returned.
       
       @param word the word to remove from the lexicon
       @post if in the lexicon, the word is removed
       @return whether the word was successfully removed
    */
    
    public boolean removeWord(String word) {
	
	word = word.toLowerCase();
	String removed = lexicon.remove(word);
	return removed != null;
    }

 
    /**
       This method returns the number of words contained
       in this lexicon.
       
       @return the number of words in the lexicon.
    */
    public int numWords() {

	return lexicon.size();
    }


    /**
       This method returns true if the specified word exists in this
       lexicon, false otherwise. Words are considered
       case-insensitively.

       @param word the word to be searched for in the lexicon
       @return whether the word was found
    */

    public boolean containsWord(String word) {

	return lexicon.contains(word.toLowerCase());
    }


    /**
       This method returns true if any words in the lexicon begin with
       the specified prefix, false otherwise. A word is defined to be
       a prefix of itself and the empty string is a prefix of
       everything.  Prefixes are considered case-insensitively.

       @param prefix the string to use as the prefix to search for in the lexicon
       @return whether any word in the lexicon starts with the given prefix.
     */

    public boolean containsPrefix(String prefix) {

	// this can probably be done more efficiently, but this works
	for (String word : lexicon) {
	    if (word.toLowerCase().startsWith(prefix)) return true;
	    if (word.toLowerCase().compareTo(prefix) > 0) return false;
	}
	return false;
    }


    /**
       This method returns an iterator over all words contained in the
       lexicon. Accessing the words from the iterator will retrieve
       them in alpahbetical order.

       @return an iterator over the contents of the lexicon
    */
    public Iterator<String> iterator() {
	return lexicon.iterator();
    }


    /**
       This method returns a pointer to a set of strings, where each
       entry is a suggested correction for the target.  All words in
       the lexicon with a distance to the target that is less than or
       equal to the parameter distance should be in the returned
       set.

       @param target the word to be potentially corrected
       @param maxDistance the maximum number of characters that can differ
       and still have a match
       @return a set of strings from the lexicon that are the same length as
       the target and differ by at most maxDistance.
     */
    public Set<String> suggestCorrections(String target, int maxDistance) {
	Set<String> suggestions = new SetVector<String>();

	for (String word : lexicon) {
	    if (word.length() == target.length()) {
		int numDifferences = 0;
		for (int pos=0;
		     pos<target.length() && numDifferences<=maxDistance;
		     pos++) {
		    if (word.charAt(pos) != target.charAt(pos)) numDifferences++;
		}
		if (numDifferences <= maxDistance) suggestions.add(word);
	    }
	}

	return suggestions;
    }
	

    /**
       This method returns a pointer to a set of strings, where each
       entry is a match for the regular expression pattern.  All words
       in the lexicon that match the pattern should be in the returned
       set.

       @param pattern the regular expression pattern to match in the lexicon
       @return the set of strings from the lexicon that match the regex
     */
    public Set<String> matchRegex(String pattern) {
	Set<String> matches = new SetVector<String>();

	String regexPattern = pattern.replace("*", ".*").replace("?",".?");

	for (String word: lexicon) {
	    if (word.matches(regexPattern)) matches.add(word);
	}

	return matches;
    }
    	
}

