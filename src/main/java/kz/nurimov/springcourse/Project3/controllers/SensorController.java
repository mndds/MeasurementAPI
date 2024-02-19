package kz.nurimov.springcourse.Project3.controllers;

import jakarta.validation.Valid;
import kz.nurimov.springcourse.Project3.dto.SensorDTO;
import kz.nurimov.springcourse.Project3.models.Sensor;
import kz.nurimov.springcourse.Project3.repositories.SensorRepository;
import kz.nurimov.springcourse.Project3.services.SensorService;
import kz.nurimov.springcourse.Project3.util.SensorErrorResponse;
import kz.nurimov.springcourse.Project3.util.SensorNotCreatedException;
import kz.nurimov.springcourse.Project3.util.SensorNotFoundException;
import kz.nurimov.springcourse.Project3.validators.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @RequestMapping()
    public List<SensorDTO> getAllSensors() {
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @RequestMapping("/{id}")
    public SensorDTO getSensor(@PathVariable("id") int id) {
        return convertToSensorDTO(sensorService.findOne(id));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorValidator.validate(convertToSensor(sensorDTO), bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error: errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMessage.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

}
