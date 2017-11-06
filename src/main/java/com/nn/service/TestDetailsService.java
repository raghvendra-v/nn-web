package com.nn.service;

import java.util.ArrayList;
import java.util.List;

import com.nn.data.User.User;

public class TestDetailsService implements DetailsService {

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		User u1 = new User();
		u1.setEmail("user1@outlook.com");
		u1.setName("user one");
		users.add(u1);
		User u2 = new User();
		u2.setEmail("user2@gmail.com");
		u2.setName("user two");
		users.add(u2);
		User u3 = new User();
		u3.setEmail("user3@yahoo.com");
		u3.setName("user three");
		users.add(u3);
		return users;
	}

}
