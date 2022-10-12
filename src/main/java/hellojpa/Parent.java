package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //자동생성/ 뭔가 값을 생성하고싶다 GenerationType.AUTO 옵션은 DB 방언 별로 적용 (확인해보고 쓰자)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL,orphanRemoval = true) // orphanRemoval = true childList 에서 빠지면 바로 빠이빠이 DELETE 쿼리
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

    public List<Child> getChildList() {
        return childList;
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
