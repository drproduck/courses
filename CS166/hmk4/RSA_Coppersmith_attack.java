import java.math.BigInteger;

/**
 * Created by drproduck on 2/22/17.
 */
public class RSA_Coppersmith_attack {
    public static void main(String[] args) {
        BigInteger apub = new BigInteger("5356488760553659");
        BigInteger bpub = new BigInteger("8021928613673473");
        BigInteger cpub = new BigInteger("56086910298885139");
        BigInteger asec = new BigInteger("4324345136725864");
        BigInteger bsec = new BigInteger("2102800715763550");
        BigInteger csec = new BigInteger("46223668621385973");
        BigInteger[] a = {asec, bsec, csec};
        BigInteger[] b = {apub, bpub, cpub};
        BigInteger res = CRT(a, b);
        BigInteger g = search(new BigInteger("0"), res, res, 3);
        System.out.println(g.toString()); //61552787537619
    }

    public static BigInteger CRT(BigInteger[] remainder, BigInteger[] modulo) {
        int l = remainder.length;
        BigInteger m = modulo[0];
        BigInteger r = remainder[0];
        for (int i = 1; i < l; i++) {
            BigInteger xi = m.modInverse(modulo[i]);
            BigInteger xj = modulo[i].modInverse(m);
            r = (xi.multiply(m).multiply(remainder[i])).add(xj.multiply(modulo[i]).multiply(r));
            m = m.multiply(modulo[i]);
            r = r.mod(m);
        }
        return r;
    }

    static BigInteger search(BigInteger min, BigInteger max, BigInteger goal, int k) {
        if (min.equals(max)) return min;
        BigInteger mid = min.add((max.subtract(min)).divide(new BigInteger("2")));
        switch (mid.pow(k).compareTo(goal)) {
            case 0:
                return mid;
            case -1:
                return search(mid.add(new BigInteger("1")), max, goal, k);
            case 1:
                return search(min, mid.subtract(new BigInteger("1")), goal, k);
        }
        return null;
    }

}