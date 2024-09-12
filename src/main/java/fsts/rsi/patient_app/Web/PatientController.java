package fsts.rsi.patient_app.Web;

import fsts.rsi.patient_app.Entities.Patient;
import fsts.rsi.patient_app.Repository.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Patient> pagepatients = patientRepository.findByNameContains(kw, PageRequest.of(page, size));
        model.addAttribute("ListPatients", pagepatients.getContent());
        model.addAttribute("pages", new int[pagepatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patient";
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@RequestParam long id,
                         @RequestParam String keyword,
                         @RequestParam int page) {
        patientRepository.deleteById(id);
        // Redirection correcte vers l'index avec paramètres
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }
    @GetMapping("/edit")
    public String edit(Model model, Long id,  @RequestParam(required = false, defaultValue = "") String keyword ,@RequestParam(defaultValue = "0") int page) {
       Patient patient=patientRepository.findById(id).orElse(null);
       if(patient==null) {throw new RuntimeException("patient not found");}
       model.addAttribute("patient", patient);
       model.addAttribute("keyword", keyword);
       model.addAttribute("page", page);
       return "editpatients";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public String formpatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult, Model model,
                       @RequestParam(required = false, defaultValue = "") String keyword ,@RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) {
            return "formPatients";
        }
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        patientRepository.save(patient);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }


}