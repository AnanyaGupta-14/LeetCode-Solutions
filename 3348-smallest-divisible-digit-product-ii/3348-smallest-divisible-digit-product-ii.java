class Solution {
     private static final Map<Integer, Map<Integer, Integer>> FACTOR_COUNTS = Map.of(
            0, Map.of(),
            1, Map.of(),
            2, Map.of(2, 1),
            3, Map.of(3, 1),
            4, Map.of(2, 2),
            5, Map.of(5, 1),
            6, Map.of(2, 1, 3, 1),
            7, Map.of(7, 1),
            8, Map.of(2, 3),
            9, Map.of(3, 2)
    );

    public String smallestNumber(String num, long t) {

        PrimeResult primeResult = getPrimeCount(t);

        if (!primeResult.valid) {
            return "-1";
        }

        Map<Integer, Integer> primeCount = primeResult.count;

        Map<Integer, Integer> factorCount = getFactorCount(primeCount);

        if (sumValues(factorCount) > num.length()) {
            return construct(factorCount);
        }

        Map<Integer, Integer> primeCountPrefix = getPrimeCount(num);

        int firstZeroIndex = num.indexOf('0');

        if (firstZeroIndex == -1) {
            firstZeroIndex = num.length();

            if (isSubset(primeCount, primeCountPrefix)) {
                return num;
            }
        }

        for (int i = num.length() - 1; i >= 0; --i) {

            int d = num.charAt(i) - '0';

            primeCountPrefix =
                    subtract(primeCountPrefix, FACTOR_COUNTS.get(d));

            int spaceAfterThisDigit =
                    num.length() - 1 - i;

            if (i > firstZeroIndex) {
                continue;
            }

            for (int biggerDigit = d + 1;
                 biggerDigit < 10;
                 biggerDigit++) {

                Map<Integer, Integer> factorsAfterReplacement =
                        getFactorCount(
                                subtract(
                                        subtract(
                                                primeCount,
                                                primeCountPrefix
                                        ),
                                        FACTOR_COUNTS.get(biggerDigit)
                                )
                        );

                if (sumValues(factorsAfterReplacement)
                        <= spaceAfterThisDigit) {

                    int fillOnes =
                            spaceAfterThisDigit
                                    - sumValues(factorsAfterReplacement);

                    return num.substring(0, i)
                            + biggerDigit
                            + "1".repeat(fillOnes)
                            + construct(factorsAfterReplacement);
                }
            }
        }

        Map<Integer, Integer> factorsAfterExtension =
                getFactorCount(primeCount);

        return "1".repeat(
                num.length() + 1
                        - sumValues(factorsAfterExtension)
        ) + construct(factorsAfterExtension);
    }

    private static class PrimeResult {
        Map<Integer, Integer> count;
        boolean valid;

        PrimeResult(Map<Integer, Integer> count,
                    boolean valid) {
            this.count = count;
            this.valid = valid;
        }
    }

    private PrimeResult getPrimeCount(long t) {

        Map<Integer, Integer> count =
                new HashMap<>();

        count.put(2, 0);
        count.put(3, 0);
        count.put(5, 0);
        count.put(7, 0);

        int[] primes = {2, 3, 5, 7};

        for (int prime : primes) {

            while (t % prime == 0) {

                t /= prime;

                count.put(
                        prime,
                        count.get(prime) + 1
                );
            }
        }

        return new PrimeResult(count, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String num) {

        Map<Integer, Integer> count =
                new HashMap<>();

        count.put(2, 0);
        count.put(3, 0);
        count.put(5, 0);
        count.put(7, 0);

        for (char c : num.toCharArray()) {

            Map<Integer, Integer> factors =
                    FACTOR_COUNTS.get(c - '0');

            for (Map.Entry<Integer, Integer> e :
                    factors.entrySet()) {

                count.put(
                        e.getKey(),
                        count.get(e.getKey())
                                + e.getValue()
                );
            }
        }

        return count;
    }

    private Map<Integer, Integer> getFactorCount(
            Map<Integer, Integer> count) {

        int count8 = count.get(2) / 3;
        int remaining2 = count.get(2) % 3;

        int count9 = count.get(3) / 2;
        int count3 = count.get(3) % 2;

        int count4 = remaining2 / 2;
        int count2 = remaining2 % 2;

        int count6 = 0;

        if (count2 == 1 && count3 == 1) {
            count2 = 0;
            count3 = 0;
            count6 = 1;
        }

        if (count3 == 1 && count4 == 1) {
            count2 = 1;
            count6 = 1;
            count3 = 0;
            count4 = 0;
        }

        Map<Integer, Integer> result =
                new HashMap<>();

        result.put(2, count2);
        result.put(3, count3);
        result.put(4, count4);
        result.put(5, count.get(5));
        result.put(6, count6);
        result.put(7, count.get(7));
        result.put(8, count8);
        result.put(9, count9);

        return result;
    }

    private String construct(
            Map<Integer, Integer> factors) {

        StringBuilder sb = new StringBuilder();

        for (int digit = 2; digit <= 9; digit++) {

            int freq =
                    factors.getOrDefault(digit, 0);

            for (int i = 0; i < freq; i++) {
                sb.append(digit);
            }
        }

        return sb.toString();
    }

    private boolean isSubset(
            Map<Integer, Integer> a,
            Map<Integer, Integer> b) {

        for (Map.Entry<Integer, Integer> e :
                a.entrySet()) {

            if (b.getOrDefault(
                    e.getKey(), 0
            ) < e.getValue()) {

                return false;
            }
        }

        return true;
    }

    private Map<Integer, Integer> subtract(
            Map<Integer, Integer> a,
            Map<Integer, Integer> b) {

        Map<Integer, Integer> result =
                new HashMap<>(a);

        for (Map.Entry<Integer, Integer> e :
                b.entrySet()) {

            int key = e.getKey();
            int value = e.getValue();

            result.put(
                    key,
                    Math.max(
                            0,
                            result.getOrDefault(key, 0)
                                    - value
                    )
            );
        }

        return result;
    }

    private int sumValues(
            Map<Integer, Integer> map) {

        int sum = 0;

        for (int value : map.values()) {
            sum += value;
        }

        return sum;
    }
}