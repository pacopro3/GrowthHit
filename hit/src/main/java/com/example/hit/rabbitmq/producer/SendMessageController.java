package com.example.hit.rabbitmq.producer;

import com.example.hit.rabbitmq.ConfigureRabbitMq;

import java.util.List;

import com.example.hit.entity.HitRespuesta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@RestController
public class SendMessageController {

    private final RabbitTemplate rabbitTemplate;

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/hit/{id}")
    public ResponseEntity<HitRespuesta> sendMessage(@PathVariable String id, @RequestHeader("accept") String headerString){

        if(headerString.equals("application/json")){
            if(id.matches("[a-zA-Z0-9]*")){
                HitRespuesta h = new HitRespuesta();
                h.setKey(id);
                rabbitTemplate.setReplyTimeout(6000);
                int respuesta = (int)rabbitTemplate.convertSendAndReceive(ConfigureRabbitMq.EXCHANGE_NAME,"Pacoqueue", id);
                h.setHits(respuesta);
                return new ResponseEntity<>(h, HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
            }
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hit")
    public ResponseEntity<Iterable<HitRespuesta>> getData(){
        rabbitTemplate.setReplyTimeout(60000);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String message = (String)rabbitTemplate.convertSendAndReceive(ConfigureRabbitMq.EXCHANGE_NAME,"PacoqueueR",1);
        TypeReference<List<HitRespuesta>> mapType = new TypeReference<List<HitRespuesta>>() {};
        try {
            List<HitRespuesta> jsonToList = objectMapper.readValue(message, mapType);   
            return new ResponseEntity<>(jsonToList,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
    }
}
