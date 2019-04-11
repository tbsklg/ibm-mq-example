package com.tbs_klg.com.control;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;

@Singleton
@Startup
public class MessageReceiver implements MessageListener {
    private static final String QUEUE_NAME = "DEV.QUEUE.2"; // Queue that the application uses to put and get messages to and fromrtup

    @PostConstruct
    public void init() {
        System.out.println("message bean constructed");
        JMSContext context = ListenerConnection.getInstance().getConnectionContext();
        Queue dest = context.createQueue("queue:///" + QUEUE_NAME);
        JMSConsumer consumer = context.createConsumer(dest);
        consumer.setMessageListener(new MessageReceiver());
    }

    public void onMessage(Message message) {
        TaskCache taskCache = TaskCache.getInstance();
        if (message instanceof TextMessage) {
            try {
                String identifier = message.getStringProperty("identifier");
                String uniqueNumber = message.getStringProperty("uniqueNumber");
                taskCache.addTask(identifier, Integer.valueOf(uniqueNumber));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

