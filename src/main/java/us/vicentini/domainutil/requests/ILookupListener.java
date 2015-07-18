package us.vicentini.domainutil.requests;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;

/**
 *
 * @author Shulander
 */
public interface ILookupListener {

    public void newResults(String domainName, Record[] records);

    public void newStatus(String domainName, ELookupStatus eLookupStatus);
    
}
