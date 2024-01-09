package com.example.demo.config;

import com.example.demo.service.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;


@Configuration
public class TcpServerConfig {

    @Value("${tcp.server.inbound.port}")
    private int inboundPort;

    @Value("${tcp.server.outbound.port}")
    private int outboundPort;

    @Value("${tcp.server.outbound.host}")
    private String host;
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        LOGGER.info("AbstractServerConnectionFactory");
        TcpNioServerConnectionFactory serverConnectionFactory = new TcpNioServerConnectionFactory(inboundPort);
        serverConnectionFactory.setUsingDirectBuffers(true);
        return serverConnectionFactory;
    }

    @Bean
    public AbstractClientConnectionFactory clientConnectionFactory() {
        LOGGER.info("AbstractServerConnectionFactory2");
        TcpNioClientConnectionFactory clientConnectionFactory = new TcpNioClientConnectionFactory(host,outboundPort);
        clientConnectionFactory.setUsingDirectBuffers(true);
        return clientConnectionFactory;
    }

    @Bean(name = "inboundChannel")
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean(name = "outboundChannel")
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

//    @Bean(name = "outboundChannel2")
//    public MessageChannel outboundChannel2() {
//        return new DirectChannel();
//    }

    @Bean
    public TcpInboundGateway inboundGateway(AbstractServerConnectionFactory serverConnectionFactory,
                                            @Qualifier("inboundChannel") MessageChannel inboundChannel) {
        LOGGER.info("TcpInboundGateway");
        TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(serverConnectionFactory);
        tcpInboundGateway.setRequestChannel(inboundChannel);
//        tcpInboundGateway.setRequestChannelName("inboundChannel");
        return tcpInboundGateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public TcpOutboundGateway outboundGateway(AbstractClientConnectionFactory clientConnectionFactory,
                                              @Qualifier("outboundChannel") MessageChannel outboundChannel) {
        LOGGER.info("outbound");
        TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
        tcpOutboundGateway.setConnectionFactory(clientConnectionFactory);
//        tcpOutboundGateway.setReplyChannelName("TcpClientEndPoint");
        return tcpOutboundGateway;
    }
}
