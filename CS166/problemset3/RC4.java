/**
 * Created by drproduck on 2/15/17.
 */
public class RC4 {
    static int[] S = new int[256];
    static int[] K = new int[256];
    static int[] key = {0x1a, 0x2b, 0x3c, 0x4d, 0x5e, 0x6f, 0x77};
    static int i = 0;
    static int j = 0;
    public static void main(String[] args) {
        initialization();
        System.out.println("After initialization: ");
        print();
        generate(100);
        System.out.println("After 100 rounds: ");
        print();
        generate(900);
        System.out.println("After 1000 rounds: ");
        print();
    }

    static void print() {
        printS();
        System.out.printf("i = %d, j = %d\n", i, j);
        System.out.println();
    }

    public static void initialization() {
       for (int i = 0; i < 256; i++) {
            S[i] = i;
            K[i] = key[i % key.length];
        }
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + K[i]) % 256;
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }
    }
    public static int generate(int c) {
        i = (i + 1) % 256;
        j = (j + S[i]) % 256;
        int temp = S[i];
        S[i] = S[j];
        S[j] = temp;
        int keyStreamByte = S[(S[i] + S[j]) % 256];
        return keyStreamByte;
    }
    static void printS() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            if (i % 16==0 && i != 0) {
                s.append(Integer.toHexString(S[i]) + "\n");
            } else s.append(Integer.toHexString(S[i]) + " ");
        }
        System.out.println("permutation S: \n"+s.toString());
    }
}
