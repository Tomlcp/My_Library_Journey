# Arquitetura em Camadas para Aplicação de Biblioteca (JDBC Java)

Para a sua aplicação de biblioteca em Java com JDBC, e considerando que você é iniciante, a arquitetura mais apropriada e profissional seria uma **Arquitetura em Camadas (Layered Architecture)**, também conhecida como **N-Tier Architecture**. Ela é simples de entender, fácil de organizar e promove a separação de preocupações de forma eficaz.

Vamos dividi-la em três camadas principais:

---

### Arquitetura em Camadas (3 Camadas)

1.  **Camada de Apresentação (Presentation Layer)**
2.  **Camada de Negócios/Serviço (Business/Service Layer)**
3.  **Camada de Acesso a Dados (Data Access/Persistence Layer)**
4.  **(Opcional, mas fundamental) Camada de Domínio/Modelo (Domain/Model Layer)**

---

### Detalhamento das Camadas e Como os Padrões se Encaixam

#### 1. Camada de Domínio/Modelo (Domain/Model Layer)

Esta camada é a mais fundamental e contém as classes que representam os conceitos do seu domínio de negócios – as "entidades". Elas são puras classes Java (POJOs - Plain Old Java Objects) sem qualquer lógica de banco de dados ou de negócio complexa.

- **Responsabilidade:** Definir a estrutura dos dados da sua aplicação.
- **Componentes:**
  - **Classes de Entidade:** `Livro`, `Usuario`, `Emprestimo`, `Autor`, etc.
- **Exemplo:**
  ```java
  public class Livro {
      private long id;
      private String titulo;
      private String autor;
      private int anoPublicacao;
      // Getters e Setters
  }
  ```
- **Onde os padrões se encaixam:** Não diretamente nesta camada, mas ela é usada por todas as outras.

---

#### 2. Camada de Acesso a Dados (Data Access/Persistence Layer)

Esta camada é responsável por toda a interação com o banco de dados. Ela sabe como salvar, buscar, atualizar e excluir as entidades. O objetivo é isolar a lógica de persistência do resto da aplicação.

- **Responsabilidade:** Persistir e recuperar dados do banco de dados.
- **Componentes:**
  - **DAOs (Data Access Objects):** As implementações do seu padrão DAO (ex: `LivroDAOJdbcImpl`, `UsuarioDAOJdbcImpl`). Eles contêm o código JDBC (SQL statements, `PreparedStatement`, `ResultSet`, etc.).
  - **ConnectionFactorySingleton:** A classe Singleton que fornece conexões ao banco de dados, conforme discutimos.
- **Padrões Aplicados:**
  - **DAO:** Cada entidade terá sua interface DAO e sua implementação (`LivroDAO`, `LivroDAOJdbcImpl`).
  - **Singleton:** Para a `ConnectionFactory` (garante uma única forma de obter conexões).
  - **Simple Factory:** Pode ser usada para criar instâncias de DAOs (ex: `DAOFactory.createLivroDAO(connection)`).
- **Exemplo:**
  ```java
  // ConnectionFactorySingleton (já mostrado anteriormente)
  // LivroDAO.java (interface)
  // LivroDAOJdbcImpl.java (implementação)
  // DAOFactory.java (para criar DAOs)
  ```

---

#### 3. Camada de Negócios/Serviço (Business/Service Layer)

Esta é a "cabeça" da sua aplicação. Ela contém a lógica de negócios real (regras de validação, fluxos de trabalho, etc.). Esta camada usa os DAOs para persistir e recuperar dados, mas não se preocupa com os detalhes de como isso é feito.

- **Responsabilidade:** Implementar as regras de negócios da aplicação, orquestrar operações e gerenciar transações.
- **Componentes:**
  - **Classes de Serviço:** `LivroService`, `UsuarioService`, `EmprestimoService`.
- **Como interage:** As classes de serviço dependem das interfaces DAO. Elas não conhecem as implementações JDBC dos DAOs.
- **Exemplo:**

  ```java
  public class LivroService {
      private LivroDAO livroDAO; // Depende da interface DAO

      public LivroService(LivroDAO livroDAO) {
          this.livroDAO = livroDAO;
      }

      public void adicionarNovoLivro(Livro livro) {
          // Regras de negócio, ex: validar se o título não é vazio
          if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
              throw new IllegalArgumentException("Título do livro não pode ser vazio.");
          }
          // Chama o DAO para persistir
          livroDAO.salvar(livro);
      }

      public Livro buscarLivroPorId(long id) {
          // Alguma lógica de negócio ou apenas delegar
          return livroDAO.buscarPorId(id);
      }

      // Outros métodos como emprestarLivro, devolverLivro, etc.
  }
  ```

- **Onde os padrões se encaixam:**
  - **Injeção de Dependência (implícita aqui):** As classes de serviço recebem seus DAOs no construtor. Isso permite que você mude a implementação do DAO sem alterar o serviço.
  - **Simple Factory (para DAOs):** A fábrica pode ser usada em algum ponto (talvez na camada de apresentação ou em um ponto de inicialização) para criar os DAOs que serão injetados nos serviços.

---

#### 4. Camada de Apresentação (Presentation Layer)

Esta camada é a interface com o usuário. Pode ser um console, uma interface gráfica (Swing, JavaFX) ou um endpoint RESTful (se fosse uma aplicação web). Esta camada chama os métodos da Camada de Serviço.

- **Responsabilidade:** Lidar com a interação do usuário (entrada/saída).
- **Componentes:**
  - **Classes Main (para console):** `BibliotecaApp.java` (com um método `main`).
  - Classes de UI (se for GUI): `LivroFrame`, `LoginWindow`.
  - Controladores (se for web): Servlets, Controllers de frameworks web.
- **Como interage:** Chama métodos da Camada de Serviço para realizar operações.
- **Exemplo (para um aplicativo de console simples):**

  ```java
  import java.sql.Connection;
  import java.sql.SQLException;

  public class BibliotecaApp {
      public static void main(String[] args) {
          Connection connection = null;
          try {
              // 1. Obter conexão (usando Singleton)
              connection = ConnectionFactorySingleton.getInstance().getConnection();

              // 2. Criar DAO (usando Simple Factory)
              LivroDAO livroDAO = DAOFactory.createLivroDAO(connection);

              // 3. Criar Serviço, injetando o DAO
              LivroService livroService = new LivroService(livroDAO);

              // 4. Interação com o usuário ou lógica de apresentação
              System.out.println("Bem-vindo à Biblioteca!");

              // Exemplo de uso
              Livro novoLivro = new Livro();
              novoLivro.setTitulo("A Metamorfose");
              novoLivro.setAutor("Franz Kafka");
              novoLivro.setAnoPublicacao(1915);

              livroService.adicionarNovoLivro(novoLivro);
              System.out.println("Livro adicionado com sucesso!");

              Livro livroBuscado = livroService.buscarLivroPorId(1);
              if (livroBuscado != null) {
                  System.out.println("Livro buscado: " + livroBuscado.getTitulo() + " por " + livroBuscado.getAutor());
              }

          } catch (SQLException e) {
              System.err.println("Erro de conexão ou SQL: " + e.getMessage());
          } catch (IllegalArgumentException e) {
              System.err.println("Erro de validação: " + e.getMessage());
          } catch (Exception e) {
              System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
              e.printStackTrace();
          } finally {
              if (connection != null) {
                  try {
                      connection.close(); // Fechar a conexão
                  } catch (SQLException e) {
                      System.err.println("Erro ao fechar conexão: " + e.getMessage());
                  }
              }
          }
      }
  }
  ```

---

### Diagrama Simplificado da Arquitetura

```Diagram
+---------------------------+
|      Apresentação         |
|   (Console, GUI, Web)     |
+-------------+-------------+
              |
              V
+-------------+-------------+
|    Serviço/Negócios       |
|  (LivroService, etc.)     |
+-------------+-------------+
              | (Depende de interfaces DAO)
              V
+-------------+-------------+
|    Acesso a Dados         |
| (LivroDAO, ConnectionFactory) |
+-------------+-------------+
              | (JDBC)
              V
+---------------------------+
|        Banco de Dados     |
|     (MySQL, PostgreSQL)   |
+---------------------------+
```

A camada de Domínio/Modelo (Entidades) perpassa todas as outras camadas, sendo a "linguagem" comum entre elas.

---

### Benefícios Desta Arquitetura:

- **Organização Clara:** O código é dividido em seções lógicas, facilitando a localização e o entendimento das funcionalidades.
- **Facilidade de Manutenção:** Uma mudança na lógica de negócios afeta principalmente a Camada de Serviço; uma mudança no banco de dados afeta principalmente a Camada de Acesso a Dados.
- **Testabilidade:** Cada camada pode ser testada de forma mais isolada. Por exemplo, você pode testar a Camada de Serviço mockando o DAO.
- **Escalabilidade:** Permite que diferentes camadas sejam distribuídas em servidores diferentes no futuro (embora para uma aplicação JDBC local isso não seja um foco inicial, a estrutura já prepara o caminho).
- **Profissionalismo:** Mesmo sendo simples, seguir essa estrutura demonstra um bom entendimento de design de software e torna o projeto mais robusto e preparado para o crescimento.

Para um iniciante, focar em ter as entidades, DAOs, uma `ConnectionFactory` e classes de serviço que usam os DAOs já é um grande passo para uma arquitetura profissional.
