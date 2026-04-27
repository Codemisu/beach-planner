package es.ulpgc.beachplanner.eventstorebuilder.app;

import es.ulpgc.beachplanner.eventstorebuilder.infrastructure.EventStoreWriter;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventStoreBuilder {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String CLIENT_ID = "event-store-builder";
    private static final String SUBSCRIPTION_NAME = "weather-events-subscription";

    private final String topicName;
    private final EventStoreWriter writer;

    public EventStoreBuilder(String topicName, EventStoreWriter writer) {
        this.topicName = topicName;
        this.writer = writer;
    }

    public void run() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            Connection connection = factory.createConnection();

            connection.setClientID(CLIENT_ID);

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(topicName);

            MessageConsumer consumer = session.createDurableSubscriber(topic, SUBSCRIPTION_NAME);

            consumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage textMessage) {
                        String eventJson = textMessage.getText();
                        writer.write(topicName, eventJson);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            connection.start();

            System.out.println("Event Store Builder listening to topic: " + topicName);
            System.out.println("Press ENTER to stop...");
            System.in.read();

            consumer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            throw new RuntimeException("Error running Event Store Builder", e);
        }
    }
}