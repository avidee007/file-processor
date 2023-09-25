package com.example.fileupload.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import com.example.fileupload.dto.Gender;
import com.example.fileupload.dto.Person;
import java.lang.reflect.Constructor;
import org.junit.jupiter.api.Test;

class PersonEntityAssemblerTest {

  @Test
  void testPrivateConstructor() throws NoSuchMethodException {
    Constructor<PersonEntityAssembler> constructor =
        PersonEntityAssembler.class.getDeclaredConstructor();
    assertThrows(IllegalAccessException.class, constructor::newInstance);
  }


  @Test
  void testToEntity() {
    // Setup
    final Person person = new Person("name", "age", Gender.FEMALE);
    final PersonEntity entity = new PersonEntity(1, "name", "age", Gender.FEMALE);

    // Run the test
    final PersonEntity result = PersonEntityAssembler.toEntity(person);

    // Verify the results
    assertEquals(result.getName(), entity.getName());
    assertEquals(result.getAge(), entity.getAge());
    assertEquals(result.getGender(), entity.getGender());
  }

  @Test
  void testFromEntity() {
    // Setup
    final PersonEntity entity = new PersonEntity(1, "name", "age", Gender.OTHER);
    final Person expectedResult = new Person("name", "age", Gender.OTHER);

    // Run the test
    final Person result = PersonEntityAssembler.fromEntity(entity);

    // Verify the results
    assertEquals(expectedResult.name(), result.name());
    assertEquals(expectedResult.age(), result.age());
    assertEquals(expectedResult.gender(), result.gender());
  }
}
