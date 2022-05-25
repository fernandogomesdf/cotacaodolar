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

# Especificação

Eu, como um investidor, gostaria de cosultar as cotações do dólar, porque eu preciso saber quais as melhores opções de investimento.

Critério de Aceite:

    - Permitir que o investidor consulte a cotação informando uma data
    - Permitir visualizar a cotação de compra, de venda e a hora da cotação
    - Permitir ser informada a data através de um calendário pra facilitar a busca

Regras de negócio:

    - Não permitir informar uma data nula
    - Não permitir informar uma data futura
    - Caso não exista cotação para o dia, deve informar que não existe cotação para o dia
    - Deve gravar em respositório local todas as cotações consultadas
    - Deve buscar a cotação externamente apenas se ela não existe localmente

Cenários de teste:

    - Informar uma data nula
    - Informar uma data futura
    - Informar uma data muito antiga
    - Consultar pelo frontend sem o backend estar funcionando
    - Verificar se as datas são consultadas primeiramente no repositório local e depois no serviço do BC, caso não encontre

