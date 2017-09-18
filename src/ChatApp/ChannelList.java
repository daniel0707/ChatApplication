package ChatApp;

import java.util.HashMap;

public class ChannelList {
    private static ChannelList ourInstance = new ChannelList();
    private HashMap<String,Channel> channelMap = new HashMap<>();
    
    private ChannelList(){
        Channel defaultChannel = new Channel("Default");
        channelMap.put("Default",defaultChannel);
    }
    
    public static ChannelList get_instance(){return ourInstance;}
    
    public boolean check_contains(String str){
        return channelMap.containsKey(str);
    }
    
    public Channel get_channel(String str){
            return channelMap.get(str);
    }

    public void add_channel(String str){
        channelMap.put(str,new Channel(str));
        System.out.println("Channel "+str+" created");
    }
}
