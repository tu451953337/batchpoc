package webpoc;

import javax.jms.Destination;

public interface JMSConsumerService {
	public void receive(Destination destination);
}
