package TakeOutSystem;

/**This class represents a Food class in a Takeout System. It represents the
 *  type of food that the restaurant sells. This class is the item that the
 *  customer shops for or chooses. It has three fields: name, description,
 *  and price. The name field represents the name of the food, the
 *  description is referred to as the category or description of food and the
 *  price refers to the current price of the food. Instead of using getters
 *  and setters in the class, this is defined in a separate Interface called
 *  PricedItem. This is in the event that another item, which is not food,
 *  that is to be sold by the Takeout System is added, all that is needed is
 *  to implement the interface.
 */
public class Food implements PricedItem<Integer>{
    private String name;
    private String description;// brief description of what the food is
    private int price;

    /**Default Constructor which initializes all the fields of the food class
     * Name and description are both set to empty, while price is set to 0*/
    public Food(){
        name = "";
        description ="";
        price = 0;
    }

    /**This is a constructor that initializes the fields of the Food object
     * and creates a food object based on the parameters given in the
     * constructor
     *
     * @param name (name of food)
     * @param description (brief description of food)
     * @param price (price of food)
     */
    public Food(String name, String description, int price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**This method is an implementation of the PricedItem interface which
     * defines the method getPrice(). This method serves as a getter method
     * for the Food class and returns the price of the food. The return type
     * is Object based on the requirements of the PricedItem interface. It
     * could have been simply an int type return too.
     *
     * @return (price of food)
     */
    @Override
    public Integer getPrice() {
        return price;
    }

    /**This method is an implementation of the PricedItem interface which
     * defines the method setPrice(). This method serves as a setter method
     * for the Food class and modifies the price of the food by changing it
     * to parameter, newPrice.
     *
     * @param newPrice (new price to change the price of current food object to)
     */
    @Override
    public void setPrice(Integer newPrice) {

        /* checks to make sure price is not negative. Since food cannot cost
         negative amount of dollars*/
        if(price >= 0)
            price = newPrice;// changes price
    }

    // getter method for the name field to be used in another class
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Enjoy " + name + ": " + description+" item"+ "\n\tCost: $" + price;
    }

    // Overriding the equals method to make two Food object equals if they
    // have the same name
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Food))
            return false;

        //noinspection PatternVariableCanBeUsed
        Food food = (Food) obj;

        return name.equals(food.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
