package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //애플리케이션 로딩시점에 딱 한개만 만들어야함 //DB당 한개

        EntityManager em = emf.createEntityManager(); //일관적인 단위작업을 할때마다 엔티티매니저를 만들어줘야함

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //JPA는 트랜잭션을얻어야 DB와 연결할수있다 begin 으로 트랜잭션을 시작할수있다


        //code
        try { //정상실행
//
//            Member findMember = em.find(Member.class, 1L); // 멤버 찾기
//
////            System.out.println("findMember = " + findMember.getId());   // findMember = 1
////            System.out.println("findMember = " + findMember.getName());  // findMember = HelloA
//
////            em.remove(findMember); //삭제
//            findMember.setName("HelloJPA"); //수정             em.persist(findMember); 안해도된다!
//            // ㄴ> JPA를 통해서 엔티티를 가져오면 관리를 하게 되는데 변경여부를 트랜잭션 커밋하는 시점에 확인을 하고 변경이 확인되면 UPDATE 쿼리를 만들고 날림!
//            //조건이 있는 조회는? JPQL로 해야한다
//            List<Member> result = em.createQuery("SELECT m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member1 : result) {
//                System.out.println("member1 = " + member1.getName());
//            }

//            Member findMember = em.find(Member.class, 101L);
//            Member findMember2 = em.find(Member.class, 101L); //쿼리가 한개만 나감 ! //1차캐시에서 가져오기때문

//            System.out.println(findMember == findMember2); // : ~ true ~ 같은 트랜잭션 내에서 영속 엔티티의 동일성 보장

//            ==== BEFORE ====
//            ==== AFTER ====
            //Hibernate:
            //    /* insert hellojpa.Member
            //        */ insert
            //        into
            //            Member
            //            (name, id)
            //        values
            //            (?, ?)
//
//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);  //차곡차곡 엔티티도 쌓이고 쿼리도 쌓인다 . 쿼리를 쓸때마다 커밋된다면 최적화를 할 여지가 없는데 , 이 타이밍에 버퍼링을 사용가능한 이점을 가질수있다
//            System.out.println("==============쿼리확인 선==============="); //선 이후에 쿼리가 나가는걸 확인할 수 있다

            //더티체킹(변경감지)


            Member member = new Member(203L, "member203");
            em.persist(member);

            //쿼리를 미리 보고싶다 (강제호출)

            em.flush(); //쿼리를 바로 반영 !! 1차캐시는 그대로 다 유지됩니다.
            //변경감지란 DB트랜잭션을 커밋하는 시점에 내부적으로 FLUSH라는게 호출되는데 1차캐시안에 있는 엔티티와 스냅샷을 비교한다. 최초로 읽어온(최초로 1차캐시에 들어온 엔티티)값을 스냅샷에 저장해놓고
            //  DB트랜잭션 을 커밋할때 FLUSH가 호출되는 시점에 JPA가 엔티티와 스냅샷을 비교함. 바뀐값이 있다면 UPDATE쿼리를 쓰지지연SQL저장소에 만들어주고나서 UPDATE쿼리를 데이터베이스에 반영하고 커밋함 !
            // JPQL 쿼리 실행시 FLUSH가 자동으로 호출이 된다. "실수로라도 쿼리가 반영되지않았을 때를 대비해서 "

            System.out.println("==============================");
            tx.commit(); //DB에 반영 필수 !! // 트랜잭션 커밋시점에  쓰지지연 SQL 저장소에 있던 엔티티들이 FLUSH되면서 쿼리가 날아감! 이후 실제 데이터베이스 트랜잭션에 커밋
            //트랜잭션이 커밋되면 FLUSH가 무조건 발생함 이때 변경감지가 일어나고ㅡ 수정된 엔티티를 쓰기지연  SQL저장소에 등록함, 쓰기지연SQL저장소의 쿼리를 데이터베이스에 전송(등록,수정,삭제 쿼리).
            //FLUSH가 발생한다고해서 트랜잭션이 커밋되는게 아니고 쿼리를 보낸후 커밋됨

            //직접 영속성 컨텍스트를 플러시하는방법
//            tx.commit(); //자동으로 호출
        } catch (Exception e) { //에러나면 롤백
            System.out.println("==========에러떴대요===========");
            tx.rollback();
        }
        finally { //다쓰고나서 무조건 엔티티 매니저 닫기
            System.out.println("==========엔티티매니저 퇴근===========");
            em.close();
        }

        emf.close(); //리소스 릴리즈를 위한 팩토리매니저 종료
    }
}
