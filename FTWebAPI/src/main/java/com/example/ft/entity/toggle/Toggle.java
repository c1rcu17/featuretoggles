package com.example.ft.entity.toggle;

import com.example.ft.entity.rule.Rule;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@NoArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "name", "enabled"})
public class Toggle {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Pattern(regexp = "[a-z-]+", message = "Toggle name can contain only letters a-z and -")
    private String name;

    @NonNull
    private String label;

    @NonNull
    private String description;

    @NonNull
    private Boolean enabled;

    @OneToMany(mappedBy = "toggle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rule> rules = new HashSet<>();

    @Builder
    public Toggle(String name, String label, String description, Boolean enabled) {
        super();
        setName(name);
        setLabel(label);
        setDescription(description);
        setEnabled(enabled);
    }

    @PostUpdate
    private void onPostChange() {
        getRules().forEach(r -> r.mqttNotify(r.getFinalStatus()));
    }

}
