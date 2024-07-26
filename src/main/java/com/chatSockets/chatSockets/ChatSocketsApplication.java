package com.chatSockets.chatSockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class ChatSocketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatSocketsApplication.class, args);
		log.info("Inicia WS chat");
	}

}
