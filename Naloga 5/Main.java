import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Main {

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

        public int hashCode(){
            String h = "";
            for(int i = 0; i < trakovi.length; i++){
                for(int j = 0; j < trakovi[i].length; j++){
                    if(trakovi[i][j] != null) h += trakovi[i][j].charAt(0);
                    else h += " ";
                }
            }
            //System.out.println(h);
            return h.hashCode();
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

    static class SetElement{
        Object element;
        SetElement next;

        SetElement(){
            element = null;
            next = null;
        }
    }

    static class Set{
        public SetElement first;

        Set(){
            first = new SetElement();
        }

        public SetElement getFirst(){
            return first;
        }

        public boolean overEnd(SetElement pos){
            if(pos.next == null) return true;
            else return false;
        }

        public void delete(SetElement pos){
            pos.next = pos.next.next;
        }

        public Object retrieve(SetElement pos){
            return pos.next.element;
        }

        public SetElement locate(Object o){
            for(SetElement iter = getFirst(); !overEnd(iter); iter = iter.next){
                if(o.equals(retrieve(iter))) return iter;
            }
            return null;
        }

        public void insert(Object obj){
            if(locate(obj) == null){
                SetElement tmp = new SetElement();
                tmp.element = obj;
                tmp.next = first.next;
                first.next = tmp;
            }
        }

        public boolean empty(){
            return first.next == null;
        }
    }

    static class MapNode{
        private Object key;
        private Object value;

        public MapNode(Object key, Object value){
            this.key = key;
            this.value = value;
        }

        public Object getKey(){
            return key;
        }

        public Object getValue(){
            return value;
        }

        public void setKey(Object key){
            this.key = key;
        }

        public void setValue(Object value){
            this.value = value;
        }

        public boolean equals(Object obj){
            if(obj instanceof MapNode){
                MapNode node = (MapNode)obj;
                return key.equals(node.key);
            }
            return false;
        }
    }

    static class Map{
        public static final int DEFAULT_SIZE = 10000003;
        public boolean isEmpty;
        Set[] tab;

        Map(){
            tab = new Set[DEFAULT_SIZE];
            isEmpty = true;
            for(int i = 0; i < tab.length; i++){
                tab[i] = new Set();
            }
        }

        int hash(Object d){
            return Math.abs(d.hashCode()) % tab.length;
        }

        public void assign(Object d, Object r){
            if(isEmpty) isEmpty = false;
            Set I = tab[hash(d)];
            MapNode node = new MapNode(d,r);
            SetElement pos = I.locate(node);

            if(pos != null){
                ((MapNode) I.retrieve((SetElement)pos)).setValue(r);
            }
            else{
                I.insert(node);
            }
        }

        public Object compute(Object d){
            Set I = tab[hash(d)];
            SetElement pos = I.locate(new MapNode(d,null));
            if(pos != null){
                return ((MapNode)I.retrieve(pos)).getValue();
            }
            return null;
        }

        public void delete(Object d){
            Set I = tab[hash(d)];
            Object pos = I.locate(new MapNode(d,null));
            if(pos != null){
                I.delete((SetElement)pos);
            }
        }
    }

    public static void main(String[] args) throws Exception{
	// write your code here
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

        /*Stanje h = new Stanje(stTrakov,dolzinaTrakov);
        h.trakovi[0][0] = "A";
        h.trakovi[0][2] = "B";
        h.trakovi[2][0] = "C";

        h.print();*/

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

        Map tab = new Map();

        while (!vrsta.isEmpty()){
            Stanje tmp = (Stanje)vrsta.front();
            System.out.println(tmp.koraki);
            vrsta.dequeue();
            if(tmp.isEqual(koncno)){
                out.print(tmp.koraki);
                out.close();
                break;
            }
            else if(!tab.isEmpty || tab.compute(tmp) == null){//vse moznosti damo v vrsto
                Stanje gor = new Stanje(tmp);
                gor.gor();
                gor.koraki += "GOR\n";
                gor.prevKorak = "GOR";
                tab.assign(gor,gor);
                vrsta.enqueue(gor);

                Stanje dol = new Stanje(tmp);
                dol.dol();
                dol.koraki += "DOL\n";
                dol.prevKorak = "DOL";
                tab.assign(dol,dol);
                vrsta.enqueue(dol);

                if(tmp.trakovi[tmp.pozicija][0] != null && tmp.zaboj == null) {
                    Stanje nalozi = new Stanje(tmp);
                    nalozi.nalozi();
                    nalozi.koraki += "NALOZI\n";
                    nalozi.prevKorak = "NALOZI";
                    tab.assign(nalozi,nalozi);
                    vrsta.enqueue(nalozi);
                }

                if(tmp.zaboj != null && tmp.trakovi[tmp.pozicija][0] == null) {
                    Stanje odlozi = new Stanje(tmp);
                    odlozi.odlozi();
                    odlozi.koraki += "ODLOZI\n";
                    odlozi.prevKorak = "ODLOZI";
                    tab.assign(odlozi,odlozi);
                    vrsta.enqueue(odlozi);
                }

                if(tmp.prevKorak == null) {
                    for (int i = 0; i < stTrakov; i++) {
                        if (i != tmp.pozicija) {
                            Stanje premik = new Stanje(tmp);
                            premik.premik(i);
                            String k = "PREMIK " + (i + 1) + "\n";
                            premik.koraki += k;
                            premik.prevKorak = "PREMIK";
                            tab.assign(premik,premik);
                            vrsta.enqueue(premik);
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
                            tab.assign(premik,premik);
                            vrsta.enqueue(premik);
                        }
                    }
                }
            }
        }

        /*zacetno.print();
        koncno.print();
        out.close();
        System.out.println(zacetno.hashCode());
        System.out.println(h.hashCode());
        System.out.print(zacetno.isEqual(koncno));*/
    }
}
