package com.sit.service.mapper;

import com.sit.domain.Alteration;
import com.sit.domain.AlterationPrice;
import com.sit.domain.PriceCategory;
import com.sit.service.dto.AlterationPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AlterationPrice and its DTO AlterationPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlterationPriceMapper {

    @Mapping(source = "priceCategory.id", target = "priceCategoryId")
    @Mapping(source = "alteration.id", target = "alterationId")
    AlterationPriceDTO alterationPriceToAlterationPriceDTO(AlterationPrice alterationPrice);

    List<AlterationPriceDTO> alterationPricesToAlterationPriceDTOs(List<AlterationPrice> alterationPrices);

    @Mapping(source = "priceCategoryId", target = "priceCategory")
    @Mapping(source = "alterationId", target = "alteration")
    AlterationPrice alterationPriceDTOToAlterationPrice(AlterationPriceDTO alterationPriceDTO);

    List<AlterationPrice> alterationPriceDTOsToAlterationPrices(List<AlterationPriceDTO> alterationPriceDTOs);

    default PriceCategory priceCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceCategory priceCategory = new PriceCategory();
        priceCategory.setId(id);
        return priceCategory;
    }

    default Alteration alterationFromId(Long id) {
        if (id == null) {
            return null;
        }
        Alteration alteration = new Alteration();
        alteration.setId(id);
        return alteration;
    }
}
