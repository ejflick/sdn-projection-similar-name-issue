package com.example.example;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@ToString(exclude = {"friends"})
@Node(primaryLabel = "Person")
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  @Id
  private long id;

  @Property
  private String name;

  @Property
  private boolean eatsDoritos;

  @Property
  private boolean friendsAlsoEatDoritos;

  @Relationship
  private Set<Person> friends = new HashSet<>();

  public Person(String name) {
    this.name = name;
  }

  public interface PropertiesProjection {

    boolean getEatsDoritos();

    // If you comment this out, tests will pass and the relationships won't get persisted:
    boolean getFriendsAlsoEatDoritos();

  }
}
