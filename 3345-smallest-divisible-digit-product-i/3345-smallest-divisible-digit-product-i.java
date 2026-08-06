class Solution {
    public int smallestNumber(int n, int t) {
        int product = 1;
        int a = 1;
        for(int i=0; i<10; i++){
            if(n>10)  a = n/ 10;
            int b = n%10;
            product  = a*b;
            if(product%t==0) return n;
            else n++;
        }
        return n;
        }
    }
