package org.acme.service;

import io.quarkus.mongodb.panache.PanacheQuery;
import org.acme.dto.CotacaoDTO;
import org.acme.model.Cotacao;
import org.acme.repository.CotacaoRepository;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Serviço para consulta das cotações do dólar
 */
@ApplicationScoped
public class CotacaoService {

  @Inject
  CotacaoRepository cotacaoRepository;

  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
  SimpleDateFormat simpleDateFormatBC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  
  /**
   * Consulta a cotção de uma data
   * @param cotacaoDTO
   * @return
   */
  public Cotacao consultarCotacao(CotacaoDTO cotacaoDTO) throws Exception {

    Date data = cotacaoDTO.getData();

    Cotacao cotacao = new Cotacao();
    cotacao.setDataSolicitada(simpleDateFormat.format(data));

    PanacheQuery<Cotacao> contacaoMongo = cotacaoRepository.find("dataSolicitada", cotacao.getDataSolicitada());
    contacaoMongo.page(0,1);

    if (contacaoMongo.hasNextPage()) {
      cotacao = contacaoMongo.firstResult();

      System.out.println("cotacao base " + cotacao);
    } else {
      cotacao = consutarEndpointEPersistir(cotacao);

      System.out.println("cotacao endpoint " + cotacao);
    }

    return cotacao;
  }

  /**
   * consulta o endpoint do BC e persiste na base
   * @param cotacao
   * @return
   * @throws Exception
   */
  private Cotacao consutarEndpointEPersistir(Cotacao cotacao) throws Exception {

    /**
     * {"@odata.context":"https://was-p.bcnet.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata$metadata#_CotacaoDolarDia",
     * "value":[{"cotacaoCompra":4.967,"cotacaoVenda":4.9676,"dataHoraCotacao":"2022-05-17 17:51:39.571"}]}
     */

    JSONObject jsonCotacao = consultarEndpoint("https://olinda.bcb.gov.br/olinda/servico/PTAX" +
      "/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='" + cotacao.getDataSolicitada()
      + "'&$top=1&$format=json");

    JSONArray valores = jsonCotacao.getJSONArray("value");
    if (valores != null) {
      JSONObject jsonValor = valores.getJSONObject(0);
      cotacao.setCotacaoCompra(BigDecimal.valueOf(jsonValor.getDouble("cotacaoCompra")));
      cotacao.setCotacaoVenda(BigDecimal.valueOf(jsonValor.getDouble("cotacaoVenda")));
      cotacao.setDataCotacao(simpleDateFormatBC.parse(jsonValor.getString("dataHoraCotacao")));
      cotacao.setDataRequisicao(new Date());
      cotacaoRepository.persist(cotacao);
    }

    return cotacao;
  }

  /**
   * consulta o endpoint do BC
   * @param urlConsulta
   * @return
   * @throws Exception
   */
  private JSONObject consultarEndpoint(String urlConsulta) throws Exception {
    URL url = new URL(urlConsulta);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.connect();
    InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
    BufferedReader buff = new BufferedReader(in);
    String line;
    StringBuilder builder = new StringBuilder();
    do {
      line = buff.readLine();
      builder.append(line).append("\n");
    } while (line != null);
    buff.close();
    conn.disconnect();
    return new JSONObject(builder.toString());
  }


}

