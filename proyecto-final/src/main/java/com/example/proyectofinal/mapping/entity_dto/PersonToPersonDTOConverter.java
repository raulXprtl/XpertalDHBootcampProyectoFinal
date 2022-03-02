package com.example.proyectofinal.mapping.entity_dto;

import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.entity.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.HashSet;
import java.util.Set;

public class PersonToPersonDTOConverter implements Converter<Set<Person>, Set<PersonDTO>> {
    @Override
    public Set<PersonDTO> convert(MappingContext<Set<Person>, Set<PersonDTO>> context) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        Set<Person> source = context.getSource();
        Set<PersonDTO> output = new HashSet<>();
        source.forEach(person -> output.add(modelMapper.map(person, PersonDTO.class)));
        return output;
    }
}
