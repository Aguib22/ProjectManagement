package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Supplement;
import com.ProjectManagement.digitalis.Repositories.SupplementRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupplementServicesImpl implements SupplementServices {

    @Autowired
    private final SupplementRepository supplementRepository;

    @Override
    public Supplement saveSupplement(Supplement supplement) {

        return supplementRepository.save(supplement);
    }

    @Override
    public Supplement getSupplement(Long idSupplement) {

        Optional<Supplement> supplement = supplementRepository.findById(idSupplement);
        return supplement.orElseThrow(() -> new RuntimeException("Supplment introuvable"));
    }

    @Override
    public Supplement edit(Long idSupplement, Supplement supplement) {
        return supplementRepository.findById(idSupplement)
                .map(supp -> {
                    supp.setNumeroSupplement(supplement.getNumeroSupplement());
                    supp.setDateSupplement(supplement.getDateSupplement());
                    supp.setSourceSupplement(supplement.getSourceSupplement());
                    supp.setRaisonSupplement(supplement.getRaisonSupplement());
                    supp.setRetardSupplement(supplement.getRetardSupplement());
                    return supplementRepository.save(supp);

                })
                .orElseThrow(() -> new RuntimeException("Supplément introuvable"));
    }

    @Override
    public List<Supplement> listSupplement() {
        return supplementRepository.findAll();
    }

    @Override
    public void deleteSupplement(Long idSupplement) {
        if (supplementRepository.existsById(idSupplement)) {
            supplementRepository.deleteById(idSupplement);

        } else {
            throw new RuntimeException("Erreur de suppression !");
        }
    }
}
