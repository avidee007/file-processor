package com.example.fileupload.jpa;

import com.example.fileupload.dto.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PersonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;
  private String name;
  private String age;
  private Gender gender;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonEntity entity = (PersonEntity) o;
    return Objects.equals(id, entity.id) && Objects.equals(name, entity.name) &&
        Objects.equals(age, entity.age) && gender == entity.gender;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age, gender);
  }
}
