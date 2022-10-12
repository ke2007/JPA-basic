package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  //InheritanceType.SINGLE_TABLE 전략 -> Dtype으로 구분, 성능상 이점을 가져갈수있다.
//@DiscriminatorColumn //InheritanceType.SINGLE_TABLE의 경우 해당어노테이션이 없으면 Dtype이 필수로 생성됨 운영상 DTYPE은 있는게 항상 좋다.~
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int Price;

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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
