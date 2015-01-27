Aida Zhumabekova
CS 211
Lexicon assignment


LexiconTrie
LexiconTrie starts with the root, which is a LexiconNode with no data. Each LexiconNode has 26 children, which are stored in the statically sized array of 26. The reason I chooses the array of 26 instead of any other ordered data structure is that I actually spend less time on sorting my reference to children every time I add/remove a word. I figured that Trie is a special kind of data structure, which performance becomes even better when there is note to store in it. So, the more words I have in the trie, the better my performance. Here is when array becomes handy, because as I already have a designated place for each letter of alphabet, I can easily access array[int] to get a letter that I need. After trying to use OrderedVector from structure5, I decided that the use of array makes more sense for trie structure and it is easier to implement as I now have a direct access, which is more important than a storage capacity since I am only storing one character per node and in my array of 26 children I do not even have nodes as children when there is no character to store, therefore the array was the best fit for me. 
 
LexiconIterator
Based on the provided example code from Bailey structure book the stack is used to traverse the trie. Global stack is initially set with the root, so whenever a next() method is called, the node is popped off the stack (initially the root), then all of its children are pushed on the temporary stack because the order of traversing is important and pushing them on the global stack would mess up the order. Then, all of the nodes are pushed back on the global stack in the correct order. After, I check whether the first node that was popped out is a word (initially the root) and if it was, then I traverse the path backwards from the node through its parents until I hit the root (initially it is not a word). If the node was not the word, we recurse next() method. 

Tests
Add method
Enter command: 
a run
"run" added to lexicon.
Lexicon now contains 1 words.

Enter command: 
a runner
"runner" added to lexicon.
Lexicon now contains 2 words.

Enter command: 
a running
"running" added to lexicon.
Lexicon now contains 3 words.

Enter command: 
p
Lexicon contains 3 words.  Here they are:
--------------------------------------------
run
runner
running

Enter command: 
a run
"run" already was in lexicon.
Lexicon now contains 3 words.


2) Contains method
Enter command: 
c run
Prefix "run" is contained in lexicon.
Word "run" is contained in lexicon.

Enter command: 
c house
Prefix "house" is not contained in lexicon.
Word "house" is not contained in lexicon.

Enter command: 
c r
Prefix "r" is contained in lexicon.
Word "r" is not contained in lexicon.

3)Remove method
Enter command: 
rem run
"run" removed from lexicon.
Lexicon now contains 2 words.

Enter command: 
p
Lexicon contains 2 words.  Here they are:
--------------------------------------------
runner
running

Enter command: 
rem dog
"dog" wasn't in lexicon.
Lexicon now contains 2 words.

4) Read method
Enter command: 
read small2.txt
Read 7 words from file "small2.txt".
Lexicon now contains 10 words.

Enter command: 
read ospd2.txt
Read 58898 words from file "ospd2.txt".
Lexicon now contains 58908 words.

5) Suggest Corrections method
Enter command: 
read small2.txt
Read 7 words from file "small2.txt".
Lexicon now contains 7 words.

Enter command: 
s ben 1
Words that are within distance 1 of "ben"
--------------------------------------------
<SetVector: <Vector: zen>>

Enter command: 
s nat 2
Words that are within distance 2 of "nat"
--------------------------------------------
<SetVector: <Vector: new not>>

Enter command: 
s ar 1
Words that are within distance 1 of "ar"
--------------------------------------------
<SetVector: <Vector: as>>

Enter command: 
s cat 2
Words that are within distance 2 of "cat"
--------------------------------------------
<SetVector: <Vector: not>>

6) Quit method
Enter command: 
q
*program quit*


 			

