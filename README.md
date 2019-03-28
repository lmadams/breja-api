# :beer: Desafio (Back-end)


## Tecnologias utilizadas?

* Sprint Boot (micro-serviço em Java);
* Maven (Gestão de dependências e build)
* H2 Database (banco simples em memória);
* JPA (Biblioteca de persistência de objetos relacionais);
* Lombok (biblioteca para simplificar codificação Java - get/set/construtores)
* Spotify Web Api (biblioteca para consumo dos serviços do Spotify - https://github.com/thelinmichael/spotify-web-api-java);
* JUnit (framework para testes);


## Endpoint público

O projeto foi publicado no Heroku.

`https://breja-api.herokuapp.com`

## API

Listar todas cervejas cadastradas

**GET** `https://breja-api.herokuapp.com/cerveja`

Recuperar uma cerveja por ID

**GET** `https://breja-api.herokuapp.com/cerveja/{ID}`

Salvar uma nova cerveja

**POST** `https://breja-api.herokuapp.com/cerveja` 

```
{
    "nome": "Cerveja",
    "tempInicial": 1,
    "tempFinal": 6
}
```

Editar uma cerveja com base no ID

**PUT** `https://breja-api.herokuapp.com/cerveja/{ID}`

```
{
    "id": 1,
    "nome": "Weissbier",
    "tempInicial": -1,
    "tempFinal": 3
}
```

Remover uma cerveja com base no ID

**DELETE** `https://breja-api.herokuapp.com/cerveja/{ID}`

Lista as cervejas com base na temperatura informada juntamente com sugestões do Spotify.

**PUT** `https://breja-api.herokuapp.com/cerveja/playlist`

```
{
	"temperatura": "1"
}
```

## Instalação

Para instalação das dependências do projeto execute (ignora os testes):

`
mvn clean install -DskipTests 
`

## Executar o projeto

Para subir o micro serviço:

`
mvn spring-boot:run
`

## Executar testes

Para executar os testes (classe de teste para a controller - ponto de entrada das request's):

`
mvn test
`
