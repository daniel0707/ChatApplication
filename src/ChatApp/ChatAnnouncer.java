package ChatApp;

public interface ChatAnnouncer {
    void register_observer(ChatObserver observer, User user);
    void remove_observer(ChatObserver observer, User user);
    void announce (ChatMessage msg);
}
