package kz.nurimov.springcourse.Project3.services;

import kz.nurimov.springcourse.Project3.models.Sensor;
import kz.nurimov.springcourse.Project3.repositories.SensorRepository;
import kz.nurimov.springcourse.Project3.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundSensor = sensorRepository.findById(id);
        return foundSensor.orElseThrow(() -> new SensorNotFoundException("Sensor with this id was not found"));
    }

    @Transactional
    public void save(Sensor sensor) {
        sensor.setCreatedAt(LocalDateTime.now());
        sensorRepository.save(sensor);
    }

    public boolean existsByName(String name) {
        return sensorRepository.existsByName(name);
    }

    public Sensor findByName(String name) {
        Optional<Sensor> foundSensor = sensorRepository.findByName(name);
        return foundSensor.orElseThrow(() -> new SensorNotFoundException("Sensor with this name was not found"));
    }

    //delete, update



}
