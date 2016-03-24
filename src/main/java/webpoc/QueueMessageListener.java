package webpoc;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.MessageListener;

public class QueueMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			// System.out.println("ConsumerMessageListener收到了文本消息：\t"
			// + tm.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
