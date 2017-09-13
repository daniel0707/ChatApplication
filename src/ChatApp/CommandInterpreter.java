package ChatApp;

import java.io.*;
import java.util.Scanner;
//      TO DO
//WTF TO USE AS INPUT AND OUTPUT READERS?!
//
public class CommandInterpreter implements ChatObserver,Runnable{
    private InputStream input;
    private OutputStream output;
    private boolean quit = false;
    Scanner scanner;
    PrintStream writer;
    User user = null;

    public CommandInterpreter(InputStream in, OutputStream out){
        this.input = in;
        this.output = out;
        this.scanner = new Scanner(in);
        this.writer = new PrintStream(out);
    }

    public void run(){
        while(!quit){
            String input = scanner.hasNext()? scanner.nextLine() : "";
            decide_input_type(input);
        }
    }

    private void decide_input_type(String str){
        if(!str.isEmpty()) {
            switch (str.charAt(0)) {
                case ':':
                    String[] split = str.split("\\s+");
                    String cmd = split[0], arg = split[1];
                    run_command(cmd, arg);
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
                //assigns a username or says its already used
                if(!UserNameList.get_instance().check_contains(new User(arg))){
                    user = new User(arg);
                    UserNameList.get_instance().insert_user_name(user);
                    writer.println("User "+arg+" has been registered");
                }else{
                    writer.println("User already exists");
                }
                break;
            case "userlist":
                //prints all users
                for(User usr:UserNameList.get_instance().print_users()){
                    writer.println(usr.get_username());
                }
                break;
            case "messages":
                //print all messages in format given in toString method
                writer.println(ChatHistory.get_instance().toString());
                break;
            case "quit":
                //shut this thing dooooooooooooown
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
        writer.println(msg.toString());
    }
}
