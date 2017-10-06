package ChatApp;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * contains all users, has methods for checking, inserting and removing
 */
class UserNameList {
    private HashSet<User> userNameSet = new HashSet<>();
    private static UserNameList myInstance = new UserNameList();

    private UserNameList(){
        userNameSet.add(new User("System"));
    }

    static UserNameList get_instance(){
        return myInstance;
    }

    void insert_user_name(User usr){
        userNameSet.add(usr);
    }

    boolean check_contains(User usr){
        return userNameSet.contains(usr);
    }

    ArrayList<User> print_users(){
        ArrayList<User> answer = new ArrayList<>();
        answer.addAll(userNameSet);
        return answer;
    }

    ArrayList<User> print_users(Channel channel){
        ArrayList<User> answer = new ArrayList<>();
        for(User usr: userNameSet){
            if(usr.get_currentChannel()==channel){
                answer.add(usr);
            }
        }
        return answer;
    }

    HashSet<User> getUserNameSet() {
        return this.userNameSet;
    }

}
