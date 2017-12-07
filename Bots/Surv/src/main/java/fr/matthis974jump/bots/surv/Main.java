package fr.matthis974jump.bots.surv;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main implements Runnable , EventListener{
    private final String token;
    private final String channel;
    private final String path;
    private JDA jda;
    private final Scanner s = new Scanner(System.in);
    private static Logger  l = Logger.getLogger("MainBot");
    private boolean inRunning;

    public Main(String token, String channel, String pathtomusic) throws Exception {
        this.token = token;
        this.channel = channel;
        this.path = pathtomusic;
        connect();
    }

    public static void main(String[] args){
        try {
            new Thread(new Main("MzgwMjUwNzIzNDE1NDkwNTYz.DQsgWQ.pMxSgywLn2qZgY4mFyIzt2G2aQs","",""),"Main").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void connect() throws Exception{
        // Note: It is important to register your ReadyListener before building
        jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .addEventListener(this)
                .buildBlocking();
        this.inRunning = true;
    }
    public void run() {
        while(inRunning){
            if(s.hasNext()) if(s.nextLine().equalsIgnoreCase("exit")) inRunning = false;
        }
        jda.shutdown();
        s.close();
        System.out.println("ByeBye");
        System.exit(0);
    }

    public void onEvent(Event event) {
        if(event instanceof ReadyEvent) l.info("The System is Ready ! :)");
    }
}
