## # 

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
