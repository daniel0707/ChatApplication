package ChatApp;

import java.sql.Timestamp;

public class ChatMessage {
    private String msg;
    private User usr;
    private Timestamp timestamp;

    ChatMessage(String msg, User user){
        this.msg = msg;
        this.usr = user;
        this.timestamp = new Timestamp(new java.util.Date().getTime());
    }

    @Override
    public String toString(){
        return this.msg + " from "+this.usr.get_username() + " at "+ this.timestamp.toString();
    }
}
