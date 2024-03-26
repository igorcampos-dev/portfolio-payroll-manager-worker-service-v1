# WBC GATEWAY API

The purpose of this application is to facilitate and abstract communication with the WBC and its different forms of communication.
In certain scenarios, WBC utilizes either SOAP or REST protocols for their API. If an API is not available, RPA is used to communicate asynchronously with the [wbc-gateway-worker](https://github.com/Matrix-Energia/matrix.wbc.gateway.worker) application for automation purposes.

## 🚀 Start

These instructions will guide you in obtaining a copy of the project on your local machine for development and testing.

### 📋 Requirements

You should have Java version 17 installed and a version of PostgresSQL 15

### 🐳 Docker

You can execute it by utilizing Docker containers. Here are some examples of how to run the project using Docker.

Firstly, ensure that Docker is installed and running on your computer. Then open the terminal on your computer go to the root folder of the project and execute the command:

``` shell
docker-compose up
```

In case you already have a running instance of PostgreSQL at port 5432, you may directly run the image by executing the following command:
- To build an image with a tag:
``` shell
docker build . -t wbc-api     
```

- To run the image:
``` shell
docker run --net=host wbc-api    
```

## Swagger
It's possible to se all the endpoints and their descriptions by going to the Swagger URL

- **[Local](http://localhost:8080/swagger)**
- **[Dev](https://dev-app.matrixenergia.com/contra-cheque/swagger-ui/index.html)**