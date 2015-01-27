
import structure5.OrderedVector;


public class LexiconNode implements Comparable<LexiconNode>{
    // Data Fields
    protected Character data; //the letter itself
    protected Boolean isWord;//true when the path by this node makes a word
    protected LexiconNode[] children;// 26 children in arraylist
    protected LexiconNode parent; 

    // Constructors
    /**
     * Constructor for setting the root
     */
    public LexiconNode(){
    	//set the root parameters
    	isWord=false;
    	children= new LexiconNode[26]; //each node has 26 children
    }
    /**
     * Construct a node with given data and no children.
     * @param data The data to store in this node
     */
    public LexiconNode(Character data, Boolean isWord) {
    	//set data
        this.data = data;
        this.isWord= isWord; //initialize the boolean 
        this.children= new LexiconNode[26]; //each node has 26 children
    }

    // Methods
  
    /**
     * Returns a string representation of the node.
     * @return A string representation of the data fields
     */
    @Override
    public String toString() {
        return data.toString();
    }

	@Override
	public int compareTo(LexiconNode o) {
		// compare datas
		 return (this.data).compareTo(o.data);
	}
}
