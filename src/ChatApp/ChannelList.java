package ChatApp;

import java.util.HashMap;

/**
 *  List of channels, methods for inserting and printing
 */
class ChannelList {
    private static ChannelList ourInstance = new ChannelList();
    private HashMap<String,Channel> channelMap = new HashMap<>();
    
    private ChannelList(){
        Channel defaultChannel = new Channel("Default");
        channelMap.put("Default",defaultChannel);
    }
    
    static ChannelList get_instance(){return ourInstance;}
    
    boolean check_contains(String str){
        return channelMap.containsKey(str);
    }
    
    Channel get_channel(String str){
            return channelMap.get(str);
    }

    void add_channel(String str){
        channelMap.put(str,new Channel(str));
        System.out.println("Channel "+str+" created");
    }
    String printChannelList(){
        HashMap<String,Integer> myMap = new HashMap<>();
        for (User usr: UserNameList.get_instance().getUserNameSet()) {
            if(myMap.containsKey(usr.get_currentChannel().get_name())){
                myMap.put(usr.get_currentChannel().get_name(),myMap.get(usr.get_currentChannel().get_name())+1);
            }else{
                myMap.put(usr.get_currentChannel().get_name(),1);
            }
        }
        StringBuilder temp = new StringBuilder();
        temp.append("Available channels:");
        for (String chn: channelMap.keySet()) {
            temp.append(" ").append(chn).append(" (").append(myMap.get(chn)).append("), ");
        }
        String tmp = temp.toString();
        tmp=tmp.substring(0,tmp.length()-2);
        return tmp;
    }
}
