package com.desafio.creditoapi.controller;

import com.desafio.creditoapi.model.Credito;
import com.desafio.creditoapi.service.CreditoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController implements CreditoApi {

    private final CreditoService service;

    public CreditoController(CreditoService service) {
        this.service = service;
    }

    @GetMapping("/{numeroNfse}")
    public List<Credito> getCreditByNfse(@PathVariable String numeroNfse) {
        return service.getByNfseNumber(numeroNfse);
    }

    @GetMapping("/credito/{numeroCredito}")
    public Credito getCreditByNumber(@PathVariable String numeroCredito) {
        return service.getByCreditNumber(numeroCredito);
    }
}

