package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //-> META-INF -> persistence.xml -> <persistence-unit name="hello">
        //-> 애플리케이션 로딩시점에 딱 한개만 만들어주어야 한다. 디비 당 한개
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //커넥션, 여러 쓰레드에서 같이 쓰면 장애가 난다.(주의)
        EntityManager em = emf.createEntityManager();
        //트랜잭션, JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        //트랜잭션 시작
        tx.begin();

//        //모든 데이터를 변경하는 작업은 JPA에서는 트랜잭션안에서 작업을 해주어야 한다.(절대중요)
//        Member member = new Member();
//        member.setId(2L);
//        member.setName("HelloB");
//        //저장
//        em.persist(member);

//        //찾기
//        Member findMember = em.find(Member.class, 1L);
//        System.out.println("Member id = " + findMember.getId());
//        System.out.println("Member name = " + findMember.getName());

//        //전체리스트 찾기
//        List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                //.setFirstResult(1) -> 1번부터
//                //.setMaxResults(10) -> 10개 가져와. 페이징
//                .getResultList();
//
//        for (Member member : result) {
//            System.out.println("member.name = " + member.getName());
//        }

//        //삭제 1.찾은 후 -> 2. 삭제
//        Member findMember = em.find(Member.class, 1L);
//        em.remove(findMember);

//        //수정 1. 찾은 후 -> 2. 수정
//        Member findMember = em.find(Member.class, 1L);
//        findMember.setName("HelloWorld");

//        //비영속
//        Member member = new Member();
//        member.setId(103L);
//        member.setName("Hello103");
//        //영속
//        System.out.println("==BEFORE==");
//        em.persist(member);
//        System.out.println("==AFTER==");

//        //엔티티 조회(영속)
//        //이런 경우, findMember1 에서 조회 할 때 select 가 날라간다.
//        //-> 하지만, findMember2 는 조회 할 때 select 가 날라가지 않는다.
//        //-> findMember1을 셀렉트로 실행 후 1차 캐시에 남아있기 때문에 findMember2는 1차캐시에서 가져온다.
//        Member findMember1 = em.find(Member.class, 103L);
//        Member findMember2 = em.find(Member.class, 103L);

//        // -> 이 경우 true를 반환.
//        // -> 영속 엔티티를 보장해준다. 자바컬렉션이랑 똑같이.
//        System.out.println(findMember1 == findMember2);

//        //엔티티 등록(영속)
//        Member member1 = new Member(150L, "A");
//        Member member2 = new Member(160L, "B");
//        //이 순간에 DB에 저장되는 것이 아닌, 영속성 데이터베이스에 차곡차곡 쌓인다.
//        // DB에 저장되는 순간은 tx.commit() 을 지나면 저장된다.
//        em.persist(member1);
//        em.persist(member2);
//        System.out.println("=========================");

//        //엔티티 수정(영속)
//        //-> 데이터를 찾은 후
//        Member member = em.find(Member.class, 150L);
//        //-> 데이터를 변경
//        member.setName("ZZZZZZZ");

//        //엔티티 삭제(영속)
//        Member memberA = em.find(Member.class, 150L);
//        em.remove(memberA);

//        //플러시(flush)
//        // -> 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
//        // -> 쌓아둔 UPDATE,DELETE,INSERT 가 DB에 날라가는 것.
//        // -> 플러시 하는 방법 : 1. em.flush() 직접호출
//        // -> 2. 트랜잭션 커밋 : 플러시 자동 호출
//        // -> 3. JPQL 쿼리 실행 : 플러시 자동 호출
//        // -> 영속성 컨텍스트를 비우지 않는다.
//        // -> 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
//        // -> 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 된다.
//
//        Member member = new Member(203L, "Member203");
//        em.persist(member);
//        em.flush();
//
//        System.out.println("----------------------------------------");


//        //준영속 상태 (detach)
//        // -> em.detach(entity); 특정 엔티티만 준영속 상태로 변환
//        // -> em.clear(); 영속성 컨텍스트를 완전히 초기화
//        // -> em.close(); 영속성 컨텍스트를 종료
//        //영속
//        Member member = em.find(Member.class, 100L);
//        // -> 데이터 변경
//        member.setName("AAAAAAA");
//        // detach를 쓰면, jpa에서 더이상 관리하지 않는다. (영속성 컨텍스트에서 제외)
//        em.detach(member);

        //커밋
        tx.commit();

        em.close();

        emf.close();

// 정석 코드(try ~ catch ~ finally)
//        try{
//            Member member = new Member();
//            member.setId(3L);
//            member.setName("HelloC");
//
//            em.persist(member);
//            tx.commit();
//        }catch (Exception e){
//            tx.rollback();
//        }finally {
//            em.close();
//        }
//        emf.close();

    }
}
