import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Naloga5 {

    static class QueueElement{
        Object element;
        QueueElement next;

        QueueElement(Object o){
            element = o;
            next = null;
        }
    }

    static class Queue{
        QueueElement front;
        QueueElement rear;

        Queue(){
            front = null;
            rear = null;
        }

        public boolean isEmpty(){
            return (front == null);
        }

        public Object front(){
            if(front != null) return front.element;
            else return null;
        }

        public void enqueue(Object o){
            QueueElement tmp = new QueueElement(o);
            if(front == null){
                front = tmp;
                rear = tmp;
            }
            else{
                rear.next = tmp;
                rear = tmp;
            }
        }

        public void dequeue(){
            if(front.next != null){
                front = front.next;
            }
            else{
                front = null;
                rear = null;
            }
        }
    }

    static class Stanje{
        public String[][] trakovi;
        public String zaboj;
        public String koraki;
        public String prevKorak;
        public int pozicija;

        @Override
        public boolean equals(Object o){
            if(o instanceof Stanje){
                return isEqual((Stanje)o);
            }
            else return false;
        }

        public boolean isEqual(Stanje drugo){
            for(int i = 0; i < trakovi.length; i++){
                for(int j = 0; j < trakovi[0].length; j++){
                    if(trakovi[i][j] == null){
                        if(drugo.trakovi[i][j] != null) return false;
                    }
                    else if(drugo.trakovi[i][j] == null){
                        if(trakovi[i][j] != null) return false;
                    }
                    else if(!trakovi[i][j].equals(drugo.trakovi[i][j])) return false;
                }
            }
            return true;
        }

        public void print(){
            for(int i = 0; i < trakovi.length; i++){
                System.out.print((i+1)+": ");
                for(int j = 0; j < trakovi[0].length; j++){
                    System.out.print(trakovi[i][j] + " ");
                }
                System.out.println();
            }
        }

        public boolean trenutniTrakPrazen(){
            for(int i = 0; i < trakovi[pozicija].length; i++){
                if(trakovi[pozicija][i] != null) return false;
            }
            return true;
        }

        public void nalozi(){
            if(zaboj == null && trakovi[pozicija][0] != null){
                zaboj = trakovi[pozicija][0];
                trakovi[pozicija][0] = null;
            }
        }

        public void premik(int i){
            pozicija = i;
        }

        public void odlozi(){
            if(zaboj != null && trakovi[pozicija][0] == null){
                trakovi[pozicija][0] = zaboj;
                zaboj = null;
            }
        }

        public void gor(){
            for(int i = trakovi[pozicija].length-1; i > 0; i--){
                trakovi[pozicija][i] = trakovi[pozicija][i-1];
            }
            trakovi[pozicija][0] = null;
        }

        public void dol(){
            for(int i = 0; i < trakovi[pozicija].length-1; i++){
                trakovi[pozicija][i] = trakovi[pozicija][i+1];
            }
            trakovi[pozicija][trakovi[pozicija].length-1] = null;
        }

        @Override
        public int hashCode(){
            int h = pozicija;
            if(zaboj != null) h = 31 * h + zaboj.charAt(0) - 'A' + 1;
            else h = 31 * h - 'A' + 1;
            for (int i = 0; i < trakovi.length; i++) {
                for (int j = 0; j < trakovi[i].length; j++) {
                    h = 31 * h;
                    if (trakovi[i][j] != null)
                        h += (trakovi[i][j].charAt(0) - 'A' + 1);
                }
            }
            return h;
            //System.out.println(h);
            //return h.hashCode();
        }

        Stanje(int st, int dolzina){
            trakovi = new String[st][dolzina];
            pozicija = 0;
            zaboj = null;
            koraki = "";
        }

        Stanje(Stanje drugo){
            trakovi = new String[drugo.trakovi.length][drugo.trakovi[0].length];
            for(int i = 0; i < trakovi.length; i++){
                for(int j = 0; j < trakovi[i].length; j++){
                    trakovi[i][j] = drugo.trakovi[i][j];
                }
            }
            this.zaboj = drugo.zaboj;
            this.koraki = drugo.koraki;
            this.pozicija = drugo.pozicija;
        }
    }

    static class ListElement{
        Stanje obj;
        ListElement next;

        ListElement(Stanje obj){
            this.obj = obj;
            next = null;
        }
    }

    static class List{
        ListElement first;

        List(){
            first = null;
        }

        public void insert(Stanje o){
            ListElement tmp = new ListElement(o);
            if(first == null){
                first = tmp;
            }
            else{
                tmp.next = first;
                first = tmp;
            }
        }

        public ListElement find(Stanje o){
            ListElement tmp = first;
            while(tmp != null){
                if(tmp.obj.equals(o)) return tmp;
                tmp = tmp.next;
            }
            return null;
        }
    }

    static class Map{
        int size = 3145739;
        List[] tab;

        Map(){
            tab = new List[size];
            for(int i = 0; i < size; i++){
                tab[i] = new List();
            }
        }

        private int hash(Object d){
            return Math.abs(d.hashCode())%tab.length;
        }

        public boolean assign(Object d, Object r){
            List temp = tab[hash(d)];
            //ListElement pos = temp.find((Stanje)r);
            //temp.insert((Stanje)r);
            if(temp.find((Stanje)r) == null){
                temp.insert((Stanje)r);
                return true;
            }
            else return false;
        }

        public List compute(Object d){
            return tab[hash(d)];
        }
    }

    public static void main(String[] args) throws Exception{
        //Long start = System.nanoTime();
        File in = new File(args[0]);
        PrintWriter out = new PrintWriter(args[1]);
        Scanner scan = new Scanner(in);
        String line = scan.nextLine();
        int stTrakov = Integer.parseInt(line.split(",")[0]);
        int dolzinaTrakov = Integer.parseInt(line.split(",")[1]);

        Stanje zacetno = new Stanje(stTrakov,dolzinaTrakov);
        for(int i = 0; i < stTrakov; i++){//Branje zacetnega traku
            line = scan.nextLine();
            String[] temp = line.split(":");
            if(temp.length > 1) {
                temp = temp[1].split(",");
                for (int j = 0; j < temp.length; j++) {
                    if(!temp[j].equals("")) zacetno.trakovi[i][j] = temp[j];
                }
            }
        }

        Stanje koncno = new Stanje(stTrakov,dolzinaTrakov);
        for(int i = 0; i < stTrakov; i++){//Branje zacetnega traku
            line = scan.nextLine();
            String[] temp = line.split(":");
            if(temp.length > 1) {
                temp = temp[1].split(",");
                for (int j = 0; j < temp.length; j++) {
                    if(!temp[j].equals("")) koncno.trakovi[i][j] = temp[j];
                }
            }
        }

        Queue vrsta = new Queue();
        vrsta.enqueue(zacetno);

        Map map = new Map();

        while (!vrsta.isEmpty()){
            Stanje tmp = (Stanje)vrsta.front();
            vrsta.dequeue();
            if(tmp.isEqual(koncno)){
                out.print(tmp.koraki);
                out.close();
                break;
            }
            else {//vse moznosti damo v vrsto
                if(!tmp.trenutniTrakPrazen()) {
                    Stanje gor = new Stanje(tmp);
                    gor.gor();
                    gor.koraki += "GOR\n";
                    gor.prevKorak = "GOR";
                    if (map.assign(gor,gor)) vrsta.enqueue(gor);

                    Stanje dol = new Stanje(tmp);
                    dol.dol();
                    dol.koraki += "DOL\n";
                    dol.prevKorak = "DOL";
                    if (map.assign(dol,dol)) vrsta.enqueue(dol);
                }

                if (tmp.trakovi[tmp.pozicija][0] != null && tmp.zaboj == null) {
                    Stanje nalozi = new Stanje(tmp);
                    nalozi.nalozi();
                    nalozi.koraki += "NALOZI\n";
                    nalozi.prevKorak = "NALOZI";
                    if (map.assign(nalozi, nalozi)) vrsta.enqueue(nalozi);
                }

                if (tmp.zaboj != null && tmp.trakovi[tmp.pozicija][0] == null) {
                    Stanje odlozi = new Stanje(tmp);
                    odlozi.odlozi();
                    odlozi.koraki += "ODLOZI\n";
                    odlozi.prevKorak = "ODLOZI";
                    if (map.assign(odlozi, odlozi)) vrsta.enqueue(odlozi);
                }

                if(tmp.prevKorak == null) {
                    for (int i = 0; i < stTrakov; i++) {
                        if (i != tmp.pozicija) {
                            Stanje premik = new Stanje(tmp);
                            premik.premik(i);
                            String k = "PREMIK " + (i + 1) + "\n";
                            premik.koraki += k;
                            premik.prevKorak = "PREMIK";
                            if(map.assign(premik,premik)) vrsta.enqueue(premik);
                        }
                    }
                }
                else if(!tmp.prevKorak.equals("PREMIK")){
                    for (int i = 0; i < stTrakov; i++) {
                        if (i != tmp.pozicija) {
                            Stanje premik = new Stanje(tmp);
                            premik.premik(i);
                            String k = "PREMIK " + (i + 1) + "\n";
                            premik.koraki += k;
                            premik.prevKorak = "PREMIK";
                            if(map.assign(premik,premik)) vrsta.enqueue(premik);
                        }
                    }
                }
            }
        }

        //System.out.println((double)(System.nanoTime()-start)/ 1_000_000_000.0);
    }
}
