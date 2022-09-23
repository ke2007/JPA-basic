package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "USER") //쿼리가 나갈때 유저 테이블에 INSERT 쿼리가 나감
public class Member {

    @Id
    private Long id;

//    @Column(name = "username") //이러면 (username, id)로 쿼리가 나감
    private String name;

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
