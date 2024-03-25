package com.example.demo.service.impl;

import com.example.demo.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Override
    public byte[] processReceiveMessage(byte[] message) {
        String messageContent = new String(message);
        LOGGER.info("Receive message: {}", messageContent);
        String responseContent = String.format("%s > 2", messageContent);
        return responseContent.getBytes();
    }
    @Override
    public byte[] processSendMessage(byte[] message) {
        String messageContent = new String(message);
        LOGGER.info("Send message: {}", messageContent);
        String responseContent = String.format("%s > 4", messageContent);
        return responseContent.getBytes();
    }

}
