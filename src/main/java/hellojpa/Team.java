package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id@GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //mappedBy => "나는 뭐랑 연결되어있는거지?(나의 반대편엔 team 으로 매핑이 되어있어)
    //연관관계의 주인과 mappedBy 의 존재이유
    // ※매우 중요 & 이해하기 어려움
    // 객체와 테이블관의 연관관계를 맺는 차이를 이해해야한다.

    // 객체 연관관계 = 2개 단방향 두개를 써서 양방향을 표현(사실 단방향 두개 ㅋ)
    // Member -> Team (단방향)
    // Team -> Member (단방향)

    // 테이블 연관관계 = 1개
    // Member <-> Team (양방향) FK 값 하나로 양쪽의 연관관계 표현이 끝난다

    //반면 객체지향세계에선 참조 가 양쪽에 다 있어야한다

    //===== 여기까지가 배경 =====

    // Q . 근데 객체지향 세계에선 누구를(team ?.. List members?...) FK 값으로 관리해야하지..?
    // 따라서 국룰을 만들었다.
    // 연관관계의 주인 (Owner) - 양방향 매핑에서 나오는 개념
    // 객체의 두 관계(Member에 있는 team , Team 에 있는 List members) 중 누가 주인이 될래?
    // 연관관계의 주인 만이 외래키를 관리(등록, 수정)
    // 주인이 아닌쪽은 읽기만 가능
    // 주인은 mappedBy 속성을 사용하면 X ( 나는 매핑 되어버렸어~ <- 이건 주인의 대사가 아니다)
    // 주인이 아니라면 mappedBy 속성으로 주인이 누구인지 지정해줘야한다.
    // member 에서 joinColumn 으로 DB와 매핑을 했다( =관리한다 )
    // Team 의 list members 의 mappedBy ="team" <- 해당 team은 members 의 Team team 에의해 관리가 된다 는 뜻
    // List members에 뭐 넣고 난리를 쳐봤자 DB에선 바뀌지않는다. 읽기만 된다, 대신 조회는 가능. JPA 가 Team.getMembers 로 조회는됨. 업데이트를 할때는 관리하는 Team team 만 참조
    // ※ 외래키가 있는곳을 주인으로 정해라 !!
    // OneToMany 집어넣은 곳에 (mappedBy = "주인") !!! -> 가짜매핑

    private List<Member> members = new ArrayList<>(); //member 와의 연관관계 매핑

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
