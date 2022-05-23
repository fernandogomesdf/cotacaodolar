package org.acme.dto;

public class ErroDTO {

  private String message;

  public ErroDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
