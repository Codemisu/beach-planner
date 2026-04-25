package es.ulpgc.beachplanner.weather.infrastructure;

import com.google.gson.Gson;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class WeatherPublisher {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";

    private final Gson gson = new Gson();

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public WeatherPublisher() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);
            producer = session.createProducer(topic);

        } catch (Exception e) {
            throw new RuntimeException("Error initializing ActiveMQ publisher", e);
        }
    }

    public void publish(Event event) {
        try {
            String json = gson.toJson(event);
            TextMessage message = session.createTextMessage(json);

            producer.send(message);

            System.out.println("Evento enviado a ActiveMQ:");
            System.out.println(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (producer != null) producer.close();
            if (session != null) session.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}