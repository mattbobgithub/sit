package com.sit.service.mapper;

import com.sit.domain.PriceCategory;
import com.sit.service.dto.PriceCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity PriceCategory and its DTO PriceCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceCategoryMapper {

    PriceCategoryDTO priceCategoryToPriceCategoryDTO(PriceCategory priceCategory);

    List<PriceCategoryDTO> priceCategoriesToPriceCategoryDTOs(List<PriceCategory> priceCategories);

    @Mapping(target = "alterationPrices", ignore = true)
    PriceCategory priceCategoryDTOToPriceCategory(PriceCategoryDTO priceCategoryDTO);

    List<PriceCategory> priceCategoryDTOsToPriceCategories(List<PriceCategoryDTO> priceCategoryDTOs);
}
