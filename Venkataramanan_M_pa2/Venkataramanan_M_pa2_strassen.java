import java.util.Random;

class MatrixMultiplication {
    
	// Funtion to computer standard matrix multiplication
    int[][] standardMatrixMultiplication(int a[][], int b[][]) { 
        int n = a.length;
        int c[][] = new int[n][n];
        int i, j, k; 
        for (i = 0; i < n; i++) { 
            for (j = 0; j < n; j++) { 
                c[i][j] = 0; 
                for (k = 0; k < n; k++) 
                    c[i][j] += a[i][k] * b[k][j]; 
            } 
        }
        return c;
    }

	// Function to subtract two matrices
	public int[][] sub(int[][] A, int[][] B)
	{
		int n = A.length;
		int[][] C = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				C[i][j] = A[i][j] - B[i][j];

		return C;
	}

	// Function to add two matrices
	public int[][] add(int[][] A, int[][] B)
	{
		int n = A.length;
		int[][] C = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				C[i][j] = A[i][j] + B[i][j];

		return C;
	}
    
	// Funtion to compute matrix multiplation by strassen algorithm
	int[][] strassenMultiply(int[][] A,  int[][] B)
	{

		int n = A.length;

		int[][] R = new int[n][n];

		if (n == 1)
			R[0][0] = A[0][0] * B[0][0];

		else {
			// Initializing 8 (n/2) matrices
			int[][] A11 = new int[n / 2][n / 2];
			int[][] A12 = new int[n / 2][n / 2];
			int[][] A21 = new int[n / 2][n / 2];
			int[][] A22 = new int[n / 2][n / 2];
			int[][] B11 = new int[n / 2][n / 2];
			int[][] B12 = new int[n / 2][n / 2];
			int[][] B21 = new int[n / 2][n / 2];
			int[][] B22 = new int[n / 2][n / 2];

			// Dividing matrix A into 4 halves
			divideMatrix(A, A11, 0, 0);
			divideMatrix(A, A12, 0, n / 2);
			divideMatrix(A, A21, n / 2, 0);
			divideMatrix(A, A22, n / 2, n / 2);

			// Dividing matrix B into 4 halves
			divideMatrix(B, B11, 0, 0);
			divideMatrix(B, B12, 0, n / 2);
			divideMatrix(B, B21, n / 2, 0);
			divideMatrix(B, B22, n / 2, n / 2);

			// M1 =(A11+A22)×(B11+B22)
			int[][] M1 = strassenMultiply(add(A11, A22), add(B11, B22));
		
			// M2 =(A21+A22)×(B11)
			int[][] M2 = strassenMultiply(add(A21, A22), B11);
		
			// M3 =(A11)×(B12-B22)
			int[][] M3 = strassenMultiply(A11, sub(B12, B22));
		
			// M4 =A22×(B21−B11)
			int[][] M4 = strassenMultiply(A22, sub(B21, B11));
		
			// M5 =(A11+A12)×(B22)
			int[][] M5 = strassenMultiply(add(A11, A12), B22);
		
			// M6 =(A21-A11)×(B11+B12)
			int[][] M6 = strassenMultiply(sub(A21, A11), add(B11, B12));
		
			// M7 =(A12-A22)x(B21−B22)
			int[][] M7 = strassenMultiply(sub(A12, A22), add(B21, B22));

			// P =M1+M4-M5+M7
			int[][] C11 = add(sub(add(M1, M4), M5), M7);
		
			// Q =M3+M5
			int[][] C12 = add(M3, M5);
		
			// R =M2+M4
			int[][] C21 = add(M2, M4);
		
			// S =M1+M3−M2+M6
			int[][] C22 = add(sub(add(M1, M3), M2), M6);

			// Step 3: Join 4 halves into one result matrix
			join(C11, R, 0, 0);
			join(C12, R, 0, n / 2);
			join(C21, R, n / 2, 0);
			join(C22, R, n / 2, n / 2);
		}

		// Step 4: Return result
		return R;
	}

	// Function to split parent matrix into child matrices
	public void divideMatrix(int[][] Parent, int[][] Child, int I, int J)
	{
		int i2 = I;
		for (int i1 = 0; i1 < Child.length; i1++){
			int j2 = J;
			for (int j1 = 0; j1 < Child.length;j1++){
				Child[i1][j1] = Parent[i2][j2];
				j2+=1;
			}   
			i2+=1; 
		}	
	}

	// Function to join child matrices into parent matrix
	public void join(int[][] Child, int[][] Parent, int I, int J)

	{	int i2=I;
		for (int i1 = 0; i1 < Child.length; i1++){
			int j2 = J;
			for (int j1 = 0; j1 < Child.length;j1++){
				 Parent[i2][j2] = Child[i1][j1];
				 j2+=1;
			}
			i2+=1;
		}
	}

}

class Utilities {
	// function to generate random values
    int[][] generateMatrixWithRandomValues(int n){
        int a[][] = new int[n][n];
        Random rand = new Random();
		int limit = (int)Math.sqrt(Integer.MAX_VALUE/n);
        for(int i=0; i<n; i++){
		    for(int j=0; j<n;j++){
		        a[i][j] = rand.nextInt(limit);
		    }
		}
		return a;
    }
    
	// function to print the matrix values
    void printMatrix(int[][] a){
        int n = a.length;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println("");
        }
    }
    
	// function to check if the given two matrices are equal
    boolean isEqual(int mat1[][], int mat2[][]){
        int n = mat1.length;

		if(mat1.length!=mat2.length) return false;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(mat1[i][j] != mat2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
}

public class Venkataramanan_M_pa2_strassen
{
	// Declaring necessary variables
    static int a[][];
    static int b[][];
    static int res1[][];
    static int res2[][];

 
	public static void main(String[] args) {
	    
	    if(args.length != 1){
            System.out.println("Usage : java Venkataramanan_M_pa2_strassen <number>");
            System.out.println("The number should be power of 2");
            return;
        }
        
        int n = Integer.parseInt(args[0]);
	    
	    Utilities utilities = new Utilities();
	    
		// Generate random values for the matrices A nad B
		a = utilities.generateMatrixWithRandomValues(n);
		b = utilities.generateMatrixWithRandomValues(n);
		
		// Print A
		System.out.println("Matrix A : ");
		utilities.printMatrix(a);
		
		// Print B
		System.out.println("\nMatrix B : ");
		utilities.printMatrix(b);
		
		MatrixMultiplication MatMulObj = new MatrixMultiplication();
		
		// Compute Matrix multiplication by standard matrix multiplication
		res1 = MatMulObj.standardMatrixMultiplication(a, b);

		// Printing result of Standard matrix multiplication
		System.out.println("\nThe Standard Matrix Multiplication A*B : ");
		utilities.printMatrix(res1);
		
		// Computer Matrix multiplication by Strassen Algorithm
		res2 = MatMulObj.strassenMultiply(a, b);

		// Printing the result of Strassen matrix multiplication, 
		// only if that is equal to the values computed by standard multiplication
		if(utilities.isEqual(res1, res2)){
		    System.out.println("\nThe Strassen Multiplication A*B : ");
	        utilities.printMatrix(res2);
		}
	}
}




