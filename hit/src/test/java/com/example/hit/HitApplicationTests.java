package com.example.hit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class HitApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void TestCorrecto()  throws ClientProtocolException, IOException {
 
		// Given
		String jsonMimeType = "application/json";
		String name = RandomStringUtils.randomAlphabetic( 5 );
		HttpUriRequest request = new HttpGet( "http://localhost:8080/hit/" + name);
		request.addHeader("accept",jsonMimeType);
		// When
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
	
		// Then
		
		assertThat(httpResponse.getStatusLine().getStatusCode(),equalTo(HttpStatus.SC_ACCEPTED));
	}
}

