package com.example.hit.rabbitmq;

import com.example.hit.rabbitmq.consumer.ReceiveMessageHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMq {

    public static final String EXCHANGE_NAME = "Pacoexchange";
    public static final String QUEUE_NAME = "Pacoqueue";
    public static final String QUEUE_NAME1 = "PacoqueueR";


    @Bean
    Queue createQueue() {
        return new Queue(QUEUE_NAME, true, false, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(TopicExchange exchange){
        return BindingBuilder.bind(createQueue()).to(exchange).with(createQueue().getName());
    }
    
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory
            , ReceiveMessageHandler handler){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(new MessageListenerAdapter(handler,"handleMessage"));
        return container;
    }
    
    @Bean
    Queue createQueue1() {
        return new Queue(QUEUE_NAME1, true, false, false);
    }

    @Bean
    Binding binding1(TopicExchange exchange){
        return BindingBuilder.bind(createQueue1()).to(exchange).with(createQueue1().getName());
    }

    @Bean
    SimpleMessageListenerContainer container1(ConnectionFactory connectionFactory
            , ReceiveMessageHandler handler1){
        SimpleMessageListenerContainer container1 = new SimpleMessageListenerContainer();
        container1.setConnectionFactory(connectionFactory);
        container1.setQueueNames(QUEUE_NAME1);
        container1.setMessageListener(new MessageListenerAdapter(handler1,"fetchAll"));
        return container1;
    }

}
