import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.*;
import static org.junit.Assert.assertNotNull;
public class DodgyDriversUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService afqService = new DDQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);
            registry.bind(Constants.DODGY_DRIVERS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_DRIVERS_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void testGenerateQuotation() throws Exception {
        DDQService service = new DDQService() ;
        ClientInfo info = new ClientInfo();
        Quotation result=service.generateQuotation(info);
        Assert.assertNotNull(result);
    }
}