//package com.ibm.demo.config;
//
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//@EnableKafka
//@Configuration
//@RequiredArgsConstructor
//public class KafkaConsumerConfig {
//
//	@Autowired
//    private final KafkaProperties kafkaProperties;
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//		ConcurrentKafkaListenerContainerFactory<String, Object> factory =
//				new ConcurrentKafkaListenerContainerFactory<>();
//		factory.setConsumerFactory(consumerFactory());
//
//		return factory;
//	}
//
//	@Bean
//	public ConsumerFactory<String, Object> consumerFactory() {
//		final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
//		jsonDeserializer.addTrustedPackages("*");
//
//		return new DefaultKafkaConsumerFactory<>(
//				kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer
//		);
//	}
//
//}