import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<Quotation>();

		Registry registry = LocateRegistry.getRegistry();

		for (String name : registry.list()) {
			if (name.startsWith("qs-")) {
				QuotationService service = null;
				try {
					service = (QuotationService) registry.lookup(name);
				} catch (NotBoundException e) {
					throw new RuntimeException(e);
				}
				quotations.add(service.generateQuotation(info));
			}
		}
		return quotations;
	}

	public void bindService(String name, QuotationService service) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry();
		try {
			registry.bind(name,service);
		} catch (AlreadyBoundException e) {
			throw new RuntimeException(e);
		}
	}
}
