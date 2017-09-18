package ChatApp;

import java.io.*;
import java.util.Scanner;

public class CommandInterpreter implements ChatObserver,Runnable{
    private boolean quit = false;
    private Scanner scanner;
    private PrintStream writer;
    private User user = null;

    public CommandInterpreter(InputStream in, OutputStream out){
        this.scanner = new Scanner(in);
        this.writer = new PrintStream(out, true);
    }

    public void run(){
        writer.println("Welcome to my humble chat server");
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
                        writer.println("Please enter username");
                    } else if (arg.isEmpty()) {
                        writer.println("Username cannot be empty");
                    } else if (!UserNameList.get_instance().check_contains(new User(arg))) {
                        user = new User(arg);
                        UserNameList.get_instance().insert_user_name(user);
                        writer.println("User " + arg + " has been registered");
                        //register to observe chat
                        ChatHistory.get_instance().register_observer(this,user);
                    } else {
                        writer.println("User already exists");
                    }
                }else {
                    writer.println("Your username is "+user.get_username());
                }
                break;
            case "userlist":
                //prints users in this channel
                if(arg.equals("all")) {
                    for (User usr : UserNameList.get_instance().print_users()) {
                        writer.println(usr.get_username());
                    }
                }else {
                 //prints all users
                for (User usr : UserNameList.get_instance().print_users(user.get_currentChannel())) {
                    writer.println(usr.get_username());
                    }
                }
                break;
            case "messages":
                //print all messages in format given in toString method
                //deprecated due to need for recognising who asks for history and giving exactly what he had access to
                //writer.println(ChatHistory.get_instance().toString());

                writer.println(ChatHistory.get_instance().print_history(user));
                break;
            case "channel":
                if (arg == null) {
                    writer.println("Please enter channel name");
                }else if(arg.isEmpty()) {
                    writer.println("Channel name cannot be empty");
                }else if(ChannelList.get_instance().check_contains(arg)){
                    user.assign_channel(ChannelList.get_instance().get_channel(arg));
                    user.set_new_login_timestamp();
                }else{
                    writer.println("Channel "+arg+" doesn't exist, do you want to create it?");
                    if(scanner.hasNext("yes|y")){
                        System.out.println("we reached line 0");
                        ChatHistory.get_instance().remove_observer(this,user);
                        ChannelList.get_instance().add_channel(arg);
                        user.assign_channel(ChannelList.get_instance().get_channel(arg));
                        ChatHistory.get_instance().register_observer(this,user);
                        writer.println("Channel "+arg+" created");
                    }
                }
                break;
            case "quit":
                //shut this thing dooooooooooooown
                //but how?
                break;
        }
    }

    private void send_message(String str){
        //if user==null, echo message
        if(user==null){
            writer.println(str);
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
