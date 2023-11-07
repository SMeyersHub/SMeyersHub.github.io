# CS321 Summer 2020 Final Project - Bioinformatics
This is our group final for the CS321 Summer 2020 Class. It is a BTree data structure that is composed of BTreeNodes. You can use BTreeCache to make the program use a cache while running to achieve faster running times in most cases.

# Team Members
Riley Schmid, Steven Meyers

# Files
* GeneBankSearch.java: Java class that searches through a BTree to find inputed strings from a gbk file.

* GeneBankCreateBTree.java: Java class that creates a BTree using a provided gbk file, as well as other inputs such as cache toggling, degree, sequence length, and the debug level

* BTree.java: Java class that is an implementation of a BTree. It creates, maintains, and has the ability to search through itself.

* BTreeNode.java: Is used as the nodes in the BTree. Used for storing objects and get and set them, which can be used by the BTree to perform such operations as a node split to maintain its BTree status.

* BTreeCache.java: Class used to run BTree operations through caching, which usually lessens the time it takes to perform most operations.

* TreeObject.java: Class that is used to be objects put inside of the BTree, which can be removed and changed.

* README: File including information about the program, how to use it, what some struggles were, and 

# Compiling and Usage 
The program is compiled by typing the following when in the current working directory of the files in your terminal: 

```
$ javac *.java
```
Execute the following to run GeneBankCreateBTree: 

```
java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]
```
  
Execute the following to run GeneBankSearch: 

```
java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]
```
  
# With Cache vs. Without Cache
####For GeneBankCreateBTree
#####With Cache:
As state previously, the programs running time is significantly lowered when running with a cache implementation. The program isnt as fast as it possibly could be but has a recorded running time with a cache of 37 seconds on average. 

#####Without Cache:
Without a cache, the program can take a while to operate. The recorded time we had in our test was 1 minute and 18 seconds, but it might differ from test to test.

As seen from these two operations, it is significantly faster to run with a cache then it is to run without.

###For GeneBankSearch:
With Cache: We ran the program with a timer and got an approximate time of 22.1 seconds to finish the run.

Without Cache: When running without the cache we saw no significant difference, the program ran approximately 0.2 seconds slower when run without the cache.

When it comes to GeneBankSearch we concluded that adding a cache does not make a significant difference in the runtime of the program.

###100 Size cache compared to the 500 Size cache
* 100 size cache running time:
* 500 size cache running time:

# Notes:
Problems: This program was a difficult one to say the lease, we ran into several programs. The code seems to be quite fragile because of how many interlocking parts there are in it, so by changing one small variable name or trying to make a method more efficient, me and Riley both easily had messed up the code. We eventually got a working product and then were very careful to not mess up what we had.

Known issues: The program reports several results off by one number (in our tests it was only 4 different values, and 1 misplaced value).

Additional notes: It was an enjoyable project to work on! The program isn't as good as it could be as we were down one group member and short on time compared to a normal semester this project is assigned in, but we tried out best.

#Program Layout:
In the BTree there are several pieces of metadata store within, the pieces of data are stored in order of: sequenceLength, degree, nodeSize, and the root offset.

The BTree program decides what data is held onto and what data goes, meaning that most of the other programs do not have metadata in their storage and can easily be deleted or created. 

# Debugging
As stated earlier in this document, the debugging in the program was very careful and so it didn't get all bugs. This is because it would most likely require a full rewrite of several major methods to do and we simply did not have enough time with the programming power we had allotted to doing this project.