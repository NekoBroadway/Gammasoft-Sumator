package feszczak;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Sumator implements SumatorInterface {

    @Override
    public void run(String[] path) {
        String confirmedPath = mFile.confirmDirectory(path);

        System.out.println("(run) START");
        float start = System.nanoTime();

        ArrayList<Boolean> resultOfComparing = mFile.compareAllContent(confirmedPath);

        float end = System.nanoTime();
        System.out.println("(run) END");
        System.out.println("(run) Time - " + new DecimalFormat("###.###").format((end - start) / 1000000000) + " sec");

        mFile.showResult(resultOfComparing);
    }

    private static class mFile {
        public static String confirmDirectory(String[] p) {
            String path;
            Scanner s = new Scanner(System.in);
            if (p.length == 0) {
                System.out.print("Enter file path: ");
                path = s.nextLine();
            }
            else {
                path = p[0];
            }

            while (Files.notExists(Paths.get(path)) || path.equals("")) {
                System.out.println("No such file or incorrect file path.");
                System.out.print("Enter absolute file path: ");
                path = s.nextLine();
            }
            s.close();

            path = path.replace("\\", "/");

            return path;
        }

        public static ArrayList<Boolean> compareAllContent(String path) {

            ArrayList<Boolean> res = new ArrayList<>();

            try {
                Scanner s = new Scanner(new File(path));
                boolean b;

                while (s.hasNext()) {
                    b = true;
                    String[] scannedNumbers = s.nextLine().split(";");

                    int n1Length = scannedNumbers[0].length();
                    int n2Length = scannedNumbers[1].length();
                    int n3Length = scannedNumbers[2].length();

                    if (n3Length < n2Length || n3Length < n1Length) {
                        b = false;
                    }
                    else {
                        //Set sum rest array
                        char[] rest = "0".repeat(scannedNumbers[2].length()).toCharArray();

                        //Extend numbers length up to sum result number length
                        scannedNumbers[0] = "0".repeat(n3Length - n1Length) + scannedNumbers[0];
                        scannedNumbers[1] = "0".repeat(n3Length - n2Length) + scannedNumbers[1];

                        int N0, N1, N2, N3;

                        for (int i = n3Length - 1; i > -1; i--) {
                            N0 = Character.getNumericValue(rest[i]);
                            N1 = Character.getNumericValue(scannedNumbers[0].charAt(i));
                            N2 = Character.getNumericValue(scannedNumbers[1].charAt(i));
                            N3 = Character.getNumericValue(scannedNumbers[2].charAt(i));

                            if (N0 + N1 + N2 != N3) {
                                if (N0 + N1 + N2 - 10 == N3) {
                                    rest[i - 1]++;
                                } else {
                                    b = false;
                                    break;
                                }
                            }
                        }
                    }
                    res.add(b);
                }
                s.close();
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex);
            }

            return res;
        }

        private static void showResult(ArrayList<Boolean> arr) {
            int t = 0;
            int f = 0;

            for (boolean b : arr) {
                if (b) t++;
                else f++;
            }

            System.out.println("TRUE's - " + t);
            System.out.println("FALSE's - " + f);
        }
    }
}
