package com.jm.controleFinanceiro.controller;


import com.jm.controleFinanceiro.controller.request.CategoryRequest;
import com.jm.controleFinanceiro.controller.response.CategoryResponse;
import com.jm.controleFinanceiro.controller.response.UserResponse;
import com.jm.controleFinanceiro.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('SCOPE_admin:all')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request){
        return categoryService.createCategory(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryResponse> findAll(Pageable pageable){
        return categoryService.findAllCategories(pageable);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse findById(@PathVariable Long id){
        return categoryService.findById(id);
    }
}
