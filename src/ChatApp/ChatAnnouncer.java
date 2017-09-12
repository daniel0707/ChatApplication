package ChatApp;

public interface ChatAnnouncer {
    void register_observer(ChatObserver announcer);
    void remove_observer(ChatObserver announcer);
    void announce (ChatMessage msg);
}
