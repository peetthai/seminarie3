package integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.TaskDTO;

import static org.junit.jupiter.api.Assertions.*;

class RepairTaskCatalogTest {

    private RepairTaskCatalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new RepairTaskCatalog();
    }

    @Test
    void findTaskWithExistingNameReturnsTaskDTO() {
        TaskDTO task = catalog.findTask("Brake Pad Replacement");
        assertNotNull(task);
    }

    @Test
    void findTaskWithExistingNameReturnsCorrectName() {
        TaskDTO task = catalog.findTask("Tire Replacement");
        assertEquals("Tire Replacement", task.getName());
    }

    @Test
    void findTaskWithExistingNameReturnsCorrectCost() {
        TaskDTO task = catalog.findTask("Battery Check");
        assertEquals(200.0, task.getCost().getValue(), 0.001);
    }

    @Test
    void findTaskWithNonExistingNameReturnsNull() {
        TaskDTO task = catalog.findTask("Non-existing Task");
        assertNull(task);
    }

    @Test
    void findTaskWithEmptyStringReturnsNull() {
        TaskDTO task = catalog.findTask("");
        assertNull(task);
    }

    @Test
    void findTaskWithAllFourSampleTasksReturnsNonNull() {
        assertNotNull(catalog.findTask("Brake Pad Replacement"));
        assertNotNull(catalog.findTask("Tire Replacement"));
        assertNotNull(catalog.findTask("Battery Check"));
        assertNotNull(catalog.findTask("Chain Lubrication"));
    }
}
