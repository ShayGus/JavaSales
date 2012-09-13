package LoggerPKG;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;

public class SaleFormatter extends XMLFormatter {

	public String format(LogRecord record) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuffer sb = new StringBuffer(1000);
        sb.append("<record>\n");
       
        sb.append("  <date>");
        sb.append(dateFormat.format(new Date()));
        sb.append("</date>\n");

        sb.append("  <sequence>");
        sb.append(record.getSequenceNumber());
        sb.append("</sequence>\n");

        if (record.getSourceClassName() != null) {
            if(record.getSourceClassName().contains("Auction") || record.getSourceClassName().contains("Auction")){
            	sb.append("  <Sale type='Auction' item = " + (record.getParameters()[0]).toString() + " action = " + (record.getParameters()[1]).toString() + ">");
            	sb.append(record.getMessage());
            	sb.append("</Sale>\n");
            }else{
            	sb.append("  <Customer name = " + (record.getParameters()[0]).toString() + " action = " + (record.getParameters()[1]).toString() + ">");
            	sb.append(record.getMessage());
            	sb.append("</Customer>\n");
            }
        }
        
        sb.append("</record>\n");
        return sb.toString();
	}
	
	
}

