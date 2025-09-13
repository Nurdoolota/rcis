package com.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class TV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private String model;

    private String screenType;

    private Double price;

    private Integer quantity;

    // Конструкторы
    public TV() {}

    public TV(String manufacturer, String model, String screenType, Double price, Integer quantity) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.screenType = screenType;
        this.price = price;
        this.quantity = quantity;
    }

    // Геттеры и сеттеры (как раньше)

    @Override
    public String toString() {
        return "id=" + id + ", производитель='" + manufacturer + "', модель='" + model + "', тип экрана='" + screenType + "', цена=" + price + ", количество=" + quantity;
    }

    // equals() и hashCode() (сгенерируй в IDE для полей)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TV TV = (TV) o;
        return id.equals(TV.id) && manufacturer.equals(TV.manufacturer) && model.equals(TV.model);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, manufacturer, model);
    }
}