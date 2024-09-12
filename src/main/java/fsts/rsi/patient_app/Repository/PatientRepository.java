package fsts.rsi.patient_app.Repository;

import fsts.rsi.patient_app.Entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    //pour la recherche des patients
    Page<Patient> findByNameContains(String name, Pageable pageable);

    // dans cette methode en utilise le HQL
    @Query("select p from Patient p where p.name like %:x%")
    Page<Patient> chercher(@Param("x") String keyword, Pageable pageable);

}