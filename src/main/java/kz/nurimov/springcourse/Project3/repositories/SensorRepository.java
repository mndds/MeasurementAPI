package kz.nurimov.springcourse.Project3.repositories;

import kz.nurimov.springcourse.Project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {
    boolean existsByName(String name);
    Optional<Sensor> findByName(String name);
}
