import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by drproduck on 2/2/17.
 */
public class Console {
    public static void main(String[] args) {
        Console c = new Console();
        c.repl();
       }

    public void repl() {
        System.out.println("Please enter your expression: ");
    boolean end = false;
        Scanner in = new Scanner(System.in);
         while (!end) {
             String s = in.nextLine();
             if (s.equals("quit")) {
                 end = true;
             } else {
                 try {
                     Expression exp = parse(s);
                     System.out.println("-> " + exp.execute());
                 } catch (Exception e) {
                     System.out.println("Unable to parse. Try again");
                 }
             }
         }
    }
    public static Expression parse(String str) throws Exception {
        str = str.trim();
            Scanner in = new Scanner(str).useDelimiter("\\s*\\+\\s*");
            Scanner on;
            List<Product> products = new ArrayList<>();
            while (in.hasNext()) {
                String next = in.next();
                if (next.equals("")) {
                    throw new Exception();
                }
                on = new Scanner(next).useDelimiter("\\s*\\*\\s*");
                List<Double> factors = new ArrayList<>();
                while (on.hasNext()) {
                    factors.add(on.nextDouble());
                }
                Product p = new Product(factors);
                products.add(p);
            }
            Expression sum = new Sum(products);
            return sum;
    }

}

interface Expression {
    public double execute(Environment env);
}

class Identifier implements Expression {
    private String name;
    public Identifier(String name) {
        this.name = name;
    }
    public double execute(Environment env) {
        return env.get(name);
    }
}

class Product implements Expression{
    private List<Identifier> factors;

    public Product(List<Double> factors) {
        this.factors = factors;
    }

    public double execute(Environment env) {
        double p = 1;
        for (Double factor :
                factors) {
            p *= factor;
        }
        return p;
    }
}

class Sum  implements Expression {
    public List<Product> products;

    public Sum(List<Product> products) {
        this.products = products;
    }

    public double execute(Environment env) {
        double sum = 0;
        for (Product p :
                products) {
            sum += p.execute(env);
        }
        return sum;
    }
}

class Environment extends HashMap<String, Double> {}
