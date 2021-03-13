package ru.andreyhoco.treatmentplan.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.presentation.viewmodels.EditProcedureViewModel
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

class EditProcedureFragment : Fragment() {
    private val viewModel = EditProcedureViewModel()

    private var procedure: Procedure? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_procedure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (procedure == null) {
            procedure = viewModel.procedure.value
        }

        if (procedure == null) {
            procedure = Procedure(
                id = 0,
                imageId = 0,
                title = "",
                person = Person(0, "", 0),
                note = "",
                timesOfIntake = emptyList(),
                startDate = 0,
                endDate = 0
            )

            viewModel.procedure.value = procedure
        }
    }

    companion object {
        fun newInstance(procedure: Procedure): EditProcedureFragment {
            val fragment = EditProcedureFragment()
            fragment.procedure = procedure

            return fragment
        }
    }
}