package com.weiquding.netty.learning.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockingIOTimeClient {

    public static void main(String[] args){
        int port = 8080;
        if(args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        try (
                Socket socket = new Socket("127.0.0.1", port);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ){
            writer.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String rsp = reader.readLine();
            System.out.println("Now. is : " + rsp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
