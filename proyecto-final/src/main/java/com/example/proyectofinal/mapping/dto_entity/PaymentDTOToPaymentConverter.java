package com.example.proyectofinal.mapping.dto_entity;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.entity.PaymentMethod;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

import java.util.ArrayList;
import java.util.List;

public class PaymentDTOToPaymentConverter implements Converter<PaymentMethodDTO, PaymentMethod> {
    @Override
    public PaymentMethod convert(MappingContext<PaymentMethodDTO, PaymentMethod> context) {
        ModelMapper modelMapper = new ModelMapper();
        PaymentMethodDTO source = context.getSource();
        return modelMapper.map(source, PaymentMethod.class);
    }
}
