package ru.krasovskii.onlineshope.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.krasovskii.onlineshope.dto.ProductDto;
import ru.krasovskii.onlineshope.entities.Product;
import ru.krasovskii.onlineshope.exceptions.AppError;
import ru.krasovskii.onlineshope.exceptions.ResourceNotFoundException;
import ru.krasovskii.onlineshope.services.CartService;
import ru.krasovskii.onlineshope.services.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    public List<ProductDto> findAllProducts(){
        return productService.faindAll().stream().map(p->new ProductDto(p.getId(), p.getTitle(), p.getPrice())).collect(Collectors.toList());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> findById(@PathVariable Long id){
//        Optional<Product> product=productService.findById(id);
//        if(!product.isPresent()){
//            ResponseEntity<AppError> err=new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),"Product not found, id:"+id), HttpStatus.NOT_FOUND);
//            return err;
//        }
//        return new ResponseEntity<>(product.get(),HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id){
        Product p=productService.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found, id:"+id));
        return new ProductDto(p.getId(), p.getTitle(), p.getPrice());
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }
}
