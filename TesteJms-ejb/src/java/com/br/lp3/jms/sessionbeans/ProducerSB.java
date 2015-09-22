package com.br.lp3.jms.sessionbeans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author 31427782
 */
@Stateless
public class ProducerSB implements ProducerSBLocal {
    @Resource(mappedName = "teste_JNDI")
    private Queue teste_JNDI;
    @Resource(mappedName = "testeFactory")
    private ConnectionFactory testeFactory;

    private Message createJMSMessageForteste_JNDI(Session session, Object messageData) throws JMSException {
        // TODO create and populate message to send
        TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }
    
    @Override
    public void enviarMensagem(String msg) throws JMSException{
        sendJMSMessageToTeste_JNDI(msg);
    }

    private void sendJMSMessageToTeste_JNDI(Object messageData) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            connection = testeFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(teste_JNDI);
            messageProducer.send(createJMSMessageForteste_JNDI(session, messageData));
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    
}
