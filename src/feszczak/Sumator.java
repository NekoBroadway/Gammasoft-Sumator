package feszczak;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        ArrayList<Boolean> resultOfComparing = mFile.processFile(confirmedPath);
        float end = System.nanoTime();

        System.out.println("(run) END");
        System.out.println("(run) Time - " + new DecimalFormat("###.###").format((end - start) / 1000000000) + " sec" + "\n");

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


        public static ArrayList<Boolean> processFile(String path) {
            ArrayList<Boolean> compareResult = new ArrayList<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                String bufferedLine;
                String[] line;

                while ((bufferedLine = br.readLine()) != null) {
                    line = bufferedLine.split(";");

                    int line1Length = line[0].length();
                    int line2Length = line[1].length();
                    int line3Length = line[2].length();

                    if (line3Length < line1Length || line3Length < line2Length || (line3Length - line1Length > 1 && line3Length - line2Length > 1)) {
                        compareResult.add(false);
                    }
                    else {
                        String line1 = line[0];
                        String line2 = line[1];
                        String line3 = line[2];

                        StringBuilder sb = new StringBuilder("");

                        sb.append("0".repeat(line3Length - line1Length)).append(line1);
                        line1 = sb.toString();
                        sb = new StringBuilder("");

                        sb.append("0".repeat(line3Length - line2Length)).append(line2);
                        line2 = sb.toString();

                        int parts = line3Length % 6 == 0 ? line3Length / 6 : Math.floorDiv(line3Length, 6) + 1;
                        int partialN1[] = getPartsOfNumber(line1, parts);
                        int partialN2[] = getPartsOfNumber(line2, parts);
                        int partialN3[] = getPartsOfNumber(line3, parts);
                        int partialN0[] = new int[partialN3.length];

                        compareResult.add(compareParts(partialN1, partialN2, partialN3, partialN0, parts));
                    }
                }
            }
            catch (IOException ex) {
                System.out.println(ex);
            }

            return compareResult;
        }


        private static int[] getPartsOfNumber(String number, int parts) {
            int[] partialNumber = new int[parts];

            if (parts == 1) {
                partialNumber[0] = Integer.parseInt(number);
            }
            else {
                StringBuilder sb = new StringBuilder(number);
                int sbLength;

                for (int part = parts - 1; part > -1; part--) {
                    sbLength = sb.length();
                    if (sbLength >= 6) {
                        String numberPart = sb.substring(sbLength - 6, sbLength);
                        partialNumber[part] = Integer.parseInt(numberPart);
                        sb.delete(sbLength - 6, sb.length());
                    }
                    else {
                        partialNumber[part] = Integer.parseInt(sb.substring(0, sbLength));
                        break;
                    }
                }
            }

            return partialNumber;
        }


        private static Boolean compareParts(int[] n1, int[] n2, int[] n3, int[] n0, int parts) {

            for (int part = parts - 1; part > -1; part--) {
                if (n0[part] + n1[part] + n2[part] != n3[part]) {
                    if (n0[part] + n1[part] + n2[part] - 1000000 != n3[part]) {
                        return false;
                    }
                    else {
                        n0[part - 1] += 1;
                    }
                }
            }
            return true;
        }

        public static void showResult(ArrayList<Boolean> arr) {
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
