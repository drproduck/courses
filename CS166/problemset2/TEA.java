import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by drproduck on 2/12/17.
 */
public class TEA {

    static final int K0 = 0xa56babcd;
    static final int K1 = 0x00000000;
    static final int K2 = 0xffffffff;
    static final int K3 = 0xabcdef01;
    static final int delta = 0x9e3779b9;
    static final int fun = 11110000;

    public static void main(String[] args) throws IOException {
        File read = new File("//home//drproduck//Desktop//alice.jpg");
        File write = new File("alicejpg.jpg");
        File writeDec = new File("alicejpgdec.jpg");
        encrypt(read, write, 1000);
        decrypt(write, writeDec, 1000);

        File fakeEncrypt = new File("fake.jpg");
        File fakeDecrypt = new File("after-fake.jpg");
        fakeEncrypt(read, fakeEncrypt);
        fakeDecrypt(fakeEncrypt, fakeDecrypt);
    }

    static void fakeEncrypt(File read, File write) throws IOException {
        byte[] a = Files.readAllBytes(read.toPath());
        for (int i = 0; i < a.length; i++) {
            a[i] += fun;
        }
        Files.write(write.toPath(), a);
    }

    static void fakeDecrypt(File read, File write) throws IOException {
        byte[] a = Files.readAllBytes(read.toPath());
        for (int i = 0; i < a.length; i++) {
            a[i] -= fun;
        }
        Files.write(write.toPath(), a);
    }

    public static void encrypt(File read, File write, int start) throws IOException {
        byte[] array = Files.readAllBytes(read.toPath());
        for (int i = start; i + 8 < array.length; i += 8) {
            int L = (array[i]&0xff) << 24 | (array[i + 1]&0xff) << 16 |  (array[i + 1]&0xff) << 8 | (array[i + 3]&0xff);
            int R = (array[i + 4]&0xff) << 24 | (array[i + 5]&0xff) << 16 | (array[i + 6]&0xff) << 8 | (array[i + 7]&0xff);
            int sum = 0;
            for (int j = 0; j < 32; j++) {
                sum += delta;
                L += ((R << 4) + K0) ^ (R + sum) ^ ((R >> 5) + K1);
                R += ((L << 4) + K2) ^ (L + sum) ^ ((L >> 5) + K3);
            }
            array[i] = (byte) (L >> 24);
            array[i + 1] = (byte) (L >> 16);
            array[i + 2] = (byte) (L >> 8);
            array[i + 3] = (byte) (L);
            array[i + 4] = (byte) (R >> 24);
            array[i + 5] = (byte) (R >> 16);
            array[i + 6] = (byte) (R >> 8);
            array[i + 7] = (byte) R;
        }
        Files.write(write.toPath(), array);
    }

    public static void decrypt(File read, File write, int start) throws IOException {
        byte[] array = Files.readAllBytes(read.toPath());
        for (int i = start; i + 8 < array.length; i += 8) {
            int L = (array[i]&0xff) << 24 | (array[i+1]&0xff) << 16 | (array[i+2]&0xff) << 8 | (array[i+3]&0xff);
            int R = (array[i+4]&0xff) | (array[i+5]&0xff) << 16 | (array[i+6]&0xff) << 8 | (array[i+7]&0xff);
            int sum = 0xc6ef3720;
            for (int j = 0; j < 32; j++) {
                R -= (((L << 4) + K2) ^ (L + sum) ^ ((L >> 5) + K3));
                L -= (((R << 4) + K0) ^ (R + sum) ^ ((R >> 5) + K1));
                sum -= delta;
            }
            array[i] = (byte) (L >> 24);
            array[i + 1] = (byte) (L >> 16);
            array[i + 2] = (byte) (L >> 8);
            array[i + 3] = (byte) (L);
            array[i + 4] = (byte) (R >> 24);
            array[i + 5] = (byte) (R >> 16);
            array[i + 6] = (byte) (R >> 8);
            array[i + 7] = (byte) R;
        }
        Files.write(write.toPath(), array);
    }
}