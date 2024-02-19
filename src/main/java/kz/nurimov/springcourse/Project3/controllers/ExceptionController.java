package kz.nurimov.springcourse.Project3.controllers;

import kz.nurimov.springcourse.Project3.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MeasurementNotFoundException.class)
    public ResponseEntity<MeasurementErrorResponse> handleMeasurementNotFoundException(MeasurementNotFoundException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MeasurementNotCreatedException.class)
    public ResponseEntity<MeasurementErrorResponse> handleMeasurementNotCreatedException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SensorNotFoundException.class)
    public ResponseEntity<SensorErrorResponse> handleSensorNotFoundException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SensorNotCreatedException.class)
    public ResponseEntity<SensorErrorResponse> handleSensorNotCreatedException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
