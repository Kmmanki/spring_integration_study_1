package com.example.demo;

public class TcpReceiveService {
    public void receive(byte[] message) {
        String resultMessage = new String(message);
        System.out.println(resultMessage);
    }
}
