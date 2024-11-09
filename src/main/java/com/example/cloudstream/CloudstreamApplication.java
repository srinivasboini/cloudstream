package com.example.cloudstream;

import com.example.avro.User;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

@SpringBootApplication
@RequiredArgsConstructor
public class CloudstreamApplication {

	private final StreamBridge streamBridge;

	public static void main(String[] args) {
		SpringApplication.run(CloudstreamApplication.class, args);
	}


	@EventListener(ApplicationStartedEvent.class)
	public void test(){
		User user = User.newBuilder()
				.setId("1")
				.setName("John Doe")
				.setAge(30)
				.build();
		streamBridge.send("user-out-0", user);

	}

	@Bean
	public Consumer<User> user(){
		return user -> {
			System.out.println(user.toString());
		};
	}

}
