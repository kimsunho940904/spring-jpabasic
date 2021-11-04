package hellojpa;

import javax.persistence.*;
import java.util.Date;

//이 어노테이션을 선언해야 JPA에서 관리.
@Entity
//@Table(name = "MBR") // -> MBR이라는 테이블과 매핑을 한다.
public class Member {

    @Id //-> PK
    @GeneratedValue(strategy = GenerationType.AUTO) // -> AUTO INCREMENT와 같은 기능.
    private Long id;
    //    @Column(unique = true, length = 255) -> 제약조건을 줄 수 있다.
    @Column(name = "name") // -> DB Column = name으로 들어간다.
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) // -> Enum 타입
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //-> 날짜 타입 (TIMESTAMP = 날짜시간)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP) //-> 날짜 타입 (TIMESTAMP = 날짜시간)
    private Date lastModifiedDate;

    @Lob //-> 큰 컨텐츠를 넣고싶을때
    private String description;

    @Transient // -> DB랑 연결하지 않는다.
    private int temp;

    public Member() {

    }
}
