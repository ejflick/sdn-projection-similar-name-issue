package com.example.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jOperations;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@DataNeo4jTest
public class PersonNeo4jTest {

  @Test
  public void projectionRespected(@Autowired Neo4jOperations neo4jOperations) {
    final var person = new Person("Bob");
    person.setEatsDoritos(true);
    person.setFriendsAlsoEatDoritos(true);
    person.setFriends(Set.of(new Person("Alice"), new Person("Zoey")));

    neo4jOperations.saveAs(person, Person.PropertiesProjection.class);

    final var saved = neo4jOperations.findById(person.getId(), Person.class);
    assertThat(saved).hasValueSatisfying(it -> assertThat(it.getFriends()).isEmpty());
  }

}
