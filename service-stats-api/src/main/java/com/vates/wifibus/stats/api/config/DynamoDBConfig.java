package com.vates.wifibus.stats.api.config;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.vates.wifibus.stats.api.repository")
public class DynamoDBConfig {

	private static final Logger logger = LoggerFactory.getLogger(DynamoDBConfig.class);

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {

		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

		if (StringUtils.isNotEmpty(amazonDynamoDBEndpoint)) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}

		return amazonDynamoDB;

		// AmazonDynamoDB dynamodb = null;
		// try {
		//
		// // Create an in-memory and in-process instance of DynamoDB Local that
		// skips HTTP
		// dynamodb = DynamoDBEmbedded.create().amazonDynamoDB();
		//
		// // use the DynamoDB API with DynamoDBEmbedded
		// listTables(dynamodb.listTables(), "DynamoDB Embedded");
		//
		// } catch(Exception e) {
		//
		// logger.error("Error starting local instance of DynamoDB", e);
		//
		// // Shutdown the thread pools in DynamoDB Local / Embedded
		// if (dynamodb != null) {
		// dynamodb.shutdown();
		// }
		// }
		//
		// return dynamodb;

	}

	@PreDestroy
	public void amazonDynamoDBShutdown() {
		AmazonDynamoDB db = amazonDynamoDB();

		db.shutdown();
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}

	public void listTables(ListTablesResult result, String method) {

		System.out.println("found " + Integer.toString(result.getTableNames().size()) + " tables with " + method);
		for (String table : result.getTableNames()) {
			System.out.println(table);
		}

	}

}
