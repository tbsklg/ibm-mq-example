package com.tbs_klg.control;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class MessageProducer {
    private static final String QUEUE_NAME = "DEV.QUEUE.2"; // Queue that the application uses to put and get messages to and from

    @Inject
    ListenerConnection listenerConnection;

    public void sendMsgToQueue(Message msg) throws JMSException {
        JMSContext context = listenerConnection.getConnectionContext();

        long uniqueNumber = System.currentTimeMillis() % 1000;
        TextMessage message = context.createTextMessage("Your lucky number today is " + uniqueNumber);
        message.setStringProperty("identifier", msg.getStringProperty("identifier"));
        message.setStringProperty("uniqueNumber", String.valueOf(uniqueNumber));

        Queue destination = context.createQueue("queue:///" + QUEUE_NAME);
        JMSProducer producer = context.createProducer();
        producer.send(destination, message);
    }
}
