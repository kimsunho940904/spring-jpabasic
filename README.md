# 자바 ORM 표준 JPA 
## # 정석 코드

<pre>
package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setId(1L);
            member.setName("HelloA");

            em.persist(member);
            tx.commit();  // -> 정상적인 경우 commit.
        } catch (Exception e) {
            tx.rollback(); // -> 문제가 생기면 rollback.
        } finally {
            em.close(); // -> db connection close.
        }
        emf.close();
    }
}
</pre>

# JPA
자바 컬렉션 처럼 디비를 다룰 수 있는 기술
## # 객체와 관계형 데이터베이스 매핑하기 (Object Relational Mapping)

## # 영속성 컨텍스트
### # 엔티티 매니저 팩토리(Entity Manager Factory)와 엔티티 매니저(Entity Manager)
1. 엔티티 매니저 팩토리를 통해서 고객의 요청이 올 때 마다 엔티티 매니저를 생성.
2. 엔티티 매니저는 내부적으로 DB Connection을 사용해서 DB를 사용.

### 영속성 컨텍스트
1. 엔티티를 영구 저장하는 환경
2. 엔티티 매니저 안에 영속성 컨텍스트가 있다.
### 엔티티의 생명주기
- 비영속(new/transient)
  - 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
<pre>
Member member = new Member();
member.setId(2L);
member.setName("HelloA");
</pre>
- 영속(managed)
  - 영속성 컨텍스트에 관리되는 상태
<pre>
Member member = new Member();
member.setId(2L);
member.setName("HelloA");

em.persist(member);
</pre>
- 준영속(detached)
  - 영속성 컨텍스트에 저장되었다가 분리된 상태
<pre>
Member member = new Member();
member.setId(2L);
member.setName("HelloA");

em.detach(member); // -> 관계가 제거
</pre>
- 삭제(removed)
  - 삭제된 상태
<pre>
Member member = new Member();
member.setId(2L);
member.setName("HelloA");

em.remove(member); // -> DB에서 날림
</pre>

## 엔티티 조회, 1차 캐시
1. 한번 조회 했던것을 또 조회 하면 디비에 쿼리 안날라감. 단, 동일한 트랜잭션 안에서 한해!
2. 영속성 컨텍스트는 내부에 1차 캐시를 들고 있다.

## 엔티티 등록,  트랜잭션을 지원하는 쓰기 지연
<pre>
EntityManager em = emf.createEntityManager();
EntityTransaction tx = em.getTransaction();

transaction.begin(); // 트랜잭션 시작

em.persist(memberA);
em.persist(memberB);

transaction.commit(); // 트랜잭션 커밋 (커밋하는 순간 INSERT SQL을 보냄)
</pre>

``<property name="hibernate.default_batch_fetch_size" value="10"/>`` // 버퍼링을 모아서, 한방에 인서트를 날릴 수 있다.

## 엔티티 수정, 변경 감지(Dirty Checking)
<pre>
Member member = em.find(Member.class, 1L);
member.setName("update");
</pre>

## 엔티티 삭제
<pre>
Member memberA = em.find(Member.class, "memberA");
em.remove(memberA); //엔티티 삭제
</pre>

## 플러시(flush)
1. 영속성 컨텍스트의 변경내용을 데이터베이스 반영(쿼리들을 디비에 날려주는 것)
2. 영속성 컨텍스트를 비워주지 않는다.
3. 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화 해주는 용도.
4. 트랜잭션이라는 작업 단위가 중요, 트랜잭션 커밋 직전에만 동기화 하면 된다.

## 플러시 발생
1. 변경 감지(Dirty Checking)
2. 수정된 엔티티를 쓰기지연 SQL 저장소에 등록
3. 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)

## 플러시 하는 방법
1. em.flush() - 직접 호출
2. 트랜잭션 커밋 - 플러시 자동 호출
3. JPQL 쿼리 실행 - 플러시 자동 호출

## 준영속 상태
1. 영속 -> 준영속
2. 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
3. 영속성 컨텍스트가 제공하는 기능을 사용 못함.

## 준영속 상태로 만드는 방법
1. em.detach(member); // 특정 엔티티만 준영속 상태로 전환하고 싶을때 사용.(JPA에서 관리 안함. 트랜잭션 해도 아무일도 일어나지 않는다.)
2. em.clear(); // 엔티티 매니저 안에 있는 영속성 컨텍스트를 완전히 초기화
3. em.close(); // 영속성 컨텍스트를 종료
