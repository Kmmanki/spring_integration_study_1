package com.example.demo.endpoint;

import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
public class TcpServerEndpoint {

    private final MessageService messageService;

    @Autowired
    public TcpServerEndpoint(MessageService messageService) {
        this.messageService = messageService;
    }

    @ServiceActivator(inputChannel = "inboundChannel",outputChannel = "outboundChannel")
    public byte[] process(byte[] message) {
        return messageService.processReceiveMessage(message);
    }

}