import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Tester {
    public static void main(String... args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            System.out.println("------ Naloga" + i + ":");
            for (int j = 1; j <= 10; j++) {
                String in = "I" + i + "_" + j + ".txt";
                String out = "O" + i + "_" + j + ".txt";
                String res = "R" + i + "_" + j + ".txt";
                long start = 0, end = 0;
                switch (i) {
                    case 1:
                        start = System.nanoTime();
                        //Naloga1.main(new String[]{in, res});
                        end = System.nanoTime();
                        break;
                    case 2:
                        start = System.nanoTime();
                        //Naloga2.main(new String[]{in, res});
                        end = System.nanoTime();
                        break;
                    case 3:
                        start = System.nanoTime();
                        //Naloga3.main(new String[]{in, res});
                        end = System.nanoTime();
                        break;
                    case 4:
                        start = System.nanoTime();
                        //Naloga4.main(new String[]{in, res});
                        end = System.nanoTime();
                        break;
                    case 5:
                        start = System.nanoTime();
                        Naloga5.main(new String[]{in, res});
                        end = System.nanoTime();
                        break;
                }

                String eq = "X";

                switch (i) {
                    /*case 2:
                    case 3:
                    case 4:
                        if (Files.exists(Paths.get(res))) {
                            byte[] outFile = Files.readAllBytes(Paths.get(out));
                            byte[] resFile = Files.readAllBytes(Paths.get(res));

                            eq = (Arrays.equals(outFile, resFile) ? "+" : "-");
                        }
                        break;
                    case 1: {
                        eq = isValidOutput1(in, res) ? "+" : "-";
                        break;
                    }*/
                    case 5: {
                        eq = isValidOutput5(in, out, res) ? "+" : "-";
                        System.out.println("(" + j + ") [" + eq + "] " + ((double)(end - start) / 1000000.0));
                        break;
                    }
                    default:
                        break;
                }

                //System.out.println("(" + j + ") [" + eq + "] " + ((double)(end - start) / 1000000.0));
            }
        }
    }

    private static boolean isValidOutput1(String in, String res) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(in));

        String[] elements = input.readLine().split(",");
        int h = Integer.parseInt(elements[0]), w = Integer.parseInt(elements[1]);

        char[][] inMap = new char[h][w];

        for (int y = 0; y < h; y++) {
            elements = input.readLine().split(",");
            for (int x = 0; x < w; x++) {
                inMap[y][x] = elements[x].charAt(0);
            }
        }

        char[][] resMap = new char[h][w];

        int n = Integer.parseInt(input.readLine());

        input.close();
        BufferedReader result = new BufferedReader(new FileReader(res));

        for (int i = 0; i < n; i++) {
            String line = result.readLine();
            if (line == null || (elements = line.split(",")).length != 5) {
                result.close();
                return false;
            }
            String s = elements[0];
            int y1 = Integer.parseInt(elements[1]);
            int x1 = Integer.parseInt(elements[2]);
            int y2 = Integer.parseInt(elements[3]);
            int x2 = Integer.parseInt(elements[4]);

            int dx = Integer.compare(x2, x1);
            int dy = Integer.compare(y2, y1);

            for (int j = 0; j < s.length(); j++) {
                resMap[y1 + j * dy][x1 + j * dx] = s.charAt(j);
            }
        }
        result.close();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (inMap[y][x] != resMap[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidOutput5(String in, String out, String res) throws Exception {
        BufferedReader input = new BufferedReader(new FileReader(in));

        String[] elements = input.readLine().split(",");

        int N = Integer.parseInt(elements[0]), P = Integer.parseInt(elements[1]);

        char[][] state = new char[N][P];
        char[][] endState = new char[N][P];

        for (int i = 0; i < N; i++) {
            elements = input.readLine().split(":");
            if (elements.length != 2) continue;
            elements = elements[1].split(",");
            for (int j = 0; j < elements.length; j++) {
                if (elements[j].length() == 1) {
                    state[i][j] = elements[j].charAt(0);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            elements = input.readLine().split(":");
            if (elements.length != 2) continue;
            elements = elements[1].split(",");
            for (int j = 0; j < elements.length; j++) {
                if (elements[j].length() == 1) {
                    endState[i][j] = elements[j].charAt(0);
                }
            }
        }
        input.close();
        BufferedReader output = new BufferedReader(new FileReader(out));

        int length = 0;
        String line;
        while ((line = output.readLine()) != null && line.length() > 2) {
            length++;
        }
        output.close();
        BufferedReader result = new BufferedReader(new FileReader(res));

        char saved = '\0';
        int position = 0;
        for (int i = 0; i < length; i++) {
            if ((line = result.readLine()) == null || line.length() <= 2) {
                break;
            }
            else if (line.equals("PREMIK 1")) {
                if (N < 1) {
                    break;
                }
                position = 0;
            }
            else if (line.equals("PREMIK 2")) {
                if (N < 2) {
                    break;
                }
                position = 1;
            }
            else if (line.equals("PREMIK 3")) {
                if (N < 3) {
                    break;
                }
                position = 2;
            }
            else if (line.equals("PREMIK 4")) {
                if (N < 4) {
                    break;
                }
                position = 3;
            }
            else if (line.equals("PREMIK 5")) {
                if (N < 5) {
                    break;
                }
                position = 4;
            }
            else if (line.equals("NALOZI")) {
                saved = state[position][0];
                state[position][0] = '\0';
            }
            else if (line.equals("ODLOZI")) {
                state[position][0] = saved;
                saved = '\0';
            }
            else if (line.equals("GOR")) {
                System.arraycopy(state[position], 0, state[position], 1, P-1);
                state[position][0] = '\0';
            }
            else if (line.equals("DOL")) {
                System.arraycopy(state[position], 1, state[position], 0, P-1);
                state[position][P-1] = '\0';
            }
            else {
                break;
            }
        }

        result.close();

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < P; x++) {
                if (state[y][x] != endState[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }
}
