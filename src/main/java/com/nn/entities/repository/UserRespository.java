package com.nn.entities.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nn.entities.User;

public interface UserRespository extends CrudRepository<User, Long> {
	List<User> findByEmail(String email);
}
/*
*@RepositoryRestResource(collectionResourceRel = "user", path = "user")
*/