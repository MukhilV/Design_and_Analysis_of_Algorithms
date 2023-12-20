class lcs {

	// Multi-dimensional array to store DP values
	int DP[][];
	char path[][];

	// Function to return the length of the longest common subsequence 
	int getLCSLength(String str1, String str2, int m, int n)
	{
		DP= new int[m+1][n+1];
		path= new char[m+1][n+1];
		for (int i=0; i<=m; i++){
			for (int j=0; j<=n; j++){
				
				// initialize the first column and first row to zero
				if (i==0 || j==0) {
 					DP[i][j]=0;
				}

				// If the characters at i and j are equal, add '1' to the value of DP[i-1][j-1] and store in DP[i][j]
				else if (str1.charAt(i-1) == str2.charAt(j-1)) {
					DP[i][j] = DP[i-1][j-1]+1;
					path[i][j] = 'D';
				} 

				// If the If the characters at i and j are not equal, get the max(DP[i-1][j],DP[i][j-1])
				else {
					DP[i][j] = (DP[i-1][j] > DP[i][j-1]) ? DP[i-1][j] : DP[i][j-1];
					path[i][j] = (DP[i-1][j] > DP[i][j-1])? 'U' : 'L';
				}

				// System.out.print(path[i][j]+" ");
			}
			// System.out.println();
		}	 

		// return length of LCS
		return DP[m][n];
	}

	// Function to return the Longest Common Subsequence
	String getLCS(String A, String B){

		// initializing the LCS with empty string
		String longestSubSequence = "";

		// initalizing two pointers, pointing the row and column of the DP array
        int row = A.length(), column = B.length();

		while(row>0 && column>0){
			if(path[row][column]=='D'){
				longestSubSequence = A.charAt(row - 1) + longestSubSequence;
				row--; column --;
			}else if(path[row][column]=='U') row--;
			else if(path[row][column]=='L') column--; 

		}

        // while(row>0 && column>0){

		// 	// if the character corresponding to str1[row]==str2[column], 
		// 	// then DP[row][column] must had got the value from DP[row-1][column-1] added by 1
		// 	// hence, the character at str1[row] or str[column] is a part of LCS
        //     if (A.charAt(row - 1) == B.charAt(column - 1)){
        //         longestSubSequence = A.charAt(row - 1) + longestSubSequence;

		// 		// backtrack to previous character of LCS
        //         row--; column--;
        //     } 

		// 	// Else, DP[row][column] must have got the value from max(DP[row-1][column],DP[row][column-1])
		// 	else if (DP[row - 1][column] > DP[row][column - 1]) row--;
        //     else column--;    
        // }

		//return LCS
		return longestSubSequence;
	}
	

	public static void main(String[] args)
	{
		lcs obj= new lcs();

		if(args.length!=2){
			System.out.println("Error!. Wrong usage of the file. \nCorrect usage : java <file_name> <input_string_1> <input_string_2>");
			return ;
		}

		String str1 = args[0];
		String str2 = args[1];

		int LCSLength = obj.getLCSLength(str1, str2, str1.length(),str2.length());
		System.out.println("Length of LCS : "+LCSLength);

		String LCS = obj.getLCS(str1, str2);
		System.out.println("LCS : "+ LCS);
	}
}

