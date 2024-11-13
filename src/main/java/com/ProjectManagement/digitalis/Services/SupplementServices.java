package com.ProjectManagement.digitalis.Services;

import com.ProjectManagement.digitalis.Entities.Supplement;

import java.util.List;

public interface SupplementServices {

    Supplement saveSupplement(Supplement supplement);

    Supplement getSupplement(Long idSupplement);

    Supplement edit(Long idSupplement, Supplement supplement);

    List<Supplement> listSupplement();

    void deleteSupplement(Long idSupplement);
}
