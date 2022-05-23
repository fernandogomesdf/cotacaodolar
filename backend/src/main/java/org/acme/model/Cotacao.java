package org.acme.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * id da requisição
 * timestamp da requisição
 * Data da cotação do dólar (data da cotação solicitada e não a data da requisição)
 * Cotação de compra
 * Cotação de venda
 * Data e Hora da Cotação
 */
@MongoEntity(collection = "cotacao", database = "cotacao")
public class Cotacao extends PanacheMongoEntity {


  String dataSolicitada;

  private Date dataRequisicao;

  private Date dataCotacao;
  private BigDecimal cotacaoCompra;
  private BigDecimal cotacaoVenda;

  public String getDataSolicitada() {
    return dataSolicitada;
  }

  public void setDataSolicitada(String dataSolicitada) {
    this.dataSolicitada = dataSolicitada;
  }

  public Date getDataRequisicao() {
    return dataRequisicao;
  }

  public void setDataRequisicao(Date dataRequisicao) {
    this.dataRequisicao = dataRequisicao;
  }

  public Date getDataCotacao() {
    return dataCotacao;
  }

  public void setDataCotacao(Date dataCotacao) {
    this.dataCotacao = dataCotacao;
  }

  public BigDecimal getCotacaoCompra() {
    return cotacaoCompra;
  }

  public void setCotacaoCompra(BigDecimal cotacaoCompra) {
    this.cotacaoCompra = cotacaoCompra;
  }

  public BigDecimal getCotacaoVenda() {
    return cotacaoVenda;
  }

  public void setCotacaoVenda(BigDecimal cotacaoVenda) {
    this.cotacaoVenda = cotacaoVenda;
  }
}
