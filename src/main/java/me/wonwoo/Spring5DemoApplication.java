package me.wonwoo;

import me.wonwoo.person.PersonHandler;
import me.wonwoo.person.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class Spring5DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(Spring5DemoApplication.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(PersonRepository repository) {
    return args -> repository.deleteAll();
  }

  @Bean
  public RouterFunction<ServerResponse> routingFunction(PersonRepository repository) {
    PersonHandler handler = new PersonHandler(repository);
    return nest(path("/person"),
        nest(accept(APPLICATION_JSON),
            route(GET("/{name}"), handler::getPerson)
                .andRoute(method(HttpMethod.GET), handler::listPeople)
        ).andRoute(POST("/").and(contentType(APPLICATION_JSON)), handler::createPerson));
  }

}
