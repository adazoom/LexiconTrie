import java.util.Iterator;

import structure5.Stack;
import structure5.StackList;
/**
 * Source:  In-order iterator for binary trees.
 			(c) 1998, 2001 duane a. bailey
 * @author aidazhumabekova
 * Date 10/04/2014
 */

public class LexiconIterator implements Iterator<String> {
	 // Data Fields
	
	private LexiconNode root;
   //Stack of nodes that maintain the state of the iterator.
    protected Stack<LexiconNode> todo; // stack of unvisited ancestors
    
    //Constructor
    public LexiconIterator(LexiconNode node){
    	todo = new StackList<LexiconNode>();
    	root=node;
    	reset();
    	
    }
	//Methods
   
    /**
     * Reset the iterator to its initial state.
     *
     */
    public void reset()
    {
        todo.clear();
        // stack is empty.  
        //push the tree
        todo.push(root);
    }
    /**
     * Return the node currently being considered.
     */
    public LexiconNode get()
    {   
        return todo.peek();
    }
    
	@Override
	public boolean hasNext() {
		
		return !todo.isEmpty();
	}
	
	@Override
	public String next(){
		while(todo.size()>0){//while there is something on stack
		 LexiconNode node = todo.pop();
		 Stack<LexiconNode> tempChild= new StackList<LexiconNode>();
	        // push all of the children
	    	 LexiconNode current = new LexiconNode();
	    		 for(int i=0; i<node.children.length; i++){//for each child
	    			 if(node.children[i]!=null){
	    				 current = node.children[i];
	    				//push on temp stack
		    			 tempChild.push(current);
	    			 }
	    		  }
	     //push everything on temp stack to todo
	     while(!tempChild.empty()){
	    	 todo.push(tempChild.pop());
	     }
	     //check if the node was a word
	     if(node.isWord){
	    	 //call method to get a node and all its parents until root
	    	 String finalW=getWord(node);
	    	 return finalW;
	     }else{
	    	 //recursive for next()
	    	 return next();
	     }
		}
		return null;
	}
	
	public String getWord(LexiconNode node){
		Stack<LexiconNode> temp= new StackList<LexiconNode>();
		temp.push(node);//push the given node first
		StringBuilder word=new StringBuilder();
		//get all of the decedents of the node up to the root
		while(node.parent!=root){
			//push parents on temp stack
			temp.push(node.parent);
			node=node.parent;
		}
		while(!temp.empty()){
			word.append(temp.pop());
		}
		return word.toString();
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
