package com.example.fileupload;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fileupload.controller.FileUploadController;
import com.example.fileupload.dto.Gender;
import com.example.fileupload.dto.Person;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class FileUploadApplicationTests {

  /*@Container
  public static PostgreSQLContainer<?> postres = new PostgreSQLContainer(PostgreSQLContainer.IMAGE)
      .withDatabaseName("test")
      .withUsername("username")
      .withPassword("password");

  static void config(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", () -> postres.getJdbcUrl());
    registry.add("spring.datasource.username", () -> postres.getUsername());
    registry.add("spring.datasource.password", () -> postres.getPassword());
  }*/

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  DataSource dataSource;

  @Test
  void loadContext() {
    Assertions.assertNotNull(dataSource);

  }

  @Test
  void testUploadFileApi() throws Exception {
    MockMultipartFile file =
        new MockMultipartFile("file", "name,age,MALE".getBytes(StandardCharsets.UTF_8));

    MockMvc mockMvc
        = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(multipart("/app/upload").file(file))
        .andExpect(status().isOk());
    //.andExpect(model().hasNoErrors(List.of(new Person("name", "age", Gender.MALE))));
  }


}
