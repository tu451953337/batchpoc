package webpoc;

import javax.jms.ConnectionFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.activemq.pool.PooledConnectionFactory;

public class JMSConnection {

	private static JMSConnection instance = null;
	private static PooledConnectionFactory factory = null;

	private JMSConnection() {
	}

	public synchronized static JMSConnection getInstance() throws Exception {
		if (instance == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"classpath:/spring/activemq.xml");
			factory = (PooledConnectionFactory) context.getBean("jmsFactory");
			instance = new JMSConnection();
		}
		return instance;
	}

	public static ConnectionFactory getConnection() throws Exception {
		return factory;
	}
}
