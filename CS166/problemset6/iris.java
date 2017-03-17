import java.util.Arrays;

/**
 * Created by drproduck on 3/15/17.
 */
public class iris {
    public static void main(String[] args){
        String A = "be439ad598ef5147";
        String B = "9c8b7a1425369584";
        String C = "885522336699ccbb";
        String[] c = {A, B, C};
        String[] a = {"c975a2132e89ceaf", "db9a8675342fec15", "a6039ad5f8cfd965", "1dca7a54273497cc", "af8b6c7d5e3f0f9a"};
        int[][] b = new int[3][5];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                String x = c[i];
                String y = a[j];
                int score = 0;
                for (int k = 0; k < 16; k++) {
                    if (x.charAt(k) == y.charAt(k)) {
                        score++;
                    }
                }
                b[i][j] = score;
            }
        }
        System.out.println(Arrays.deepToString(b));
    }
}
