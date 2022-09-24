package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "USER") //쿼리가 나갈때 유저 테이블에 INSERT 쿼리가 나감
public class Member {

    public Member(){ // JPA는 기본생성자가 필요함

    }

    @Id
    private Long id;

//    @Column(name = "username") //이러면 (username, id)로 쿼리가 나감
    private String name;

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
