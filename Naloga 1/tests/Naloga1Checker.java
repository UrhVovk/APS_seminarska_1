import java.io.*;

public class Naloga1Checker {
    public static void main(String[] args) throws IOException, FileNotFoundException {
        String inputFilename = args[0];
        String outputFilename = args[1];

        BufferedReader inputReader = new BufferedReader(new FileReader(inputFilename));
        BufferedReader outputReader = new BufferedReader(new FileReader(outputFilename));

        String gridData = inputReader.readLine();
        String[] gridSize = gridData.split(",");
        int height = Integer.parseInt(gridSize[0]);
        int width = Integer.parseInt(gridSize[1]);

        char[][] inputGrid = new char[height][width];
        char[][] outputGrid = new char[height][width];

        for (int i = 0; i < height; i++) {
            String line = inputReader.readLine();
            String[] lineLetters = line.split(",");
            for (int j = 0; j < lineLetters.length; j++) {
                inputGrid[i][j] = lineLetters[j].charAt(0);
            }
        }


        int wordCount = Integer.parseInt(inputReader.readLine());

        // besede
        String[] words = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            words[i] = inputReader.readLine();
        }

        // construct output grid
        for (int i = 0; i < wordCount; i++) {
            String entry = outputReader.readLine();
            String[] entryValues = entry.split(",");

            String word = entryValues[0];
            int startY = Integer.parseInt(entryValues[1]);
            int startX = Integer.parseInt(entryValues[2]);
            int endY = Integer.parseInt(entryValues[3]);
            int endX = Integer.parseInt(entryValues[4]);

            int pathX = endX - startX;
            int pathY = endY - startY;

            int stepX = pathX != 0 ? pathX / Math.abs(pathX) : 0;
            int stepY = pathY != 0 ? pathY / Math.abs(pathY) : 0;

            for (int j = 0; j < word.length(); j++) {
                int x = startX + j * stepX;
                int y = startY + j * stepY;

                if (y >= outputGrid.length || x >= outputGrid[0].length || y < 0 || x < 0) {
                    System.out.println("NAPAKA: Beseda " + word + " gre izven mreze");
                    break;
                }

                if (outputGrid[y][x] != 0) {
                    System.out.println("NAPAKA: Beseda " + word + " se prekriva z drugo besedo v koordinatah (" + x + "," + y + ")");
                }
                outputGrid[y][x] = word.charAt(j);
            }
        }
        
        // preveri
        for (int y = 0; y < outputGrid.length; y++) {
            for (int x = 0; x < outputGrid[0].length; x++) {
                if (outputGrid[y][x] == 0) {
                    System.out.println("NAPAKA: Mreza je prazna na koordinatah (" + x + "," + y + ")");
                }
                else if (outputGrid[y][x] != inputGrid[y][x]) {
                    System.out.println("NAPAKA: Mrezi se ne ujemata v koordinatah (" + x + "," + y + ")");
                }
            }
        }
        
        System.out.println("Preverjanje koncano. Ce ni nobenega drugega izpisa, je naloga uspesno resena");


        inputReader.close();
        outputReader.close();
    }
}