package com.weiquding.netty.learning.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimeServerHandler implements Runnable {

    private final Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        // try resource
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                PrintWriter writer = new PrintWriter(this.socket.getOutputStream(), true);
        ) {
            String currentTime = null;
            String body = null;
            while (true) {
                body = reader.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("The time server receive order:" + body);
                currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                writer.println(currentTime);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}