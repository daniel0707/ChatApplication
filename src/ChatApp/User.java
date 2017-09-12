package ChatApp;

public class User{
    private String username;

    public User(String str){
        this.username = str;
    }

    public String get_username() {
        return username;
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
