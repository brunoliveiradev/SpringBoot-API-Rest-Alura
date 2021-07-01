<h1 align="center">
    Spring Boot: Criando uma API Rest
</h1>


<p align="center">
  <a href="#-tecnologias">Tecnologias</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-licença">Licença</a>
</p>



<p align="center">
 <img src="https://img.shields.io/static/v1?label=PRs&message=welcome&color=15C3D6&labelColor=000000" alt="PRs welcome!" />


  <img alt="License" src="https://img.shields.io/static/v1?label=license&message=MIT&color=15C3D6&labelColor=000000">
</p>

<br>

<p align="center">
    <img alt="home" src="https://images.unsplash.com/photo-1517694712202-14dd9538aa97?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1950&q=80" width="80%" height="80%">
</p>

<br>



## 🚀 Tecnologias

Esses códigos foram desenvolvidos com as seguintes tecnologias:

- [Java™ Platform, Standard Edition 11](https://docs.oracle.com/en/java/javase/11/docs/api/index.html)

- [Spring](https://spring.io)
  - [Spring Boot](https://spring.io/projects/spring-boot), [Spring Data JPA](https://spring.io/projects/spring-data-jpa), [Spring Web](https://spring.io/projects/spring-ws#overview), [Spring Security](https://spring.io/projects/spring-security), [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#actuator), [Sprig Boot Validation - Javax/Bean Validation](https://docs.sprwww.baeldung.com/javax-validation).   

- [JJWT - JSON Web Tokens for Java](https://java.jsonwebtoken.io)

- [Springfox - Swagger - Automated JSON API documentation for API's built with Spring](https://springfox.github.io/springfox/)

- [Codecentric’s Spring Boot Admin](https://codecentric.github.io/spring-boot-admin/current/)

- [MySQL 8.0 ](https://dev.mysql.com/doc/refman/8.0/en/)

- [Maven](https://maven.apache.org/guides/index.html)

- [Postman](https://www.postman.com/api-documentation-tool/)

- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

  <br>


## 💻 Projeto

🚀 O repositório tem como propósito compartilhar o conhecimento absorvido e os códigos desenvolvidos nos cursos [**Spring Boot API Rest: Construa uma API**](https://cursos.alura.com.br/course/spring-boot-api-rest) e [**Spring Boot API Rest: Segurança da API, Cache e Monitoramento**](https://cursos.alura.com.br/course/spring-boot-seguranca-cache-monitoramento) proporcionado pela Alura.

⚡️ Criei resumos e anotações no Notion das partes relevantes com a minha visão de aluno. [Clique aqui para conferir.](https://www.notion.so/Spring-Framework-6c29e51bad174a849500a5ba8701615f)

:coffee: O Spring boot é um framework para Java bastante popular que pode ser utilizado tanto para a construção de aplicações web tradicionais, ou APIs REST, que são muito utilizadas no modelo de arquitetura de micro serviços.

👾 O propósito foi montar a API REST, baseada no modelo de fórum da Alura. 
- ✨ Na versão 1 da API, o foco foi construir as lógicas e endpoints para listar os tópicos que foram cadastrados, cadastrar novos tópicos, atualizar, excluir, basicamente fazer o "CRUD", testando com o Postman. 
  - Validação com Bean Validation, personalizamos as mensagens de erro com o *Controller Advice* do Spring, e o acesso ao banco de dados, usando os *Repositorys* do Spring Boot Data JPA e MySQL. 
- ✨ Na versão 2 da API, implementamos novos recursos:
  - Configuramos a parte de **paginação** para listar os tópicos; 
  - Utilizamos **cache** para melhorar desempenho; 
  - Implementamos o módulo de segurança e a parte da **autenticação**, utilizando ***JSON Web Token***. Todos os endpoints que precisam de autenticação temos que mandar o token do authorization no cabeçalho.
  - Utilizamos o Spring Boot Admin para fazer a parte do monitoramento;
  - Concluímos com a parte de documentação, com o **Swagger**, ele imprime todos os controllers, cada um dos endpoints, conseguimos testar por aqui também, simular todos os testes.
    - Documentação do **Swagger UI** via `http://localhost:8080/api/v2/swagger-ui.html/`.
    - No endpoint `/auth` você gera o token utilizando os parâmetros ` aluno@email.com ` e `123456`.

:blue_heart: Caso queria testar localmente:
*  ``` git clone https://github.com/brunoliveiradev/SpringBoot-API-Rest-Alura ``` 

📫 Espero que goste, qualquer dúvida ou sugestão me encontro a disposição! [LinkedIn](https://www.linkedin.com/in/brunoliveiradev/)

<br>

## :memo: Licença

Esse projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE.md) para mais detalhes.

---

Códigos feitos com ♥ by Bruno Oliveira! [LinkedIn](https://www.linkedin.com/in/brunoliveiradev/). :blue_heart: 

<br>