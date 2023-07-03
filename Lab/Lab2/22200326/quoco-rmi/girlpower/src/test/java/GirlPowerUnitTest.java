import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
public class GirlPowerUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService gpqService = new GPQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gpqService,0);
            registry.bind(Constants.GIRL_POWER_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.GIRL_POWER_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void testGenerateQuotation() throws Exception {
        GPQService service = new GPQService() ;
        ClientInfo info = new ClientInfo();
        Quotation result=service.generateQuotation(info);
        Assert.assertNotNull(result);
    }
}