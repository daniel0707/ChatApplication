package ChatApp;

import java.util.HashSet;
import java.util.LinkedList;

public class ChatHistory implements ChatAnnouncer{
    private static ChatHistory ourInstance = new ChatHistory();
    private LinkedList<ChatMessage> messageList = new LinkedList<>();
    private HashSet<ChatObserver> observersSet = new HashSet<>();

    public static ChatHistory get_instance() {
        return ourInstance;
    }

    private ChatHistory() {
    }

    public void insert(ChatMessage message){
        messageList.addLast(message);
        announce(message);
    }

    //deprecated due to new function print_history(User usr)
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(ChatMessage msg:messageList){
            str.append(msg.toString()+System.getProperty("line.separator"));
        }
        return str.toString();
    }

    public String print_history(User usr){
        StringBuilder str = new StringBuilder();
        for(ChatMessage msg:messageList){
            if(msg.get_timestamp().after(usr.get_login_timestamp()) && msg.get_channel().equals(usr.get_currentChannel())) {
                System.out.println("is channel equal: "+msg.get_channel().equals(usr.get_currentChannel()));
                str.append(msg.toString() + System.getProperty("line.separator"));
            }
        }
        return str.toString();
    }


    public void register_observer(ChatObserver observer) {
        observersSet.add(observer);
    }

    @Override
    public void remove_observer(ChatObserver observer) {
        observersSet.remove(observer);
    }

    @Override
    public void announce(ChatMessage msg) {
        for(ChatObserver observer: observersSet){
            observer.update(msg);
        }
    }
}
