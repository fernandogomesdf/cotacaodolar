# cotacaodolar

Pequeno projeto para consulta da cotação do dólar

Swagger : http://localhost:8080/q/swagger-ui
Métricas : http://localhost:8080/q/metrics

O diretório principal corresponde ao frontend angular. O backend quarkus fica no diretório 'backend' 

Para rodar o projeto:

- npm run install
- npm run build (está configurado pra construir o frontend e colocar o build na pasta backend/src/main/resources/META-INF/resources, sendo acessível através de http://localhost:8080)

O comando npm run build também irá criar e registrar a imagem docker. Após o build completo executar:

- docker-compose up -d

