package ChatApp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class ChatHistory implements ChatAnnouncer{
    private static ChatHistory ourInstance = new ChatHistory();
    private HashMap<String,HashSet<ChatObserver>> observersMap = new HashMap<>();
    private HashMap<String, LinkedList<ChatMessage>> chatHistoryMap = new HashMap<>();

    public static ChatHistory get_instance() {
        return ourInstance;
    }

    private ChatHistory() {
        chatHistoryMap.put("Default",new LinkedList<>());
        observersMap.put("Default",new HashSet<>());
    }

    public void insert(ChatMessage message){
        chatHistoryMap.get(message.get_channel().get_name()).addLast(message);
        announce(message);
    }

    //deprecated due to new function print_history(User usr)
    /*
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(ChatMessage msg:messageList){
            str.append(msg.toString()+System.getProperty("line.separator"));
        }
        return str.toString();
    }
    */

    public String print_history(User usr){
        StringBuilder str = new StringBuilder();
        for(ChatMessage msg:chatHistoryMap.get(usr.get_currentChannel().get_name())){
            //if msg is after user logged in and its from current user channel
            if(msg.get_timestamp().after(usr.get_login_timestamp()) && msg.get_channel().equals(usr.get_currentChannel())) {
                str.append(msg.toString() + System.getProperty("line.separator"));
            }
        }
        return str.toString();
    }

    public void add_channel_to_history(String str){
        chatHistoryMap.put(str,new LinkedList<>());
        observersMap.put(str,new HashSet<>());
    }

    @Override
    public void register_observer(ChatObserver observer, User user) {
        observersMap.get(user.get_currentChannel().get_name()).add(observer);
        System.out.println("Observer registered to "+user.get_currentChannel().get_name());
    }


    @Override
    public void remove_observer(ChatObserver observer, User user) {
        observersMap.get(user.get_currentChannel().get_name()).remove(observer);
        System.out.println("Observer removed");
    }

    @Override
    public void announce(ChatMessage msg) {
        for(ChatObserver observer: observersMap.get(msg.get_channel().get_name())){
            observer.update(msg);
        }
    }
}