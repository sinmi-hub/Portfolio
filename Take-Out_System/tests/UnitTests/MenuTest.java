package TakeoutSystem.UnitTests;

import TakeOutSystem.Food;
import TakeOutSystem.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @Test
    void addDish() {
        Menu menu = new Menu();// comes with 3 dishes by default

        // adds a new Food object to the menu
        assertTrue(menu.addDish(new Food("Rice and Beans","All day special",
                15)));
        assertTrue(menu.addDish(new Food("Gumbo", "Native Breakfast",20)));
        assertFalse(menu.addDish( new Food("pancakes", "breakfast",16)));

        assertEquals(5, menu.numDishOffered());
    }

    @Test
    void removeDish() {
        Menu menu = new Menu();
        assertFalse(menu.removeDish(new Food("Frog legs", "delicacy", 30)));
        assertTrue(menu.removeDish(new Food("Potatoes", "dinner",10)));

        assertEquals(2, menu.numDishOffered());
    }

    @Test
    void searchDish() {
        Menu menu = new Menu();
        Food pancakes = new Food("pancakes", "breakfast",16);
        Food pasta = new Food("pasta","carbohydrate",22);

        assertTrue(menu.searchDish(pancakes));
        assertFalse(menu.searchDish(pasta));
    }

    @Test
    void getFood() {
        Menu menu = new Menu();
        // This gets the 3 food options which are made by constructor

        Food pancakes = menu.getFood(1);
        Food riceAndSteaks = menu.getFood(2);
        Food potatoes = menu.getFood(3);
        Food nullFood = menu.getFood(0);//1 based index is used. Not valid

        // should be  true since index was used to access food
        assertTrue(menu.searchDish(pancakes));
        assertTrue(menu.searchDish(riceAndSteaks));
        assertTrue(menu.searchDish(potatoes));

        assertNull(nullFood);
        assertNull(menu.getFood(0), "Food option" +
                " does not exist in menu");
        assertNull(menu.getFood(4), "Food option" +
                                               " does not exist in menu");
        assertEquals(3, menu.numDishOffered());
    }

    @Test
    void getLowestFoodCost() {
        Menu menu = new Menu();
        assertEquals(10,menu.getLowestFoodCost());

        menu.addDish(new Food("Tres leches","desserts",7));
        assertEquals(7,menu.getLowestFoodCost());

        menu.addDish(new Food("truffles","desserts",3));
        assertEquals(3,menu.getLowestFoodCost());

        menu.removeDish(new Food("truffles","desserts",3));
        menu.removeDish(new Food("Tres leches","desserts",7));
        menu.removeDish(new Food("pancakes", "breakfast",16));
        menu.removeDish(new Food("Rice and Steaks", "lunch",32));
        menu.removeDish(new Food("Potatoes", "dinner",10));

        assertEquals(-1,menu.getLowestFoodCost());
        assertEquals(0, menu.numDishOffered());

        menu.addDish(new Food("Chocolate Fantasy","wow",1000));
        assertEquals(1000,menu.getLowestFoodCost());
        assertEquals(1, menu.numDishOffered());

    }
}