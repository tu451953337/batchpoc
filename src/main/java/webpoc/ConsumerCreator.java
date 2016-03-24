package webpoc;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerCreator {

	private ApplicationContext context = null;

	public ConsumerCreator(ApplicationContext context) {
		this.context = context;
	}

	public void create() {
		Destination destination = null;
		ConnectionFactory factory = null;
		Connection conn = null;
		Session session = null;
		try {

			destination = (Destination) context.getBean("destination");
			JMSConnectionMgr.createConnection();
			conn = JMSConnectionMgr.getConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// 消息消费者
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message m) {
					try {
						TextMessage message = (TextMessage) m;
						// message.getText();
						// System.out.println("receive......" +
						// message.getText());
						System.out.println("onconsummer--> "
								+ message.getText());
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
					}
				}
			});
		} catch (Exception e) {

		}
	}
}
