import java.util.Scanner;

public class CoffeeMachine {
    static Scanner scanner = new Scanner(System.in);
    private static int water;
    private static int milk;
    private static int coffee;
    private static int cups;
    private static int cash;
    private final static String BUY = "buy";
    private final static String FILL = "fill";
    private final static String TAKE = "take";
    private final static String REMAINING = "remaining";
    private final static String EXIT = "exit";

    public static void main(String[] args) {
        String input;

        startup();

        do {
            input = getUserInput();

            switch (input) {
                case BUY:
                    buyAction();
                    break;
                case FILL:
                    fillAction();
                    break;
                case TAKE:
                    takeMoney();
                    break;
                case REMAINING:
                    remaining();
                    break;
                case EXIT:
                    break;
            }
        } while (!input.equals(EXIT));
    }

    private static String getUserInput() {
        System.out.println("Write action (buy, fill, take, remaining, exit):\n");
        return scanner.nextLine();
    }


    private static void takeMoney() {
        System.out.println("I gave you $" + cash);
        cash = 0;
    }

    private static void fillAction() {
        String input;
        System.out.println("Write how many ml of water do you want to add:\n");
        input = scanner.nextLine();
        water += Integer.parseInt(input);
        System.out.println("Write how many ml of milk do you want to add:\n");
        input = scanner.nextLine();
        milk += Integer.parseInt(input);
        System.out.println("Write how many grams of coffee beans do you want to add:\n");
        input = scanner.nextLine();
        coffee += Integer.parseInt(input);
        System.out.println("Write how many disposable cups of coffee do you want to add:\n");
        input = scanner.nextLine();
        cups += Integer.parseInt(input);
    }

    public enum Latte {
        WATER(350),
        MILK(75),
        COFFEE(20),
        CUPS(1),
        CASH(7);

        private int resource;

        Latte(int resource) {
            this.resource = resource;
        }

        public int getResource() {
            return resource;
        }
    }

    public enum Cappuccino {
        WATER(200),
        MILK(100),
        COFFEE(12),
        CUPS(1),
        CASH(6);

        private int resource;

        Cappuccino(int resource) {
            this.resource = resource;
        }

        public int getResource() {
            return resource;
        }
    }

    public enum Espresso {
        WATER(250),
        MILK(0),
        COFFEE(16),
        CUPS(1),
        CASH(4);

        private int resource;

        Espresso(int resource) {
            this.resource = resource;
        }

        public int getResource() {
            return resource;
        }
    }


    private static void buyAction() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:\n");
        final String input;
        input = scanner.nextLine();

        switch (input) {
            case "1":
                makeCustomCoffee(Espresso.WATER.getResource(), Espresso.MILK.getResource(), Espresso.COFFEE.getResource(), Espresso.CUPS.getResource(), Espresso.CASH.getResource());
                break;
            case "2":
                makeCustomCoffee(Latte.WATER.getResource(), Latte.MILK.getResource(), Latte.COFFEE.getResource(), Latte.CUPS.getResource(), Latte.CASH.getResource());
                break;
            case "3":
                makeCustomCoffee(Cappuccino.WATER.getResource(), Cappuccino.MILK.getResource(), Cappuccino.COFFEE.getResource(), Cappuccino.CUPS.getResource(), Cappuccino.CASH.getResource());
                break;
            case "back":
            default:
                break;
        }
    }

    private static void makeCustomCoffee(int water, int milk, int coffee, int cups, int cash) {

        if (CoffeeMachine.water < water) {
            System.out.println("Sorry, not enough water!\n");
            return;
        }
        if (CoffeeMachine.milk < milk) {
            System.out.println("Sorry, not enough milk!\n");
            return;
        }
        if (CoffeeMachine.coffee < coffee) {
            System.out.println("Sorry, not enough coffee beans!\n");
            return;
        }
        if (CoffeeMachine.cups < cups) {
            System.out.println("Sorry, not enough disposable cups!\n");
            return;
        }

        System.out.println("I have enough resources, making you a coffee!\n");
    
        CoffeeMachine.water -= water;
        CoffeeMachine.milk -= milk;
        CoffeeMachine.coffee -= coffee;
        CoffeeMachine.cups -= cups;
        CoffeeMachine.cash += cash;
    }


    private static void startup() {
        water = 400;
        milk = 540;
        coffee = 120;
        cups = 9;
        cash = 550;
    }

    private static void remaining() {
        final StringBuilder sb = new StringBuilder();

        sb.append("The coffee machine has:\n")
                .append(water + " of water\n")
                .append(milk + " of milk\n")
                .append(coffee + " of coffe beans\n")
                .append(cups + " of of disposable cups\n")
                .append("$" + cash + " of money\n");

        System.out.println(sb);
    }
}
