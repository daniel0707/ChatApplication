package ChatApp;

import java.io.*;
import java.util.Scanner;

public class CommandInterpreter implements ChatObserver,Runnable{
    private InputStream input;
    private OutputStream output;
    private boolean quit = false;
    Scanner scanner;
    PrintWriter writer;
    User user = null;

    public CommandInterpreter(InputStream in, OutputStream out){
        this.input = in;
        this.output = out;
        this.scanner = new Scanner(in);
        this.writer = new PrintWriter(out);
    }

    public void run(){
        while(!quit){
            String input = scanner.nextLine();
            decide_input_type(input);
        }
    }

    private void decide_input_type(String str){
        switch(str.charAt(0)) {
            case ':':
                String[] split = str.split("\\s+");
                String cmd = split[0], arg=split[1];
                run_command(cmd,arg);
                break;
            default:
                send_message(str);
                break;
        }
    }

    private void run_command(String cmd, String arg){
        switch (cmd){
            case "user":

                if(UserNameList.get_instance().check_contains(new User(arg))){
                    user = new User(arg);
                    UserNameList.get_instance().insert_user_name(user);
                    writer.println("User "+arg+" has been registered");
                }else{
                    writer.println("User already exists");
                }
                break;
            case "userlist":
                //print all users
                for(User usr:UserNameList.get_instance().print_users()){
                    writer.println(usr.get_username());
                }
                break;
            case "messages":
                //print all messages in format Name : MSG @ timestamp
                break;
            case "quit":
                //shut this thing dooooooooooooown
                break;
        }
    }

    private void send_message(String str){
        //if user==null, echo message
        ChatMessage msg = new ChatMessage(str,user);
        writer.println(msg.toString());
    }

    @Override
    public void update(ChatMessage msg) {
        writer.println(msg.toString());
    }
}
