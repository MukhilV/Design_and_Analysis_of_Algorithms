## Commands to compile the java files
	javac createkn01.java 
	javac bruteforce.java
    javac dynpro.java
    javac igreedy.java
	
## Commands to run the program
    java createkn01 knapsack01.txt
    java bruteforce knapsack01.txt
    java dynpro knapsack01.txt
    java igreedy knapsack01.txt

- **java createkn01 knapsack01.txt** - creates items with random profits and weights and stores it in *knapsack01.txt*.

- **java bruteforce knapsack01.txt** - computes the maximum profit by brutefore algorithm by taking *knapsack01.txt* as input and stores the result in *output1.txt*.

- **java dynpro knapsack01.txt** - computes the maximum profit by refined DP algorithm algorithm by taking *knapsack01.txt* as input and stores the result in output2.txt. This program also creates a file called *entries2.txt*.

- **java igreedy knapsack01.txt** - computes the maximum profit by Greedy4 and MaxB algorithm by taking *knapsack01.txt* as input and stores the result in *output3.txt*.
