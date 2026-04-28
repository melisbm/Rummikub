package Console;

import java.util.Scanner;

public class Console {

    private Scanner scanner;

    public Console(){
        scanner = new Scanner(System.in);
    }

    public void print(String text){
        System.out.print(text);
    }

    public void println(String text){
        System.out.println(text);
    }

    public int inputInt(String inputText){

        print(inputText);

        while(!scanner.hasNextInt()){
            scanner.nextLine();
            print("Invalid input, type a number: ");
        }

        int input = scanner.nextInt();
        scanner.nextLine();

        return input;
    }

    public void close(){
        scanner.close();
    }
}
