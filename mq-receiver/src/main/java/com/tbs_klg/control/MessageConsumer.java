package com.tbs_klg.control;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.*;
import java.util.concurrent.locks.LockSupport;

@Startup
@Singleton
public class MessageConsumer implements MessageListener {
    private static final String QUEUE_NAME = "DEV.QUEUE.1"; // Queue that the application uses to put and get messages to and from
    private JMSContext context;

    @Inject
    ListenerConnection listenerConnection;

    @Inject
    MessageProducer messageProducer;

    public MessageConsumer() {
    }

    @PostConstruct
    private void init() {
        context = listenerConnection.getConnectionContext();
        Queue dest = context.createQueue("queue:///" + QUEUE_NAME);
        JMSConsumer consumer = context.createConsumer(dest);
        consumer.setMessageListener(this);
    }

    public void onMessage(Message message) {
        try {
            System.out.println("retrieve message: " + message.getStringProperty("identifier"));
            LockSupport.parkNanos(20_000_000_000L);
            messageProducer.sendMsgToQueue(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
