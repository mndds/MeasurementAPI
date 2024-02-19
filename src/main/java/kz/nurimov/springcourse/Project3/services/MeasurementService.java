package kz.nurimov.springcourse.Project3.services;

import kz.nurimov.springcourse.Project3.models.Measurement;
import kz.nurimov.springcourse.Project3.repositories.MeasurementRepository;
import kz.nurimov.springcourse.Project3.util.MeasurementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findOne(int id) {
        Optional<Measurement> foundMeasurement = measurementRepository.findById(id);
        return foundMeasurement.orElseThrow(() -> new MeasurementNotFoundException("Measurement was not found"));
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }


    public int rainyDaysCount() {
        return measurementRepository.countByRainingTrue();
    }
}
