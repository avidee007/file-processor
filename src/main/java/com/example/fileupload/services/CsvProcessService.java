package com.example.fileupload.services;

import com.example.fileupload.dto.Gender;
import com.example.fileupload.dto.Person;
import com.example.fileupload.jpa.PersonEntity;
import com.example.fileupload.jpa.PersonEntityAssembler;
import com.example.fileupload.jpa.IPersonRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvProcessService {
  private final IPersonRepository repository;

  public List<Person> processCsv(MultipartFile file) throws IOException {
    log.info("csv processing started.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),
        StandardCharsets.UTF_8));
    List<PersonEntity> persons = getPersonEntities(reader);
    return repository.saveAllAndFlush(persons)
        .stream()
        .map(PersonEntityAssembler::fromEntity)
        .toList();
  }

  private static List<PersonEntity> getPersonEntities(BufferedReader reader) {
    log.info("Buffered reader started");
    return reader.lines()
        .map(CsvProcessService::getEntity)
        .toList();
  }

  private static PersonEntity getEntity(String e) {
    log.info("Reading line {}", e);
    String[] data = e.split(",");
    Person p = new Person(data[0].trim(), data[1].trim(), Gender.valueOf(data[2].trim()));
    log.info("Person domain object created : {}", p);
    return PersonEntityAssembler.toEntity(p);
  }
}




