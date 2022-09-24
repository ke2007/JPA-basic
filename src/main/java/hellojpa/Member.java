package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
//@Table(name = "USER") //쿼리가 나갈때 유저 테이블에 INSERT 쿼리가 나감
//기본적으로 클래스 이름과 같은 테이블을 사용한다. 같은이름이 있는경우는 별도로 지정해서 사용.
//하지만 테이블을 다른이름으로 사용하고싶다면?
//@Table(name = "MBR")// 이렇게 하면 MBR 이라는 테이블과 매핑, 인자로 카탈로그, 스키마 매핑가능, 유니크제약조건도 가능
//@Table(uniqueConstraints = )// 유니크제약조건은 이 방식이 더 선호됨.
public class Member {
    @Id //(PK 매핑
    private Long id;

    @Column(name = "username",updatable = true,nullable = false) //객체는 name이라고 쓰고싶은데 DB에는 username이라고 써야한다면 이렇게 작성 (데이터베이스 컬럼명은 username이야 //insertable 컬럼을 수정했을때 인서트를 할꺼야 말꺼야 , updatable update문이 나갈때 해당컬럼을 변경 할거야말거야~
    private String name; //등록하고 변경은 절대하면안된다? updatable을 false로 두면 절대로 변경되지않는다. DB에서 강제로 업데이트하면 바뀌긴하지만 어플리케이션에선 바뀌지않음.
    //nullable Null 제약조건이 붙는다,
    private Integer age; //DB에도 숫자타입으로 지정해짐

    @Enumerated(EnumType.STRING) //enum타입을 쓰고싶을때 사용 기본이 ORDINAL인데 쓰면 안됨!! 왜냐면 변경사항이 추가되었을때 바뀌지 않기때문!! 0(user) ,1(ADMIN), 0(GUEST *추가됨) 으로 나오기때문.
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) //생성일자. DATE, TIME, TIMESTAMP(날짜시간 두개다포함) JAVA8  이후  LocalDate와 localDateTime이 들어온이후 잘 쓰지않는 어노테이션
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)//DATE, TIME, TIMESTAMP(날짜시간 두개다포함)
    private Date lastModifiedDate;

    @Lob //지정할 수 있는 속성이 없다.
    private String description; //varchar 를 넘어가는 컨텐츠의 경우 Lob 을사용 //문자타입은 Clob 이 기본, 나머지는 다 Blob

    @Transient// 뭔가 메모리에서만 임시로 쓰고싶다, 캐시데이터를 넣어둔다던지 DB 신경쓰고싶지않아..이럴때 사용
    private int temp; //

    public Member(){ // JPA는 기본생성자가 필요함

    }
}
