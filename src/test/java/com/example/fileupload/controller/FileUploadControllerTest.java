package com.example.fileupload.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.fileupload.dto.Gender;
import com.example.fileupload.dto.Person;
import com.example.fileupload.dto.ResponseModel;
import com.example.fileupload.services.CsvProcessService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

class FileUploadControllerTest {

  @Mock
  private CsvProcessService mockCsvProcessService;

  private FileUploadController fileUploadControllerUnderTest;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fileUploadControllerUnderTest = new FileUploadController(mockCsvProcessService);
  }

  @Test
  void testUploadCsv() throws Exception {
    // Setup
    final MultipartFile mockFile = mock(MultipartFile.class);
    final ResponseEntity<ResponseModel> expectedResult =
        new ResponseEntity<>(new ResponseModel(List.of(new Person("name", "age", Gender.MALE))),
            HttpStatusCode.valueOf(200));
    when(mockCsvProcessService.processCsv(any(MultipartFile.class)))
        .thenReturn(List.of(new Person("name", "age", Gender.MALE)));

    // Run the test
    final ResponseEntity<ResponseModel> result = fileUploadControllerUnderTest.uploadCsv(mockFile);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  void testUploadCsv_CsvProcessServiceReturnsNoItems() throws Exception {
    // Setup
    final MultipartFile mockFile = mock(MultipartFile.class);
    final ResponseEntity<ResponseModel> expectedResult =
        new ResponseEntity<>(
            new ResponseModel(Collections.emptyList()), HttpStatusCode.valueOf(200));
    when(mockCsvProcessService.processCsv(any(MultipartFile.class))).thenReturn(
        Collections.emptyList());

    // Run the test
    final ResponseEntity<ResponseModel> result = fileUploadControllerUnderTest.uploadCsv(mockFile);

    // Verify the results
    assertEquals(expectedResult, result);
  }

  @Test
  void testUploadCsv_CsvProcessServiceThrowsIOException() throws Exception {
    // Setup
    final MultipartFile mockFile = mock(MultipartFile.class);
    when(mockCsvProcessService.processCsv(any(MultipartFile.class))).thenThrow(IOException.class);

    // Run the test
    assertThrows(IOException.class, () -> fileUploadControllerUnderTest.uploadCsv(mockFile));
  }
}
