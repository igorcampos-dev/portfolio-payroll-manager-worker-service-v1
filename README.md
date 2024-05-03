# API de Contra Cheque

---

## Introdução

Este projeto foi desenvolvido por Igor de Campos com o objetivo de testar suas habilidades lógicas e experiências em
programação.

---

## Sobre o Projeto

A API de Contra Cheques é uma solução empresarial que permite que qualquer funcionário de uma empresa possa consultar
seus contra cheques de maneira fácil e acessível, disponível 24 horas por dia. Ela oferece funcionalidades como a
pesquisa de contra cheques em uma data específica, a possibilidade de fazer o download do contra cheque ou de
visualizá-lo online.

---

## Processo de instalação

### Implantação Local

1. **Requisitos minimos:**
    - Jdk 17
    - Docker

Siga as etapas abaixo para implantar o projeto localmente:

1. Execute os seguintes comandos: :
   ```bash
   docker compose up -d mysql-db
   docker compose up -d aws-s3rver
2. Aguarde até o processo de instalação do docker finalizar, depois,execute o projeto.

---

### Implantação no Docker

1. **Requisitos minimos:**
    - Docker

Siga as etapas abaixo para implantar o projeto no Docker:

1. Execute:
   ```bash
   docker compose up
2. Aguarde até o processo de instalação finalizar.

---

## Especificação geral do Projeto, entendendo o seu fluxo:

### Vamos entender com mais facilidade,como pode ser desenvolvido o frontend do projeto,para que ele possa se conectar posivitamente com o backend do projeto.

1. **Funcionários :**
    - O funcionário acessa a tela de registro e faz o registro com o seu cpf e senha. [clique aqui](#post-nexusvalidate)
    - O funcionário loga no sistema pela tela de login com as suas credenciais. [clique aqui](#post-nexuslogin)
    - Após o sistema armazenar as informações do usuário retornadas na rota de login,ele é redirecionado para o menu principal.
    - Usuário clica em um botão escrito "Ver contraCheques e o sistema lista previamente cada contra cheque". [clique aqui](#get-nexuspaycheckuserid)
    - Usuário decide visualizar um contraCheque especifico, clica em um dos pdfs que possuem visualização prévia,e o sistema renderiza online o pdf para o funcionário visualizar. [clique aqui](#get-nexuspaycheckuseridpaycheckdate)
    - O usuário decide efetuar um "logout" apertando em um botão referente,então o frontend deve apagar as informações salvas no cache e não permitir que o usuário acesse mais as suas rotas.

   <br>
2. **Administradores:**
    - O administrador efetua o login na tela de login. [clique aqui](#post-nexusloginadmin)
    - O administrador decide listar os funcionários dessa api. [clique aqui](#get-nexusadminemployees)
    - Dado que o administrador sabe que o funcionário X não possui o contraCheque do Mês,o administrador acessa uma tela ao clicar em "enviar contra Cheque", preenche o campo: nome do usuário, data do contraCheque e envia para o backend. [clique aqui](#post-nexusadminuseridpaycheckdate)
    - Dado que o administrador notou uma indiferença relatada por algum funcionário,ele decide atualizar o contra cheque do mês x, acessa a tela ao clicar: "atualizar contra Cheque",e coloca: nome do usuário, data do contraCheque e envia para o backend. [clique aqui](#put-nexusadminuseridpaycheckdate)
    - Usuário decide efetuar o "logout" clicando no botão de sair,então o sistema deve limpar as credenciais armazenadas no cache.

---

## Atenção!

Todos os Curls declarados no Readme são do ambiente de dev, certifique-se de mudar a porta para a porta do ambiente adequado

---

## Portas do projeto:
- **docker:** 80
- **local:** 8087
- **dev:** 80
- **hml:** 8081
- **prd:** 8083
---

## Rotas para Funcionários

---

### `POST /tech-spring-solutions/nexus/employees/login`

```bash
curl --location 'http://localhost:8087/tech-spring-solutions/nexus/employees/login' \
--header 'Content-Type: application/json' \
--data '{
    "cpf": "CPF_USUARIO_REGISTRADO",
    "password": "SENHA_CRIADA"
}'
```

Esta rota é utilizada para efetuar o login do funcionário. A existência do funcionário na empresa é verificada com base
no seu cpf e senha.

<br>

### Exemplo de Frontend: Tela de Login

Este é uma descrição de  uma tela interativa para o usuário fazer login. Ela contém dois campos: CPF e senha. Após o usuário preencher esses campos, faremos uma pequena validação:

1. **Validação do CPF**: Aceitaremos CPFs tanto com pontuação (como "123.456.789-01") quanto sem pontuação (como "12345678901"). A decisão sobre a formatação ficará a cargo do frontend.

2. **Envio de Dados para o Backend**: Os dados preenchidos serão enviados via JSON para o backend.

3. **Retorno do Backend**:
    - Se a validação for bem-sucedida, o backend retornará um objeto com as seguintes informações:

      ```json
      {
        "id": "Id UUID do usuário",
        "paychecks": 1,
        "name": "Igor de Campos",
        "profession": "REPOSITOR",
        "token": "{token de acesso do usuário}"
      }
      ```

    - O token de acesso permitirá que o usuário acesse outras rotas.

4. **Redirecionamento para a Tela Principal**: Após o login, podemos redirecionar o usuário para a tela principal, exibindo uma frase de boas-vindas. Além disso, usaremos os dados retornados do login para informar o número de contracheques disponíveis.
5. **Atente-se aos erros:** muitos deles estão tratados e o backend pode retornar, como Usuário inexistente e outros.

---

### `POST /tech-spring-solutions/nexus/employees/validate`

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/employees/validate' \
--header 'Content-Type: application/json' \
--data '{
"cpf": "CPF_FUNCIONARIO EXISTENTE",
"password": "SENHA_CRIADA"
}'
```

Nesta rota, o funcionário pode criar sua conta para receber seus contra cheques online. Para isso, ele precisará
especificar o código do cpf e senha, nesse contexto,as informacões restantes desse usuário serão pegos de uma tabela
principal,como se fosse uma tabela de todos os funcionários

<br>

### Exemplo de Frontend: Tela da rota validate (register)

Este é uma descrição de  uma tela interativa para o usuário fazer o registro. Ela contém dois campos: CPF e senha. Após o usuário preencher esses campos, faremos uma pequena validação:

1. **Validação do CPF**: Aceitaremos CPFs tanto com pontuação (como "123.456.789-01") quanto sem pontuação (como "12345678901"). A decisão sobre a formatação ficará a cargo do frontend.

2. **Envio de Dados para o Backend**: Os dados preenchidos serão enviados via JSON para o backend.

3. **Retorno do Backend**:
    - Se a validação for bem-sucedida, o backend retornará uma mensagem indicando que o cadastro foi bem sucedido:

4. **Redirecionamento para a Tela login**: Após o registro, podemos redirecionar o usuário para a tela de login.
5. **Atente-se aos erros:** muitos deles estão tratados e o backend pode retornar, como Usuário inexistente e outros.

---

### `GET /tech-spring-solutions/nexus/paycheck/{userId}`

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/employees/paycheck/ID_DO_USUARIO_AQUI' \
--header 'Authorization: Bearer SEU_TOKEN_AQUI'
```

Nesta rota, o ID do funcionário é recebido através da rota e todos os contra cheques são retornados em ordem de data
para visualização do funcionário. As informações retornadas incluem a data do contra cheque.

<br>

### Exemplo de Frontend: Tela para Visualizar Contracheques

Este é um exemplo de uma tela interativa que permite aos usuários visualizar os nomes dos contracheques.

1. **Botão ou Ícone de Acesso aos Contracheques**: Após o usuário fazer login e ser redirecionado para o menu principal, haverá um botão ou ícone de acessibilidade que permitirá ao usuário ver seus contracheques. Ao clicar nesse elemento, o frontend pegará o ID do usuário e enviará uma requisição para a rota correspondente.

2. **Rota para Listar Todos os Contracheques**:
    - A rota receberá o ID do usuário como parâmetro.
    - O backend retornará os dados de todos os contracheques associados a esse usuário.
    - Os dados serão formatados de maneira interativa e interessante no frontend.

3. **Renderização Simplificada**:
    - após o usuário ter clicado no icone, o frontend renderizará uma versão simplificada das informações desses contracheques.
    - Essa rota específica servirá para exibir detalhes simples de todos os contraCheques.

---

### `GET /tech-spring-solutions/nexus/paycheck/{userId}/{paycheckDate}`

Nesta rota, o ID do funcionário e a data do contra cheque são recebidos. A rota retorna as informações do contra cheque em
bytes, permitindo que a interface do usuário torne visível o contra cheque do funcionário.

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/employees/paycheck/ID_DO_USARIO/DATA_DO_CONTRACHEQUE' \
--header 'Authorization: Bearer TOKEN_AQUI'
```

<br>

### Exemplo de Frontend: Tela para Permitir que o Usuário visualize um contraCheque

1. **id do usuario, ex:**
    - a53cc484-8e68-40a4-8701-47cb38ad0bd6

2. **data do arquivo, seguindo a convensão, ex:**
    - 03-2023  (mes-ano)

3. **Ideia de Tela**:
    - Imagine que o funcionário efetuou o login e está na tela de menu principal.
    - Então o funcionário clicou na opção de pré visualizar os contracheques (já citado em outro endpoint)
    - E ele clica em um contraCheque que ele precisa visualizar
    - O front deve pegar os parametros que o backend precisa: id desse funcionario, o seu token, e a data (que é o nome do contraCheque) e enviar para o backend
    - O endpoint retorna o conteudo do arquivo em bytes,que deve ser formatado e gerado a moda que permite que o funcionário o visualize.

---

## Rotas para Administradores

---

### `POST /tech-spring-solutions/nexus/admin/login`

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/admin/login' \
--header 'Content-Type: application/json' \
--data '{
    "cpf": "58892239090",
    "password": "224654"
}'
```

Esta rota é utilizada para efetuar o login do administrador. A existência do administrador na empresa é verificada com
base no seu nome, profissão e senha.

<br>

### Exemplo de Frontend: Tela para Login do administrador

Este é uma descrição de  uma tela interativa para o usuário fazer login. Ela contém dois campos: CPF e senha. Após o usuário preencher esses campos, faremos uma pequena validação:

1. **Validação do CPF**: Aceitaremos CPFs tanto com pontuação (como "123.456.789-01") quanto sem pontuação (como "12345678901"). A decisão sobre a formatação ficará a cargo do frontend.

2. **Envio de Dados para o Backend**: Os dados preenchidos serão enviados via JSON para o backend.

3. **Retorno do Backend**:
    - Se a validação for bem-sucedida, o backend retornará um objeto com as seguintes informações:

      ```json
      {
        "name": "Igor de Campos",
        "token": "{token de acesso do usuário}"
      }
      ```

    - O token de acesso permitirá que o administrador acesse e use recursos mais avançados .

4. **Redirecionamento para a Tela Principal**: Após o login, podemos redirecionar o administrador para a tela principal, exibindo uma frase de boas-vindas.
5. **Atente-se aos erros:** muitos deles estão tratados e o backend pode retornar, como Usuário inexistente e outros.

---

### `POST /tech-spring-solutions/nexus/admin/{userId}/{paycheckDate}`

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/admin/USER_ID/DATA_DO_CONTRA_CHEQUE' \
--header 'Authorization: Bearer TOKEN_AQUI' \
--form 'file=@"CAMINHO_DO_ARQUIVO"'
```

`multipart`: contra cheque

Nesta rota, o arquivo do contra cheque é enviado como um multipartFile para o funcionário especificado pelo ID e a data do contra cheque.

1. **id do usuario, ex:**
    - a53cc484-8e68-40a4-8701-47cb38ad0bd6

2. **data do arquivo, seguindo a convensão, ex:**
    - 03-2023  (mes,ano)

3**Arquivo pdf.**

<br>

### Exemplo de Frontend: Tela para Enviar contraCheque de um funcionário

3. **Ideia de Tela**:
    - Imagine que o administrador efetuou o login e está na tela de menu principal.
    - O administrador pode ter um botão que ao clicar,abre uma tela em que ele pode preencher os campos: data do contraCheque(formato: MM-AAAA), nome do usuário  e arquivo do contraCheque no formato PDF.
    - Então o Frontend deve,por outros meios,obter o id do Funcionário, e utilizar de todos esses dados para enviar para o backend
    - Atente-se as mensagens de retorno

---

### `GET /tech-spring-solutions/nexus/admin/employees`

```bash
curl --location 'http://localhost:8085/tech-spring-solutions/nexus/admin/employees' \
--header 'Authorization: Bearer SEU_TOKEN_AQUI'
```

Esta rota é utilizada para obter uma lista de todos os funcionários. O retorno incluirá o ID, nome do funcionário e profissão.

<br>

### Exemplo de Frontend: Tela para Mostrar pré informações dos funcionários


1. **Obtendo o Token da Rota de Login**:
    - Após o administrador fazer o login, o token deve ser armazenado no frontend de tal forma que ele possa ser usado em rotas como esta.

2. **Retorno do Backend**:
    - Após enviar a solicitação para o backend com o token de administrador (que não está expirado, pois o token dura 24 horas), o retorno será o seguinte:
      ```json
      [
         {
            "id": "375b8220-819d-4ebb-a2d6-4d2be54ace75",
            "name": "admin",
            "profession": "GERENTE_DE_LOJA"
         },
         {
            "id": "5f44c9ca-2a18-48dd-8d21-c534739bd72f",
            "name": "Igor de Campos",
            "profession": "REPOSITOR"
         }
      ]
      ```
    - O token de acesso permitirá que o administrador acesse e utilize recursos mais avançados.

3. **Ideia de Tela**:
    - Imagine que o administrador efetuou o login e está na tela de menu principal.
    - O administrador pode ter um botão que permite obter essas informações e renderizá-las na tela do frontend para ele.

---

### `PUT /tech-spring-solutions/nexus/admin/{userId}/{paycheckDate}`

```bash
curl --location --request PUT 'http://localhost:8085/tech-spring-solutions/nexus/admin/ID_DO_USUARIO/DATA_CONTRACHEQUE' \
--header 'Authorization: Bearer TOKEN_BEARER AQUI' \
--form 'DIRETORIO_DO_ARQUIVO'
```

Nesta rota, o ID do funcionário e a data do contra cheque são recebidos, juntamente com um novo arquivo de contra cheque.
Com base nessas informações, o contra cheque especificado é atualizado.

<br>

### Exemplo de Frontend: Tela para Permitir que o administrador atualize um contraCheque

1. **id do usuario, ex:**
    - a53cc484-8e68-40a4-8701-47cb38ad0bd6

2. **data do arquivo, seguindo a convensão, ex:**
    - 03-2023  (mes-ano)

3. **Ideia de Tela**:
    - Imagine que o administrador efetuou o login e está na tela de menu principal.
    - O administrador pode ter um botão que permite inserir o nome do funcionária e o mês do contraCheque e o arquivo do contracheque
    - O front deve utilizar outros métodos para pegar o id desse funcionário pelo nome, e enviar uma requisição para o backend
    - Atente-se as mensagens de retorno.

---

### `DELETE /tech-spring-solutions/nexus/admin/{userId}/{paycheckDate}`

```bash
curl --location --request DELETE 'http://localhost:8087/tech-spring-solutions/nexus/admin/{ID_DO_USUARIO_AQUI}/{DATA_DO_ARQUIVO}' \
--header 'Authorization: Bearer TOKEN_BEARER_ADMIN'
```

<br>

### Exemplo de Frontend: Tela para Permitir que o administrador delete um contraCheque

1. **id do usuario, ex:**
    - a53cc484-8e68-40a4-8701-47cb38ad0bd6

2. **data do arquivo, seguindo a convensão, ex:**
    - 03-2023  (mes,ano)

3. **Ideia de Tela**:
    - Imagine que o administrador efetuou o login e está na tela de menu principal.
    - O administrador pode ter um botão que permite inserir o nome do funcionária e o mês do contraCheque
    - O front deve utilizar outros métodos para pegar o id desse funcionário pelo nome, e enviar uma requisição para o backend