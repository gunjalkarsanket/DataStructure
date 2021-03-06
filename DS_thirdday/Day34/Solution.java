package Day34;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'componentsInGraph' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_INTEGER_ARRAY gb as parameter.
     */

    public static List<Integer> componentsInGraph(List<List<Integer>> gb) {
    // Write your code here
    int n = gb.size();

        UF uf = new UF(n * 2);

        for (int i = 0; i < n; i++) {
            int a = gb.get(i).get(0) - 1;
            int b = gb.get(i).get(1) - 1;

            uf.union(a, b);
        }

        int min = 999999;
        int max = -1;

        for (int i = 0; i < n * 2; i++) {
            if (uf.sizes[i] > max) {
                max = uf.sizes[i];
            }

            if (uf.sizes[i] < min && uf.counts[i] == 1 && uf.sizes[i] > 0) {
                min = uf.sizes[i];
            }
        }

//        System.out.println(min + " " + max);
        List<Integer> result = new LinkedList<Integer>();
        result.add(min);
        result.add(max);

        return result;
    }

}

class UF {
    static int count;
    static int[] arr;
    static int[] sizes;
    static int[] counts;

    UF(int n) {
        count = n;
        arr = new int[n];
        sizes = new int[n];
        counts = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = i;
            sizes[i] = 1;
            counts[i] = 0;
        }
    }

    static int findSet(int i) {
        if (arr[i] == i) {
            return i;
        } else {
            return findSet(arr[i]);
        }
    }

    static boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    void union(int i, int j) {
        if (isSameSet(i, j))
            return;

        counts[i] = 1;
        counts[j] = 1;

        int a = findSet(i);
        int b = findSet(j);

        arr[a] = b;
        sizes[b] = sizes[b] + sizes[a];
        sizes[a] = 0;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> gb = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                gb.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.componentsInGraph(gb);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

