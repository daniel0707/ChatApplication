package ChatApp;

public class Channel {
    private String channelName;

    public Channel(String str){
        this.channelName=str;
    }

    public String get_name(){
        return this.channelName;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Channel)) {
            return false;
        }else if (obj == this) {
            return true;
        }else {
            return this.channelName.equals(((Channel) obj).channelName);
        }
    }
}