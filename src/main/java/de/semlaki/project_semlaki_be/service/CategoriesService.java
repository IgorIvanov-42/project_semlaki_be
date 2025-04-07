package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.dto.CategoryCreateDto;
import de.semlaki.project_semlaki_be.domain.dto.CategoryResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.exception.RestApiException;
import de.semlaki.project_semlaki_be.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriesService {

    private final CategoryRepository categoryRepository;

    public CategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto createCategory(CategoryCreateDto category) {
        Categories newCategory = category.toEntity();
        return CategoryResponseDto.toDto(categoryRepository.save(newCategory));
    }

    public CategoryResponseDto deleteCategory(Long id) {
        Categories foundCategory = findOrThrow(id);
        categoryRepository.delete(foundCategory);
        return CategoryResponseDto.toDto(foundCategory);
    }

    public CategoryResponseDto getCategoryById(Long categoryId) {
        Categories foundCategory = findOrThrow(categoryId);
        return CategoryResponseDto.toDto(foundCategory);
    }

    public Categories findOrThrow(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RestApiException("Category not found", HttpStatus.NOT_FOUND));
    }
}
