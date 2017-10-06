package ChatApp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * template for System communicates
 */
public class SystemMessage {
    private String msg;
    private Timestamp timestamp;

    SystemMessage(String str){
        this.msg = str;
        this.timestamp = new Timestamp(new java.util.Date().getTime());
    }

    @Override
    public String toString(){
        return (new SimpleDateFormat("HH:mm:ss").format(this.timestamp))+" System "+this.msg;
    }
}
