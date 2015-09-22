package com.br.lp3.jms.sessionbeans;

import javax.ejb.Local;
import javax.jms.JMSException;

/**
 *
 * @author 31427782
 */
@Local
public interface ProducerSBLocal {
    
        public void enviarMensagem(String msg) throws JMSException;

    
}
