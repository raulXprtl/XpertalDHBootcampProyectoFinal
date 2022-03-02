package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.BookResDTO;
import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.touristPack.*;
import com.example.proyectofinal.entity.BookingOrReservation;
import com.example.proyectofinal.entity.Customer;
import com.example.proyectofinal.entity.TouristPack;
import com.example.proyectofinal.exception.BoRNotFound;
import com.example.proyectofinal.exception.ClientNotFound;
import com.example.proyectofinal.repository.BookingOrReservationRepository;
import com.example.proyectofinal.repository.CustomerRepository;
import com.example.proyectofinal.repository.TouristPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TouristPackServiceImpl implements TouristPackService {
    @Autowired
    private TouristPackRepository touristPackRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingOrReservationRepository borRepository;

    @Override
    public CrudResponseDTO saveTouristPack(TouristPackRequestDTO request) {

        //Validacion de que existan las reservaciones a convertir en paquetes
        checkBookingAndReservation(request.getBookingsOrReservations());

        Customer customer = customerRepository.findById(request.getClient_id()).get();
        TouristPack touristPack = new TouristPack();
        touristPack.setCreationDate(request.getCreation_date());
        touristPack.setCustomer(customer);

        CrudResponseDTO crudResponseDTO = new CrudResponseDTO();
        try{
            touristPackRepository.save(touristPack);
            crudResponseDTO.setMessage("Paquete turístico dado de alta correctamente.");
        }
        catch (Exception e)
        {
            crudResponseDTO.setMessage("Error al dar de alta el paquete turístico.");
        }
        return crudResponseDTO;
    }

    private void findClientById(Integer id) {
        if (!customerRepository.findById(id).isPresent()) {
            throw new ClientNotFound();
        }
    }

    private void checkBookingAndReservation(BookResDTO request)
    {
        Optional<BookingOrReservation> bor1 = borRepository.findById(request.getBookResId1());
        if(!bor1.isPresent())
        {
            throw new BoRNotFound();
        }
        Optional<BookingOrReservation> bor2 = borRepository.findById(request.getBookResId2());
        if(!bor2.isPresent())
        {
            throw new BoRNotFound();
        }
    }

    @Override
    public CrudResponseDTO updateTouristPack(Integer packageNumber, TouristPackUpdateDTO request) {
        //Validacion de que existan las reservaciones a convertir en paquetes
        checkBookingAndReservation(request.getBookingsOrReservations());

        TouristPack touristPack = touristPackRepository.findById(packageNumber).get();

        touristPack.setCreationDate(request.getCreation_date());

        Customer customer = customerRepository.findById(request.getClient_id()).get();
        touristPack.setCustomer(customer);

        CrudResponseDTO crudResponseDTO = new CrudResponseDTO();
        try{
            touristPackRepository.save(touristPack);
            crudResponseDTO.setMessage("Paquete turístico actualizado correctamente.");
        }
        catch (Exception e)
        {
            crudResponseDTO.setMessage("Error al actualizar el paquete turístico.");
        }
        return crudResponseDTO;

    }

    @Override
    public CrudResponseDTO deleteTouristPack(Integer packageNumber) {
        CrudResponseDTO crudResponseDTO = new CrudResponseDTO();
        try{
            touristPackRepository.deleteById(packageNumber);
            crudResponseDTO.setMessage("Paquete turístico eliminado correctamente.");
        }
        catch (Exception e)
        {
            crudResponseDTO.setMessage("Error al eliminar el paquete turístico.");
        }
        return crudResponseDTO;
    }

    @Override
    public TouristPackResponseDTO getAllTouristPack() {

        List<TouristPackDTO> responseList = new ArrayList<>();
        TouristPackResponseDTO responseDTO = new TouristPackResponseDTO();

        try {
            List<TouristPack> touristPacks = touristPackRepository.findAll();
            for(TouristPack tp : touristPacks)
            {
                TouristPackDTO response = new TouristPackDTO();

                //Declaramos customer para sacar el customer de touristPack
                Customer customer = tp.getCustomer();

                BookResDTO bookRes = new BookResDTO();
                int cont = 0;

                //For para recorrer los booking or reservation y sacar los 2 ID
                for(BookingOrReservation bor : customer.getBookingsOrReservations())
                {
                    if(cont == 0)
                    {
                        bookRes.setBookResId1(bor.getBookingReservationId());
                    }
                    if(cont == 1)
                    {
                        bookRes.setBookResId2(bor.getBookingReservationId());
                    }
                    cont++;
                    response.setBookingsOrReservations(bookRes);
                }

                response.setPackageNumber(tp.getTouristPackId());
                response.setName(tp.getCustomer().getUserName());
                response.setCreation_date(tp.getCreationDate());
                response.setClient_id(tp.getCustomer().getCustomerId());
                responseList.add(response);
            }
            responseDTO.setPackages(responseList);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return responseDTO;
    }

}
