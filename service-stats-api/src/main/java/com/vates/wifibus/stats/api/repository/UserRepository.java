package com.vates.wifibus.stats.api.repository;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.vates.wifibus.stats.api.model.User;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
	List<User> findByLastName(String lastName);
}
