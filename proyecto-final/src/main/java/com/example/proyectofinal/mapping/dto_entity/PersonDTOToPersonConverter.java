package com.example.proyectofinal.mapping.dto_entity;

import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.entity.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.HashSet;
import java.util.Set;

public class PersonDTOToPersonConverter implements Converter<Set<PersonDTO>, Set<Person>> {
    @Override
    public Set<Person> convert(MappingContext<Set<PersonDTO>, Set<Person>> context) {
        ModelMapper modelMapper = new ModelMapper();
        Set<PersonDTO> source = context.getSource();
        Set<Person> output = new HashSet<>();
        source.forEach(person -> output.add(modelMapper.map(person, Person.class)));
        return output;
    }
}
