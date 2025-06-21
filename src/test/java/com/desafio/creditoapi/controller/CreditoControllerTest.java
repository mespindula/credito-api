package com.desafio.creditoapi.controller;

import com.desafio.creditoapi.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.desafio.creditoapi.model.Credito;
import com.desafio.creditoapi.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditoController.class)
class CreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditoService creditoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Credito credito;

    @BeforeEach
    void setUp() {
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
    void testBuscarPorNumeroNfseComResultados() throws Exception {
        List<Credito> lista = Arrays.asList(credito);
        when(creditoService.getByNfseNumber("7891011")).thenReturn(lista);

        mockMvc.perform(get("/api/creditos/7891011")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCredito").value("123456"));

        verify(creditoService, times(1)).getByNfseNumber("7891011");
    }

    @Test
    void testBuscarPorNumeroNfseSemResultados() throws Exception {
        when(creditoService.getByNfseNumber("9999999")).thenReturn(List.of());

        mockMvc.perform(get("/api/creditos/9999999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(creditoService, times(1)).getByNfseNumber("9999999");
    }

    @Test
    void testBuscarPorNumeroCreditoComResultado() throws Exception {
        when(creditoService.getByCreditNumber("123456")).thenReturn(credito);

        mockMvc.perform(get("/api/creditos/credito/123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroNfse").value("7891011"))
                .andExpect(jsonPath("$.valorIssqn").value(1500.75));

        verify(creditoService, times(1)).getByCreditNumber("123456");
    }

    @Test
    void testBuscarPorNumeroCreditoNotFound() throws Exception {
        String numeroCredito = "000000";
        String mensagemErro = "Crédito com número '" + numeroCredito + "' não encontrado.";

        when(creditoService.getByCreditNumber(numeroCredito))
                .thenThrow(new ResourceNotFoundException(mensagemErro));

        mockMvc.perform(get("/api/creditos/credito/"+numeroCredito)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(mensagemErro));

        verify(creditoService, times(1)).getByCreditNumber(numeroCredito);
    }
}
