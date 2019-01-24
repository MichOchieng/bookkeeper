import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class bookkeeper{
    // responsible for chosing the number of pairs in a word
    static Scanner input = new Scanner(System.in); 
    static int pairNum = input.nextInt();
    // list of pairs
    static ArrayList<Integer> pairCount = new ArrayList();
        
    public static void main(String[] args) throws IOException{        
        File file = new File("wordFile.text");
        Scanner sc = new Scanner(file); 

        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            int temp2 = counter(temp);
            pairCount.add(temp2);            
        }        
        finder();
        }
    
    public static void finder(){
        int finderCount = 0;
        if(pairNum>=0){
            for(int i=0; i<pairCount.size();i++){
                int temp = pairNum;
                int temp2 = pairCount.get(i);
                if(temp == temp2){
                    finderCount++;
                }
            }
            System.out.println("There are " + finderCount + " words with " + pairNum + " two letter pairs.");
        } else{
            System.out.println("You must at least have no pairs");
            }        
        }
    // counts the number of pairs
    public static int counter(String x) throws IOException{
        int count=0;
        for(int i = 0; i<x.length()-1;i++){
            char first = x.charAt(i);
            char second = x.charAt(i+1);           
            if (first==second)
                count++;
                // author(x);              
        }
        return count;
    }

    public static void author(String x) throws IOException{
        File outFile = new File("outFile.txt");
        PrintWriter pw = new PrintWriter(outFile);
        pw.println(x);
        pw.flush();
        pw.close();
    }
}