package model.entities;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @ToString
@EqualsAndHashCode
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
