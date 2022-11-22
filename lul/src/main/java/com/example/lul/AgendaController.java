package com.example.lul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AgendaController {
    @Autowired
    ContatoRepository contatoRepository;
    List<Contato> contatos = new ArrayList<>();
    @RequestMapping("/")
    public String listarContatos (Model model){
        System.out.println(contatoRepository.findAll());
        contatos = contatoRepository.findAll();
        model.addAttribute("contatos", contatos);
        return "lista";
    }

    @GetMapping("/add")
    public String contatoForm(Model model){
        model.addAttribute("contato", new Contato());
        return "contatoForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Contato contato, BindingResult result){
        if(result.hasErrors()){
            return "contatoForm";
        }
        contatoRepository.save(contato);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String mostrarUpdateForm(@PathVariable("id") long id, Model model) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact Id:" + id));

        model.addAttribute("contato", contato);
        return "updateForm";
    }

    @PostMapping("/update/{id}")
    public String updateContato(@PathVariable("id") long id, @Valid Contato contato,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            contato.setId(id);
            return "updateForm";
        }

        contatoRepository.save(contato);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteContato(@PathVariable("id") long id, Model model) {
        Contato user = contatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contato Id:" + id));
        contatoRepository.delete(user);
        return "redirect:/";
    }

}
