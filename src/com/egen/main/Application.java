package com.egen.main;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.egen.main.rules.Launcher;
import com.mongodb.MongoClient;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Application {

	@Autowired
	Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Datastore datastore() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(environment.getProperty("mongodb.host"),
				Integer.parseInt(environment.getProperty("mongodb.port")));
		Morphia morphia = new Morphia();
		morphia.mapPackage("com.egen.main.entity");
		Datastore datastore = morphia.createDatastore(mongoClient, environment.getProperty("mongodb.database"));
		datastore.ensureIndexes();
		return datastore;
	}

	@Bean
	public Launcher Launcher() {
		return new Launcher(Double.parseDouble(environment.getProperty("base.weight")),
				Boolean.valueOf(environment.getProperty("alert.post")));
	}

}