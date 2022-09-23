package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.swing.plaf.basic.BasicBorders;
import java.util.List;


public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //애플리케이션 로딩시점에 딱 한개만 만들어야함 //DB당 한개

        EntityManager em = emf.createEntityManager(); //일관적인 단위작업을 할때마다 엔티티매니저를 만들어줘야함

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //JPA는 트랜잭션을얻어야 DB와 연결할수있다 begin으로 트랜잭션을 시작할수있다



        //code

        Member member = null;

        try { //정상실행

            Member findMember = em.find(Member.class, 1L); // 멤버 찾기

//            System.out.println("findMember = " + findMember.getId());   // findMember = 1
//            System.out.println("findMember = " + findMember.getName());  // findMember = HelloA

//            em.remove(findMember); //삭제
            findMember.setName("HelloJPA"); //수정             em.persist(findMember); 안해도된다!
            // ㄴ> JPA를 통해서 엔티티를 가져오면 관리를 하게 되는데 변경여부를 트랜잭션 커밋하는 시점에 확인을 하고 변경이 확인되면 UPDATE쿼리를 만들고 날림!
            //조건이 있는 조회는? JPQL로 해야한다
            List<Member> result = em.createQuery("SELECT m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member1 : result) {
                System.out.println("member1 = " + member1.getName());
            }

            tx.commit();
        } catch (Exception e) { //에러나면 롤백

            tx.rollback();
        }
        finally { //다쓰고나서 무조건 엔티티 매니저 닫기
            em.close();
        }

        emf.close();
    }
}
