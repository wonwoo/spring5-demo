package me.wonwoo.person;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * Created by wonwoolee on 2017. 10. 3..
 */
public class PersonHandler {

  private final PersonRepository repository;

  public PersonHandler(PersonRepository repository) {
    this.repository = repository;
  }

  public Mono<ServerResponse> listPeople(ServerRequest request) {
    Flux<Person> people = repository.findAll();
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(people, Person.class);
  }

  public Mono<ServerResponse> createPerson(ServerRequest request) {
    Mono<Person> person = request.bodyToMono(Person.class);
    Mono<Void> then = repository.insert(person).then();
    return ServerResponse.ok().build(then);
  }

  public Mono<ServerResponse> getPerson(ServerRequest request) {
    String name = request.pathVariable("name");
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    Mono<Person> personMono = this.repository.findByName(name);
    return personMono
        .flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(person)))
        .switchIfEmpty(notFound);
  }
}