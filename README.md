# Feature Toggles
A REST service that enables applications to have toggles to enable/disable features at run-time without deployment

# Technologies Used

REST API: Java + Spring framework + Java JPA (Mysql db) + JWT tokens for authentication

*Messaging*: Eclipse Mosquitto

*Documentation*: Live Swagger UI (Spring integration)

*Tests*: Spring tests (JUnit)

*Deploy*: Docker + Docker Compose

# Install Dependencies

*Docker*:
https://docs.docker.com/install/linux/docker-ce/ubuntu/

*Docker Compose*:
https://docs.docker.com/compose/install/#prerequisites

# To start the project

```sh
git clone https://github.com/c1rcu17/featuretoggles.git
cd featuretoggles
sudo docker-compose -f docker-compose.yml -f prod.yml up
```

The application is configured to reset the database at every restart.

# Web Interface

## Admin Account:

*Username*: admin

*Password*: 1234

## Web frontend for toggles
*Access*: http://127.0.0.1:10001

This frontend let's you test the toggles. After you enter the password, it will fetch the JWT token from the REST API and allow you to use the toggles.

There is also a text area where you can see the published messages on the Mosquitto broker

## REST API documentation:
*Access*: http://127.0.0.1:10001/swagger-ui.html

This documentation allows you to try the api.

* The only endpoint available is POST (/api/auth) to fetch the JWT token.
* There is a button on the Swagger UI that lets you insert the token to try the other endpoints.
* For the admin account, every endpoint is available.
* For application users, only GET (/api/applications/{name}/{version}/toggles) and GET (/api/applications/{name}/{version}/toggles/{toggleName}) is available to retrieve the application toggle status

# Subscribing to mosquitto broker

By installing the package *mosquitto-clients* (apt-get install mosquitto-clients) you can use the folowing commands to simulate live token change notifications:


| Command | Description |
| ------------- | ------------- |
| mosquitto_sub -v -h "mosquitto-ip" -t '#' | subscribe to all events |
| mosquitto_sub -v -h "mosquitto-ip" -t 'applications/main-store/+/+' | subscribe to all toggle changes for main-store application |
| mosquitto_sub -v -h "mosquitto-ip" -t 'applications/main-store/1.0/+' | subscribe to all toggle changes for main-store application 1.0 |
| mosquitto_sub -v -h "mosquitto-ip" -t 'applications/+/+/shiny-buttons' | subscribe to all toggle changes for applications affected by the shiny-buttons toggle |
