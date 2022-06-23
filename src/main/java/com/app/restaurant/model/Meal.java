package com.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.app.restaurant.model.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meal {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;
    private String component;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="categoryId")
    private Category category;
    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "meals")
    private List<Order> orders = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(id, meal.id) &&
                Objects.equals(name, meal.name) &&
                Objects.equals(price, meal.price) &&
                Objects.equals(component, meal.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, component);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", component='" + component + '\'' +
                '}';
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	
}
