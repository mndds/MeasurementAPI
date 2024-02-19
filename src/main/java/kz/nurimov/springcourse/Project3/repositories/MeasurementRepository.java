package kz.nurimov.springcourse.Project3.repositories;

import kz.nurimov.springcourse.Project3.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    @Query("SELECT COUNT(m) FROM Measurement m WHERE m.raining = true")
    int countByRainingTrue();
}
