package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.dto.ServiceCreateRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.ServiceResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.Categories;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.User;
import de.semlaki.project_semlaki_be.exception.RestApiException;
import de.semlaki.project_semlaki_be.repository.ServiceRepository;
import de.semlaki.project_semlaki_be.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

    public ServiceResponseDto getServiceById(Long id) {
        return ServiceResponseDto.toDto(findOrThrow(id));
    }

    public Services findOrThrow(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RestApiException("Service not found", HttpStatus.NOT_FOUND));
    }

    public List<ServiceResponseDto> getServicesByCategory(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId)
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public List<ServiceResponseDto> getServicesByUser(String userEmail) {
        User authentiicatedUser = userService.findOrThrow(userEmail);
        return serviceRepository.findByUser(authentiicatedUser)
                .stream()
                .map(ServiceResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public ServiceResponseDto createService(ServiceCreateRequestDto createDto, String userEmail) {
        User authentiicatedUser = userService.findOrThrow(userEmail);
        Categories foundCategory = categoriesService.findOrThrow(createDto.categoryId());

        Services createdService = createDto.toEntity(authentiicatedUser, foundCategory);
        return ServiceResponseDto.toDto(serviceRepository.save(createdService));
    }

    public ServiceResponseDto deleteService(Long id) {
        Services foundService = findOrThrow(id);
        serviceRepository.delete(foundService);
        return ServiceResponseDto.toDto(foundService);
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
