package br.edu.ifsp.prw3.api_2025_1.Repository;

import br.edu.ifsp.prw3.api_2025_1.Models.Conserto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsertoRepository extends JpaRepository <Conserto, Long> {
    Page<Conserto> findAllByAtivoTrue(Pageable pageable);
}
