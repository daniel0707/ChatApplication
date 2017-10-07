package ChatApp;

import java.io.*;
import java.util.Scanner;

/**
 * Runs commands depending on input
 */
public class CommandInterpreter implements ChatObserver,Runnable{
    private boolean quit = false;
    private Scanner scanner;
    private PrintStream writer;
    private User user = null;

    CommandInterpreter(InputStream in, OutputStream out){
        this.scanner = new Scanner(in);
        this.writer = new PrintStream(out, true);
    }

    public void run(){
        writer.println(new SystemMessage("Welcome to my humble chat server"));
        while(!quit){
            String input = scanner.hasNext()? scanner.nextLine() : "";
            decide_input_type(input);
        }
    }

    private void decide_input_type(String str){
        String cmd, arg;
        if(!str.isEmpty()) {
            switch (str.charAt(0)) {
                case ':':
                    String[] split = str.substring(1).split("\\s+");
                    if(split.length>0){
                        cmd = split[0];
                    }else{
                        cmd = null;
                    }
                    if(split.length>1){
                        arg = split[1];
                    }else{
                        arg = null;
                    }
                    if(cmd!=null) {
                        run_command(cmd, arg);
                    }
                    break;
                default:
                    send_message(str);
                    break;
            }
        }
    }

    private void run_command(String cmd, String arg){
        switch (cmd){
            case "user":
                if(user==null) {
                    //assigns a username or says its already used
                    if (arg == null) {
                        writer.println(new SystemMessage("Please enter username").toString());
                    } else if (arg.isEmpty()) {
                        writer.println(new SystemMessage("Username cannot be empty").toString());
                    } else if (!UserNameList.get_instance().check_contains(new User(arg))) {
                        user = new User(arg);
                        UserNameList.get_instance().insert_user_name(user);
                        writer.println(new SystemMessage("User " + arg + " has been registered").toString());
                        ChatHistory.get_instance().register_observer(this,user.get_currentChannel());
                    } else {
                        writer.println(new SystemMessage("Username already exists").toString());
                    }
                }else {
                    writer.println(new SystemMessage("Your username is "+user.get_username()).toString());
                }
                break;
            case "userlist":
                //prints users in this channel
                if(arg!=null && !arg.isEmpty()){
                if(arg.equals("all")) {
                    StringBuilder temp = new StringBuilder();
                    for (User usr : UserNameList.get_instance().print_users()) {
                        temp.append(usr.get_username()).append(" ");
                    }
                    writer.println(new SystemMessage(temp.toString()).toString());
                }
                }else {
                 //prints all users
                    StringBuilder temp = new StringBuilder();
                    for (User usr : UserNameList.get_instance().print_users(user.get_currentChannel())) {
                        temp.append(usr.get_username()).append(" ");
                        }
                        writer.println(new SystemMessage(temp.toString()).toString());
                }
                break;
            case "messages":
                writer.println(new SystemMessage(ChatHistory.get_instance().print_history(user)));
                break;
            case "channel":
                if (arg == null) {
                    writer.println(new SystemMessage("Please enter channel name").toString());
                }else if(arg.isEmpty()) {
                    writer.println(new SystemMessage("Channel name cannot be empty").toString());
                }else if(ChannelList.get_instance().check_contains(arg)){
                    ChatHistory.get_instance().remove_observer(this,user.get_currentChannel());
                    user.assign_channel(ChannelList.get_instance().get_channel(arg));
                    ChatHistory.get_instance().add_channel_to_history(ChannelList.get_instance().get_channel(arg));
                    user.set_new_login_timestamp();
                    writer.println(new SystemMessage("Switched to "+arg));
                }else{
                    writer.println(new SystemMessage("Channel "+arg+" doesn't exist, do you want to create it?").toString());
                    if(scanner.hasNext(":yes|:y")){
                        ChatHistory.get_instance().remove_observer(this,user.get_currentChannel());
                        ChannelList.get_instance().add_channel(arg);
                        ChatHistory.get_instance().add_channel_to_history(ChannelList.get_instance().get_channel(arg));
                        user.assign_channel(ChannelList.get_instance().get_channel(arg));
                        ChatHistory.get_instance().register_observer(this,user.get_currentChannel());
                        writer.println(new SystemMessage("Channel "+arg+" created").toString());
                        writer.println(new SystemMessage("Switched to "+arg));
                    }
                }
                break;
            case "channellist":
                writer.println(new SystemMessage(ChannelList.get_instance().printChannelList()));
                break;
            case "quit":
                UserNameList.get_instance().getUserNameSet().remove(this.user);
                quit = true;
                scanner.close();
                writer.close();
                break;
        }
    }

    private void send_message(String str){
        //if user==null, echo message through System
        if(user==null){
            writer.println(new SystemMessage(str));
        }else {
            ChatMessage msg = new ChatMessage(str, user);
            ChatHistory.get_instance().insert(msg);
        }
    }

    @Override
    public void update(ChatMessage msg) {
        if(msg.get_channel().equals(user.get_currentChannel())){writer.println(msg.toString());}
    }

}
