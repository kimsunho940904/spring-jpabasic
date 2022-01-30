package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("memberA");
            member.setTeam(team);
            em.persist(member);

            em.flush(); // -> 영속성 컨텍스트에 있는것들을 디비에 다 날린후, 싱크를 맞추고
            em.clear(); // -> 영속성 컨텍스트를 초기화

            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers(); // -> 양방향 연관관계! 멤버에서 팀으로, 팀에서 다시 멤버로!

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
