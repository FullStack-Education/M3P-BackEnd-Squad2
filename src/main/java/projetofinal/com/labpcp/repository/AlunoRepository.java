package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.entity.AlunoEntity;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<AlunoEntity, Long> {
    Optional<AlunoEntity> findByUsuarioId(Long usuarioId);
}
