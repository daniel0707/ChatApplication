package ChatApp;

public interface ChatAnnouncer {
    void register_observer(ChatObserver observer, Channel chn);
    void remove_observer(ChatObserver observer, Channel chn);
    void announce (ChatMessage msg);
}
