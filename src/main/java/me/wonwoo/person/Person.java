package me.wonwoo.person;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by wonwoolee on 2017. 10. 3..
 */
@Document
@Data
public class Person {
  @Id
  private String id;

  Person() {
  }

  public Person(String name) {
    this.name = name;
  }

  private String name;

}
