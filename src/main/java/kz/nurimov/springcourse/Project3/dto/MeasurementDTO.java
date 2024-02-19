package kz.nurimov.springcourse.Project3.dto;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kz.nurimov.springcourse.Project3.models.Sensor;

import java.time.LocalDateTime;

public class MeasurementDTO {
    @NotNull(message = "Value field should not be empty")
    @Min(value = -100, message = "Value should be more than -100")
    @Max(value = 100, message = "Value should be less than 100")
    private Float value;

    @NotNull (message = "Raining field should not be empty")
    private boolean raining;

    @Valid
    private SensorDTO sensor;

    public MeasurementDTO() {
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
