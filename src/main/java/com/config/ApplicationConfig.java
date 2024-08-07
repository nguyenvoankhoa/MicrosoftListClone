package com.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dto.*;
import com.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        CustomMap customMap = new CustomMap();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(Cell.class, CellDTO.class)
                .setConverter(context -> customMap.mapCell(context.getSource()));

        modelMapper.createTypeMap(Column.class, ColumnDTO.class)
                .setConverter(context -> customMap.mapColumn(context.getSource()));

        modelMapper.createTypeMap(Row.class, RowDTO.class)
                .setConverter(context -> customMap.mapRow(context.getSource()));

        modelMapper.createTypeMap(SmartList.class, SmartListDTO.class)
                .setConverter(context -> customMap.mapSmartListDTO(context.getSource()));

        modelMapper.createTypeMap(View.class, ViewDTO.class)
                .setConverter(context -> customMap.mapView(context.getSource()));

        modelMapper.createTypeMap(Template.class, TemplateDTO.class)
                .setConverter(context -> customMap.mapTemplate(context.getSource()));
        return modelMapper;
    }


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dsdwh3bxe",
                        "api_key", "118986666866357",
                        "api_secret", "ALqS681v3w3OAgq9JwAaPZAyXwg",
                        "secure", true
                )
        );
    }

    @Bean
    public CustomMap customMap() {
        return new CustomMap();
    }
}
