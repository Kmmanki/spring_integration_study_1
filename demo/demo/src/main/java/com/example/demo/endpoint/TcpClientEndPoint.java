//package com.example.demo.endpoint;
//
//import com.example.demo.service.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.annotation.MessageEndpoint;
//import org.springframework.integration.annotation.ServiceActivator;
//
////@MessageEndpoint
////public class TcpClientEndPoint {
////    private final MessageService messageService;
////    @Autowired
////    public TcpClientEndPoint(MessageService messageService) {
////        this.messageService = messageService;
////    }
////
////    @ServiceActivator(inputChannel = "outboundChannel")
////    public byte[] process(byte[] message){
////        return messageService.processSendMessage(message);
////    }
////}
