package ru.andreyhoco.treatmentplan.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.presentation.viewmodels.ProceduresListViewModel

class ProceduresListFragment : Fragment() {
    private val viewModel = ProceduresListViewModel()

    private lateinit var proceduresListAdapter: ProceduresListAdapter
    private lateinit var recyclerView: RecyclerView

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
    }

    private fun setupViewElements(view: View) {
        recyclerView = view.findViewById(R.id.rv_procedures_list)
    }

    private fun setupListOfProcedures() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        proceduresListAdapter = ProceduresListAdapter(
            viewModel.proceduresList.value as MutableList<ProcedureListItem>
        )
        recyclerView.adapter = proceduresListAdapter
    }
}