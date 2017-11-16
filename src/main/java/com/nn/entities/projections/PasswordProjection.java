package com.nn.entities.projections;

import org.springframework.data.rest.core.config.Projection;

import com.nn.entities.User;

@Projection(name = "passwords", types = { User.class })
interface PasswordProjection {
  String getPassword();
}

