package me.wonwoo.person;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by wonwoolee on 2017. 10. 3..
 */
@RestController
public class PersonController {

  private final PersonRepository personRepository;

  public PersonController(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @GetMapping("/")
  public Flux<Person> listPeople() {
    return this.personRepository.findAll();
  }

  @PostMapping("/")
  public Mono<Void> createPerson(@RequestBody Publisher<Person> person) {
    return this.personRepository.insert(person).then();
  }

  @GetMapping("/{name}")
  public Mono<Person> getPerson(@PathVariable String name) {
    return this.personRepository.findByName(name);
  }

}
