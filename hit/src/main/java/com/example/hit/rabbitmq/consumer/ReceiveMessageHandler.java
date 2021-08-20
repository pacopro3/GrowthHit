package com.example.hit.rabbitmq.consumer;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.hit.entity.HitEntity;
import com.example.hit.entity.HitRespuesta;
import com.example.hit.interf.HitInterface;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class ReceiveMessageHandler{

    @Autowired
    private HitInterface hitInterface;

    public ReceiveMessageHandler(HitInterface hitInterface){
        this.hitInterface = hitInterface;
    }

    public int handleMessage(String messageBody){
        System.out.println("Mensaje recibido");
        HitEntity entidad = new HitEntity();
        entidad.setHits(messageBody);
        entidad.setCount(0);
        Iterable<HitEntity> optional = hitInterface.findAll();
        for(HitEntity data:optional){
            if(data.getHits().equals(messageBody)){
                Optional<HitEntity> opt = hitInterface.findById(data.getId());
                entidad = opt.get();
                break;
            }
        }
        entidad.setCount(entidad.getCount() + 1);
        hitInterface.save(entidad);
        return entidad.getCount();
    }

    public String fetchAll(int messageBody){
        System.out.println("Mensaje sector 2 recibido");
        List<HitRespuesta> lista = new ArrayList<HitRespuesta>();
        Iterable<HitEntity> optional = hitInterface.findAll();
        for(HitEntity data:optional){
            HitRespuesta temp = new HitRespuesta();
            temp.setKey(data.getHits());
            temp.setHits(data.getCount());
            lista.add(temp);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //Set pretty printing of json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson;
        try {
            arrayToJson = objectMapper.writeValueAsString(lista);
            return arrayToJson;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

}
