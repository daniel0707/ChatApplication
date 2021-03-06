package ChatApp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Template for user messages
 */
public class ChatMessage {
    private String msg;
    private User usr;
    private Timestamp timestamp;
    private Channel channel;

    ChatMessage(String msg, User user){
        this.msg = msg;
        this.usr = user;
        this.channel = user.get_currentChannel();
        this.timestamp = new Timestamp(new java.util.Date().getTime());
    }

    Timestamp get_timestamp(){return this.timestamp;}

    Channel get_channel() {
        return channel;
    }

    @Override
    public String toString(){
        return (new SimpleDateFormat("HH:mm:ss").format(this.timestamp))+" "+this.usr.get_username()+" "+this.msg;
    }
}
