package org.acme.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class CotacaoDTO {

  @PastOrPresent(message="Não pode ser uma data futura")
  @NotNull(message="Data não pode ser nula")
  public Date data;

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }
}
