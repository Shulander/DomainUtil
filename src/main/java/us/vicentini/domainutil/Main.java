package us.vicentini.domainutil;

import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.NSRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 *
 * @author Shulander
 */
public class Main {

    public static void main(String[] args) throws TextParseException {
        System.out.println("Teste");

        Record[] records = new Lookup("gmail.com", Type.NS).run();
        for (int i = 0; i < records.length; i++) {
            NSRecord ns = (NSRecord) records[i];
            System.out.println("Nameserver " + ns.getTarget());
        }
        
        records = new Lookup("superlopes.com.br", Type.MX).run();
        for (int i = 0; i < records.length; i++) {
            MXRecord mx = (MXRecord) records[i];
            System.out.println("MX " + mx.getTarget());
        }
    }
}
