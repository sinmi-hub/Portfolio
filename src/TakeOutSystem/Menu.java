package TakeOutSystem;

/*This class represents a Menu class in the current Takeout system. This
 * class serves as a List of Food objects that a Customer object interacts
 * with in the Takeout system. It uses the List structure to store a list of
 * Food objects.
 */

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Food> menu;// to store Food objects

    /**Default Constructor that initializes the List, "menu". This default
     * constructor also creates 3 Food objects and adds it to menu, which
     * means by default, whenever a menu object is created, it comes with 3
     * Food options available
     */
    public Menu(){
        menu = new ArrayList<>();// uses arrayList to initialize menu

        // create new Food objects and simultaneously adds them to menu
        menu.add(new Food("pancakes", "breakfast",16));
        menu.add(new Food("Rice and Steaks", "lunch",32));
        menu.add(new Food("Potatoes", "dinner",10));
    }

    /**This method adds a new Food object to the current Menu Object. It
     * checks to make sure that the parameter, "newMenuOption" i.e. new dish
     * to add, is not current Menu object and adds it if this is true. If it
     * is currently in the Menu object then it will not be added
     *
     * @param newMenuOption (Food object to add to current Menu object)
     * @return true if Food object was added successfully
     *          false otherwise
     */
    public boolean addDish (Food newMenuOption){
        boolean makeDish = false;

        // check for valid parameters
        if(newMenuOption == null)
            throw new IllegalArgumentException("Cannot add an invalid menu " +
                    "options");

        // checks if the newMenuOption to be added already exist in the menu
        if(!menu.contains(newMenuOption))
            makeDish = menu.add(newMenuOption);// returns true after adding

        return makeDish;
    }

    /**This method is the opposite of addDish().This method removes a Food
     * object (i.e. parameter, "menuOptions") from the current Menu Object. It
     * checks to make sure that the parameter, "menuOption" is a dish in the
     * current Menu object and removes it if it is present. Returns true if
     * the Food object was removed successfully and false otherwise.
     *
     * @param menuOption (Food object to remove from the current Menu object)
     * @return True if menuOption is successfully removed from current Menu
     *          object or false otherwise
     */
    public boolean removeDish(Food menuOption){
        boolean remove;

        // check for valid parameters first
        if(menuOption == null)
            throw new IllegalArgumentException("Cannot remove an invalid menu" +
                    " option");

        /* check to see if menuOption currently exist in the menu, and it
         removes it*/
       remove = menu.remove(menuOption);

       return remove;
    }

    /**This method checks if parameter, "menuOptions" is in the current Menu
     * object. If the Food object exist, true is returned, otherwise false is
     * returned
     *
     * @param menuOption (Food object to search for)
     * @return True if menuOptions is found
     *          False otherwise
     */
    public boolean searchDish(Food menuOption){
        boolean found;

        //checks for valid parameters
        if(menuOption==null)
            throw new IllegalArgumentException("Cannot search for an invalid " +
                    "menu option");

        /*flag is updated with result of contains() method that checks if
        menuOptions is in menu*/
        found = menu.contains(menuOption);

        return found;
    }

    /**This method uses an index to search for a specific Food object in the
     * current Menu object. It makes sure the index is valid .i.e. it is
     * within the bounds of index of the arrayList, menu. If the index is out
     * of bounds, then null is simply returned, which means the Food object
     * does not exist in the current Menu object. The Food object returned is
     * the object as position index - 1. This is because, in the Driver class,
     * it shows the menu as starting from 1 instead of 0. Since arrayList
     * uses zero based indexing, in order to reconcile both, we access the
     * arrayList using 1-based indexing.
     *
     * @param index (Position of where Food object is stored)
     * @return Food object that is stored at the parameter "index"
     */
    public Food getFood(int index){
        Food foundFood;

        // checking for valid index to get Food object. Any index less than
        // or equal to zero cannot be passed
        if(index <= 0 || index > menu.size())
            foundFood = null;

        else
            // 1- based indexing is used
            foundFood = menu.get(index-1);

        return foundFood;
    }

    /**This method checks for a Food object that is currently in the Menu
     * object, that has the lowest price and returns the price. It does this
     * by iterating through all Food objects in menu and looking for lowest
     * price. If menu is empty, -1 is returned
     *
     * @return lowest price of all Food objects in the current menu object
     */
    public int getLowestFoodCost(){
        int cheapest;

        /* initializing cheapest based on whether menu is empty or not
        * If menu is not empty*/
        if(numDishOffered()!= 0){
            // gets the first food in menu, and set cheapest to price of that
            // food
            cheapest= menu.get(0).getPrice();
        }

        else // if menu is empty, cheapest is set to zero
            cheapest = -1;

        // Iterates through every food in the menu and gets the price
        for (Food food : menu) {

            /*if it finds a price smaller than what cheapest is at any point
            in the iteration, cheapest is updated with that price*/
            if (cheapest > food.getPrice())
                cheapest = food.getPrice();// updates cheapest
        }

        return cheapest;
    }

    /**This method returns a String representation of current Menu object.
     * One thing to state here is that, as Food objects in menu is printed,
     * they are numbered. This number start from 1 and is printed alongside
     * every Food object that exist in current Menu object. This is the
     * reason why we use 1-based indexing in getFood(). StringBuilder is also
     * used here because concatenation would be needed if string was used
     *
     * @return (String representation of menu)
     */
    @Override
    public String toString(){
        StringBuilder printMenu = null;
        int i = 1;// starts at 1

        for(Food food : menu) {
            /* checks if printMenu is null. i.e. if this is the first Food
             object being printed*/
            if(printMenu == null) {
                // creates String Builder object
                printMenu = new StringBuilder(i + ". " + food.toString());
            } else {
                // appends every food object on a new line
                printMenu.append("\n").append(i).append(". ").append(food.toString());
            }
            i++;
        }

        // asserts if PrintMenu is not null and returns it
        return printMenu == null ? null : printMenu.toString();
    }

    /**This method returns the number of Food object that currently in the
     * menu object. Since menu is stored as an arrayList, it simply returns
     * the size of the arrayList
     *
     * @return (Number of Food object that the menu has)
     */
    public int numDishOffered(){
        return menu.size();
    }

}
