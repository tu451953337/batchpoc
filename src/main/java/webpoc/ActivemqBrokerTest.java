package webpoc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class ActivemqBrokerTest {

	public static void main(String[] args) {
		String text = "hello broker";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:/spring/activemq.xml");
		JmsTemplate template = (JmsTemplate) applicationContext
				.getBean("jmsTemplate");
		template.convertAndSend(text);
		System.out.println("send: " + text);
	}
}
