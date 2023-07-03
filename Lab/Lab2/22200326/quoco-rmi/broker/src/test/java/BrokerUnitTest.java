import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class BrokerUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        BrokerService broker = new LocalBrokerService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService brokerService = (BrokerService)
                    UnicastRemoteObject.exportObject(broker,0);
            registry.bind(Constants.BROKER_SERVICE, brokerService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        BrokerService service = (BrokerService)
                registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull(service);
    }


}