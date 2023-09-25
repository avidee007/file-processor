package com.example.fileupload.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<PersonEntity, Integer> {
}
