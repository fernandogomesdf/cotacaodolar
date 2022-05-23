package org.acme.resource;

import org.acme.dto.CotacaoDTO;
import org.acme.dto.ErroDTO;
import org.acme.model.Cotacao;
import org.acme.service.CotacaoService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cotacao")
public class CotacaoResource {

  @Inject
  CotacaoService counterService;

  /**
   * Consulta a contação de uma data
   * @param cotacaoDTO
   * @return
   */
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response consultarCotacao(@Valid CotacaoDTO cotacaoDTO) throws Exception {
    Cotacao cotacao;
    try {
      cotacao = counterService.consultarCotacao(cotacaoDTO);
      return Response.ok().entity(cotacao).build();
    } catch (Exception e) {
      return Response.status(400).entity(new ErroDTO("Sem cotação para a data")).build();
    }
  }
}
