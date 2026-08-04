class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        ArrayList<Integer> arr = new ArrayList<>();
        int max = 0;
        int min=101;
        for(int i =0; i<nums.length; i++){
                if(nums[i]> max){
                    max = nums[i];
                }
                if(nums[i]<min){
                    min = nums[i];
                }
        }

        int len = max - min +1;
        if(len==nums.length) return arr;
        else{
            for(int j=min; j<=max; j++){
            arr.add(j);
        }
            for(int k=0; k<nums.length; k++){
                for(int i=0; i<arr.size(); i++){
                    if(nums[k]==arr.get(i)){
                         arr.remove(i);
                         break;
                    } 
                }
            }
            return arr;
        }
        
    }
}