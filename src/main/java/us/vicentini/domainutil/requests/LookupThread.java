package us.vicentini.domainutil.requests;
import java.util.LinkedList;
import java.util.List;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;

/**
 *
 * @author Shulander
 */
public class LookupThread extends Thread {
    private final Lookup lookup;

    private List<ILookupListener> listeners;
    private final String domainName;

    public LookupThread(String domainName, Lookup lookup) {
        this.domainName = domainName;
        this.lookup = lookup;
        listeners = new LinkedList<ILookupListener>();
    }
    
    public void addListener(ILookupListener iListener) {
        listeners.add(iListener);
        iListener.newStatus(domainName, ELookupStatus.ADDED);
    }
    
    @Override
    public void run() {
        notifyStatus(ELookupStatus.STARTED);
        Record[] records = lookup.run();
        notifyResults(records);
        notifyStatus(ELookupStatus.FINISHED);
    }

    private void notifyResults(Record[] records) {
        for (ILookupListener listener : listeners) {
            listener.newResults(domainName, records);
        }
    }

    private void notifyStatus(ELookupStatus eLookupStatus) {        
        for (ILookupListener listener : listeners) {
            listener.newStatus(domainName, eLookupStatus);
        }
    }
    
}
