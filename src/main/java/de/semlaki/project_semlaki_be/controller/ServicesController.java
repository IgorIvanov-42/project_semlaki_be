package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.dto.ServiceCreateRequestDto;
import de.semlaki.project_semlaki_be.domain.dto.ServiceResponseDto;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.service.ServicesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ServicesController {

    private final ServicesService servicesService;

    public ServicesController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        return ResponseEntity.ok(servicesService.getAllServices());
    }

    @GetMapping("/random")
    public ResponseEntity<List<ServiceResponseDto>> getRandomServices(
            @RequestParam(value = "service_counter", required = false, defaultValue = "4")
            int serviceCounter
    ) {
        return ResponseEntity.ok(servicesService.getRandomServices(serviceCounter));
    }

    // host/filter?term=car
    @GetMapping("/filter")
    public ResponseEntity<List<ServiceResponseDto>> getFilteredServices(
            @RequestParam("term")
            String term
    ) {
        return ResponseEntity.ok(servicesService.getServicesByTitle(term));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServiceById(@PathVariable Long id) {
        Optional<Services> service = servicesService.getServiceById(id);
        return service
                .map(ServiceResponseDto::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ServiceResponseDto>> getServicesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(servicesService.getServicesByCategory(categoryId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ServiceResponseDto>> getServicesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(servicesService.getServicesByUser(userId));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ServiceResponseDto> createService(@RequestBody ServiceCreateRequestDto createServiceDto,
                                                            @AuthenticationPrincipal String userEmail) {
        return ResponseEntity.ok(servicesService.createService(createServiceDto, userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
