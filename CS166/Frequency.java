import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by drproduck on 2/5/17.
 */
public class Frequency {
    public static String cha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String example = "MXDXBVTZWVMXNSPBQXLIMSCCSGXSCJXBOVQXCJZMOJZCVCTVWJCZAAXZBCSSCJXBQCJZCOJZCNSPOXBXSBTVWJCJZDXGXXMOZQMSCSCJXBOVQXCJZMOJZCNSPJZHGXXMOSPLHJZDXZAAXZBXHCSCJXTCSGXSCJXBOVQX";
    public static void main(String[] args) {
        System.out.println("Please enter ciphertext (in uppercase): ");
        Scanner in = new Scanner(System.in);
        String str = in.nextLine();
        char[] seq = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            seq[i] = str.charAt(i);
        }

        int[] count = new int[26];
        for (int i = 0; i < str.length(); i++) {
            count[cha.indexOf(str.charAt(i))]++;
        }
        int[][] table = new int[26][2];
        for (int i = 0; i < 26; i++) {
            table[i][0] = cha.charAt(i)-65;
            table[i][1] = count[i];
        }
        Arrays.sort(table, (a, b) -> a[1] - b[1]);
        System.out.println();
        System.out.println("Frequency of letters in ciphertext: ");
        for (int i = 0; i < 26; i++) {
            System.out.println(cha.charAt(table[i][0])+": "+table[i][1]);
        }
        for (int i = 25; i >= 0; i--) {
            System.out.printf("Please guess a substitution pair in the form \"A=B\": ");
            String next = in.nextLine();
            Scanner tokenizer = new Scanner(next).useDelimiter("\\s*=\\s*");
            char b = tokenizer.next().charAt(0);
            char a = tokenizer.next().charAt(0);
            for (int j = 0; j < seq.length; j++) {
                if (seq[j] == b) {
                    seq[j] = Character.toLowerCase(a);
                }
            }
            for (char c :
                seq) {
                System.out.print(c);
        }
            System.out.println();
        }
    }
}
