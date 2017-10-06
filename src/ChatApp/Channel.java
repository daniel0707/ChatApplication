package ChatApp;

/**
 * template for Channel objects
 */
public class Channel {
    private String channelName;

    Channel(String str){
        this.channelName=str;
    }

    String get_name(){
        return this.channelName;
    }

    @Override
    public int hashCode(){
        return this.channelName.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Channel && (obj == this || this.channelName.equals(((Channel) obj).channelName));
    }
}
