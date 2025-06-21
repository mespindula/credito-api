package com.desafio.creditoapi.service;

import com.desafio.creditoapi.exception.ResourceNotFoundException;
import com.desafio.creditoapi.messaging.KafkaPublisher;
import com.desafio.creditoapi.model.Credito;
import com.desafio.creditoapi.repository.CreditoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final KafkaPublisher kafkaPublisher;


    public CreditoService(CreditoRepository creditoRepository,
                          KafkaPublisher kafkaPublisher) {
        this.creditoRepository = creditoRepository;
        this.kafkaPublisher = kafkaPublisher;
    }

    public List<Credito> getByNfseNumber(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);
        kafkaPublisher.publicarEventoConsulta("Consulta realizada para NFS-e: " + numeroNfse);
        return creditos;
    }

    public Credito getByCreditNumber(String numeroCredito) {
        Optional<Credito> credito = creditoRepository.findByNumeroCredito(numeroCredito);
        kafkaPublisher.publicarEventoConsulta("Consulta realizada para Crédito: " + numeroCredito);
        return credito.orElseThrow(() -> new ResourceNotFoundException("Crédito com número '" + numeroCredito + "' não encontrado."));
    }

}
