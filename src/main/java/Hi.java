import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;

import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int q = in.nextInt();
        int[] a = new int[n];
        Queue<Integer> Q = new ArrayBlockingQueue<>();
        for(int a_i=0; a_i < n; a_i++){
            a[a_i] = in.nextInt();
            Q.add(a[a_i]);
        }

        for(int i=0; i<k; i++)
            Q.add(Q.poll());
        for(int i=0; i<q; i++)
            Q.add(Q.poll());

        for(int a0 = 0; a0 < q; a0++){
            int m = in.nextInt();
            System.out.println(Q.toArray()[m]);
        }
    }
}
