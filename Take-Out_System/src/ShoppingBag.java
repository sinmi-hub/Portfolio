package TakeOutSystem;

/*This class represents a ShoppingBag class in a current TakeOut system
 * object. This class uses Map data structure, specifically, a LinkedHashMap
 * to store how the Customer object interacts with the takeout system. It
 * uses the map to store the item that the customer purchases, specifically,
 * food, and performs various operation on item. The map in this class,
 * shoppingBag, stores a generic key (in this case, any item that customer
 * interacts with) and stores Integer as it values. it values represent how
 * much of the key(item) that the customer uses or wants. For example, a
 * customer ordering pancakes would appear in the map with pancakes as the key
 * and its value as how many the customer orders.
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingBag<T extends PricedItem<Integer>>{
    // to be used to store item customer interacts with
    private Map<T,Integer> shoppingBag;

    /**Default constructor that initializes the map, "shoppingBag" to a
     * LinkedHashMap. A regular hashmap could also be used here, but we would
     * like to keep the shoppingBag in the order that the customer chooses
     * the items.
     */
    public ShoppingBag(){
        shoppingBag = new LinkedHashMap<>();
    }

    /**This method adds an item to the map, "shoppingBag". This method is
     * called upon when a customer decides to purchase an item. The method
     * checks if the item is currently in the map. If it is, it gets the
     * value. i.e. quantity of the item and increases it by one and adds it
     * back to the map. If it was not previously in the map, i.e. the
     * customer decides to purchase the item for the first time, the item is
     * simply added to the map with 1 as its value.
     *
     * @param item (Object to add to map, "shoppingBag")
     */
    public void addItem(T item){
        // checking for valid parameters
        if(item == null)
            throw new IllegalArgumentException("Cannot add invalid items to " +
                    "cart");

        /* check if the item is not yet in the shopping bag. If it is not,
        once addItem() is called, it should add 1 quantity of item to
        shopping bag*/
        if(!shoppingBag.containsKey(item))
            shoppingBag.put(item, 1);// adds 1 of item to shopping bag

        //if item is already in shopping bag.
        else {
            // get the current quantity of item
            int quantity = shoppingBag.get(item);
            quantity++;// increase it by one
            shoppingBag.put(item, quantity);// put it back into shopping bag
        }
    }

    /**This method is the opposite of addItem(). This method removes an item to
     * the map, "shoppingBag". This method is called upon when a customer
     * decides to remove an item they previously wanted to purchase. The method
     * checks if the item is currently in the map. If it is, it gets the
     * value. i.e. quantity of the item and decreases it by one and adds it
     * back to the map. However, if there is only 1 of the item in the cart,
     * it is simply removed and not decreased. This means that there can never
     * be 0 of an item in a cart, since the least we can add to a cart is 1
     * item as specified in addItem().
     *
     * @param item (Object to remove from map, "shoppingBag")
     */
    public void removeItem(T item){
        // check for valid parameters
        if(item == null)
            throw new IllegalArgumentException("Cannot remove invalid item " +
                    "from cart");

        // check to see if item is in the map
        if(shoppingBag.containsKey(item)){
            /* checks to see if there is more than 1 quantity of the item in
             the map, "shoppingBag"*/
            if(shoppingBag.get(item) > 1){
                int quantity = shoppingBag.get(item);
                quantity--; // decrease quantity, instead of completely removing
                shoppingBag.put(item, quantity);// put it back in map
            }

            else
                shoppingBag.remove(item);// simply remove the item from bag
        }

        // if it is not in the cart, nothing to remove
        else
            System.out.println("The item you have chosen is not in your cart");
    }

    /**This method returns the total price of items present in the map,
     * "shoppingBag". It does this by iterating through every item present
     * and getting the quantity. After getting the quantities of each item,
     * it gets the price of each item too and multiplies it together to get
     * total cost of the item in shoppingBag based on the quantities. It does
     * this for every item in shoppingBag and adds it all up to get the total
     * price of items in the cart.
     *
     * @return (Total cost of all items present in the cart)
     */
    public int getTotalPrice(){
        int count = 0;

        // iterate through the items in the map
        for(T item: shoppingBag.keySet())
            /* gets the quantities of current iteration of item and multiply
             it by the price. It then adds it to count at every iteration*/
            count += (shoppingBag.get(item) * item.getPrice());

        return count;
    }

    /**This method returns the number of items that are currently in the
     * customer's cart to check out. It does this by returning the size of
     * "shoppingBag", which is the map used to implement the checkout process
     * for customers. Also,using this method, we can check if shoppingBag is
     * empty.
     *
     * @return (number of items in shoppingBag)
     */
    public int numItemsCheckout(){
        return shoppingBag.size();
    }

    /**This method prints a string representation of the current shoppingBag
     * object. It does this by printing every item currently in the shopping
     * bag on a new line except for the last item. A count variable is used
     * to keep track of last item so a new line is not printed after it.
     *
     * @return String representation of customer's shopping bag
     */
    @Override
    public String toString() {
        int count = numItemsCheckout();// current number of items in shoppingBag
        StringBuilder result = new StringBuilder("Your cart currently contains:\n");

        for(T item: shoppingBag.keySet()){
            /* if more than 1 item is left to be printed, a new line is
             printed at the end of the sentence.*/
            if(count != 1){
                // checks quantity of item to determine what vocabulary of
                // quantities to use.
                if(shoppingBag.get(item) > 1)
                    result.append(shoppingBag.get(item)).
                        append(" quantities of ").append(item).append("\n");

                else // plural
                    result.append(shoppingBag.get(item)).
                            append(" quantity of ").append(item).append("\n");

                count--;// reduces after an item is printed
            }

            // does not print newline at the end
            else{
                // checks quantity of item to determine what vocabulary of
                // quantities to use.
                if(shoppingBag.get(item) > 1)
                    result.append(shoppingBag.get(item)).
                            append(" quantities of ").append(item);

                else // plural
                    result.append(shoppingBag.get(item)).
                            append(" quantity of ").append(item);
            }
        }

        return result.toString();
    }
}
