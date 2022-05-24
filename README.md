# cotacaodolar
Pequeno projeto para consulta da cotação do dólar

Swagger : http://localhost:8080/q/swagger-ui
Métricas : http://localhost:8080/q/metrics


docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:latest


./mvnw compile quarkus:dev -Djvm.args="-DJAEGER_SERVICE_NAME=cotacaoservice -DJAEGER_SAMPLER_TYPE=const -DJAEGER_SAMPLER_PARAM=1"