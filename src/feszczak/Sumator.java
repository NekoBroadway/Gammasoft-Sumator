package feszczak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;

public class Sumator implements SumatorInterface {

    @Override
    public void run(String file) {
        System.out.println("START");
        float start = System.nanoTime();

        File.readFile(file);
        File.splitFile();
        File.comparedContent = new String[File.splitedContentLength][];

        for (int i = 0; i < File.splitedContentLength; i++) {
            File.comparedContent[i] = File.compareLine(File.splitedContent[i]);
        }

        int t = 0;
        int f = 0;
        for (int i = 0; i < File.splitedContentLength; i++) {
            if (File.comparedContent[i][3].equals("FALSE")) f++;
            else t++;
        }
        System.out.println("TRUE - " + t);
        System.out.println("FALSE - " + f);

        float end = System.nanoTime();
        System.out.println("END");
        System.out.println("Time - " + new DecimalFormat("###.##").format((end - start) / 1000000000) + " sec");

    }

    private static class File {
    private static String content;
    private static String[][] splitedContent;
    private static int splitedContentLength;
    private static String[][] comparedContent;



        public static void readFile(String path) {
            StringBuilder sb = new StringBuilder(path);
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '\\') {
                    sb.setCharAt(i, '/');
                }
            }
            path = sb.toString();

            Path p = Path.of(path);
            try {
                content = Files.readString(p);
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
        }

        public static void splitFile() {
            int n = 0;
            for (int i = 0; i < content.length(); i++) {
                if (content.charAt(i) == '\n') n++;
            }

            String[] s = content.split("\n");
            String[][] ss = new String[n][];
            for (int i = 0; i < ss.length; i++) {
                ss[i] = s[i].split(";");
            }
            splitedContent = ss;
            splitedContentLength = n;
        }

        public static String[] compareLine(String[] line) {
            String compareResult = "TRUE";
            char[] n0 = new char[line[2].length() - 1];
            //for (int i = 0; i < n0.length; i++) n0[i] = '0';
            Arrays.fill(n0, 0, n0.length, '0');

            char[] n1 = n0.clone();
            char[] n2 = n0.clone();
            char[] n3 = line[2].toCharArray().clone();

            char[] charLine1 = line[0].toCharArray();
            char[] charLine2 = line[1].toCharArray();

            for (int i = n1.length - 1; i > n1.length - charLine1.length - 1; i--) {
                n1[i] = charLine1[i - n1.length + charLine1.length];
            }
            for (int i = n2.length - 1; i > n2.length - charLine2.length - 1; i--) {
                n2[i] = charLine2[i - n2.length + charLine2.length];
            }

            int iN0;
            int iN1;
            int iN2;
            int iN3;

            for (int i = n0.length - 1; i > -1; i--) {
                iN0 = Character.getNumericValue(n0[i]);
                iN1 = Character.getNumericValue(n1[i]);
                iN2 = Character.getNumericValue(n2[i]);
                iN3 = Character.getNumericValue(n3[i]);

                if (iN0 + iN1 + iN2 < iN3) compareResult = "FALSE";
                else if (iN0 + iN1 + iN2 > iN3) {
                    if (iN0 + iN1 + iN2 - 10 != iN3) compareResult = "FALSE";
                    else {
                        n0[i - 1]++;
                    }
                }
            }

            return new String[]{line[0], line[1], line[2], compareResult};
        }
    }

}
