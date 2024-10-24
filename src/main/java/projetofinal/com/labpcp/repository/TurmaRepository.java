package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.entity.TurmaEntity;


public interface TurmaRepository extends JpaRepository<TurmaEntity, Long> {

    boolean existsByDocenteId(Long docenteId);
}
