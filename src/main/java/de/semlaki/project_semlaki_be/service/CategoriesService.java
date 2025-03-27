package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.entity.Categories;

import de.semlaki.project_semlaki_be.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {

    private final CategoryRepository categoryRepository;

    public CategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Categories> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Categories createCategory(Categories categories) {
        return categoryRepository.save(categories);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
