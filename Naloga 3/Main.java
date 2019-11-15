import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Main {

    static class ListElement{
        int element;
        ListElement next;
        ListElement prev;
        ListElement(int val){
            element = val;
            next = null;
        }
    }

    static class List{
        protected ListElement first;
        protected ListElement last;

        List(){
            first = null;
        }

        void push(int val){
            ListElement x = new ListElement(val);
            if(first == null){
                first = x;
                last = x;
            }
            else{
                x.prev = last;
                last.next = x;
                last = x;
            }
        }

        ListElement pop(ListElement x){
            if(x == first){
                x.next.prev = null;
                first = x.next;
                return first;
            }
            else if(x == last){
                x.prev.next = null;
                last = x.prev;
                return null;
            }
            else{
                x.prev.next = x.next;
                x.next.prev = x.prev;
                return x.next;
            }
        }

        void preslikaj(char operation, int val){
            switch(operation){
                case '*': {
                    for(ListElement tmp = first; tmp != null; tmp = tmp.next){
                        tmp.element *= val;
                    }
                    break;
                }
                case '+': {
                    for(ListElement tmp = first; tmp != null; tmp = tmp.next){
                        tmp.element += val;
                    }
                    break;
                }
            }
        }

        void ohrani(char operation, int val){
            switch (operation){
                case '>':{
                    for(ListElement tmp = first; tmp != null; ){
                        if(tmp.element > val) tmp = tmp.next;
                        else tmp = pop(tmp);
                    }
                    break;
                }
                case '<':{
                    for(ListElement tmp = first; tmp != null; ){
                        if(tmp.element < val) tmp = tmp.next;
                        else tmp = pop(tmp);
                    }
                    break;
                }
                case '=':{
                    for(ListElement tmp = first; tmp != null; ){
                        if(tmp.element == val) tmp = tmp.next;
                        else tmp = pop(tmp);
                    }
                    break;
                }
            }
        }

        void zdruzi(char operation){
            int sum = 0;
            switch (operation){
                case '+':{
                    for(ListElement tmp = first; tmp != null; ){
                        if(tmp == first){
                            sum += tmp.element;
                            tmp = tmp.next;
                        }
                        else{
                            sum += tmp.element;
                            tmp = pop(tmp);
                        }
                    }
                    break;
                }
                case '*':{
                    sum = 1;
                    for(ListElement tmp = first; tmp != null; ){
                        if(tmp == first){
                            sum *= tmp.element;
                            tmp = tmp.next;
                        }
                        else{
                            sum *= tmp.element;
                            tmp = pop(tmp);
                        }
                    }
                    break;
                }
            }
            first.element = sum;
        }

        void izpisi(){
            for(ListElement tmp = first; tmp != null; tmp = tmp.next){
                if(tmp != last){
                    System.out.print(tmp.element+",");
                }
                else{
                    System.out.println(tmp.element);
                }
            }
        }

        void pisiVDatoteko(PrintWriter writer){
            for(ListElement tmp = first; tmp != null; tmp = tmp.next){
                if(tmp != last){
                    writer.print(tmp.element+",");
                }
                else{
                    writer.println(tmp.element);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        long startTime = System.nanoTime();
        File input = new File(args[0]);
        PrintWriter writer = new PrintWriter(args[1], "UTF-8");
        Scanner scan = new Scanner(input);
        String line = scan.nextLine();
        String[] numsS = line.split(",");
        List nums = new List();
        for(int i = 0; i < numsS.length; i++){
            nums.push(Integer.parseInt(numsS[i]));
        }
        line = scan.nextLine();
        //nums.izpisi();
        int numOfCommands = Integer.parseInt(line);
        String[] commands = new String[numOfCommands];
        for(int i = 0; i < numOfCommands; i++){
            commands[i] = scan.nextLine();
        }
        for(int i = 0; i < numOfCommands; i++){
            String[] command = commands[i].split(",");
            switch (command[0]){
                case "o":{
                    nums.ohrani(command[1].charAt(0),Integer.parseInt(command[2]));
                    break;
                }
                case "p":{
                    nums.preslikaj(command[1].charAt(0),Integer.parseInt(command[2]));
                    break;
                }
                case "z":{
                    nums.zdruzi(command[1].charAt(0));
                    break;
                }
            }
            nums.pisiVDatoteko(writer);
        }
        writer.close();
        System.out.println((double)(System.nanoTime() - startTime)/1_000_000_000.0);
    }
}
