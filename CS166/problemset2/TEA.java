import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by drproduck on 2/12/17.
 */
public class TEA {
    private static final long K0 = 0xa56babcd;
    private static final long K1 = 0x00000000;
    private static final long K2 = 0xffffffff;
    private static final long K3 = 0xabcdef01;
    private static final long delta = 0x9e3779b9;

    public static void main(String[] args) throws IOException {
        File read = new File("//home//drproduck//Desktop//alice.jpg");
        File write = new File("alicejpg.jpg");
        File writeDec = new File("alicejpgdec.jpg");
        encrypt(read, write, 0);
        decrypt(write, writeDec, 0);
    }

    public static void encrypt(File read, File write, int start) throws IOException {
        BufferedImage image = ImageIO.read(read);
        byte[] array = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        for (int i = start; i + 8 < array.length; i += 8) {
            long L = array[i] << 24 + array[i + 1] << 16 + array[i + 2] << 8 + array[i + 3];
            long R = array[i + 4] << 24 + array[i + 5] << 16 + array[i + 6] << 8 + array[i + 7];
            long sum = 0;
            for (int j = 0; j < 32; j++) {
                sum += delta;
                L += (((R << 4) + K0) ^ (R + sum) ^ ((R >> 5) + K1));
                R += (((L << 4) + K2) ^ (L + sum) ^ ((L >> 5) + K3));
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
        ImageIO.write(image, "bmp", write);
    }

    public static void decrypt(File read, File write, int start) throws IOException {
        BufferedImage image = ImageIO.read(read);
        byte[] array = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        for (int i = start; i + 8 < array.length; i += 8) {
            long L = array[i] << 24 + array[i + 1] << 16 + array[i + 2] << 8 + array[i + 3];
            long R = array[i + 4] << 24 + array[i + 5] << 16 + array[i + 6] << 8 + array[i + 7];
            long sum = delta << 5;
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
        ImageIO.write(image, "bmp", write);
    }
}