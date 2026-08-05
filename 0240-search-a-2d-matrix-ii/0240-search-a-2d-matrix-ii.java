class Solution {
    public boolean searchMatrix(int[][] arr, int target) {
        for(int i=0 ;i<arr.length; i++){
            for(int j=0; j<arr[0].length;j++){
                if(target==arr[i][j]) return true;
            }
        }
        return false;
    }
}