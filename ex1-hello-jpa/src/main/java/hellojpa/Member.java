package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity //이 어노테이션을 선언해야 JPA에서 관리.
public class Member {

    @Id //-> PK
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
