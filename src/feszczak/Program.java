package feszczak;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        String path; // D:\Learning\4Me\Git Projects\furry-python\PythonApplication1\lorem4.txt
        if (args.length == 0) {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter file path: ");
            path = s.nextLine();
            s.close();
        }
        else {
            path = args[0];
        }


        System.out.println("START");
        float start = System.nanoTime();

        Sumator sum = new Sumator();
        sum.run(path);

        float end = System.nanoTime();
        System.out.println("END");
        System.out.println("Time - " + new DecimalFormat("###.##").format((end - start) / 1000000000) + " sec");
    }
}