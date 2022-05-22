package org.acme.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.acme.model.Cotacao;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CotacaoRepository implements PanacheMongoRepository<Cotacao> {

  /**
   * recupera todos os registros
   * @return
   */
  public List<Cotacao> list(){
    return findAll().list();
  }

  /**
   * Adiciona um registro
   * @param cotacao
   */
  public void add(Cotacao cotacao){
    persist(cotacao);
  }

}
