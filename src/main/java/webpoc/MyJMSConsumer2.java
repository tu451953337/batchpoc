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

public class MyJMSConsumer2 {
	private boolean stop = false;

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");
		for (int i = 0; i < 1; i++) {
			ConsumerCreator creator = new ConsumerCreator(context);
			creator.create();
		}
	}

}
