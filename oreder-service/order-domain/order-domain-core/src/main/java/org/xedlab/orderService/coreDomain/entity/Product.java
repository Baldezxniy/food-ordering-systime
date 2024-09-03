package org.xedlab.orderService.coreDomain.entity;

import org.xedlab.commonDomain.entity.BaseEntity;
import org.xedlab.commonDomain.valueobject.Money;
import org.xedlab.commonDomain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}
