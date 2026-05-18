package startup;

import controller.Controller;
import integration.BikeRegistry;
import integration.RepairTaskCatalog;
import model.RepairShop;
import view.View;

/**
 * Contains the application entry point. Responsible for creating and wiring
 * all top-level objects in dependency order.
 */
public class Main {

    /**
     * Starts the Repair Electric Bike application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        BikeRegistry bikeRegistry = new BikeRegistry();
        RepairTaskCatalog taskCatalog = new RepairTaskCatalog();
        RepairShop repairShop = new RepairShop();
        Controller controller = new Controller(repairShop, bikeRegistry, taskCatalog);
        View view = new View(controller);
        view.runFakeExecution();
    }
}
