package com.sit.repository.tenant;

import com.sit.domain.Alteration;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Alteration entity.
 */
@SuppressWarnings("unused")
public interface AlterationRepository extends JpaRepository<Alteration,Long> {

    @Query("select distinct alteration from Alteration alteration left join fetch alteration.alterationDisplayTypes")
    List<Alteration> findAllWithEagerRelationships();

    @Query("select alteration from Alteration alteration left join fetch alteration.alterationDisplayTypes where alteration.id =:id")
    Alteration findOneWithEagerRelationships(@Param("id") Long id);

}
