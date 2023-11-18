package com.example.mail1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.mail1.model.Register;


@Repository
public interface RegisterRepository extends JpaRepository <Register,Integer> {

public Register findByVerificationcode(String code);
public Register findByEmail(String email);
}
