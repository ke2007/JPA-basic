package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@Table(name = "USER") //쿼리가 나갈때 유저 테이블에 INSERT 쿼리가 나감
//기본적으로 클래스 이름과 같은 테이블을 사용한다. 같은이름이 있는경우는 별도로 지정해서 사용.
//하지만 테이블을 다른이름으로 사용하고싶다면?
//@Table(name = "MBR")// 이렇게 하면 MBR 이라는 테이블과 매핑, 인자로 카탈로그, 스키마 매핑가능, 유니크제약조건도 가능
//@Table(uniqueConstraints = )// 유니크제약조건은 이 방식이 더 선호됨.
//@SequenceGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        sequenceName = "MEMBER_SEQ", //매핑할 DB시퀀스 이름
//        initialValue = 1, allocationSize = 50) // initialValue = DDL생성시에만 사용됨  처음 시작하는 수를 지정한다,
//        allocationSize = 시퀀스 한번 호출에 증가하는 수 (성능 최적화에 사용됨) 매번 호출하면 네트워크를 계속 쓰게되니 미리 50개를 DB에 먼저 올려놓고 메모리에서 1씩  사용. <- 반복.
public class Member extends BaseEntity {
/*엔티티 매핑 - 필드와 컬럼 매핑
    @Id //(PK 매핑
    private Long id;

    @Column(name = "username",updatable = true,nullable = false) //객체는 name 이라고 쓰고싶은데 DB 에는 username 이라고 써야한다면 이렇게 작성 (데이터베이스 컬럼명은 username이야 //insertable 컬럼을 수정했을때 인서트를 할꺼야 말꺼야 , updatable update문이 나갈때 해당컬럼을 변경 할거야말거야~
    private String name; //등록하고 변경은 절대하면안된다? updatable을 false로 두면 절대로 변경되지않는다. DB에서 강제로 업데이트하면 바뀌긴하지만 어플리케이션에선 바뀌지않음.
    //nullable Null 제약조건이 붙는다,
    private Integer age; //DB 에도 숫자타입으로 지정해짐

    @Enumerated(EnumType.STRING) //enum타입을 쓰고싶을때 사용 기본이 ORDINAL인데 쓰면 안됨!! 왜냐면 변경사항이 추가되었을때 바뀌지 않기때문!! 0(user) ,1(ADMIN), 0(GUEST *추가됨) 으로 나오기때문.
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //생성일자. DATE, TIME, TIMESTAMP(날짜시간 두개다포함) JAVA8  이후  LocalDate 와 localDateTime 이 들어온이후 잘 쓰지않는 어노테이션
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)//DATE, TIME, TIMESTAMP(날짜시간 두개다포함)
    private Date lastModifiedDate;

    @Lob //지정할 수 있는 속성이 없다.
    private String description; //varchar 를 넘어가는 컨텐츠의 경우 Lob 을사용 //문자타입은 Clob 이 기본, 나머지는 다 Blob

    @Transient// 뭔가 메모리에서만 임시로 쓰고싶다, 캐시데이터를 넣어둔다던지 DB 신경쓰고싶지않아..이럴때 사용
    private int temp; //
*/
    /*
    *엔티티매핑 - 기본키 매핑
    * 기본 키 (PK) 제약 조건 : null 아님, 유일, 변하면안된다(서비스의 전체기간 9억년동안 변하면 안된다.)
    * 9억년 까지 이 조건을 만족하는 자연키(Nature key :비즈니스적으로 의미있는 키 : 전화번호, 주민번호)를 찾기 어렵다. 대리키(대체키 : 비즈니스와 전혀 상관없는 키)를 사용하자.
    * 주민등록번호도 기본 키로 적절하지 않다.(ex: 정부: 주민번호 보관하지 마세요, 기업 : 헉 우리 PK 주민번호 인데 . ex2: 회원을 갖다쓴 나머지 테이블도 FK로 PK(주민번호)들고있었을텐데...그러면 난리나겠죠 ??)
    * 권장 : Long 자료형 + 대체키(시퀀스, UUID) + 키 생성전략 을 조합해서 사용(결론: Auto increment, sequence object 둘중에 하나를 쓰던가 ..회사내의 룰대로..※ 절대로 자연키를 PK로 끌고오지말것 !
    */

//    @Id //직접할당은 @id, 자동생성은 (@GeneratedValue) DB 별로 다름
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //자동생성/ 뭔가 값을 생성하고싶다 GenerationType.AUTO 옵션은 DB 방언 별로 적용 (확인해보고 쓰자)
    @Column(name = "MEMBER_ID")
//    generator = "MEMBER_SEQ_GENERATOR")  //@SequenceGenerator

    //GenerationType.IDENTITY 전략 (strategy): MySQL 에서 사용 기본 키 생성을 데이터 베이스에 위임한다 . 이 전략은 내가 ID에 값을 넣으면안되고(쿼리는 null 로 insert 날아감) DB에 insert 를 해야한다, ==DB에 값이 들어가야 ID값을 알수있다..
    //근데 jpa 는 Entity manager(영속성 컨텍스트)에서 관리되려면 무조건 PK 값이 있어야한다.근데 DB에 넣기전까지 PK 값을 모르는데..?? 이게 무슨 모순 ??
    //JPA 의 울며겨자먹기 해결방법::이 IDENTITY 전략에서만 특수하게 em.persist 를 호출한시점에 바로 INSERT 쿼리가 날아감(원래는 commit 시점에 날아가지만..)

    //GenerationType.SEQUENCE 전략 (strategy) : 주로 Oracle 데이터베이스에서 사용 . SEQUENCE  전략도 마찬가지로 Entity Manager 가 MEMBER_SEQ 에서 PK 값을 끌어옴 그 후에 영속성 컨텍스트에 저장, 쿼리는 날아가지 않음
    private Long id; //Long 인 이유 : int 의 경우 10억이 넘어가면 다시 1부터인데 그때 다시 바꾸느니 차라리 처음부터 Long 을 써라

    @Column(name = "USERNAME" )
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;
    @ManyToOne(fetch = FetchType.EAGER) // FetchType.LAZY team을 프록시 객체를 조회 , 멤버클래스만 DB에서조회.
    @JoinColumn(name = "TEAM_ID") //객체지향 모델링 (ORM 매핑)
    private Team team;



    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 연관관계 편의 메서드, 근데 set 은 이름이 좀 그러니까 (로직이 들어가서) changeTeam이라던지 ..로 바꾸어서 사용자!
        // 좀 더 깊이있게 쓰려면 복잡하다. 체크할게 있나없나 ~
    }

    public Member() { // JPA는 기본생성자가 필요함


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
