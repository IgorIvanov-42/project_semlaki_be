package de.semlaki.project_semlaki_be.service;

import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.domain.entity.Services;
import de.semlaki.project_semlaki_be.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {

    private final ServiceRepository serviceRepository;

    public ServicesService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Services> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public List<Services> getServicesByCategory(Long categoryId) {
        return serviceRepository.findByCategoryId(categoryId);
    }

    public List<Services> getServicesByUser(Long userId) {
        return serviceRepository.findByUserId(userId);
    }

    public Services createService(Services services) {
        return serviceRepository.save(services);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
