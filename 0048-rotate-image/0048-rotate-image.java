class Solution {
    public void rotate(int[][] arr) {
       for(int i=0; i<arr.length; i++){
            for(int j=0; j<i; j++){
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }
        for(int i=0; i<arr.length; i++){
            int st =0;
            int end = arr[0].length -1;
            while(st <end){
                int temp = arr[i][st];
                arr[i][st] = arr[i][end];
                arr[i][end] = temp;
                st ++;
                end --;
            }
        } 
        
    }
}