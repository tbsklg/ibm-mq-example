package com.tbs_klg.com.control;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSContext;
import javax.jms.JMSException;

public class ListenerConnection {
    private static final String HOST = "ibm-mq"; // Host name or IP address
    private static final int PORT = 1414; // Listener port for your queue manager
    private static final String QMGR = "QM1"; // Queue manager name
    private static final String APP_USER = "app"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "mysecretpassword"; // Password that the application uses to connect to MQ
    private static final String CHANNEL = "DEV.APP.SVRCONN";
    private static ListenerConnection listenerConnection = new ListenerConnection();

    public static ListenerConnection getInstance() {
        return listenerConnection;
    }

    public JMSContext getConnectionContext() {
        JmsConnectionFactory cf = null;
        try {
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            cf = ff.createConnectionFactory();
            setProperties(cf);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return cf.createContext(JMSContext.AUTO_ACKNOWLEDGE);
    }

    private void setProperties(JmsConnectionFactory cf) throws JMSException {
        cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
        cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
        cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
        cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        cf.setStringProperty(WMQConstants.USERID, APP_USER);
        cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
    }
}
