import java.math.BigInteger;
import java.util.Random;


class LargeInteger{
    String addStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry != 0) {
            int x = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int y = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int sum = x + y + carry;
            carry = sum / 10;
            sum %= 10;
            result.insert(0, sum);
            i--;
            j--;
        }
        return result.toString();
    }

    String subtractStrings(String num1, String num2) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        while (i >= 0 || j >= 0) {
            int x = (i >= 0) ? num1.charAt(i) - '0' : 0;
            int y = (j >= 0) ? num2.charAt(j) - '0' : 0;
            int diff = x - y - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.insert(0, diff);
            i--;
            j--;
        }
        return result.toString();
    }

    String padZeros(String num, int zeros) {
        StringBuilder result = new StringBuilder(num);
        for (int i = 0; i < zeros; i++) {
            result.append("0");
        }
        return result.toString();
    }
}


class LargeIntegerMultiplication {

    public String divideInto2AndMultiply(String u, String v) {
        // If length of u or v is 1, perform direct multiplication
        if (u.length() == 1 || v.length() == 1) {
            return String.valueOf(Integer.parseInt(u) * Integer.parseInt(v));
        }
        
        int n = Math.max(u.length(), v.length());
        int half = n / 2;

        // Splitting x and y into halves
        String x = u.substring(0, u.length() - half);
        String y = u.substring(u.length() - half);
        String w = v.substring(0, v.length() - half);
        String z = v.substring(v.length() - half);
        
        LargeInteger largeInteger = new LargeInteger();

        // Recursive steps
        String c0 = divideInto2AndMultiply(x, w);
        String c1 = divideInto2AndMultiply(y, z);
        String c2 = divideInto2AndMultiply(largeInteger.addStrings(x, y), largeInteger.addStrings(w, z));
        c2 = largeInteger.subtractStrings(largeInteger.subtractStrings(c2, c0), c1);

        // Combine results using formula
        String result = largeInteger.addStrings(
                            largeInteger.addStrings(
                                largeInteger.padZeros(c0, 2 * half), 
                                largeInteger.padZeros(c2, half)
                                ), 
                            c1
                        );
        return result;
    }

     /** 
     *  divideInto3AndMultiply - Algorithm:
     * 
     *  Lets assume u and v are two numbers for 6 digits, then they can be splitted into
     *  u = u0*10^4 + u1*10^2 + u2
     *  v = v0*10^4 + v1*10^2 + v2
     * 
     *  then,
     *  u*v = (u0*10^4 + u1*10^2 + u2) * (v0*10^4 + v1*10^2 + v2)
     * 
     *  upon solving the above equation, we get the final formula to calculate the product by dividing the integer into 3 is,
     *  uv = (u0*v0)x10^4m 
     *          + [(u0*v1) + (u1*v0)]x10^3m 
     *          + [(u0*v2)+(u1*v1)+(u2*v0)]x10^2m
     *          + [(u1*v2)+(u2*v1)]x10^m 
     *          + (u2*v2)
     * where, m = n/3 and n=max(len(u),len(v))
     */
    String divideInto3AndMultiply(String u,String v){

        // Base case
        if (u.length() <= 4 || v.length() <= 4 ) {
            int a = Integer.parseInt(u);
            int b = Integer.parseInt(v);
            if (a<0 || b<0) {
                return String.valueOf(0);
            }
            return String.valueOf(a * b);
        }

        if(u.length()<v.length()){
            u = "0".repeat(v.length()-u.length())+u;
        } else {
            v = "0".repeat(u.length()-v.length())+v;
        }

        int n = Math.max(u.length(), v.length());
        
        int sliceSize = n/3;
        
        // split u into u0, u1, u2
        String u0 = u.substring(0, sliceSize);
        String u1 = u.substring(sliceSize, 2*sliceSize);
        String u2 = u.substring(2*sliceSize);
        
        // split v into v0, v1, v2
        String v0 = v.substring(0, sliceSize);
        String v1 = v.substring(sliceSize, 2*sliceSize);
        String v2 = v.substring(2*sliceSize);

        LargeInteger largeInteger = new LargeInteger();

        // c0 = (u0*v0)x10^4m 
        String c0 = largeInteger.padZeros(divideInto3AndMultiply(u0,v0),4*sliceSize);

        //c1 = [(u0*v1) + (u1*v0)]x10^3m
        String c1 = largeInteger.padZeros(
                            largeInteger.addStrings(divideInto3AndMultiply(u0,v1), divideInto3AndMultiply(u1,v0)),
                            3*sliceSize
                        );
        
        // c2 = [(u0*v2)+(u1*v1)+(u2*v0)]x10^2m
        String c2 = largeInteger.padZeros(
                            largeInteger.addStrings(
                                largeInteger.addStrings(divideInto3AndMultiply(u0,v2), divideInto3AndMultiply(u1,v1)),
                                divideInto3AndMultiply(u2,v0)
                            ),
                            2*sliceSize
                        );

        // c3 = [(u1*v2)+(u2*v1)]x10^m
        String c3 = largeInteger.padZeros(
                            largeInteger.addStrings(divideInto3AndMultiply(u1,v2), divideInto3AndMultiply(u2,v1)),
                            sliceSize
                        );
        
        // c4 = (u2*v2)
        String c4 = divideInto3AndMultiply(u2,v2);

        return largeInteger.addStrings(
                    largeInteger.addStrings(
                        largeInteger.addStrings(
                            largeInteger.addStrings(c0,c1), 
                            c2),
                        c3),
                    c4);
    }    
    // */
}


// Utility class
class Utility{

    // function to generate random values
    String generateRandomValue(int n){
        Random rand = new Random();
        String s = ""+ (int)((Math.random() * (9 - 1)) + 1); 
        for(int i=1;i<n;i++){
            s = s+""+rand.nextInt(10);
        }
        return s;
    }

    // function to find max in an array
    int findMax(int arr[]){
        int max = arr[0];
        for(int i=0; i<arr.length; i++){
            if(arr[i]>max) max = arr[i];
        }
        return max;
    }
}

public class Venkataramanan_M_pa2_lim
{
	public static void main(String[] args) {
        
        // Handling error scenario
        if(args.length != 1){
            System.out.println("Usage : java Venkataramanan_M_pa2_lim <number>");
            System.out.println("The number should be multiple of 6");
            return;
        }
        
        int n = Integer.parseInt(args[0]);

        // Handling error scenario
        if(n%6!=0){
            System.out.println("The number should be multiple of 6");
            return;
        }

        // generate random values of length n
		Utility utility= new Utility();
		String val1 = utility.generateRandomValue(n);
		String val2 = utility.generateRandomValue(n);

        // Print generated numbers
        System.out.println("A = "+val1);
        System.out.println("B = "+val2);

        // Calculate values of large integer multiplication
		LargeIntegerMultiplication obj = new LargeIntegerMultiplication();
		String result2 = obj.divideInto2AndMultiply(val1, val2);
		String result3 = obj.divideInto3AndMultiply(val1, val2);

        // Print the results
        System.out.println("The large integer multiplication from the division of two smaller integers is : ");
        System.out.println(result2);
        System.out.println("The large integer multiplication from the division of three smaller integers is : ");
        System.out.println(result3);
        // if(utility.checkIfEquals(result2, result3)){
        //     System.out.println("Results are same.");    
        // } else {
        //     System.out.println("Calculation error!");
        // }
        
	}
}


