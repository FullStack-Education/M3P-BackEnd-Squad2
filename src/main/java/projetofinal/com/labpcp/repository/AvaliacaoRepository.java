package projetofinal.com.labpcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projetofinal.com.labpcp.entity.AvaliacaoEntity;
import projetofinal.com.labpcp.entity.MateriaEntity;

import java.util.List;


public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Long> {
    List<AvaliacaoEntity> findByAlunoId(Long alunoId);
}
