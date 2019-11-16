import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class test {

    static class Karta{
        Object element;
        Karta next;

        Karta(Object o){
            element = o;
            next = null;
        }
    }

    static class Kup{
        protected Karta first;
        protected Karta last;
        protected Karta mestoVstavljanja;
        protected Karta mestoDeljenja;
        int steviloKartNaIteracijo;

        void prazenKup(){
            first = null;
            mestoVstavljanja = null;
            mestoDeljenja = null;
            steviloKartNaIteracijo = 0;
        }

        Kup(){
            prazenKup();
        }

        void dodajKarto(Object obj){
            Karta tmp = new Karta(obj);
            if(first == null){
                first = tmp;
                last = tmp;
            }
            else {
                last.next = tmp;
                last = tmp;
            }
        }

        void shuffle(String splitElement, String insertElement, int iterN){
            steviloKartNaIteracijo = iterN;
            najdiMesti(splitElement,insertElement);
            if(mestoVstavljanja != null && mestoDeljenja != null){//imamo veljavno mesto vstavljanja in deljenja kupa
                Karta afterSplit = mestoDeljenja.next;
                mestoDeljenja.next = null;
                Karta afterInsert = mestoVstavljanja.next;
                while(afterSplit != null){
                    int iter = iterN-1;//en korak manj da pravilno prevezemo
                    Karta afterSplitStart = afterSplit;
                    while(afterSplit.next != null && iter > 0){
                        iter--;
                        afterSplit = afterSplit.next;
                    }
                    Karta tmp = afterSplit.next;
                    afterSplit.next = mestoVstavljanja.next;
                    mestoVstavljanja.next = afterSplitStart;
                    afterSplit = tmp;

                }
            }
            else{//vstavlanje na zacetek (opcije deljenje veljavno z neveljavnim vstavljanjem, neveljavno deljenje z veljavnim vstavljanjem in oboje neveljavno)
                if(mestoDeljenja != null && mestoVstavljanja == null){
                    Karta afterSplit = mestoDeljenja.next;
                    mestoDeljenja.next = null;
                    while(afterSplit != null) {
                        int iter = iterN - 1;//en korak manj da pravilno prevezemo
                        Karta afterSplitStart = afterSplit;
                        while (afterSplit.next != null && iter > 0) {
                            iter--;
                            afterSplit = afterSplit.next;
                        }
                        Karta tmp = afterSplit.next;
                        afterSplit.next = first;
                        first = afterSplitStart;
                        afterSplit = tmp;
                    }
                }
                else if(mestoDeljenja == null && mestoVstavljanja != null){
                    Karta afterSplit = first;
                    mestoVstavljanja = null;
                    while(afterSplit != null){
                        int iter = iterN - 1;
                        Karta afterSplitStart = afterSplit;
                        while(afterSplit.next != null && iter > 0){
                            iter--;
                            afterSplit = afterSplit.next;
                        }
                        Karta tmp = afterSplit.next;
                        afterSplit.next = mestoVstavljanja;
                        mestoVstavljanja = afterSplitStart;
                        first = afterSplitStart;
                        afterSplit = tmp;
                    }
                }
                else if(mestoDeljenja == null && mestoVstavljanja == null){
                    Karta afterSplit = first;
                    mestoVstavljanja = null;
                    while(afterSplit != null){
                        int iter = iterN - 1;
                        Karta afterSplitStart = afterSplit;
                        while(afterSplit.next != null && iter > 0){
                            iter--;
                            afterSplit = afterSplit.next;
                        }
                        Karta tmp = afterSplit.next;
                        afterSplit.next = mestoVstavljanja;
                        mestoVstavljanja = afterSplitStart;
                        first = mestoVstavljanja;
                        afterSplit = tmp;
                    }
                }
            }
            //Na koncu mesanja damo mesti nazaj na null
            mestoVstavljanja = null;
            mestoDeljenja = null;
        }

        void najdiMesti(String splitElement, String insertElement){
            for(Karta iter = first; iter != null; iter = iter.next){
                if(mestoVstavljanja == null && iter.element.equals(insertElement)){
                    mestoVstavljanja = iter;
                }
                if(mestoDeljenja == null && iter.element.equals(splitElement)){//Nastavimo kazalec na mesto deljenja
                    mestoDeljenja = iter;
                    break;
                }
            }
        }

        void print(){
            for(Karta iter = first; iter != null; iter = iter.next){
                if(iter.next != null)System.out.print(iter.element + ",");
                else System.out.println(iter.element);
            }
        }

        void print(PrintWriter writer){
            StringBuilder sb = new StringBuilder();
            for(Karta iter = first; iter != null; iter = iter.next){
                if(iter.next != null) sb.append(iter.element + ",");
                else sb.append(iter.element);
            }
            writer.write(sb.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        long beg = System.nanoTime();
        File input = new File(args[0]);
        PrintWriter writer = new PrintWriter(args[1],"UTF-8");
        Scanner scan = new Scanner(input);
        String line = scan.nextLine();
        int stKart = Integer.parseInt(line.split(",")[0]);
        int stMesanj = Integer.parseInt(line.split(",")[1]);
        //System.out.println(stKart + " " + stMesanj);
        Kup start = new Kup();
        String[] karte = new String[stKart];
        line = scan.nextLine();
        karte = line.split(",");
        for(int i = 0; i < stKart; i++){//Karte damo v nek zacetni seznam
            start.dodajKarto(karte[i]);
        }
        String[][] ukazi = new String[stMesanj][3];
        for(int i = 0; i < stMesanj; i++) {
            line = scan.nextLine();
            ukazi[i] = line.split(",");
            //System.out.println(ukazi[i][0] + " " + ukazi[i][1] + " " + ukazi[i][2]);
            start.shuffle(ukazi[i][0],ukazi[i][1],Integer.parseInt(ukazi[i][2]));
        }
        start.print(writer);
        writer.close();
        System.out.println((double)(System.nanoTime() - beg)/ 1_000_000_000.0);
    }
}
