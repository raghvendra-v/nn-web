package com.nn.view.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nn.data.User.User;
import com.nn.service.DetailsService;
import com.nn.service.TestDetailsService;

@RestController
public class UserController {

	DetailsService svc;
	{
		//TODO spring injection
		svc = new TestDetailsService();
	}

	@RequestMapping(value = "/api/list/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return svc.getUsers();
	}
	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public List<User> getUser(@PathVariable long userid) {
		return svc.getUsers();
	}
	
}
