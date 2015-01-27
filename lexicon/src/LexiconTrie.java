import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

import structure5.Set;
import structure5.SetVector;


public class LexiconTrie implements Lexicon {
	  // Data Fields
	protected LexiconNode root; // the root of the trie
	protected int numWords=0;
	protected int origL=0;
	 /**
     * Constructor for setting the root
     */
    public LexiconTrie(){
    	//initialize the root
    	root= new LexiconNode();
    }
	
	// Methods
	@Override
	public boolean addWord(String word) {
		
		return addHelper(word, root);//start with a root;
	}
	
	public boolean addHelper(String word, LexiconNode node){
				//trace out path (just like contains())
				char letter = word.charAt(0);
				int numVal = 0;//position in alphabet
				if(letter>='a' && letter<='z'|| letter>='A' && letter<='Z' ){
			        numVal=((int)letter - 'a');
				}else{
					return false;
				}
				if(containsWord(word)){
					return false;
				}else{
				//Add a new node for each letter that is not there
				if(node.children[numVal]==null){
					node.children[numVal] = new LexiconNode(word.charAt(0), false);
				    node.children[numVal].parent = node;
				}
				//base case if word length is 1
				if(word.length()==1){
					//the node is making up a word
					node.children[numVal].isWord=true;
					numWords=numWords+1;
					return true;
				}else{
					//recursively add next letter in the word
					 //System.out.println("node is "+ node.children[numVal]+"\nsubstring is "+word.substring(1));
					return addHelper(word.substring(1), node.children[numVal]);
				}
				}
	}

	@Override
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

	@Override
	public boolean removeWord(String word) {
		//check if the word is in the trie
		if(!containsWord(word)){
			return false;
		}
		else{
			removeHelper(word, root);//call helper method
			numWords=numWords-1;
			return true;
		}

	}
	public void removeHelper(String word, LexiconNode node){
		//trace out path (just like contains())
		char letter = word.charAt(0);
		int numVal = ((int)letter - 'a');//position in alphabet
		LexiconNode key=node.children[numVal];
			//traverse to the path until hit the isWord node
			//base case if word length is 1
			if(word.length()==1){
					//if final isWord node has NO children
					if(!hasChildren(key)){
						//delete all the parent nodes who dont have children
						node.children[numVal]=null;
					}
					//if final isWord node have children
					else{
						//uncheck isWord
						key.isWord=false;
					}
			}
			//starting at root check children
			else{
					//recursively search for the next letter
					removeHelper(word.substring(1),key);
					//if final isWord node has NO children
					if(!hasChildren(node)){
						//delete all the parent nodes who dont have children
						node=null;
					}
				
			}
				
	}
	public boolean hasChildren(LexiconNode check){
		int counter=0;//counter for nulls
		//get every child
		for(LexiconNode child: check.children){
			if(child==null){
				counter=+1;
			}
		}
		//two scenarios
		//if final isWord node has NO children
		if(counter==26){
			return false;
			
		}
		//if final isWord node have children
		else{
			return true;
		}
	}

	@Override
	public int numWords() {
		// return num of added words
		return numWords;
	}
	
	@Override
	public boolean containsWord(String word) {
		return containsHelper(word,root );//start with a root
	}
	
	public boolean containsHelper(String word, LexiconNode node){
		try{
			//get the numerical value of each letter in the word RECURSIVE
			char letter = word.charAt(0);
			int numVal = 0;//position in alphabet
			if(letter>='a' && letter<='z'){
		        numVal=((int)letter - 'a');
			}else{//invalid input
				return false;
			}
			//base case if word length is 1
			if(word.length()==1){
				if(node.children[numVal].isWord){
					return true;
				}
			}
			//starting at root check children
			else{
				if(node.children[numVal]!=null){
					//recursively search for the next letter
					return containsHelper(word.substring(1),node.children[numVal]);
				}
			}}catch(NullPointerException e){
				return false;
			}
			return false;
	}
	@Override
	public boolean containsPrefix(String prefix) {
		return prefixHelper(prefix, root);
	}
	public boolean prefixHelper(String prefix, LexiconNode node){
		try{
			//get the numerical value of each letter in the word RECURSIVE
			char letter = prefix.charAt(0);
			int numVal = 0;//position in alphabet
			if(letter>='a' && letter<='z'){
		        numVal=((int)letter - 'a');
			}else{//invalid input
				return false;
			}
			//base case if word length is 1
			if(prefix.length()==1){
				if(node.children[numVal]!=null){
					return true;
				}
			}
			//starting at root check children
			else{
				if(node.children[numVal]!=null){
					//recursively search for the next letter
					return prefixHelper(prefix.substring(1),node.children[numVal]);
				}
			}}catch(NullPointerException e){
				System.out.println("Lexicon is empty.");
			}
			return false;
	}

	@Override
	public Iterator<String> iterator() {
		return new LexiconIterator(root);
	}

	@Override
	public Set<String> suggestCorrections(String target, int maxDistance) {
		//create a set
		Set<String> set=new SetVector<String>();
		origL=target.length();
		//call helper for every child of root
		for(int i=0; i<root.children.length; i++){
			if(root.children[i]!=null){
				LexiconNode child=root.children[i];
			 correctHelper( target, maxDistance, 0, set, "",child );
			}
		}
		 return set;
	}
	
	public void correctHelper(String target, int maxDistance, int dif, Set<String> set, String word, LexiconNode node){
		try{
		//System.out.println(node.data+" is compared to "+target.charAt(0));
		//check if the node holds first letter of word
		if(node.data!=target.charAt(0)){
			dif++;//add up to difference
			//System.out.println("dif is "+ dif);
		}
		//starting from the root and check all of the children
		//then go to the children children and check for the letter
		//base case 
		if(dif<=maxDistance){
			//check if the node is word
			//System.out.println("node is "+node);
			//System.out.println("word is "+word);
			//System.out.println("word length is "+word.length());
			//System.out.println("target length is "+target.length());
			//System.out.println("target is "+target);
			if(node.isWord && word.length()>=target.length()){
				word=word+node.data;//append the last char
				if(word.length()==origL){
				set.add(word);
				}
			}else{
				//for each child of the node
				for(int i=0; i<node.children.length; i++){
					if(node.children[i]!=null){
					//recurse
					correctHelper(target.substring(1), maxDistance, dif, set, word+node.data,node.children[i]);
					}
				}
			}
		}}catch(StringIndexOutOfBoundsException e){
			return;
		}
	}

	@Override
	public Set<String> matchRegex(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
