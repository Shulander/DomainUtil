package us.vicentini.domainutil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import us.vicentini.domainutil.requests.ELookupStatus;
import us.vicentini.domainutil.requests.ILookupListener;
import us.vicentini.domainutil.requests.LookupThread;

/**
 *
 * @author Shulander
 */
public class DomainUtil implements ActionListener, ILookupListener {

    private final static Log log = LogFactory.getLog(Main.class);
    private IDomainRequest domainRequest;
    private LinkedList<LookupThread> lookupThreads = new LinkedList<>();
    
    private Map<String, Record[]> results = new LinkedHashMap<>();

    void addDomainRequestListener(IDomainRequest domainPanel) {
        this.domainRequest = domainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.info(e.getActionCommand());
        if (e.getActionCommand().equals("processRequest")) {
            processNewRequest();
        }
    }

    private void processNewRequest() {
        List<String> domainsName = domainRequest.getListDomainNames();
        if (!domainsName.isEmpty()) {
            
            lookupThreads.clear();
            results.clear();
            for (String domainsName1 : domainsName) {
                LookupThread newLookup;
                try {
                    newLookup = new LookupThread(domainsName1, new Lookup(domainsName1, org.xbill.DNS.Type.MX));
                    newLookup.addListener(this);
                    newLookup.addListener((ILookupListener) domainRequest);
                    lookupThreads.add(newLookup);
                    results.put(domainsName1, null);
                } catch (TextParseException ex) {
                    log.error("error creating a new lookup for domain '" + domainsName1 + "'", ex);
                }
            }
            
            for (LookupThread lookupThread : lookupThreads) {
                lookupThread.start();
            }
        } else {

        }
    }

    
    @Override
    public void newResults(String domainName, Record[] records) {
        log.info("["+domainName+"]newResults");
        
        results.put(domainName, records);
        
        String text = "";
        for (int i = 0; i < records.length; i++) {
            Record mx = records[i];
            if (mx.getType() == org.xbill.DNS.Type.MX) {
                text = ((MXRecord) mx).getTarget().toString(true);
            } else {
                text = mx.toString();
            }
            log.info("[i] "+domainName+": "+text);
        }

    }

    @Override
    public void newStatus(String domainName, ELookupStatus eLookupStatus) {
        log.info("["+domainName+"]newStatus: "+eLookupStatus.toString());
    }
    

}
