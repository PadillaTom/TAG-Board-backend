package com.padillatom.tagboard.model;

import com.padillatom.tagboard.model.abstracts.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
<<<<<<<< HEAD:src/main/java/com/padillatom/tagboard/model/UserEntity.java
@Getter
@Setter
========
@Builder
@Data
>>>>>>>> develop:src/main/java/com/padillatom/tagboard/model/User.java
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
<<<<<<<< HEAD:src/main/java/com/padillatom/tagboard/model/UserEntity.java
@Where(clause = "deleted = false")
public class UserEntity extends Auditable implements Serializable {
========
public class User extends Auditable implements Serializable {
>>>>>>>> develop:src/main/java/com/padillatom/tagboard/model/User.java

    @Serial
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Builder.Default
    private boolean deleted = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}
