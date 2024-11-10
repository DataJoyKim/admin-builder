package com.datajoy.admin_builder.console.rest;

import com.datajoy.admin_builder.apibuilder.service.Service;
import com.datajoy.admin_builder.console.repository.ConsoleServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/console/api/service")
public class ServiceRestController {
    @Autowired
    private ConsoleServiceRepository serviceRepository;

    @GetMapping("")
    public ResponseEntity<?> getService() {
        List<Service> results = serviceRepository.findAll();

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getService(@PathVariable("id") Long id) {
        Service results = serviceRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createService(@RequestBody Map<String,Object> params) {

        Service service = Service.builder()
                .serviceName((String) params.get("serviceName"))
                .displayName((String) params.get("displayName"))
                .note((String) params.get("note"))
                .build();

        Service results = serviceRepository.save(service);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateService(@PathVariable("id") Long id, @RequestBody Map<String,Object> params) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        serviceRepository.update(
                service.getId(),
                (String) params.get("serviceName"),
                (String) params.get("displayName"),
                (String) params.get("note")
        );

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable("id") Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        serviceRepository.deleteById(service.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
