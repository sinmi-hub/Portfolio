package TakeOutSystem;

/**This class represents a Customer class in a Takeout System. A customer is a
 * client that uses the takeout system. It always has two properties. The
 * name of the customer and the amount of money that the customer has to
 * spend. These two field help identify the customer in the takeout system.
 * The fields are made private. Getters and setters are created only where
 * they are needed as creating getters and setters reduces privacy. In this
 * class, a setter method is not created for customer's name as it is not
 * needed to be modified.
 *
 */
public class Customer {
    private String name;// name of customer
    private int money;// amount of money that the customer currently has

    /**Default Constructor
     * This constructor initializes all fields of the customer class
     */
    public Customer(){
        name = "";// name is set to empty
        money = 0;// money is set to zero to start
    }

    /**This constructor initializes and create a new customer object with
     * certain parameters, name and money. It checks to make sure the
     * parameters are valid before creating a new customer object
     *
     * @param name (name of the customer)
     * @param money ( amount of money customer has)
     */
    public Customer(String name, int money){

        /* checks to see if name is valid and money passed in is not negative
         because you cannot have negative numbers*/
        if(name != null && money >= 0){
            this.name = name;// sets customer's name to name
            this.money = money;// sets customer's money to money
        }
    }

    // getter method for name
    public String getName() {
        return name;
    }

    // getter method for money
    public int getMoney() {
        return money;
    }

    // setter method for money
    public void setMoney(int money) {
        this.money = money;
    }

}
