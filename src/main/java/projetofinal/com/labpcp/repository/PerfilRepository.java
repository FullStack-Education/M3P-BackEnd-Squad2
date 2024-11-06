package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projetofinal.com.labpcp.entity.PerfilEntity;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {

    @Query
    Optional<PerfilEntity> findByNome(String login);
}
