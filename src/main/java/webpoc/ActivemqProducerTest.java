package webpoc;

import javax.jms.Destination;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivemqProducerTest {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");

		Destination destination = (Destination) applicationContext
				.getBean("destination");

		JMSProducerService producer = (JMSProducerService) applicationContext
				.getBean("producerService");

		for (int i = 0; i < 5; i++) {
			producer.sendMessage(destination, "Hello China~~~~~~~~~~~~~~~");
		}
	}

}
