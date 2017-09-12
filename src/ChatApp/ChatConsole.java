package ChatApp;

public class ChatConsole implements ChatObserver,Runnable{
    @Override
    public void update(ChatMessage msg) {
        System.out.println(msg.toString());
    }

    @Override
    public void run() {
        ChatHistory.get_instance().register_observer(this);
    }
}
