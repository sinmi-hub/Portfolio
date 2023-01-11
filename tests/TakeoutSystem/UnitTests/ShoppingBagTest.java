package TakeoutSystem.UnitTests;

import TakeOutSystem.Food;
import TakeOutSystem.ShoppingBag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingBagTest {

    @Test
    void addItem() {
        ShoppingBag<Food> checkout = new ShoppingBag<>();
        // checking to see same item can be added to cart twice
        checkout.addItem(new Food("Chocolate Fantasy","wow",1000));
        checkout.addItem(new Food("Chocolate Fantasy","wow",1000));

        // Only 1 item should be in checkout
        assertEquals(1, checkout.numItemsCheckout());
        /* 2 quantities of 1 item(chocolate fantasy) should be in checkout.
         Therefore, total price at checkout should be 2000, not 1000*/
        assertEquals(2000,checkout.getTotalPrice());

        checkout.addItem(new Food(" Oats","weak",39));
        assertEquals(2, checkout.numItemsCheckout());
    }

    @Test
    void removeItem() {
        ShoppingBag<Food> cart = new ShoppingBag<>();
        // removing from cart without adding
        cart.removeItem(new Food("Grits","Soft",10));

        Food oats = new Food("pasta","carbohydrate",22);
        Food pasta = new Food("pasta","carbohydrate",67);
        // adding to cart
        cart.addItem(oats);
        cart.addItem(pasta);
        // removing from cart
        cart.removeItem(oats);
        assertEquals(1,cart.numItemsCheckout());

        cart.removeItem(pasta);
        assertEquals(0,cart.numItemsCheckout());
    }

    @Test
    void getTotalPrice() {
        ShoppingBag<Food> bag = new ShoppingBag<>();
        Food pasta = new Food("pasta","carbohydrate",22);

        // checking to see same item can be added to cart twice
        bag.addItem(new Food("Chocolate Fantasy","wow",1000));
        bag.addItem(new Food("Chocolate Fantasy","wow",1000));
        bag.addItem(new Food("Frog legs", "delicacy", 30));
        bag.addItem(new Food("Gumbo", "Native Breakfast",20));
        bag.addItem(pasta);

        assertEquals(4,bag.numItemsCheckout());
        assertEquals(2072, bag.getTotalPrice());

        bag.removeItem(new Food("Gumbo", "Native Breakfast",20));
        bag.removeItem(new Food("Frog legs", "delicacy", 30));

        assertEquals(2,bag.numItemsCheckout());
        assertEquals(2022, bag.getTotalPrice());

    }
}