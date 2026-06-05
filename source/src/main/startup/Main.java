package startup;

import controller.Controller;
import integration.RegistryCreator;
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
        RegistryCreator registryCreator = new RegistryCreator();
        RepairShop repairShop = new RepairShop();
        Controller controller = new Controller(repairShop, registryCreator);
        View view = new View(controller);
        view.runFakeExecution();
    }
}
