package com.example.hit.rabbitmq.producer;

import com.example.hit.rabbitmq.ConfigureRabbitMq;

import com.example.hit.entity.HitRespuesta;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


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
                int respuesta = (int)rabbitTemplate.convertSendAndReceive(ConfigureRabbitMq.EXCHANGE_NAME,"paco.springmessages", id);
                h.setHits(respuesta);
                return new ResponseEntity<>(h, HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
            }
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*@GetMapping("/hit")
    public List<HitRespuesta> getData(){
        rabbitTemplate.setReplyTimeout(60000);
        List<HitRespuesta> respuesta = (List<HitRespuesta>)rabbitTemplate.convertSendAndReceive(ConfigureRabbitMq.EXCHANGE_NAME,"paco.springmessages1",1);
        return respuesta;
    }*/
}
