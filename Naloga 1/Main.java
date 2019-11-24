import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    private String[] args;



    static void sortByLength(String[] besede){//Sortiramo besede po dolzini
        for(int i = 0; i < besede.length-1; i++){
            for(int j = 0; j < besede.length-1-i; j++){
                if(besede[j].length() < besede[j+1].length()){
                    String tmp = besede[j];
                    besede[j] = besede[j+1];
                    besede[j+1] = tmp;
                }
            }
        }
    }

    static boolean lookUp(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(x-i < 0){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x-i][y].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static  boolean lookDown(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(x+i >= crke.length){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x+i][y].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookLeft(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y-i < 0){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x][y-i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookRight(int x, int y, String[][] crke, String beseda) {//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y+i >= crke[0].length){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x][y+i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookUpLeft(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y-i < 0 || x-i < 0){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x-i][y-i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookUpRight(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y+i >= crke[0].length || x-i < 0){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x-i][y+i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookDownLeft(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y-i < 0 || x+i >= crke.length){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x+i][y-i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static boolean lookDownRight(int x, int y, String[][] crke, String beseda){//x in y sta invertirana
        boolean isTrue = true;
        for(int i = 0; i < beseda.length(); i++){
            if(y+i >= crke[0].length || x+i >= crke.length){
                isTrue = false;
                break;
            }
            if(beseda.charAt(i) != crke[x+i][y+i].charAt(0)){
                isTrue = false;
                break;
            }
        }
        return isTrue;
    }

    static void oznaciPorabljeno(boolean[][] porabljeno, int x1, int y1, int dir, int len){
        //dir: 1-gor, 2-dol, 3-levo, 4-desno
        //     5-gor levo, 6 gor desno, 7 dol levo, 8 dol desno
        if(dir == 1){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1] = true;
            }
        }
        if(dir == 2){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1] = true;
            }
        }
        if(dir == 3){
            for(int i = 0; i < len; i++){
                porabljeno[x1][y1-i] = true;
            }
        }
        if(dir == 4){
            for(int i = 0; i < len; i++){
                porabljeno[x1][y1+i] = true;
            }
        }
        if(dir == 5){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1-i] = true;
            }
        }
        if(dir == 6){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1+i] = true;
            }
        }
        if(dir == 7){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1-i] = true;
            }
        }
        if(dir == 8){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1+i] = true;
            }
        }
    }

    static void odznaciPorabljeno(boolean[][] porabljeno, int x1, int y1, int dir, int len){
        //dir: 1-gor, 2-dol, 3-levo, 4-desno
        //     5-gor levo, 6 gor desno, 7 dol levo, 8 dol desno
        if(dir == 1){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1] = false;
            }
        }
        if(dir == 2){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1] = false;
            }
        }
        if(dir == 3){
            for(int i = 0; i < len; i++){
                porabljeno[x1][y1-i] = false;
            }
        }
        if(dir == 4){
            for(int i = 0; i < len; i++){
                porabljeno[x1][y1+i] = false;
            }
        }
        if(dir == 5){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1-i] = false;
            }
        }
        if(dir == 6){
            for(int i = 0; i < len; i++){
                porabljeno[x1-i][y1+i] = false;
            }
        }
        if(dir == 7){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1-i] = false;
            }
        }
        if(dir == 8){
            for(int i = 0; i < len; i++){
                porabljeno[x1+i][y1+i] = false;
            }
        }
    }

    static boolean valid(boolean[][] porabljeno, int x1, int y1, int dir, int len){
        //dir: 1-gor, 2-dol, 3-levo, 4-desno
        //     5-gor levo, 6 gor desno, 7 dol levo, 8 dol desno
        boolean isValid = true;
        if(dir == 1){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1-i][y1] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 2){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1+i][y1] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 3){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1][y1-i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 4){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1][y1+i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 5){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1-i][y1-i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 6){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1-i][y1+i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 7){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1+i][y1-i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        if(dir == 8){
            for(int i = 0; i < len; i++){
                if(porabljeno[x1+i][y1+i] == true){
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    static boolean solve(String[][] crke, String[] besede, boolean[][] porabljeno, int trenutnaBeseda,PrintWriter out){
        if(trenutnaBeseda >= besede.length) return true;
        for(int i = 0; i < crke.length; i++){
            for(int j = 0; j < crke[i].length; j++){

                if(lookUp(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,1,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,1,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i-besede[trenutnaBeseda].length()+1)+","+j);
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,1,besede[trenutnaBeseda].length());
                    }
                }

                if(lookDown(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,2,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,2,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i+besede[trenutnaBeseda].length()-1)+","+j);
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,2,besede[trenutnaBeseda].length());
                    }
                }

                if(lookLeft(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,3,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,3,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+i+","+(j-besede[trenutnaBeseda].length()+1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,3,besede[trenutnaBeseda].length());
                    }
                }

                if(lookRight(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,4,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,4,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+i+","+(j+besede[trenutnaBeseda].length()-1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,4,besede[trenutnaBeseda].length());
                    }
                }

                if(lookUpLeft(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,5,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,5,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i-besede[trenutnaBeseda].length()+1)+","+(j-besede[trenutnaBeseda].length()+1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,5,besede[trenutnaBeseda].length());
                    }
                }

                if(lookUpRight(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,6,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,6,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i-besede[trenutnaBeseda].length()+1)+","+(j+besede[trenutnaBeseda].length()-1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,6,besede[trenutnaBeseda].length());
                    }
                }

                if(lookDownLeft(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,7,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,7,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i+besede[trenutnaBeseda].length()-1)+","+(j-besede[trenutnaBeseda].length()+1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,7,besede[trenutnaBeseda].length());
                    }
                }

                if(lookDownRight(i,j,crke,besede[trenutnaBeseda])){
                    if(valid(porabljeno,i,j,8,besede[trenutnaBeseda].length())){
                        oznaciPorabljeno(porabljeno,i,j,8,besede[trenutnaBeseda].length());
                        if(solve(crke,besede,porabljeno,trenutnaBeseda+1,out)){
                            out.println(besede[trenutnaBeseda]+","+i+","+j+","+(i+besede[trenutnaBeseda].length()-1)+","+(j+besede[trenutnaBeseda].length()-1));
                            return true;
                        }
                        else odznaciPorabljeno(porabljeno,i,j,8,besede[trenutnaBeseda].length());
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        //long beg = System.nanoTime();
        File input = new File(args[0]);
        PrintWriter output = new PrintWriter(args[1]);
        Scanner scan = new Scanner(input);
        //Branje vhodnih podatkov
        String line = scan.nextLine();
        int v = Integer.parseInt(line.split(",")[0]);//Dobivanje visine
        int s = Integer.parseInt(line.split(",")[1]);//Dobivanje sirine
        String[][] crke = new String[v][s];
        for(int i = 0; i < v; i++){
            line = scan.nextLine();
            crke[i] = line.split(",");
        }
        int stBesed = scan.nextInt();//Prebere stevilo besed
        String[] besede = new String[stBesed];
        for(int i = 0; i < stBesed; i++){//Prebere besede
            besede[i] = scan.next();
        }
        boolean[][] porabljeno = new boolean[v][s];//tabela bool, ki gleda katera mesta so prosta (avtomatsko vse na false)
        /*for(int i = 0; i < v; i++){
            System.out.println(crke[i][0]);
        }*/
        sortByLength(besede);
        solve(crke,besede,porabljeno,0,output);
        output.close();
        //System.out.println((double)(System.nanoTime()-beg)/1_000_000_000.0);
    }
}
