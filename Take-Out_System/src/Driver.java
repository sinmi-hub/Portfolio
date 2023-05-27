package TakeOutSystem;

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int money = 0;
        String customerName;

        System.out.println("Please enter your name:");
        customerName = input.next();
        System.out.println("Hi "+customerName+". Please enter your how much " +
                "money you have to spend:");


        if(input.hasNextInt()){
            money = input.nextInt();
        }

        else
            System.out.println("Please enter your balance in numbers");


        Customer customer = new Customer(customerName, money);
        TakeOutSimulator order = new TakeOutSimulator(customer, input);

        order.startTakeOutSimulator();
        input.close();
    }
}