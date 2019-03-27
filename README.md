# :beer: Desafio (Back-end)


## Tecnologias utilizadas?

* Sprint Boot (micro-serviço em Java);
* Maven (Gestão de dependências e build)
* H2 Database (banco simples em memória);
* JPA (Biblioteca de persistência de objetos relacionais);
* Lombok (biblioteca para simplificar codificação Java - get/set/construtores)
* Feign Client (biblioteca para consumo de WebServices);
* JUnit (framework para testes);


## Endpoint público

O projeto foi publicado no Heroku.

`https://breja-api.herokuapp.com`

## API


**GET** `https://breja-api.herokuapp.com/cerveja`

**GET** `https://breja-api.herokuapp.com/cerveja/{ID}`

**POST** `https://breja-api.herokuapp.com/cerveja` 

```
{
    "nome": "Cerveja",
    "tempInicial": 1,
    "tempFinal": 6
}
```

**PUT** `https://breja-api.herokuapp.com/cerveja/{ID}`

```
{
    "id": 1,
    "nome": "Weissbier",
    "tempInicial": -1,
    "tempFinal": 3
}
```

**DELETE** `https://breja-api.herokuapp.com/cerveja/{ID}`

**PUT** `https://breja-api.herokuapp.com/cerveja/playlist`

```
{
	"temperatura": "1"
}
```

## Instalação

@TODO 

## Executar testes

@TODO

## Build do projeto e publicação

@TODO