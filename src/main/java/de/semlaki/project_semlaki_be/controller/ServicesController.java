package de.semlaki.project_semlaki_be.controller;

import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.service.ServicesService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Services>> getAllServices() {
        return ResponseEntity.ok(servicesService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable Long id) {
        Optional<Services> service = servicesService.getServiceById(id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Services>> getServicesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(servicesService.getServicesByCategory(categoryId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Services>> getServicesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(servicesService.getServicesByUser(userId));
    }

    @PostMapping
    public ResponseEntity<Services> createService(@RequestBody Services service) {
        return ResponseEntity.ok(servicesService.createService(service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
