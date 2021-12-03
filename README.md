# Controll

## Ferramentas:
- :pushpin: Java 11
- :pushpin: Maven
- :pushpin: MariaDB 10.7


## Proposta
Criar um controle de Starter para GFT.

### Regras Gerais
- :heavy_check_mark: Popular banco: ou com botão ou assim que iniciar a aplicação.
- :heavy_check_mark: Criar CRUD para todas as entidades acima
- :heavy_check_mark: Validação de todos os campos
- :heavy_check_mark: Login Gerente com acesso a toda a Aplicação
- :heavy_check_mark: Login SM(Scrum Master) Acesso apenas as Dailys
- :heavy_check_mark: Pode acrescentar entidades se achar necessário, juntar, mas não pode eliminar
### Exceeds
- :heavy_check_mark: Cadastro de Empregado com foto.
- :x: Criar um relatório mostrando quais Starters tem maior nota e menor nota.
- :heavy_check_mark: Enviar email automático para clecio.silva@gft.com sempre que um SM acessar a aplicação. (Neste caso sugiro criar um email fake para não ter que colocar senha de um email seu real) (***[na branch send-email](https://git.gft.com/dlrv/desafio_mvc_11_2021/-/tree/send-email)***)


## Screenshot

<p align="center">
    <img  width="350" src="screenshot/01.png" />
    <img  width="350" src="screenshot/02.png" />
    <img  width="350" src="screenshot/03.png" />
    <img  width="350" src="screenshot/04.png" />
    <img  width="350" src="screenshot/05.png" />
    <img  width="350" src="screenshot/06.png" />
</p>

## :cd: Iniciar

Na linha de comando:
Use o ```mvnw``` para ambiente Linux e ```mvnw.cmd``` para Windows
```
./mvnw spring-boot:run
```

## Usuários:
- Clecio Gomes
  - login: clecio.silva@gft.com
  - senha: Gft2021
  - tipo: ADMIN

- Astrogildo Perrengue
    - login: astrogildo-scm@example.com
    - senha: Gft2021
    - tipo: SCRUM_MASTER

- Martiano Luanova
    - login: martiano-scm@example.com
    - senha: Gft2021
    - tipo: SCRUM_MASTER
  

### SGBD
Maria DB, com usuário ***root*** sem senha


