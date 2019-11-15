import java.io.*;
import java.util.Scanner;

public class Main {

    static class VrecaElement{
        int element;
        int numOfElement;
        VrecaElement next;

        VrecaElement(int obj, int numOfElement){
            this.element = obj;
            this.numOfElement = numOfElement;
            next = null;
        }
    }

    static class Vreca{
        VrecaElement first;
        VrecaElement last;
        String name;

        Vreca(String name){
            first = null;
            last = null;
            this.name = name;
        }

        VrecaElement remove(VrecaElement rem){
            if(rem == first){
                first = rem.next;
                return first;
            }
            else if(rem == last){
                VrecaElement tmp = first;
                while(tmp.next != last){
                    tmp = tmp.next;
                }
                tmp.next = null;
                last = tmp;
                return tmp;
            }
            else{
                VrecaElement tmp = first;
                while(tmp.next != rem){
                    tmp = tmp.next;
                }
                tmp.next = rem.next;
                return tmp;
            }
        }

        void sortedInsert(int obj, int num){
            VrecaElement tmp = new VrecaElement(obj,num);
            if(first == null || obj < first.element){
                if(first != null) tmp.next = first;
                first = tmp;
                if(last == null) last = tmp;
            }
            else if(obj > last.element){
                last.next = tmp;
                last = tmp;
            }
            else{
                VrecaElement iter = first;
                while(iter.next != null && iter.next.element < obj){
                    iter = iter.next;
                }
                tmp.next = iter.next;
                iter.next = tmp;
            }
        }

        void combine(Vreca other){
            VrecaElement tmp = other.first;
            for(;tmp != null; tmp = tmp.next){
                VrecaElement h = locate(tmp.element);
                if(h != null){
                    h.numOfElement += tmp.numOfElement;
                }
                else{
                    sortedInsert(tmp.element,tmp.numOfElement);
                }
            }
        }

        VrecaElement locate(int obj){
            VrecaElement tmp = first;
            for(;tmp != null; tmp = tmp.next){
                if(tmp.element == obj)
                    return tmp;
            }
            return null;
        }

        void cutOff(int n){
            for(VrecaElement tmp = first; tmp != null; tmp = tmp.next){
                if(tmp.numOfElement > n){
                    tmp.numOfElement = n;
                }
            }
        }

        void shared(Vreca other){
            VrecaElement tmp = first;
            while(tmp != null){
                VrecaElement h = other.locate(tmp.element);
                if(h == null){
                    tmp = remove(tmp);
                }
                else{
                    tmp.numOfElement = minimum(tmp.numOfElement, h.numOfElement);
                    tmp = tmp.next;
                }
            }
        }

        void remain(int n){
            VrecaElement tmp = first;
            while(tmp != null){
                if(tmp.numOfElement < n){
                    tmp = remove(tmp);
                }
                else tmp = tmp.next;
            }
        }

        void subtract(Vreca other){
            VrecaElement tmp = other.first;
            for(; tmp != null; tmp = tmp.next){
                VrecaElement h = locate(tmp.element);
                if(h != null){
                    if(h.numOfElement - tmp.numOfElement > 0) h.numOfElement -= tmp.numOfElement;
                    else remove(h);
                }
            }
        }

        void printVreca(){
            for(VrecaElement iter = first; iter != null; iter = iter.next){
                if(iter.next != null) System.out.print(iter.element+":"+iter.numOfElement+",");
                else System.out.println(iter.element+":"+iter.numOfElement);
            }
        }

        void printVreca(PrintWriter writer){
            for(VrecaElement iter = first; iter != null; iter = iter.next){
                if(iter.next != null) writer.print(iter.element+":"+iter.numOfElement+",");
                else writer.println(iter.element+":"+iter.numOfElement);
            }
        }
    }

    static int findBag(String name,Vreca[] bags, int numOfBags){
        for(int i=0; i < numOfBags; i++){
            if(bags[i].name.equals(name)) return i;
        }
        return -1;
    }

    static int minimum(int a, int b){
        int ret = a > b ?  b : a;
        return ret;
    }

    public static void main(String[] args) throws Exception{
        long start = System.nanoTime();
        File input = new File(args[0]);
        PrintWriter writer = new PrintWriter(args[1],"UTF-8");
        Scanner scan = new Scanner(input);
        String line = scan.nextLine();
        int stUkazov = Integer.parseInt(line);
        Vreca[] vrece = new Vreca[stUkazov];//tabela (potencialnih)vrec
        int stTrenutnihVrec = 0; //stevec trenutnih vrec
        for(int i = 0; i < stUkazov; i++){
            line = scan.nextLine();
            String[] params = line.split(",");
            if(params[0].charAt(0) == 'U'){
                vrece[stTrenutnihVrec] = new Vreca(params[1]);
                for(int j = 2; j < params.length; j++){
                    String[] el = params[j].split(":");
                    vrece[stTrenutnihVrec].sortedInsert(Integer.parseInt(el[0]),Integer.parseInt(el[1]));
                }
                stTrenutnihVrec++;
            }
            if(params[0].charAt(0) == 'Z'){
                int prvi = findBag(params[1],vrece,stTrenutnihVrec);
                int drugi = findBag(params[2],vrece,stTrenutnihVrec);
                vrece[prvi].combine(vrece[drugi]);
            }
            if(params[0].charAt(0) == 'I'){
                int h = findBag(params[1],vrece,stTrenutnihVrec);
                vrece[h].printVreca(writer);
            }
            if(params[0].charAt(0) == 'P'){
                int h = findBag(params[1],vrece,stTrenutnihVrec);
                vrece[h].cutOff(Integer.parseInt(params[2]));
            }
            if(params[0].charAt(0) == 'S'){
                int prvi = findBag(params[1],vrece,stTrenutnihVrec);
                int drugi = findBag(params[2],vrece,stTrenutnihVrec);
                vrece[prvi].shared(vrece[drugi]);
            }
            if(params[0].charAt(0) == 'O'){
                int prvi = findBag(params[1],vrece,stTrenutnihVrec);
                vrece[prvi].remain(Integer.parseInt(params[2]));
            }
            if(params[0].charAt(0) == 'R'){
                int prvi = findBag(params[1],vrece,stTrenutnihVrec);
                int drugi = findBag(params[2],vrece,stTrenutnihVrec);
                vrece[prvi].subtract(vrece[drugi]);
            }
        }
        writer.close();
        System.out.println((double)(System.nanoTime()-start)/1_000_000_000.0);
    }
}
