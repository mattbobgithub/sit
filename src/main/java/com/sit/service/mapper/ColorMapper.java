package com.sit.service.mapper;

import com.sit.domain.Color;
import com.sit.service.dto.ColorDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Color and its DTO ColorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ColorMapper {

    ColorDTO colorToColorDTO(Color color);

    List<ColorDTO> colorsToColorDTOs(List<Color> colors);

    Color colorDTOToColor(ColorDTO colorDTO);

    List<Color> colorDTOsToColors(List<ColorDTO> colorDTOs);
}
