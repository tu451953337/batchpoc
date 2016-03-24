package webpoc;

import javax.jms.Destination;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class ActivemqConsumerTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");


		Destination destination = (Destination) applicationContext
				.getBean("destination");
		
		JMSConsumerService consumer=(JMSConsumerService)applicationContext
				.getBean("consumerService");
		
		consumer.receive(destination);
	}

}
