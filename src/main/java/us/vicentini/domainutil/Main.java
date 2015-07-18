package us.vicentini.domainutil;

import java.io.File;
import org.xbill.DNS.TextParseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import us.vicentini.domainutil.view.DomainPanel;

/**
 *
 * @author Shulander
 */
public class Main{
    private final static Log log = LogFactory.getLog(Main.class);
    
    static {
        if((new File("config/log4j.xml")).exists()){
            DOMConfigurator.configure("config/log4j.xml");
        }else if((new File("config/log4j.properties")).exists()) {
            PropertyConfigurator.configure("config/log4j.properties");
        }
    }
      
    public static void main(String[] args) throws TextParseException, InterruptedException {
        log.info("Start");
        
        DomainUtil domainUtil = new DomainUtil();
        
        DomainPanel domainPanel = new DomainPanel();
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DomainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DomainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DomainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DomainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        domainPanel.addActionListener(domainUtil);
        domainUtil.addDomainRequestListener(domainPanel);
        
        domainPanel.setVisible(true);
        
        
        log.info("End");
    }


}
