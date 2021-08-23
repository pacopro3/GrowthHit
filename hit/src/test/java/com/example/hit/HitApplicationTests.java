package com.example.hit;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.hit.entity.HitEntity;
import com.example.hit.entity.HitRespuesta;
import com.example.hit.interf.HitInterface;
import com.example.hit.rabbitmq.consumer.ReceiveMessageHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class HitApplicationTests {

	@Autowired
	HitInterface interface1;



	@Test
	public void TestEntity(){
		HitEntity entity = new HitEntity();
		entity.setId(1);
		entity.setHits("test");
		entity.setCount(3);
		assertEquals(1, entity.getId());
		assertEquals("test", entity.getHits());
		assertEquals(3, entity.getCount());
	}

	@Test
	public void TestRespuesta(){
		HitRespuesta respuesta = new HitRespuesta();
		respuesta.setKey("test1");
		respuesta.setHits(5);
		assertEquals("test1", respuesta.getKey());
		assertEquals(5, respuesta.getHits());
	}

	@Test
	public void ReceiveMessageHandlerTest(){
		ReceiveMessageHandler msghandler;
		msghandler = new ReceiveMessageHandler(interface1);
		String name = "test2";
		assertEquals(1, msghandler.handleMessage(name));
		assertEquals(2, msghandler.handleMessage(name));
	}

	@Test
	public void ReceiveMessageHandlerTest1(){
		ReceiveMessageHandler msghandler;
		msghandler = new ReceiveMessageHandler(interface1);
		String jsonbody = msghandler.fetchAll(1);
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String arrayToJson = "";
		List<HitRespuesta> lista = new ArrayList<HitRespuesta>();
        HitRespuesta temp = new HitRespuesta();
		temp.setKey("test2");
		temp.setHits(2);
		lista.add(temp);
		try {
            arrayToJson = objectMapper.writeValueAsString(lista);
			assertEquals(arrayToJson,jsonbody);
	} catch (JsonProcessingException e) {
		e.printStackTrace();
		assertNotEquals(arrayToJson, jsonbody);
	}
	}
}
