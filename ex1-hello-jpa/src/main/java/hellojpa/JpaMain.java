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

        //비영속
        Member member = new Member();
        member.setId(100L);
        member.setName("Hello100");
        //영속
        System.out.println("==BEFORE==");
        em.persist(member);
        System.out.println("==AFTER==");


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
