class Solution {
    public List<Integer> getRow(int rowIndex) {
        int n = rowIndex +1;
        List<List<Integer>> ans = new ArrayList<>();
        for(int i =0; i<n; i++){
            List<Integer> list = new ArrayList<>();
            for(int j=0; j<=i; j++){
                list.add(1);
            }
            ans.add(list);
        }
        for(int i=0; i<n; i++){
            for(int j=0; j<=i;j++){
                if(j==0 || j==i){
                    ans.get(i).set(j, 1);
                }
                else{
                    int a = ans.get(i-1).get(j) + ans.get(i-1).get(j-1);
                    ans.get(i).set(j,a);
                }
            }
        }
        return ans.get(rowIndex);
    }
}