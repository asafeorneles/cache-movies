# 📽💺 Cache Movies – Reserva de Assentos de Cinema.

API desenvolvida em Java com Spring Boot para controle e reserva de assentos de um cinema, focada em regra de negócio e solução de problemas em multiplas reservas de um assento já ocupado. 

## 🚀 Tecnologias Utilizadas
- Java 17
- Spring Boot 3
- Spring Data JPA / Hibernate
- Flyway
- MySQL
- Maven
- Lombok
- Docker

## 🧾 Funcionalidades
- Cadastro de filmes e categorização de gênero
- Cadastro de salas de cinema e assentos
- Criação de sessões para os filmes
- Criação de reservas de assentos por sessão
- Controle de assentos disponíveis, reservados e ocupados 

## 🧱 Arquitetura e Boas Práticas
- Arquitetura em camadas:
  - Controller
  - Service
  - Repository
- Uso de DTOs para transferência de dados em respostas e requisições
- API baseada em princípios REST

## 🗂️ Estrutura

```
src/
 └── main/
     ├── java/com.asafeorneles.gymstock
     │    ├── controllers/
     │    ├── dto/
     |    ├── entities/
     │    ├── enums/
     │    ├── mapper/    
     │    ├── repositories/
     │    └── services/
     └── resources/
         ├── db.migration/
         ├── application.properties
     └── test
          └ ── ...
```

## ⚙️ Como Executar o Projeto

### 🐳 Rodando a aplicação com Docker:
O projeto utiliza **Docker Compose** para subir o banco de dados MySQL localmente.

```yaml
services:
  mysql:
    image: mysql:8.0.36
    container_name: spring_security
    environment:
      MYSQL_USER: admin
      MYSQL_PASSWORD: root
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: spring_security_db
    ports:
      - "3306:3306"
    volumes:
      - spring_security_data:/var/lib/mysql

volumes:
  spring_security_data:
```


### ▶️ Como Executar o Projeto

1. Clone o repositório:

   ```bash
   git clone <url-do-repositorio>
   ```

2. Suba o banco de dados com Docker:

   ```bash
   docker-compose up -d
   ```

3. Execute a aplicação Spring Boot:

   ```bash
   mvn spring-boot:run
   ```

4. A aplicação estará disponível em:

   ```
   http://localhost:8080
   ```
#### Obs: As migrações de banco são executadas automaticamente via Flyway.

---

## 👤 Autor
- Asafe Orneles
-  [![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?logo=linkedin)](https://www.linkedin.com/in/asafeorneles)


