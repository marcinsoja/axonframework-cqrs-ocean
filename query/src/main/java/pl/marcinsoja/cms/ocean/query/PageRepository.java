package pl.marcinsoja.cms.ocean.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageRepository extends JpaRepository<PageEntry, UUID> {
}
