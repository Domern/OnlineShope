package ru.krasovskii.onlineshope.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.krasovskii.onlineshope.entities.Product;
import ru.krasovskii.onlineshope.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> faindAll(){
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }

}
