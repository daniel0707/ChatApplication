package ChatApp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ChatMessage {
    private String msg;
    private User usr;
    private Timestamp timestamp;

    ChatMessage(String msg, User user){
        this.msg = msg;
        this.usr = user;
        this.timestamp = new Timestamp(new java.util.Date().getTime());
    }

    public Timestamp get_timestamp(){return this.timestamp;}

    @Override
    public String toString(){
        return (new SimpleDateFormat("HH:mm:ss").format(this.timestamp))+ String.format("|%-20s|",this.usr.get_username())+this.msg;
    }
}
