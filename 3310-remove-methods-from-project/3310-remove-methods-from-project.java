class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
         ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < invocations.length; i++) {
            int from = invocations[i][0];
            int to = invocations[i][1];
            graph.get(from).add(to);
        }

        // Find all suspicious methods
        boolean[] suspicious = new boolean[n];

        Stack<Integer> stack = new Stack<>();
        stack.push(k);

        while (!stack.isEmpty()) {
            int curr = stack.pop();

            if (suspicious[curr]) {
                continue;
            }

            suspicious[curr] = true;

            for (int next : graph.get(curr)) {
                if (!suspicious[next]) {
                    stack.push(next);
                }
            }
        }

        // Check if any outside method invokes a suspicious one
        for (int i = 0; i < invocations.length; i++) {
            int from = invocations[i][0];
            int to = invocations[i][1];

            if (!suspicious[from] && suspicious[to]) {
                ArrayList<Integer> ans = new ArrayList<>();

                for (int j = 0; j < n; j++) {
                    ans.add(j);
                }

                return ans;
            }
        }

        // Return remaining methods
        ArrayList<Integer> ans = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!suspicious[i]) {
                ans.add(i);
            }
        }

        return ans;
    }
}