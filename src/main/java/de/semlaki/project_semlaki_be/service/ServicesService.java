package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.dto.ServiceCreateRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.ServiceResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.repository.ServiceRepository;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicesService {

    private final ServiceRepository serviceRepository;
    private final UserService userService;
    private final CategoriesService categoriesService;

    public ServicesService(ServiceRepository serviceRepository, UserService userService, CategoriesService categoriesService) {
        this.serviceRepository = serviceRepository;
        this.userService = userService;
        this.categoriesService = categoriesService;
    }

    public List<ServiceResponseDto> getAllServices() {
        return serviceRepository.findAll()
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public Optional<Services> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public List<ServiceResponseDto> getServicesByCategory(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId)
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ServiceResponseDto> getServicesByUser(Long userId) {
        return serviceRepository.findByUserId(userId)
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public ServiceResponseDto createService(ServiceCreateRequestDto createDto, String userEmail) {
        User authentiicatedUser = userService.findOrThrow(userEmail);
        Categories foundCategory = categoriesService.findOrThrow(createDto.categoryId());

        Services createdService = createDto.toEntity(createDto, authentiicatedUser, foundCategory);
        return ServiceResponseDto.toDto(serviceRepository.save(createdService));
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    public List<ServiceResponseDto> getRandomServices(int count) {
        List<Services> services = serviceRepository.findAll();
        Collections.shuffle(services);
        return services.stream()
                .limit(count)
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ServiceResponseDto> getServicesByTitle(String term) {
        return serviceRepository.findAllByDescriptionOrTitle(term)
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
