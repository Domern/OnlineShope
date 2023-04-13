package ru.krasovskii.onlineshope.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krasovskii.onlineshope.dto.Cart;
import ru.krasovskii.onlineshope.entities.Product;
import ru.krasovskii.onlineshope.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCatr() {
        return tempCart;
    }

    public void add(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Неудается добавить продукт с id " + productId + " в корзину. Продукт не найден."));
        tempCart.add(product);
    }

}
