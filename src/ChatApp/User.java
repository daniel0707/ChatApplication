package ChatApp;

import java.sql.Timestamp;

public class User{
    private String username;
    private Timestamp loginTimestamp;

    public User(String str){
        this.username = str;
        this.loginTimestamp = new Timestamp(new java.util.Date().getTime());
    }

    public String get_username() {
        return username;
    }

    public Timestamp get_login_timestamp() {
        return loginTimestamp;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof User)) {
            return false;
        }else if (obj == this) {
            return true;
        }else {
            return this.username.equals(((User) obj).username);
        }
    }
}
