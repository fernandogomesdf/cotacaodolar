package org.acme.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.acme.model.Cotacao;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CotacaoRepository {

  @Inject
  MongoClient mongoClient;

  public List<Cotacao> list(){
    List<Cotacao> list = new ArrayList<>();
    MongoCursor<Document> cursor = getCollection().find().iterator();

    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        Cotacao cotacao = new Cotacao();
        cotacao.setData(document.getDate("data"));
        list.add(cotacao);
      }
    } finally {
      cursor.close();
    }
    return list;
  }

  public void add(Cotacao cotacao){
    Document document = new Document()
      .append("data", cotacao.getData());
    getCollection().insertOne(document);
  }

  private MongoCollection<Document> getCollection(){
    return mongoClient.getDatabase("cotacao").getCollection("cotacao");
  }
}
