package com.crowdstorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket echo;

    public ConnectionHandler(Socket echo){
        this.echo = echo;
    }

    public void run() {

        try {
            BufferedReader echoIn = new BufferedReader(new InputStreamReader(echo.getInputStream()));
            PrintWriter echoOut = new PrintWriter(echo.getOutputStream(), true);

            while(true) {
                String line;
                while((line = echoIn.readLine())!= null) {
                    echoOut.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
