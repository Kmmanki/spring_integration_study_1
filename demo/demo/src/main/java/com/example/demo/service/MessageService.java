package com.example.demo.service;

public interface MessageService {
    byte[] processReceiveMessage(byte[] message);
    byte[] processSendMessage(byte[] message);
}
