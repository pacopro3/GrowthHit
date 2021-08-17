package com.example.hit.rabbitmq.consumer;

import org.springframework.stereotype.Service;

import java.util.Optional;

import com.example.hit.entity.HitEntity;
import com.example.hit.interf.HitInterface;
import org.springframework.beans.factory.annotation.Autowired;

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

}
