package me.wonwoo.person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Created by wonwoolee on 2017. 10. 3..
 */
@RunWith(SpringRunner.class)
@WebFluxTest
public class PersonControllerTests {

  @Autowired
  private WebTestClient webClient;

  @MockBean
  private PersonRepository personRepository;

  @Test
  public void listPeople() {
    given(personRepository.findAll()).willReturn(Flux.just(new Person("wonwoo")));
    this.webClient
        .get()
        .uri("/")
        .exchange()
        .expectBody(new ParameterizedTypeReference<List<Person>>() {
        }).consumeWith(result -> assertThat(result.getResponseBody().get(0).getName()).isEqualTo("wonwoo"));
  }

  //TODO
  @Test
  public void createPerson() {
//    given(personRepository.insert(any(Mono.class))).willReturn(Flux.just(new Person("wonwoo")));
//    this.webClient
//        .post()
//        .uri("/")
//        .body(Mono.just(new Person("wonwoo")), Person.class)
//        .exchange()
//        .expectBody(Void.class).returnResult();
  }

  @Test
  public void getPerson() {
    given(personRepository.findByName(any())).willReturn(Mono.just(new Person("wonwoo")));
    this.webClient
        .get()
        .uri("/{name}", "wonwoo")
        .exchange()
        .expectBody(Person.class).consumeWith(result -> assertThat(result.getResponseBody().getName()).isEqualTo("wonwoo"));
  }
}