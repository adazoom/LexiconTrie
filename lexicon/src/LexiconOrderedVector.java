/*
  An implementation of a Lexicon that uses an OrderedVector.

  Most of the functionality is in the abstract class LexiconOrderedStructure.

  Your final submission will not use this class, but you will use it
  for testing to compare with your own implementation of LexiconTrie,
  and for timing comparisons.

  Jim Teresco, Mount Holyoke College, November 2009

  $Id: LexiconOrderedVector.java 1007 2009-11-06 04:41:23Z terescoj $
  
 */

import structure5.*;

public class LexiconOrderedVector extends LexiconOrderedStructure {

    /**
       Constructor for our lexicon.
    */
    public LexiconOrderedVector() {

	lexicon = new OrderedVector<String>();
    } 	
}

