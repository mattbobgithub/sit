package com.sit.repository.tenant;

import com.sit.domain.AlterationGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the AlterationGroup entity.
 */
@SuppressWarnings("unused")
public interface AlterationGroupRepository extends JpaRepository<AlterationGroup,Long> {

    @Query("select distinct alterationGroup from AlterationGroup alterationGroup left join fetch alterationGroup.garments")
    List<AlterationGroup> findAllWithEagerRelationships();

    @Query("select alterationGroup from AlterationGroup alterationGroup left join fetch alterationGroup.garments where alterationGroup.id =:id")
    AlterationGroup findOneWithEagerRelationships(@Param("id") Long id);

}
