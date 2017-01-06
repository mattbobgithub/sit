package com.sit.service.mapper;

import com.sit.domain.*;
import com.sit.service.dto.SkuDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Sku and its DTO SkuDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkuMapper {

    @Mapping(source = "garmentSize.id", target = "garmentSizeId")
    @Mapping(source = "garment.id", target = "garmentId")
    @Mapping(source = "sizeType.id", target = "sizeTypeId")
    @Mapping(source = "color.id", target = "colorId")
    SkuDTO skuToSkuDTO(Sku sku);

    List<SkuDTO> skusToSkuDTOs(List<Sku> skus);

    @Mapping(source = "garmentSizeId", target = "garmentSize")
    @Mapping(source = "garmentId", target = "garment")
    @Mapping(source = "sizeTypeId", target = "sizeType")
    @Mapping(source = "colorId", target = "color")
    Sku skuDTOToSku(SkuDTO skuDTO);

    List<Sku> skuDTOsToSkus(List<SkuDTO> skuDTOs);

    default GarmentSize garmentSizeFromId(Long id) {
        if (id == null) {
            return null;
        }
        GarmentSize garmentSize = new GarmentSize();
        garmentSize.setId(id);
        return garmentSize;
    }

    default Garment garmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Garment garment = new Garment();
        garment.setId(id);
        return garment;
    }

    default SizeType sizeTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        SizeType sizeType = new SizeType();
        sizeType.setId(id);
        return sizeType;
    }

    default Color colorFromId(Long id) {
        if (id == null) {
            return null;
        }
        Color color = new Color();
        color.setId(id);
        return color;
    }
}
