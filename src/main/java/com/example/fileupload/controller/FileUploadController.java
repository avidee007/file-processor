package com.example.fileupload.controller;

import com.example.fileupload.dto.DtoAssembler;
import com.example.fileupload.dto.ResponseModel;
import com.example.fileupload.services.CsvProcessService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app")
public class FileUploadController {

  private final CsvProcessService csvProcessService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseModel> uploadCsv(@RequestParam("file") MultipartFile file)
      throws IOException {
    return ResponseEntity.ok(DtoAssembler.toDto(csvProcessService.processCsv(file)));

  }
}
