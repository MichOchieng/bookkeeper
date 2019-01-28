import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;

public class bookkeeper{
    // list of pairs
    private static ArrayList<Integer> pairCount = new ArrayList();
    private static String outgoingFile;  
    private static int pairNum;
    private static File outFile;
    private static PrintWriter pw;

    public static void main(String[] args) throws IOException{ 
        // allows for input from command line 
        pairNum = Integer.parseInt(args[0]);                

        if(args.length == 3){
            outgoingFile = args[2];
            String inComingFile = args[1];
            middleMan(pairNum,inComingFile,outgoingFile);            
        }    
        else if(args.length == 2){
            String inComingFile = args[1];
            middleMan(pairNum,inComingFile);            
        }
        else if(args.length == 1)
            middleMan(pairNum);     
        
    }    

        // takes 3 arguments -- number of paris + infile + outfile
    public static void middleMan(int pairNum, String inComingFile, String outgoingFile) throws IOException {
        File file = new File(inComingFile);
        Scanner sc = new Scanner(file);
        outFile = new File(outgoingFile);
        pw = new PrintWriter(outFile); 
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            int temp2 = counter(temp);
            pairCount.add(temp2);                                   
        }
        // finder method begins
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
        } else
            System.out.println("You must at least have no pairs.");              
    }      

    // takes two arguments -- number of pairs and infile ++ can print pair words to the console?
    public static void middleMan(int pairNum, String inComingFile) throws IOException {
        File file = new File(inComingFile);
        Scanner sc = new Scanner(file); 
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            int temp2 = counter2(temp);
            pairCount.add(temp2);                                   
        }        
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
        } else
            System.out.println("You must at least have no pairs.");              
    } 

    // takes one argument -- number of pairs prints to console 
    public static void middleMan(int pairNum) throws IOException {
        System.out.println("Enter your words below, when you're done entering words type 'exit':");        
        Scanner sc = new Scanner(System.in); 
        while(sc.hasNextLine()){
            String temp = sc.nextLine();
            if(temp.equals("exit"))
                break;
            else{
                int temp2 = counter3(temp);
                pairCount.add(temp2);
            }                                               
        }        
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
        } else
            System.out.println("You must at least have no pairs.");              
    } 

        // counts the number of pairs in a string + adds words to file
    public static int counter(String x) throws IOException{
        int count=0;
        for(int i = 0; i<x.length()-2;i++){
            char first = x.charAt(i);
            char second = x.charAt(i+1);
            char third = x.charAt(i+2);           
            if (first==second && first!=third){
                count++;                                                
            }                                      
        }
        if(count == pairNum){
            printer(x);
        }
        return count;
    }

    // doesnt add any words to a file prints to console
    public static int counter2(String x) throws IOException{
        int count=0;
        for(int i = 0; i<x.length()-2;i++){
            char first = x.charAt(i);
            char second = x.charAt(i+1);
            char third = x.charAt(i+2);           
            if (first==second && first!=third){
                count++;                                                
            }                                       
        }
        if(count == pairNum){
            System.out.println(x);
        }        
        return count;
    }

    public static int counter3(String x) throws IOException{
        int count=0;
        for(int i = 0; i<x.length()-2;i++){
            char first = x.charAt(i);
            char second = x.charAt(i+1);
            char third = x.charAt(i+2);           
            if (first==second && first!=third){
                count++;                                                
            }                                       
        }              
        return count;
    }
    // prints words to a second file
    public static void printer(String word) throws IOException{        
        pw.println(word);                        
    }
}