### M3P-BackEnd-Squad2

# API REST para Gestão de Cursos, Turmas, Alunos e Docentes

Este projeto é uma API REST desenvolvida com o framework Spring Boot, voltada para a gestão de cursos, turmas, alunos e docentes. As tecnologias utilizadas incluem Java, Spring Boot, PostgreSQL, Git e Docker.

## Tecnologias Utilizadas

- Java
- Spring Boot
- PostgreSQL
- Git
- Docker

## Pré-requisitos

Certifique-se de ter o Docker e o Docker Compose instalados na sua máquina.
## Como Executar o Projeto

1. **Navegue até o diretório onde está o arquivo `docker-compose.yml`.** Você pode usar o terminal do IntelliJ ou qualquer terminal de sua escolha.

2. Execute o seguinte comando para iniciar os serviços:
   ```bash
   docker-compose up --build

Isso irá construir as imagens e iniciar os containers.

## Configurações de Banco de Dados

A aplicação está configurada para usar um banco de dados PostgreSQL com as seguintes configurações:

- **Banco de Dados:** `database`
- **Usuário:** `meuUsuario`
- **Senha:** `minhaSenha`
- **Portas:**
    - **API:** `8080`
    - **PostgreSQL:** `1432`

## Usuários Pré-Cadastrados

Existem três tipos de usuários pré-cadastrados na aplicação:

- **Administrador**
    - **Email:** `administrador@gmail.com`
    - **Senha:** `123456`

- **Docente**
    - **Email:** `docente@teste.com`
    - **Senha:** `123456`

- **Aluno**
    - **Email:** `maria@teste.com`
    - **Senha:** `123456`

## Autenticação

Apenas o login não necessita de token. Ao se logar, o usuário receberá um token que deverá ser utilizado nas demais requisições.

## Criação de Entidades

Utilize o token gerado pelas credenciais do usuário administrador ao se logar para os demais acessos:

- **Docente:** Cadastre docentes nos endpoints de docente
- **Curso:** Crie cursos nos endpoints de curso.
- **Matéria:** Crie matéria nos endpoints de matéria.
- **Turma:** Crie turma nos endpoints de turma.
- **Aluno:** Crie alunos nos endpoints de aluno
- **Notas:** Lançe notas a um aluno utilizando os endpoints de nota.

## Testando a API com Insomnia

Você pode importar as requisições do Insomnia para facilitar os testes. Para fazer isso, siga as etapas:

1. Abra o Insomnia.
2. Vá em `File` > `Import`.
3. Selecione o arquivo `insomnia_requests.json` incluído neste repositório.

### Insomnia Requests
O arquivo `insomnia_requests.json` contém as requisições para todos os endpoints da API.

## Projeto Angular

É possível utilizar este projeto em conjunto com um frontend desenvolvido em Angular. Você pode encontrar o repositório do projeto Angular [aqui](https://github.com/FullStack-Education/M3P-FrontEnd-Squad2).

Siga as instruções do README do projeto Angular para configurá-lo e utilizá-lo junto com esta API.