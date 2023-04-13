package ru.krasovskii.onlineshope.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.krasovskii.onlineshope.entities.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void add(Product product) {
        boolean newProduct=false;
        if(items.size()!=0) {
            for (CartItem item : items) {
                if (item.getProductId() == product.getId()) {
                    item.setQuantity(item.getQuantity() + 1);
                    item.setPrice(item.getPricePerProduct() * item.getQuantity());
                    newProduct = false;
                    break;
                } else {
                    newProduct = true;
                }
            }
        }else {
            newProduct=true;
        }
            if(newProduct) {
                items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
            }
        recalculate();
    }

    private void recalculate() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getPrice();
        }
    }
}
