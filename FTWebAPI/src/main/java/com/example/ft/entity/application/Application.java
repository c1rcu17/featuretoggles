package com.example.ft.entity.application;

import com.example.ft.entity.rule.Rule;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "version"}),
        @UniqueConstraint(columnNames = {"username"})
})
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "name", "version", "username"})
public class Application {

    @Transient
    private static PasswordEncoder passwordEncoder;

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Pattern(regexp = "[a-z-]+", message = "Name can contain only letters a-z and -")
    private String name;

    @NonNull
    private String label;

    @NonNull
    @Pattern(regexp = "[0-9.]+", message = "Version can contain only digits 0-9 and .")
    private String version;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rule> rules = new HashSet<>();

    @NonNull
    private String username;

    @NonNull
    private String password;

    @Builder
    public Application(String name, String label, String version, String username, String password) {
        super();
        setName(name);
        setLabel(label);
        setVersion(version);
        setUsername(username);
        setPassword(password);
    }

    @PostUpdate
    private void onPostChange() {
        getRules().forEach(r -> r.mqttNotify(r.getFinalStatus()));
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

}
