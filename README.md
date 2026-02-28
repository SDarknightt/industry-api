# Gerenciador de Produção - API REST Spring Boot

## Funcionalidades
- Manter produtos;
- Manter matérias primas;
- Manter relação entre produtos e matérias primas;

## Conceitos aplicados

- Spring Boot
    - Inicialização simplificada do projeto e configuração automática.
- Tratamento de Exceções
    - Implementação de exceções personalizadas.
    - Mensagens claras para facilitar a identificação de erros de negócio.
- Banco de Dados
    - Utilização do banco relacional PostgreSQL.
- DTO (Data Transfer Object)
    - Padronização da entrada e saída de dados.
    - Separação entre camada de domínio e exposição da API.
- Arquitetura em Camadas
    - Controller – Responsável por lidar com as requisições HTTP.
    - Service – Contém as regras de negócio.
    - Repository – Responsável pela comunicação com o banco de dados.
- Containerização com Docker
    - Garante que a aplicação rode de forma consistente em qualquer ambiente.

## Como executar o projeto (localmente)

### Pré-requisitos
- Java JDK 21+
- Maven **ou** Maven Wrapper (`mvnw`)

### Definição variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto com as configurações do banco:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/industry
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=1234
```

### Executando com Maven Wrapper
Na raiz do projeto, execute:

```bash
./mvnw spring-boot:run
```

### Executando com Docker

Na raiz do projeto, execute para gerar o **jar** na pasta **target**:
```bash
./mvnw clean package 
```

Gere uma imagem docker:
```bash
docker build -t industry-api:1.0 . 
```

Rode o container a partir da imagem gerada (usando o arquivo .env):
```bash
docker run -d --name industry-api -p 8080:8080 --env-file .env industry-api:1.0
```

Ou defina as variáveis diretamente:
```bash
docker run -d --name industry-api -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/industry \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=1234 \
  industry-api:1.0
```

A aplicação será iniciada em:
```bash
 http://localhost:8080
```

## Endpoints

### Products

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /products | Criar um novo produto |
| PUT | /products/{id} | Atualizar um produto existente |
| DELETE | /products/{id} | Deletar um produto |
| GET | /products/{id} | Buscar um produto pelo ID |
| GET | /products | Listar todos os produtos |
| GET | /products/production | Obter recomendação de produção |

### Raw Materials

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /raw-materials | Criar uma nova matéria prima |
| PUT | /raw-materials/{id} | Atualizar uma matéria prima existente |
| DELETE | /raw-materials/{id} | Deletar uma matéria prima |
| GET | /raw-materials/{id} | Buscar uma matéria prima pelo ID |
| GET | /raw-materials | Listar todas as matérias primas |

### Product Raw Materials

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | /product/{productId}/materials/{materialId} | Associar uma matéria prima a um produto |
| PUT | /product/{productId}/materials/{materialId} | Atualizar a quantidade de matéria prima de um produto |
| DELETE | /product/{productId}/materials/{materialId} | Remover uma matéria prima de um produto |
| GET | /product/{productId}/materials | Listar todas as matérias primas de um produto |

### Melhorias Futuras
- Definição de unidade de medida nos produtos;
- Adicionar suporte a edição, deleção e inserção de multiplas matérias primas a produtos ja criados;
- Adicionar login e autenticação via JWT;