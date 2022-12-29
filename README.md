# Producing and consuming messages with Spring Boot and RabbitMQ 
The following was discovered as part of building this project:

## Setting up
You will have to install rabbitMQ locally. If you are on MAC, you can install it using homebrew.
Check their website to find an installation that better fits you.
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/3.0.1/reference/htmlsingle/#messaging.amqp)

The application.properties has the RabbitMQ connections properties and the retry rules.
Check them out to make sure they match with your environment.

This project was tested using OpenJDK 17. You can check more versions and details in the build.gradle file.

## Usage
Now that you have RabbitMQ up and running, you can run this application and start sending messages.
There are two endpoints that will help you with it:
 * This will send a successful message:
   * http://localhost:8080/send-message/success?message=your%20message%20here


 * This will send a message that will fail, exhaust all tries, then will be sent to the dead letter:
   * http://localhost:8080/send-message/error?message=your%20message%20here 