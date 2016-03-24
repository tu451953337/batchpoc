package webpoc;

import javax.jms.Destination;

public interface JMSProducerService {
	public void sendMessage(Destination destination, final String msg);
}
