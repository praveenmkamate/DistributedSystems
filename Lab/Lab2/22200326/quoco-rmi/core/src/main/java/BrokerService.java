

import java.rmi.RemoteException;
import java.util.List;


/**
 * Interface for defining the behaviours of the broker service
 * @author Rem
 *
 */
public interface BrokerService extends java.rmi.Remote {
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException;

	public void bindService(String name, QuotationService service) throws RemoteException;
}
