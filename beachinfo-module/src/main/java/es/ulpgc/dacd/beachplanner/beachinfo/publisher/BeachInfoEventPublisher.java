package es.ulpgc.dacd.beachplanner.beachinfo.publisher;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
public class BeachInfoEventPublisher {

    private static final String BROKER_URL = "tcp://localhost:61616";

    public void publish(String topicName, String eventJson) throws Exception {

        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(BROKER_URL);

        Connection connection = factory.createConnection();

        connection.start();

        Session session =
                connection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

        Destination destination =
                session.createTopic(topicName);

        MessageProducer producer =
                session.createProducer(destination);

        TextMessage message =
                session.createTextMessage(eventJson);

        producer.send(message);

        producer.close();
        session.close();
        connection.close();
    }
}