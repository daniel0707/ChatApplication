package ChatApp;

import java.sql.Timestamp;

public class User{
    private String username;
    private Timestamp loginTimestamp;
    private Channel currentChannel;

    public User(String str){
        this.username = str;
        this.loginTimestamp = new Timestamp(new java.util.Date().getTime());
        this.currentChannel = ChannelList.get_instance().get_channel("Default");
    }

    public String get_username() {
        return username;
    }

    public Timestamp get_login_timestamp() {
        return loginTimestamp;
    }

    public void assign_channel(Channel chn){
        this.currentChannel = chn;
    }

    public Channel get_currentChannel(){
        return currentChannel;
    }

    public void set_new_login_timestamp(){
        this.loginTimestamp = new Timestamp(new java.util.Date().getTime());
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && (obj == this || this.username.equals(((User) obj).username));
    }
}
