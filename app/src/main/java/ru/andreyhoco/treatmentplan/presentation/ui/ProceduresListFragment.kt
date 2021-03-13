package ru.andreyhoco.treatmentplan.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.presentation.viewmodels.ProceduresListViewModel
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

class ProceduresListFragment :
    Fragment(),
    ProcedureItemClickListener {
    private val viewModel = ProceduresListViewModel()

    private lateinit var proceduresListAdapter: ProceduresListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAdd: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_procedures_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewElements(view)
        setupListOfProcedures()

        viewModel.loadProceduresByDate()

        viewModel.proceduresList.observe(viewLifecycleOwner, this::updateListOfProcedures)
        viewModel.selectedProcedure.observe(viewLifecycleOwner, this::editProcedure)

        btnAdd.setOnClickListener {
            editProcedure(
                Procedure(
                    id = 0,
                    imageId = 0,
                    title = "",
                    person = Person(0, "", 0),
                    note = "",
                    timesOfIntake = emptyList(),
                    startDate = 0,
                    endDate = 0
                )
            )
        }
    }

    private fun setupViewElements(view: View) {
        recyclerView = view.findViewById(R.id.rv_procedures_list)
        btnAdd = view.findViewById(R.id.fab_add)
    }

    private fun setupListOfProcedures() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        proceduresListAdapter = ProceduresListAdapter(
            viewModel.proceduresList.value as MutableList<ProcedureListItem>,
            this
        )
        recyclerView.adapter = proceduresListAdapter
    }

    private fun updateListOfProcedures(newList: List<ProcedureListItem>) {
        proceduresListAdapter.items.clear()
        proceduresListAdapter.items.addAll(newList)
        proceduresListAdapter.notifyDataSetChanged()
    }

    private fun editProcedure(procedure: Procedure?) {
        procedure?.let { proc ->
            parentFragmentManager.beginTransaction().apply {
                addToBackStack(null)
                add(R.id.fcv_main, EditProcedureFragment.newInstance(proc))
                commit()
            }
        }
    }

    override fun onItemClick(procedureId: Long) {
        viewModel.getPersonById(procedureId)
    }

    override fun onItemCheckBoxClick(procedureId: Long, timeOfIntake: TimeOfIntake) {
        viewModel.setCheckBox(procedureId, timeOfIntake)
    }

    companion object {
        fun newInstance(): ProceduresListFragment {
            return ProceduresListFragment()
        }
    }
}

interface ProcedureItemClickListener {
    fun onItemClick(procedureId: Long)
    fun onItemCheckBoxClick(procedureId: Long, timeOfIntake: TimeOfIntake)
}