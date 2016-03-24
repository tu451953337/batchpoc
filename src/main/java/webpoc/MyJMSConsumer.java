package webpoc;

import javax.jms.Connection;
import org.apache.activemq.pool.PooledConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyJMSConsumer {
	private boolean stop = false;

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");
		PooledConnectionFactory factory = (PooledConnectionFactory) context
				.getBean("jmsFactory");

		Connection conn = factory.createConnection();
		conn.start();

		Destination destination = (Destination) context.getBean("destination");

		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 消息消费者
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					TextMessage message = (TextMessage) m;
					System.out.println("receive......" + message.getText());
					// System.out.println("onconsummer--> " +
					// message.getText());
				} catch (Exception e) {
					e.printStackTrace();
				} finally {

				}
			}
		});

	}

}
