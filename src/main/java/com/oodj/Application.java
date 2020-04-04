package com.oodj;

import com.oodj.controller.LoginController;
import com.oodj.model.User;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static User user;

    public static AtomicBoolean running = new AtomicBoolean(false);

    public static void exit(){
        running.set(false);
        System.exit(0);
    }

    private static void start(){
        running.set(true);
        Thread thread = new Thread(() -> {
            while (running.get()){
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                new LoginController();
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        start();
    }


}
