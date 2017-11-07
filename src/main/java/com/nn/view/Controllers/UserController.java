package com.nn.view.Controllers;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nn.entities.User;
import com.nn.entities.repository.UserRespository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRespository repository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Optional<User> getUser(@PathVariable Long id) {
		return repository.findById(id);
	}
}
