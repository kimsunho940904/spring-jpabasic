package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;
    @OneToOne(mappedBy = "locker") // 읽기 전용이 된다.
    private Member member;
}
