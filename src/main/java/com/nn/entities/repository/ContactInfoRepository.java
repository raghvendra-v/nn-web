package com.nn.entities.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nn.entities.ContactInfo;

@RepositoryRestResource(collectionResourceRel = "contactinfo", path = "contactinfo")
public interface ContactInfoRepository extends CrudRepository<ContactInfo, Long> {
	List<ContactInfo> findByTelephone(String telephone);
}