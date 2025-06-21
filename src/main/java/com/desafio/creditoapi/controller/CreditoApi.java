package com.desafio.creditoapi.controller;

import com.desafio.creditoapi.model.Credito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "API Rest para consulta de créditos")
public interface CreditoApi {

    @Operation(description = "Retorna uma lista de créditos constituídos com base no número da NFS-e")
    List<Credito> getCreditByNfse(@PathVariable String numeroNfse);

    @Operation(description = "Retorna os detalhes de um crédito constituído específico com base no número do crédito constituído.")
    Credito getCreditByNumber(@PathVariable String numeroCredito);
}
