package org.acme.resource;

import org.acme.dto.CotacaoDTO;
import org.acme.model.Cotacao;
import org.acme.service.CotacaoService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cotacao")
public class CotacaoResource {

  @Inject
  CotacaoService counterService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Cotacao consultarCotacao(CotacaoDTO cotacaoDTO) {
    Cotacao cotacao = counterService.consultarCotacao(cotacaoDTO);
    return cotacao;
  }
}
