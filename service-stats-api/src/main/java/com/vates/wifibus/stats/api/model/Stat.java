package com.vates.wifibus.stats.api.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;

@DynamoDBTable(tableName = "Users")
@Getter
@Setter
public class Stat {

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	private String id;

	@DynamoDBAttribute
	private String step;

	@DynamoDBAttribute
	private String hostname;

	@DynamoDBAttribute
	private String identity;

	@DynamoDBAttribute
	private String loginBy;

	@DynamoDBAttribute
	private String serverAddress;

	@DynamoDBAttribute
	private String serverName;

	@DynamoDBAttribute
	private String domain;

	@DynamoDBAttribute
	private String interfaceName;

	@DynamoDBAttribute
	private String ip;

	@DynamoDBAttribute
	private String loggedIn;

	@DynamoDBAttribute
	private String mac;

	@DynamoDBAttribute
	private String username;

	@DynamoDBAttribute
	private String hostIp;

}
