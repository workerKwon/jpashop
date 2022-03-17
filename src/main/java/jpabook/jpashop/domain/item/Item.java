package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 구현체를 가지고 할 것이기 때문에(Book, Album, Movie)
    
    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
