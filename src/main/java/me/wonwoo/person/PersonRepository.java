package me.wonwoo.person;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Created by wonwoolee on 2017. 10. 3..
 */
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
  Mono<Person> findByName(String name);
}
