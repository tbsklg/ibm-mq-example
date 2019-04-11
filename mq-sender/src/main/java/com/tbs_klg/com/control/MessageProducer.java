package com.tbs_klg.com.control;

import javax.ejb.Singleton;
import javax.jms.*;

@Singleton
public class MessageProducer {
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

    public MessageProducer() {
    }

    public void sendMessage(String identifier) {
        JMSContext context = ListenerConnection.getInstance().getConnectionContext();

        Message message = context.createMessage();
        try {
            System.out.println("identifier: " + identifier);
            message.setStringProperty("identifier", identifier);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        Queue destination = context.createQueue("queue:///" + QUEUE_NAME);
        JMSProducer producer = context.createProducer();
        System.out.println("message sent");
        producer.send(destination, message);
    }
}
