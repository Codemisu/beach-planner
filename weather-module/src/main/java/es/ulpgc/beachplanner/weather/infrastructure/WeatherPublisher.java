package es.ulpgc.beachplanner.weather.infrastructure;


import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import es.ulpgc.dacd.beachplanner.common.model.Event;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import java.util.Map;

public class WeatherPublisher {

    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "Weather";

    private final Gson gson = new Gson();


    public void publish(Event event) throws Exception {
        ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;

        try {
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(TOPIC_NAME);
            producer = session.createProducer(topic);

            String json = gson.toJson(event);
            TextMessage message = session.createTextMessage(json);

            producer.send(message);

            System.out.println("Evento enviado a ActiveMQ:");
            System.out.println(json);

        } finally {
            if (producer != null) {
                producer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}