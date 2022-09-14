# Utilizando Spring Security com token JWT como microserviço para autorização e autenticação de usuários

Olá! Este projeto foi criado para estudo e aprender a implementar o Spring Security sem herdar da classe WebSecurityConfigurerAdapter, pois essa forma foi depreciada em fevereiro de 2022.

Neste vídeo eu apresento a documentação do projeto: (Em breve)

### Você precisa ter o LOMBOK instalado na sua IDE para conseguir rodar a API OU ter o docker instalado e utilizar o docker-compose que está na pasta raiz deste projeto.

Esta implementação utiliza token JWT para autenticacao e autorização dos usuários.
Você consegue ter acesso a documentação pelo swagger, após iniciar o projeto:
- http://localhost:8080/swagger-ui/index.html#/
#### Você pode utilizar os JSON pré-configurados no swagger ou testar com parâmetros a sua escolha.

Foram implementadas as seguintes funcionalidades:

#### Endpoints liberados para usuarios não autenticados
  - Cadastro de usuário 
  - Autenticacao de usuário 
  - Recuperação de senha, "esqueci minha senha"
  
#### Endpoints liberados somente para usuários autenticados
  - Usuario pode se auto-excluir
  - Usuario pode trocar seu username
  - Usuario pode trocar sua senha

## Tecnologias utilizadas
  - Java com Spring Boot
  - Spring Security com Token JWT
  - Spring Data JPA
  - Docker
  - Spring Validation
  - Java Mail Sender
  - Banco de dados MySQL
  - FlyWay Migrations
  - Documentação com SpringDoc
  -	JUnit e Mockito
  -	Lombok

### Esta aplicação contém testes unitários das Controllers, Services, Repositories e classes de validação (Em breve).

### Validações
    - Não permite cadastrar ou modificar para usernames que já estejam no banco de dados
    - Valida se a senha é forte. Ela precisa
      - ter de 8 a 30 caracteres
      - pelo menos 1 caractere maiusculo
      - pelo menos 1 caractere minusculo
      - pelo menos 1 caractere numerico
      - pelo menos um caractere especial
      - não podem haver grandes sequencias de números
      - não podem haver espaços em branco dentro da senha


