package es.ulpgc.beachplanner.weather.infrastructure;

import com.google.gson.Gson;
import es.ulpgc.beachplanner.weather.app.WeatherEventBuilder;
import es.ulpgc.beachplanner.weather.app.WeatherEventPublisher;
import es.ulpgc.beachplanner.weather.model.WeatherRecord;
import es.ulpgc.dacd.beachplanner.common.model.Event;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class WeatherPublisher implements WeatherEventPublisher {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";

    private final Gson gson = new Gson();
    private final WeatherEventBuilder eventBuilder = new WeatherEventBuilder();

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

    @Override
    public void publish(WeatherRecord record) {
        try {
            Event event = eventBuilder.build(record);
            String json = gson.toJson(event);
            TextMessage message = session.createTextMessage(json);

            producer.send(message);

            System.out.println("Evento enviado a ActiveMQ:");
            System.out.println(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
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