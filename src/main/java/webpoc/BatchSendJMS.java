package webpoc;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchSendJMS {

	public static void send(ApplicationContext context) {
		ConnectionFactory factory = null;
		Connection conn = null;
		Destination destination = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			destination = (Destination) context.getBean("destination");
			JMSConnectionMgr.createConnection();
			conn = JMSConnectionMgr.getConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = session.createProducer(destination);
			Message message = session.createTextMessage(Thread.currentThread()
					.getId() + "...Hello JMS!");
			producer.send(message);
		} catch (Exception e) {
			String errorMessage = "JMSException while queueing HTTP JMS Message";
			e.printStackTrace();
		} finally {
			try {
				if (producer != null) {
					producer.close();
					producer = null;
				}
			} catch (Exception e) {
			}
			try {
				if (session != null) {
					session.close();
					session = null;
				}
			} catch (Exception e) {
			}
			JMSConnectionMgr.endConnection();
			// System.out.println("send......" +
			// Thread.currentThread().getId());
		}
	}

	public static void main(String[] args) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");
		for (int i = 0; i < 100; i++) {
			new Thread() {
				public void run() {
					while (true) {
						send(context);
						try {
							sleep(10);
						} catch (Exception e) {
						}
					}
				}
			}.start();

		}

	}

}
