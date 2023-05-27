package TakeoutSystem.UnitTests;
// using junit5

import static org.junit.jupiter.api.Assertions.*;

import TakeOutSystem.Customer;
import org.junit.jupiter.api.Test;

class CustomerTest {
    Customer customer = new Customer("Rump Don", 0);
    @Test
    void getName() {
        assertEquals("Rump Don", customer.getName());
    }

    @Test
    void getMoney() {
        assertEquals(0, customer.getMoney());
    }

    @Test
    void setMoney() {
        Customer noName = new Customer();
        assertEquals("", noName.getName());
        noName.setMoney(100000);
        assertEquals(100000, noName.getMoney());
    }
}