package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.entity.DocenteEntity;

import java.util.Optional;

public interface DocenteRepository extends JpaRepository<DocenteEntity, Long> {
    Optional<DocenteEntity> findByUsuarioId(Long usuarioId);
}
