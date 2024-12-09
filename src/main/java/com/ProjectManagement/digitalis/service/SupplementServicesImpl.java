package com.ProjectManagement.digitalis.service;

import com.ProjectManagement.digitalis.entitie.Supplement;
import com.ProjectManagement.digitalis.repositorie.SupplementRepository;
import com.ProjectManagement.digitalis.service.serviceIntreface.SupplementServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j // Activation des logs avec SLF4J
@Service
public class SupplementServicesImpl implements SupplementServices {

    private final SupplementRepository supplementRepository;

    public SupplementServicesImpl(SupplementRepository supplementRepository) {
        this.supplementRepository = supplementRepository;
    }

    @Override
    public Supplement saveSupplement(Supplement supplement) {
        log.info("Enregistrement du supplément : {}", supplement);
        Supplement savedSupplement = supplementRepository.save(supplement);
        log.info("Supplément enregistré avec succès : {}", savedSupplement);
        return savedSupplement;
    }

    @Override
    public Supplement getSupplement(Long idSupplement) {
        log.info("Récupération du supplément avec l'ID : {}", idSupplement);
        Optional<Supplement> supplement = supplementRepository.findById(idSupplement);
        if (supplement.isPresent()) {
            log.info("Supplément trouvé : {}", supplement.get());
            return supplement.get();
        } else {
            log.error("Supplément avec l'ID : {} introuvable", idSupplement);
            throw new RuntimeException("Supplément introuvable");
        }
    }

    @Override
    public Supplement edit(Long idSupplement, Supplement supplement) {
        log.info("Modification du supplément avec l'ID : {}", idSupplement);
        return supplementRepository.findById(idSupplement)
                .map(supp -> {
                    log.info("Supplément trouvé à modifier : {}", supp);
                    supp.setNumeroSupplement(supplement.getNumeroSupplement());
                    supp.setDateSupplement(supplement.getDateSupplement());
                    supp.setSourceSupplement(supplement.getSourceSupplement());
                    supp.setRaisonSupplement(supplement.getRaisonSupplement());
                    supp.setRetardSupplement(supplement.getRetardSupplement());
                    Supplement updatedSupplement = supplementRepository.save(supp);
                    log.info("Supplément modifié avec succès : {}", updatedSupplement);
                    return updatedSupplement;
                })
                .orElseThrow(() -> {
                    log.error("Supplément avec l'ID : {} introuvable", idSupplement);
                    return new RuntimeException("Supplément introuvable");
                });
    }

    @Override
    public List<Supplement> listSupplement() {
        log.info("Récupération de tous les suppléments");
        List<Supplement> supplements = supplementRepository.findAll();
        log.info("Nombre de suppléments trouvés : {}", supplements.size());
        return supplements;
    }

    @Override
    public void deleteSupplement(Long idSupplement) {
        log.info("Tentative de suppression du supplément avec l'ID : {}", idSupplement);
        if (supplementRepository.existsById(idSupplement)) {
            supplementRepository.deleteById(idSupplement);
            log.info("Supplément avec l'ID : {} supprimé avec succès", idSupplement);
        } else {
            log.error("Supplément avec l'ID : {} introuvable pour la suppression", idSupplement);
            throw new RuntimeException("Erreur de suppression !");
        }
    }
}
