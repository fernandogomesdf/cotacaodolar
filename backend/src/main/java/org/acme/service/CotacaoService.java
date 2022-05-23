package org.acme.service;

import io.quarkus.mongodb.panache.PanacheQuery;
import org.acme.dto.CotacaoDTO;
import org.acme.model.Cotacao;
import org.acme.repository.CotacaoRepository;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class CotacaoService {

  @Inject
  CotacaoRepository cotacaoRepository;

  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

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
    if (contacaoMongo.hasNextPage()) {
      cotacao = contacaoMongo.firstResult();
    } else {
      cotacao = consutarEndpointEPersistir(cotacao);
    }

    return cotacao;
  }

  private Cotacao consutarEndpointEPersistir(Cotacao cotacao) throws Exception {
    JSONObject jsonCotacao = consultarEndpoint("https://olinda.bcb.gov.br/olinda/servico/PTAX" +
      "/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='" + cotacao.getDataSolicitada() + "'&$top=1&$format=json");

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
