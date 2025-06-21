package com.desafio.creditoapi.service;

import com.desafio.creditoapi.exception.ResourceNotFoundException;
import com.desafio.creditoapi.messaging.KafkaPublisher;
import com.desafio.creditoapi.model.Credito;
import com.desafio.creditoapi.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @InjectMocks
    private CreditoService creditoService;

    private Credito credito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        credito = new Credito();
        credito.setNumeroCredito("123456");
        credito.setNumeroNfse("7891011");
        credito.setDataConstituicao(LocalDate.of(2024, 2, 25));
        credito.setValorIssqn(new BigDecimal("1500.75"));
        credito.setTipoCredito("ISSQN");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("5.0"));
        credito.setValorFaturado(new BigDecimal("30000.00"));
        credito.setValorDeducao(new BigDecimal("5000.00"));
        credito.setBaseCalculo(new BigDecimal("25000.00"));
    }

    @Test
    void testBuscarPorNumeroNfse() {
        String numeroNfse = "7891011";

        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(Collections.singletonList(credito));

        List<Credito> result = creditoService.getByNfseNumber(numeroNfse);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123456", result.getFirst().getNumeroCredito());
        verify(kafkaPublisher, times(1)).publicarEventoConsulta("Consulta realizada para NFS-e: " + numeroNfse);
    }

    @Test
    void testBuscarPorNumeroCredito() {
        String numeroCredito = "123456";

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));

        Credito result = creditoService.getByCreditNumber(numeroCredito);

        assertNotNull(result);
        assertEquals("7891011", result.getNumeroNfse());
        verify(kafkaPublisher, times(1)).publicarEventoConsulta("Consulta realizada para Crédito: " + numeroCredito);
    }

    @Test
    void testBuscarPorNumeroCreditoNotFound() {
        String numeroCredito = "000000";

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            creditoService.getByCreditNumber(numeroCredito);
        });

        assertEquals("Crédito com número '"+numeroCredito+"' não encontrado.", exception.getMessage());

        verify(creditoRepository).findByNumeroCredito(numeroCredito);
        verify(kafkaPublisher, times(1)).publicarEventoConsulta("Consulta realizada para Crédito: " + numeroCredito);
    }
}
