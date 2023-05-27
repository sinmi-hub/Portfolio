package TakeoutSystem.UnitTests;

import static org.junit.Assert.*;

import TakeOutSystem.Food;
import org.junit.Test;

public class FoodTest {
    Food breakfast = new Food("baguette","French",20);

    @Test
    public void getPrice() {
        Integer price = 20;
        assertEquals(price, breakfast.getPrice());
    }

    @Test
    public void setPrice() {
        Food dish = new Food();
        dish.setPrice(100);// sets price of dish
        breakfast.setPrice(135);// sets price of global var, breakfast

        Integer dishPrice = 100;
        Integer breakfastPrice = 135;

        assertEquals(dishPrice, dish.getPrice());
        assertEquals(breakfastPrice, breakfast.getPrice());
    }

    @Test
    public void getName() {
        Food noName = new Food();
        assertEquals("baguette", breakfast.getName());
        assertEquals("", noName.getName());
    }

    @Test
    public void testToString() {
        Food food = new Food("Sausage and Eggs", "breakfast", 40);

        // could have used food.getName and getPrice(). but was too lazy
        assertEquals("Enjoy Sausage and Eggs: breakfast item\n\tCost: $40",
                food.toString());
    }
}