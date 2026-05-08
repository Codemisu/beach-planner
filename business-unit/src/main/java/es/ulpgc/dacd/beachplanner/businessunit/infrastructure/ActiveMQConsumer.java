package es.ulpgc.dacd.beachplanner.businessunit.infrastructure;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.function.Consumer;

public class ActiveMQConsumer {

    private final String brokerUrl;
    private final String topicName;
    private final Gson gson = new Gson();

    public ActiveMQConsumer(String brokerUrl, String topicName) {
        this.brokerUrl = brokerUrl;
        this.topicName = topicName;
    }

    public void start(Consumer<Event> eventHandler) throws JMSException {

        ActiveMQConnectionFactory factory =
                new ActiveMQConnectionFactory(brokerUrl);

        Connection connection = factory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createTopic(topicName);

        MessageConsumer consumer = session.createConsumer(destination);

        consumer.setMessageListener(message -> {
            try {
                if (message instanceof TextMessage textMessage) {

                    String json = textMessage.getText();

                    Event event = gson.fromJson(json, Event.class);

                    eventHandler.accept(event);
                }

            } catch (Exception e) {
                System.out.println("Error processing ActiveMQ message: " + e.getMessage());
            }
        });

        System.out.println("Listening ActiveMQ topic: " + topicName);
    }
}