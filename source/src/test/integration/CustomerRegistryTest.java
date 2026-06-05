package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.CustomerDTO;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistryTest {

    private CustomerRegistry customerRegistry;

    @BeforeEach
    void setUp() {
        customerRegistry = new CustomerRegistry();
    }

    @Test
    void findCustomerWithExistingIdReturnsCustomer() {
        CustomerDTO customer = customerRegistry.findCustomer("CUST-001");
        assertNotNull(customer);
    }

    @Test
    void findCustomerWithExistingIdReturnsCorrectName() {
        CustomerDTO customer = customerRegistry.findCustomer("CUST-002");
        assertEquals("Bob Lindqvist", customer.getName());
    }

    @Test
    void findCustomerWithNonExistingIdReturnsNull() {
        CustomerDTO customer = customerRegistry.findCustomer("CUST-999");
        assertNull(customer);
    }

    @Test
    void findCustomerWithEmptyStringReturnsNull() {
        CustomerDTO customer = customerRegistry.findCustomer("");
        assertNull(customer);
    }
}
