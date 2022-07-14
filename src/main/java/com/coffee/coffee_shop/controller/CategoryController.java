package com.coffee.coffee_shop.controller;

import com.coffee.coffee_shop.model.Category;
import com.coffee.coffee_shop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/parents/{id}")
    public List<Category> findAllByParent(@PathVariable Long id) {
        return categoryService.findAllByParent(id);
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping()
    public Category addCategory(@RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteById(id);
    }
}
