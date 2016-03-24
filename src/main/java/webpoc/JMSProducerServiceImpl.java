package webpoc;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSProducerServiceImpl implements JMSProducerService {

	private JmsTemplate jmsTemplate;

	/**
	 * 向指定队列发送消息
	 */
	public void sendMessage(Destination destination, final String msg) {

		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});
	}

	/**
	 * 向默认队列发送消息
	 */
	public void sendMessage(final String msg) {
		String destination = jmsTemplate.getDefaultDestination().toString();
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(msg);
			}
		});

	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
