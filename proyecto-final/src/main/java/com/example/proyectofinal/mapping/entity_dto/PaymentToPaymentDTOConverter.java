package com.example.proyectofinal.mapping.entity_dto;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.entity.PaymentMethod;
import com.example.proyectofinal.entity.Person;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

//public class PaymentToPaymentDTOConverter implements Converter<PaymentMethod, PaymentMethodDTO> {
//    @Override
//    public PaymentMethodDTO convert(MappingContext<PaymentMethod, PaymentMethodDTO> context) {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setSkipNullEnabled(true);
//        PaymentMethod source = context.getSource();
//        return modelMapper.map(source, PaymentMethodDTO.class);
//    }
//}
