package feszczak;

import java.text.DecimalFormat;

public class Program {

    public static void main(String[] args) {
        System.out.println("(main) START");
        float start = System.nanoTime();

        Sumator sum = new Sumator();
        sum.run(args);

        float end = System.nanoTime();
        System.out.println("(main) END");
        System.out.println("(main) Time - " + new DecimalFormat("###.###").format((end - start) / 1000000000) + " sec");
    }
}