package ru.andreyhoco.treatmentplan.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

class EditProcedureViewModel : ViewModel() {
    var procedure = MutableLiveData<Procedure?>(null)
}