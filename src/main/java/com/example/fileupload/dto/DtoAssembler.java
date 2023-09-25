package com.example.fileupload.dto;

import java.util.List;

public class DtoAssembler {

  public static ResponseModel toDto(List<Person> persons) {
    return new ResponseModel(persons);
  }
}
