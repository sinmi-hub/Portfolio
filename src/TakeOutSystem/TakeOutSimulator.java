package TakeOutSystem;

import java.util.Scanner;

/**This is the class where the fun happens. The TakeoutSimulator class brings
 * together all the functionality of the TakeOut system. This class gets user
 * input to allow customers to add food to their bags and checkout when they
 * are ready (or have run out money). This is achieved by implementing the
 * UserInputRetriever interface to get user's input where it is necessary.
 * Instead of implementing it for the whole class, it is only implemented in
 * specific method where user input is required, using lambda expressions.
 * There are only 2 constructors in this class.
 *
 */

public class TakeOutSimulator{
    // Customer object to store current customer
    private Customer customer;
    // Menu object to store food options that restaurant might have
    private Menu menu;
    // Scanner object to get user input where necessary
    private Scanner input;

    /**Default Constructor that initializes fields of the TakeoutSimulator
     * object*/
    public TakeOutSimulator(){
        customer = new Customer();
        menu = new Menu();
        input = new Scanner(System.in);// initializes scanner
    }

    /**This constructor initializes the customer and input fields with the
     *  parameters, "consumer" and "input". The current TakeOutSimulator
     *  object will have the parameters, consumer as its Customer object and
     *  input, as its scanner object.
     *
     * @param consumer (Customer object that is passed)
     * @param input (Scanner object that is passed)
     */
    public TakeOutSimulator(Customer consumer, Scanner input){
        customer = consumer;
        menu = new Menu();// initializes menu by using default constructor
        this.input = input;
    }

    /**This method serves as entry and exit point for customers. This method
     * starts the Takeout process by calling the method shouldSimulate().
     * shouldSimulate() is the first part of the program. This method will
     * run in a while loop until users decide to exit, at that point in which
     * shouldSimulate() returns false.
     */
    public void startTakeOutSimulator(){
        boolean start = true;

        /*Iteration that runs Takeout Simulation until customer decides to
        exit. i.e. shouldSimulate() returns false*/
        while (start){
            /* flag is updated to return result of shouldSimulate() after it
             runs*/
            start = shouldSimulate();

            process(1500);// buffers for 1.5 seconds

            /* if start is true. i.e. Customer decides to proceed with
             simulation*/
            if(start){
                // Prints Intro and welcome greeting
                System.out.print("Hello "+customer.getName()+
                        ", Welcome to Taste of Nola. ");
                System.out.println("We are really glad you chose us today!");

                takeOutPrompt();// calls next part of program
            }
        }
    }

    /**This method is the first part of the program. This method
     * shouldSimulate() prompts and ask the user if they would like to begin
     * to use the program. It serves as an entry and exit point into the
     * TakeoutSimulator based on user's response.
     *
     * @return True if user decides and can use the Takeout simulation
     *          False otherwise
     */
    public boolean shouldSimulate() {
        /*A prompt to get user input. Asks user what steps they would like to
         do*/
        String userPrompt = """
                Please choose an option from the following:
                0. To end simulation process
                1. To proceed with simulation process""";

        /* Implementing the interface that gets user's input using lambda
         expression*/
        UserInputRetriever<?> intUserInputRetriever = selection -> {
            boolean hasMoney;

            /*Checks if the parameter, "selection" i.e. userInput is 1 and if
             the current amount of money that user has is enough to shop or
             use the Takeout system*/
            if(selection == 1 && customer.getMoney() >= menu.getLowestFoodCost()){
                // Lets user know that simulation is starting
                System.out.println("You have enough money. Proceeding with " +
                        "simulation...\n");
                hasMoney = true;// updates flag
            }

            /*if customer decides to proceed with simulation, but does not
            have enough money, the simulation should terminate, because
            enough money is need to interact in the simulation*/
            else if (selection == 1 && customer.getMoney() < menu.getLowestFoodCost()){
                /*Explains why simulation is terminated*/
                System.out.println("You do not have enough money to shop. "
                        +"The minimum amount of money you need to have to " +
                        "proceed is $"+menu.getLowestFoodCost()+".\n"+
                        "Ending the simulation...");
                hasMoney = false;
            }

            // if customer decides to terminate according to userPrompt
            else if(selection == 0){
                System.out.println("Terminating simulation...");
                hasMoney = false;
            }

            // if an invalid option is passed as input
            else
                throw new IllegalArgumentException("Please choose a valid " +
                        "option from the choices listed.");

            return hasMoney;
        };

        // calls the method below to get the output based on user's input
        return (boolean)getOutputOnInput(userPrompt, intUserInputRetriever);
    }

    /**This method is the second part of the program. This method serves as
     * en entry point into all the functionality of the Takeout system. At
     * this point in the program, user is willing to interact with the
     * program, so it is necessary to create a ShoppingBag object to store
     * interaction that user has with the Takeout system (i.e. Shops or buys
     * food).
     *
     */
    public void takeOutPrompt(){
        // creates a new ShoppingBag object that stores Food objects
        ShoppingBag<Food> cart = new ShoppingBag<>();
        // keeps track of how much customer has left
        int customerMoneyLeft = customer.getMoney();

        boolean state = true;

        /* a loop that will run until customer has no money left to spend or
        if customer decides to check out*/
        while(state){
            System.out.println("Your current balance is: $"+customerMoneyLeft+".");
            System.out.println();
            process(2000);
            // checks if a customer has enough money to buy food
            if(customerMoneyLeft >= menu.getLowestFoodCost()) {
                System.out.println("Our Menu is as follows:");
                /* returns the Food that user chooses after getMenuSelection() is
                called*/
                Food chosenFood = getMenuSelection();

                 /* Before adding to cart, check to see if customer has enough
                money for purchase they chose*/
                if (customerMoneyLeft >= chosenFood.getPrice()) {
                    /* subtract cost of food that user chose from the amount of
                    money they have*/
                    customerMoneyLeft -= chosenFood.getPrice();
                    cart.addItem(chosenFood);// adds food to cart
                    System.out.println("Added " + chosenFood.getName()
                                    + " to cart");
                }

                // if user does not have enough money for food they chose
                else{
                    System.out.println("You do not have enough money left" +
                            " for " + chosenFood.getName() + ". " +
                            "You can choose another item" + " or checkout");
                }
            }
            // if user has no money left to buy food
            else {
               System.out.println("You do not have enough money left for any" +
                       " purchase.\nPlease proceed to checkout...");
            }
            System.out.println();
            process(1500);
            int status = isStillOrderingFood();

            if(status == 0)// if customer wants to check out
                checkoutCustomer(cart);// checks out customer

            // if customer wants to view cart
            else if (status == 2) {
                System.out.println(cart);
                System.out.println("Total price of food in cart is $"+
                        cart.getTotalPrice()+".");
                status = isStillOrderingFood();
            }
            System.out.println();
            // updating current state of program
            state = status == 1;// customer keeps shopping
        }
    }


    /**This is a helper method returns a specific output by using a specific
     * input from user as stated by the parameter, "intUserInputRetriever".
     * This method takes two parameters, "userInputPrompt" and
     * "intUserInputRetriever". It prints out the userInputPrompt.
     * intUserInputRetriever is basically an implemented version of the
     * interface UserInputRetriever. It simply returns the return value of
     * whatever method it was implemented in, based on the local variable
     * userInput. This method is frequently called in methods that the
     * interface, UserInputRetriever is implemented in. The return value,
     * (which is the output based on user input) is cast from generic to
     * match the method in which it is implemented. This allows prevents code
     * duplication and repetition
     *
     * @param userInputPrompt (Prompt as specified by whatever method calls
     *                        getOutput0nInput())
     * @param intUserInputRetriever (Implemented version of the interface,
     *                              UserInputRetriever, which contains
     *                              user's input. Implemented in
     *                              whatever method calls getOutputOnInput())
     * @return Generic value, which represents output of whatever process is
     *          done based on user's input
     */
   private <T> T getOutputOnInput(String userInputPrompt,
                              UserInputRetriever<T> intUserInputRetriever)
   {
        T value = null; // result

       // Input
        System.out.println(userInputPrompt);
        int userInput = input.nextInt();

       /* Process, as specified by whatever method implemented parameter,
       "intUserInputRetriever". Exception is also caught as defined by the
       interface  UserInputRetriever
        */
        try {
            value = intUserInputRetriever
                    .produceOutputOnIntUserInput(userInput);
        } catch (IllegalArgumentException error){
            System.out.println("Invalid input");
        }

        // output
        return value;
   }

    /**This method create a way for the take-out system to retrieve a user
     * menu selection. This method prints out the Menu object and prompts
     * user to select an item of the menu. It returns the Food object that
     * user selects off the menu.
     *
     * @return Food that user chooses from menu
     */
    public Food getMenuSelection(){
        String userPrompt = "\nOur menu items is listed above. What " +
                "you would like to order: ";

        // prints the menu for user to choose
       System.out.println(menu.toString());

       //Implementation of interface to get input of user
       UserInputRetriever<?> retrieveMenu = selection -> {
            Food food;

            /* checks if the Food that customer wants, based on their input,
           is available. Their input, in this case, being index of food item
           that is on menu*/
            if(menu.getFood(selection) != null)
                food = menu.getFood(selection);

            // if index is invalid, then food is not on menu
            else
                throw new IllegalArgumentException("We do not have any food " +
                        "item with such number on out menu");

            return food;
        };

       // returns food
        return (Food)getOutputOnInput(userPrompt, retrieveMenu);
    }

    /**This method  create a way to notify the take-out system that a
     * user is ready to check out. The method prompts user and asks if user
     * is ready to check out. The method then implements the interface,
     * UserInputRetriever to get the user's input and returns a result based
     * on the input
     *
     * @return True if user is still ordering food
     *          False otherwise (user checking out)
     */
    public int isStillOrderingFood(){
        // prompt for user
        String userPrompt = """
                Main Menu:
                Enter corresponding number proceed:
                0. Go to checkout
                1. Continue Shopping
                2. To view order in cart""";

        /* Implementing interface to process output based on an input
         parameter,"selection"*/
        UserInputRetriever<?> retrieve = selection -> {
            int status;

            // checks if input is 1, then true is returned according to prompt
           if(selection == 1)
               status = 1;

           /* if false, then this takes user to check out as seen
           in takeOutPrompt()*/
           else if (selection == 0)
               status = 0;

           else if(selection ==2){
               status = 2;
           }

           // if invalid option is chosen
           else
               throw new IllegalArgumentException("Invalid choice. Please try" +
                       " again");

           return status;
        };
        // returns result
         return (int) getOutputOnInput(userPrompt, retrieve);
    }

    /**This method checks out the customer. This is the exit point of the
     * takeout system. It checks the cart of the current user and calculates
     * how much each food ordered costs and deducts the cost from the
     * amount of money user has. The method processes customer's payment.
     *
     * @param cart (ShoppingBag object that contains customer's order)
     */
    public void checkoutCustomer(ShoppingBag<Food> cart){
        //check for valid parameters
        if (cart == null)
            throw new IllegalArgumentException("Cannot have an invalid cart");

        // checks to see if customer actually ordered any food
        if(cart.numItemsCheckout() != 0) {
            System.out.println("Currently processing your payment...");
            process(4000);
            // gets total cost of every item in customer's cart
            int totalCost = cart.getTotalPrice();

            int balance;// to update how much the customer has left to spend
            // gets how much should be left after customer purchases food items
            balance = customer.getMoney() - totalCost;
            customer.setMoney(balance);// changes how much customer has left
        }

        else // customer did not order any food
            System.out.println("We see that you did not order anything. We " +
                    "hope to serve you better next time");

        System.out.println("Your current balance is: $"+ customer.getMoney());
        System.out.println("Thanks for choosing Taste of NOLA.\n");
    }

    /**This method simply causes the main thread to sleep. Calling this
     * method pauses the main method in driver class, which runs the program
     * and gives and illusion of the program processing or buffering to get
     * information or perform an operation. It uses the Thread class to
     * implement this.
     *
     * @param sleepTime (Number of milliseconds to buffer)
     */
    public void process(int sleepTime){
        try{
            Thread.sleep(sleepTime);
        }
        catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
    }
}