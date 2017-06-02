package com.vates.wifibus.stats.api;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vates.wifibus.stats.api.model.User;
import com.vates.wifibus.stats.api.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceStatsApiApplicationTests {

	@Autowired
	UserRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	  public void sampleTestCase() {
	    User dave = new User("Dave", "Matthews");
	    repository.save(dave);
	         
	    User carter = new User("Carter", "Beauford");
	    repository.save(carter);
	         
	    List<User> result = repository.findByLastName("Matthews");
	    assertThat(result.size(), is(1));
	    assertThat(result, hasItem(dave));
	  }

}
