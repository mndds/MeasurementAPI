package kz.nurimov.springcourse.Project3.controllers;

import jakarta.validation.Valid;
import kz.nurimov.springcourse.Project3.dto.MeasurementDTO;
import kz.nurimov.springcourse.Project3.models.Measurement;
import kz.nurimov.springcourse.Project3.models.Sensor;
import kz.nurimov.springcourse.Project3.services.MeasurementService;
import kz.nurimov.springcourse.Project3.services.SensorService;
import kz.nurimov.springcourse.Project3.util.*;
import kz.nurimov.springcourse.Project3.validators.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements() {
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
        return convertToMeasurementDTO(measurementService.findOne(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error: errors) {
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementNotCreatedException(errorMessage.toString());
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Map<String,Integer>> rainyDaysCount() {
        Map<String, Integer> response = Collections.singletonMap("rainyDaysCount",  measurementService.rainyDaysCount());
        return ResponseEntity.ok(response);
    }


    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();
        measurement.setValue(measurementDTO.getValue());
        measurement.setRaining(measurementDTO.isRaining());

        // Поиск сенсора по имени или другому идентификатору из SensorDTO
        Sensor sensor = sensorService.findByName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);
        return measurement;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDTO.class);
    }


}
