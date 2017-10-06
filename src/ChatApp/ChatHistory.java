package ChatApp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * History of all messages, method for insert and printing
 */
public class ChatHistory implements ChatAnnouncer{
    private static ChatHistory ourInstance = new ChatHistory();
    private HashMap<Channel,HashSet<ChatObserver>> observersMap = new HashMap<>();
    private HashMap<Channel,LinkedList<ChatMessage>> chatHistoryMap = new HashMap<>();

    static ChatHistory get_instance() {
        return ourInstance;
    }

    private ChatHistory() {
        Channel defaultChannel = new Channel("Default");
        chatHistoryMap.put(defaultChannel,new LinkedList<>());
        observersMap.put(defaultChannel,new HashSet<>());
    }

    void insert(ChatMessage message){
        chatHistoryMap.get(message.get_channel()).addLast(message);
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

    String print_history(User usr){
        StringBuilder str = new StringBuilder();
        str.append("History of ").append(usr.get_currentChannel().get_name()).append(":").append(System.getProperty("line.separator"));
        for(ChatMessage msg:chatHistoryMap.get(usr.get_currentChannel())){
            //if msg is after user logged in and its from current user channel
            if(msg.get_timestamp().after(usr.get_login_timestamp()) && msg.get_channel().equals(usr.get_currentChannel())) {
                str.append(msg.toString()).append(System.getProperty("line.separator"));
            }
        }
        return str.toString();
    }

    void add_channel_to_history(Channel chn){
        chatHistoryMap.put(chn,new LinkedList<>());
        observersMap.put(chn,new HashSet<>());
    }

    @Override
    public void register_observer(ChatObserver observer, Channel chn) {
        observersMap.get(chn).add(observer);
    }


    @Override
    public void remove_observer(ChatObserver observer, Channel chn) {
        observersMap.get(chn).remove(observer);
    }

    @Override
    public void announce(ChatMessage msg) {
        for(ChatObserver observer: observersMap.get(msg.get_channel())){
            observer.update(msg);
        }
    }
}