package org.acme.service;

import org.acme.dto.CotacaoDTO;
import org.acme.model.Cotacao;
import org.acme.repository.CotacaoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;

@ApplicationScoped
public class CotacaoService {

  @Inject
  CotacaoRepository cotacaoRepository;

  public Cotacao consultarCotacao(CotacaoDTO cotacaoDTO) {

    Cotacao cotacao = new Cotacao();
    cotacao.setData(new Date());
    cotacaoRepository.add(cotacao);

    return cotacaoRepository.list().get(0);
  }
}
