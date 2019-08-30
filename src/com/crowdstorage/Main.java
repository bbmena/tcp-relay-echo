package com.crowdstorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        int relayPort = 8080;
        int selfPort = 4444;
        String host = "localhost";

        if(args.length == 0){
            System.out.println("No ports or host specified. Starting on port 4444. Connecting to relay server at localhost on port 8080");
        } else {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-p")) {
                    i++;
                    relayPort = Integer.parseInt(args[i]);
                }
                if (args[i].equalsIgnoreCase("-h")) {
                    i++;
                    host = args[i];
                }
                if (args[i].equalsIgnoreCase("-s")) {
                    i++;
                    selfPort =Integer.parseInt(args[i]);
                }
            }
        }


        ServerSocket serverSocket = new ServerSocket(selfPort);

        Socket relayListenerSocket = new Socket(host, relayPort);
        System.out.println("Connected to relay at " + host + " on port " + relayPort);
        BufferedReader relayIn = new BufferedReader(new InputStreamReader(relayListenerSocket.getInputStream()));


        while(true) {
            String line;
            while((line = relayIn.readLine())!= null) {
                String[] hostAndPort =  line.split(":");
                Socket newListenerSocket = new Socket(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
                new Thread(new ConnectionHandler(newListenerSocket)).start();
            }
        }
    }
}
