package com.example.fileupload.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.fileupload.dto.Gender;
import com.example.fileupload.dto.Person;
import com.example.fileupload.jpa.PersonEntity;
import com.example.fileupload.jpa.IPersonRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class CsvProcessServiceTest {

  @Mock
  private IPersonRepository mockRepository;

  private CsvProcessService csvProcessServiceUnderTest;

  @BeforeEach
  void setUp() {
    csvProcessServiceUnderTest = new CsvProcessService(mockRepository);
  }

  @Test
  void testProcessCsv() throws Exception {
    // Setup
    final MultipartFile file = new MockMultipartFile("name", "name,age,MALE".getBytes());
    final List<Person> expectedResult = List.of(new Person("name", "age", Gender.MALE));
    final List<PersonEntity> resultPersonEntities =
        List.of(new PersonEntity(1, "name", "age", Gender.MALE));

    // Configure PersonRepository.saveAllAndFlush(...).
    when(mockRepository.saveAllAndFlush(List.of(getPersonEntity())))
        .thenReturn(resultPersonEntities);

    // Run the test
    final List<Person> result = csvProcessServiceUnderTest.processCsv(file);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  private static PersonEntity getPersonEntity() {
    final var personEntity = new PersonEntity();
    personEntity.setName("name");
    personEntity.setAge("age");
    personEntity.setGender(Gender.MALE);
    return personEntity;
  }

  @Test
  void testProcessCsv_PersonRepositoryReturnsNoItems() throws Exception {
    // Setup

    final MultipartFile file = new MockMultipartFile("name", "name,age,MALE".getBytes());
    when(mockRepository.saveAllAndFlush(List.of(getPersonEntity())))
        .thenReturn(Collections.emptyList());

    // Run the test
    final List<Person> result = csvProcessServiceUnderTest.processCsv(file);

    // Verify the results
    assertEquals(Collections.emptyList(), result);
  }
}
