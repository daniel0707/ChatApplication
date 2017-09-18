package ChatApp;

import java.util.ArrayList;
import java.util.HashSet;

public class UserNameList {
    private HashSet<User> userNameSet = new HashSet<>();
    private static UserNameList myInstance = new UserNameList();

    private UserNameList(){
    }

    public static UserNameList get_instance(){
        return myInstance;
    }

    public void insert_user_name(User usr){
        userNameSet.add(usr);
    }

    public boolean check_contains(User usr){
        return userNameSet.contains(usr);
    }

    public ArrayList<User> print_users(){
        ArrayList<User> answer = new ArrayList<>();
        for(User usr: userNameSet){
            answer.add(usr);
        }
        return answer;
    }

    public ArrayList<User> print_users(Channel channel){
        ArrayList<User> answer = new ArrayList<>();
        for(User usr: userNameSet){
            if(usr.get_currentChannel()==channel){
                answer.add(usr);
            }
        }
        return answer;
    }

}
