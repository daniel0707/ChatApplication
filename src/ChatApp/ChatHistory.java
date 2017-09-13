package ChatApp;

import java.util.HashSet;
import java.util.LinkedList;

public class ChatHistory implements ChatAnnouncer{
    private static ChatHistory ourInstance = new ChatHistory();
    private LinkedList<ChatMessage> messageList = new LinkedList<>();
    private HashSet<ChatObserver> ObserversSet = new HashSet<>();

    public static ChatHistory get_instance() {
        return ourInstance;
    }

    private ChatHistory() {
    }

    public void insert(ChatMessage message){
        messageList.addLast(message);
        announce(message);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(ChatMessage msg:messageList){
            str.append(msg.toString()+System.getProperty("line.separator"));
        }
        return str.toString();
    }

    public void register_observer(ChatObserver observer) {
        ObserversSet.add(observer);
    }

    @Override
    public void remove_observer(ChatObserver observer) {
        ObserversSet.remove(observer);
    }

    @Override
    public void announce(ChatMessage msg) {
        for(ChatObserver observer: ObserversSet){
            observer.update(msg);
        }
    }
}
