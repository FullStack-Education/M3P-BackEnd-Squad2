package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.entity.MateriaEntity;

import java.util.List;
import java.util.Optional;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    Optional<MateriaEntity> findByNome(String nome);
    List<MateriaEntity> findByCursoId(Long cursoId);
}
