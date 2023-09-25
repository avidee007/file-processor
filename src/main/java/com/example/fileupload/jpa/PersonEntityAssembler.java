package com.example.fileupload.jpa;

import com.example.fileupload.dto.Person;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PersonEntityAssembler {

  private PersonEntityAssembler() throws IllegalAccessException {
    throw new IllegalAccessException("Assembler class");
  }

  public static PersonEntity toEntity(Person person) {
    PersonEntity personEntity = new PersonEntity();
    personEntity.setName(person.name());
    personEntity.setAge(person.age());
    personEntity.setGender(person.gender());
    log.info("PersonEntity created :{}", personEntity);
    return personEntity;
  }

  public static Person fromEntity(PersonEntity entity) {
    Person person = new Person(entity.getName(), entity.getAge(), entity.getGender());
    log.info("Person from person entity: {}", person);
    return person;
  }
}
