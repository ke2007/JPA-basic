package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class JpaMain {
    public static void main(String[] args){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); //애플리케이션 로딩시점에 딱 한개만 만들어야함 //DB당 한개

        EntityManager em = emf.createEntityManager(); //일관적인 단위작업을 할때마다 엔티티매니저를 만들어줘야함

        EntityTransaction tx = em.getTransaction();
        tx.begin(); //JPA는 트랜잭션을얻어야 DB와 연결할수있다 begin 으로 트랜잭션을 시작할수있다


        //code
        try { //정상실행
/* 영속성관리 - 내부 동작 방식
            Member findMember = em.find(Member.class, 1L); // 멤버 찾기

//            System.out.println("findMember = " + findMember.getId());   // findMember = 1
//            System.out.println("findMember = " + findMember.getName());  // findMember = HelloA

//            em.remove(findMember); //삭제
            findMember.setName("HelloJPA"); //수정             em.persist(findMember); 안해도된다!
            // ㄴ> JPA를 통해서 엔티티를 가져오면 관리를 하게 되는데 변경여부를 트랜잭션 커밋하는 시점에 확인을 하고 변경이 확인되면 UPDATE 쿼리를 만들고 날림!
            //조건이 있는 조회는? JPQL로 해야한다
            List<Member> result = em.createQuery("SELECT m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member1 : result) {
                System.out.println("member1 = " + member1.getName());
            }

            Member findMember = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L); //쿼리가 한개만 나감 ! //1차캐시에서 가져오기때문

            System.out.println(findMember == findMember2); // : ~ true ~ 같은 트랜잭션 내에서 영속 엔티티의 동일성 보장

            ==== BEFORE ====
            ==== AFTER ====
            Hibernate:
                 insert hellojpa.Member
                     insert
                    into
                        Member
                        (name, id)
                    values
                        (?, ?)

            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);  //차곡차곡 엔티티도 쌓이고 쿼리도 쌓인다 . 쿼리를 쓸때마다 커밋된다면 최적화를 할 여지가 없는데 , 이 타이밍에 버퍼링을 사용가능한 이점을 가질수있다
            System.out.println("==============쿼리확인 선==============="); //선 이후에 쿼리가 나가는걸 확인할 수 있다

            더티체킹(변경감지)


            Member member = new Member(203L, "member203");
            em.persist(member);

            쿼리를 미리 보고싶다 (강제호출)
            em.flush(); //쿼리를 바로 반영 !! 1차캐시는 그대로 다 유지됩니다.
              변경감지란 DB트랜잭션을 커밋하는 시점에 내부적으로 FLUSH 라는게 호출되는데 1차캐시안에 있는 엔티티와 스냅샷을 비교한다. 최초로 읽어온(최초로 1차캐시에 들어온 엔티티)값을 스냅샷에 저장해놓고
              DB트랜잭션 을 커밋할때 FLUSH가 호출되는 시점에 JPA 가 엔티티와 스냅샷을 비교함. 바뀐값이 있다면 UPDATE 쿼리를 쓰지지연SQL저장소에 만들어주고나서 UPDATE쿼리를 데이터베이스에 반영하고 커밋함 !
              JPQL 쿼리 실행시 FLUSH 가 자동으로 호출이 된다. "실수로라도 쿼리가 반영되지않았을 때를 대비해서 "

            em.detach(member); //영속성 컨텍스트(논리적인 개념)에서 제외시키기 ( 준영속 상태, detach 된 상태 )
            em.clear();//entity manager를통해 영속성 컨텍스트 안에있는 모든걸 제외시킴 , 1차캐시를 모두 비움

            System.out.println("==============================");
            tx.commit(); //DB에 반영 필수 !! // 트랜잭션 커밋시점에  쓰지지연 SQL 저장소에 있던 엔티티들이 FLUSH되면서 쿼리가 날아감! 이후 실제 데이터베이스 트랜잭션에 커밋
            트랜잭션이 커밋되면 FLUSH가 무조건 발생함 이때 변경감지가 일어나고ㅡ 수정된 엔티티를 쓰기지연  SQL저장소에 등록함, 쓰기지연SQL저장소의 쿼리를 데이터베이스에 전송(등록,수정,삭제 쿼리).
            FLUSH가 발생한다고해서 트랜잭션이 커밋되는게 아니고 쿼리를 보낸후 커밋됨

            직접 영속성 컨텍스트를 플러시하는방법

 */
            /*
            * 엔티티 매핑 - 기본 키 매핑
            * */

//            Member member = new Member();
////            member.setId("ID_A"); //직접 ID를 만들어서 사용한다면
//            member.setUsername("C");
//
////            em.persist(member)
//            Team team =new Team();
//            team.setName("Team_A");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeamId(team.getId());
//            em.persist(member); //select * from member m join team t on m.team_id = t.team_id; ANSI 표준 조인쿼리
//            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId); //둘이 연관관계가 없기때문에  객체를 테이블에 맞추어 표현하면  상당히 귀찮고 객체지향 스럽지않다..

            // 객체지향 모델링 (ORM 매핑) 저장

            Team team = new Team();
            team.setName("TeamB");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member3");
//            member.setTeam(team); //JPA 가 알아서 team 에서 PK 값을 꺼내서 INSERT 할때 FK 값으로 사용
            member.setTeam(team); // TEAM_ID = 1 값이 들어감.
            em.persist(member);

//            team.getMembers().add(member); // 값이 DB에 안들어갈거같은데 ..? 가짜매핑이기때문에 JPA가 관리 하지않음. DB의 TEAM_ID에 값이 안들어감(Null). 따라서 값을 넣으려면 ? 연관관계의 주인인 member.setTeam(team); 해야함
            //근데 JPA의 입장에서나 그렇지 객체지향 관점에선 두개 다 적는게 맞다

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers(); //members의 데이터를 끌고오는 시점에 select 쿼리가 날아가서 DB에서 값을 가져옴. 따로 값을 team.getMembers().add(member); 이렇게 넣을필요가없다.
            // 그렇다고 넣지않는다는것은 객체지향 스럽지 않다..
            // 심지어 두 군데에서 문제가 발생한다 .
            // 첫 번쨰로.
            // 완전히 em.flush, clear 하면 team.getMembers().add(member) 해도 문제가 없다.
            // 근데 flush와 clear가 없을때는? ( 쿼리가 쓰기지연 SQL 저장소에서 안나가서 DB에 값이 안들어 갔을때는? )
            // 해당 member collection에는 JPA가 DB에서 끌어올 값이 없다. -> select쿼리가 안나간다  -> jpa가 끌고와서 조회가 안된다.
            // 양쪽에 넣어야겠지?
            // 두 번째로는
            // JPA없이 순수 자바코드로 테스트케이스를 작성해야하는데
            // 따로 저장된 값이 없으니 테스트를 할수가 없다.
            // 따라서 양쪽에 전부 값을 세팅 해줘야겠지??
            // 근데 까먹으면 어떡하지
            // 연관관계 편의 메서드 를 만들자 Member.setTeam()에 team.getMembers().add(member); 작성

            //무한루프 조심
            // toString, lombok, JSON라이브러리등...
            // JSON 라이브러리 같은경우 DTO를 따로 생성해서 거기서 처리를하자

            // 단방향 매핑만으로도 이미 연관관계 매핑은 완료된다.
            // 양방향 매핑은 반대방향으로 조회(객체 그래프 탐색) 기능이 추가된것 뿐이다.
            // 양방향은 필요할때 추가해도 되므로(테이블에 영향을 주지않음) JPQL 에서 역방향으로 탐색할 일이 많다.
            // 단방향 매핑을 잘해두면 양방향 매핑은 테이블 손댈 필요없이 코드 몇줄만 더 적으면된다 !!
            // 객체입장에선 양방향매핑이 고민거리만 많아진다..
            // 매핑 관점에선 단방향 매핑만으로도 매핑이 된다.
            // 1. 설계시 기본적 마인드셋
            // 단방향 매핑으로 다 끝낸다
            // 1 대 N 관계일때 N인 쪽에 연관관계 매핑을 쫙~ 바르고 설계를 끝낸다.
            // 실제 개발단계에 들어가서 양방향 매핑을 고민해도 늦지 않는다.
            // ※ 연관관계의 주인은 외래 키의 위치를 기준으로 정해야한다.
            // ※ 비즈니스 로직을 기준으로( 제일 중요한 로직이라던지 ) 연관관계의 주인을 선택하면 안된다.!!!!!무조건 외래키의 위치를 기준으로!!

            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }


            em.flush(); // 쿼리확인
            em.clear();
            // 객체지향 모델링 (ORM 매핑) 조회


//            Member findMember = em.find(Member.class, member.getId()); //양방향 연관관계
//
//            List<Member> members = findMember.getTeam().getMembers(); // findMember (Member) 에서 team으로 team 에서 Memeber 로
//
//            for (Member m : members) {
//                System.out.println("m = " + m.getUsername());
//
//            }
//            Team findTeam = findMember.getTeam(); //꺼내서 바로사용
//            System.out.println("findTeam = " + findTeam.getName());

            //수정 (DB에 100번이 있다는 가상의 예제)
//            Team newTeam = em.find(Team.class, 100L);//PK가 100L 이 있다고 가정

//            findMember.setTeam(newTeam); //찾은 팀을 newTeam 으로 바꾸고싶다 UPDATE 쿼리가 나감. FK가 업데이트됨
                
            tx.commit(); //자동으로 호출
        } catch (Exception e) { //에러나면 롤백
            System.out.println("==========에러발생===========");
            tx.rollback();
        }
        finally { //다쓰고나서 무조건 엔티티 매니저 닫기
            System.out.println("==========엔티티매니저 종료===========");
            em.close();
        }

        emf.close(); //리소스 릴리즈를 위한 팩토리매니저 종료
    }
}
