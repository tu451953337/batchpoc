package webpoc;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;

public class JMSConsumerServiceImpl implements JMSConsumerService {
	private JmsTemplate jmsTemplate;

	@Override
	public void receive(Destination destination) {
		TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
		try {
			System.out.println("从队列" + destination.toString() + "收到了消息：\t"
					+ tm.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
