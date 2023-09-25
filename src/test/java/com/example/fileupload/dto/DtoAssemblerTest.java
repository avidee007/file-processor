package com.example.fileupload.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class DtoAssemblerTest {

  @Test
  void testToDto() {
    // Setup
    Person person = new Person("name", "age", Gender.MALE);
    final ResponseModel expectedResult = new ResponseModel(List.of(person));

    // Run the test
    final ResponseModel result = DtoAssembler.toDto(List.of(person));

    // Verify the results
    assertEquals(expectedResult, result);
  }
}
