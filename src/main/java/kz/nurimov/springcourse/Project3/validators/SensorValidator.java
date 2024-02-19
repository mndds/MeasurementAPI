package kz.nurimov.springcourse.Project3.validators;

import kz.nurimov.springcourse.Project3.models.Sensor;
import kz.nurimov.springcourse.Project3.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor =  (Sensor) target;

        if ( sensorService.existsByName(sensor.getName())) {
            errors.rejectValue("name","sensor.name.duplicate", "This name is already taken");
        }

    }
}
