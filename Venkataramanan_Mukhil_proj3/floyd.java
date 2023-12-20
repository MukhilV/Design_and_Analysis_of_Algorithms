
import java.io.*;
import java.util.*;


class floyd {

    static int INF = 99999;
    static int V = 0, problemNumber = 0;
	String shortestPath = "";

	void allPairShortestPath(int dist[][], int P[][])
	{

		int i,j,k;
		for (k=1; k<=V; k++) {
			for (i=1; i<=V; i++) {
				for (j=1; j<=V; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						    dist[i][j] = dist[i][k] + dist[k][j];
						    P[i][j] = k;
						}	
				}
			}
		}

        writeOutput("Output.txt", dist, P);
	}


	void printPathInOutPutFile(int P[][], int q, int r, FileWriter myWriter) {
	    if(P[q][r]!=0) {
	        printPathInOutPutFile(P, q,P[q][r], myWriter);
			shortestPath = shortestPath+""+P[q][r];
	        printPathInOutPutFile(P, P[q][r],r, myWriter);
	    }
	    return;
	}


	public void writeOutput(String filePath, int dist[][], int P[][]) {
		try {
		FileWriter myWriter = new FileWriter(filePath, true);
		myWriter.write("Problem"+problemNumber+": n = "+V+"\n");
		myWriter.write("P matrix:\n");
		for(int i=1; i<=V; i++){
			for(int j=1; j<=V; j++){
				myWriter.write(P[i][j]+"  ");
			}
			myWriter.write("\n");
		}
		myWriter.write("\n");
		
			for(int i=1; i<=V; i++){
				myWriter.write("V"+i+" - Vj:shortest path and length\n");
				for(int j=1; j<=V; j++){
					myWriter.write("V"+i+" ");
					printPathInOutPutFile(P, i, j, myWriter);
					if(shortestPath!=""){
						for(int k=0; k<shortestPath.length(); k++){
							myWriter.write("V"+shortestPath.charAt(k)+" ");
						}
					}
					myWriter.write("V"+j+" : "+dist[i][j]+"\n");
					shortestPath="";

				}
				myWriter.write("\n");
			}

		myWriter.write("\n");
		myWriter.close();
		} catch (IOException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
		}
	}

	
	// Driver Code
	public static void main(String[] args){

		// if args length is not equal to 1, then the usage is wrong
		if(args.length!=1){
			System.out.println("Error!. Wrong usage of the file. \nCorrect usage : java <file_name> <input_file_path> ");
			return ;
		}
		
		String filepath = args[0];
        try {

			FileWriter myWriter = new FileWriter("Output.txt");
            myWriter.close();

            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);  

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String strArr[] = data.split(":", 0);
				int startIndex = strArr[0].indexOf("Problem ") + "Problem".length();
				// int endIndex = strArr[0].indexOf(" ");
                problemNumber = Integer.parseInt(strArr[0].substring(startIndex).trim());
				startIndex = strArr[1].indexOf(" n = ") + " n = ".length();
                V = Integer.parseInt(strArr[1].substring(startIndex));
				// System.out.println("V:"+V);
				int P[][] = new int[V+1][V+1];
                int graph1[][] =  new int[V+1][V+1];

                for(int i=0; i<V; i++){
                    String row = myReader.nextLine();
                    String values[] = row.split("\s+",0);
                    for(int j=0; j<V; j++){	
                        graph1[i+1][j+1] = Integer.parseInt(values[j]);
                    }
                }
                if(myReader.hasNextLine())data = myReader.nextLine();

				floyd a = new floyd();
                a.allPairShortestPath(graph1, P);
            }
            myReader.close();
        } catch (Exception e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}




				/************************************************** 
				
				int graph2[][] = { { 0, 0, 0, 0, 0, 0, 0 },
		                { 0, 0, 4, INF, INF, INF, 10, INF },
						{ 0, 3, 0, INF, 18, INF, INF, INF },
						{ 0, INF, 6, 0, INF, INF, INF, INF },
						{ 0, INF, 5, 15, 0, 2, 19, 5 },
						{ 0, INF, INF, 12, 1, 0, INF, INF }, 
						{ 0, INF, INF, INF, INF, INF, 0, 10}, 
						{ 0, INF, INF, INF, 8, INF, INF, 0}};

                a.allPairShortestPath(graph2, P);

				/************************************************** */