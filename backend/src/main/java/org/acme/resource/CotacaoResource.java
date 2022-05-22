package org.acme.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cotacao")
public class CotacaoResource {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String consultarCotacao() {
    return "Cotação";
  }
}
