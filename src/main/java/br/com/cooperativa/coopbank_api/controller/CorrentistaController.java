package br.com.cooperativa.coopbank_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.cooperativa.coopbank_api.model.Correntista;
import br.com.cooperativa.coopbank_api.repository.CorrentistaRepository;

@RestController
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaRepository repository;

    @PostMapping
    public Correntista criar(@RequestBody Correntista correntista) {
        return repository.save(correntista);
    }

    @GetMapping
    public List<Correntista> listar() {
        return repository.findAll();
    }
}
