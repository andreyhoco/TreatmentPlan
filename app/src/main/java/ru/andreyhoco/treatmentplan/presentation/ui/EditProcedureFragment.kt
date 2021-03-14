package ru.andreyhoco.treatmentplan.presentation.ui

import android.app.AlertDialog
import android.os.BaseBundle
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.andreyhoco.treatmentplan.R
import ru.andreyhoco.treatmentplan.TreatmentPlanApp
import ru.andreyhoco.treatmentplan.presentation.viewmodels.EditProcedureViewModel
import ru.andreyhoco.treatmentplan.presentation.viewmodels.ViewModelFactory
import ru.andreyhoco.treatmentplan.repository.modelEntities.Person
import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake
import java.lang.String.format
import java.util.*

class EditProcedureFragment : Fragment(), TimeItemClickListener {
    private val viewModel: EditProcedureViewModel by viewModels {
        ViewModelFactory(
                (requireActivity().application as TreatmentPlanApp)
                        .appDi
                        .procedureAndPersonRepository
        )
    }

    private var procedure: Procedure? = null

    private lateinit var ivPerson: ImageView
    private lateinit var ibAddPerson: ImageButton
    private lateinit var sPerson: Spinner

    private lateinit var ivTitle: ImageView
    private lateinit var etTitle: EditText

    private lateinit var ivTimes: ImageView
    private lateinit var ibAddTime: ImageButton
    private lateinit var tvTimes: TextView
    private lateinit var rvTimes: RecyclerView

    private lateinit var ivDates: ImageView
    private lateinit var tvDateStart: TextView
    private lateinit var tvDateEnd: TextView

    private lateinit var ivNotes: ImageView
    private lateinit var etNotes: EditText

    private lateinit var btnSave: Button

    private lateinit var timeListAdapter: TimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(TimePickerFragment.KEY_RESULT, ::onTimeSet)
        setFragmentResultListener(DatePickerFragment.KEY_RESULT_DATE_START, ::onDateStartSet)
        setFragmentResultListener(DatePickerFragment.KEY_RESULT_DATE_END, ::onDateEndSet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_procedure, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewElements(view)
        setupEventListeners()

        setupProcedure()

        viewModel.loadPersons()

        setupAdapters()

        viewModel.persons.observe(viewLifecycleOwner, this::setupPersonsAdapter)

        setupFieldsFromProcedure()
    }

    private fun addPerson() {
        val input = EditText(context)

        AlertDialog.Builder(context)
            .setTitle("Enter person name")
            .setView(input)
            .setPositiveButton(
                "OK"
            ) { dialog, which ->
                val personName = input.text.toString()

                if (personName != "") {
                    viewModel.addPerson(personName)
                }
            }
            .setNegativeButton(
                "Cancel"
            ) { dialog, which -> }
            .show()
    }

    private fun setupViewElements(view: View) {
        ivPerson = view.findViewById(R.id.iv_person)
        ibAddPerson = view.findViewById(R.id.ib_add_person)
        sPerson = view.findViewById(R.id.s_person)

        ivTitle = view.findViewById(R.id.iv_title)
        etTitle = view.findViewById(R.id.et_title)

        ivTimes = view.findViewById(R.id.iv_times)
        ibAddTime = view.findViewById(R.id.ib_add_time)
        tvTimes = view.findViewById(R.id.tv_times)
        rvTimes = view.findViewById(R.id.rv_times)

        ivDates = view.findViewById(R.id.iv_dates)
        tvDateStart = view.findViewById(R.id.tv_date_start)
        tvDateEnd = view.findViewById(R.id.tv_date_end)

        ivNotes = view.findViewById(R.id.iv_notes)
        etNotes = view.findViewById(R.id.et_notes)

        btnSave = view.findViewById(R.id.btn_save)
    }

    private fun setupEventListeners() {
        ibAddPerson.setOnClickListener { addPerson() }

        sPerson.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val persons = viewModel.persons.value ?: listOf()

                if (persons.size > position) {
                    procedure?.person = persons[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        ibAddTime.setOnClickListener {
            val dialog = TimePickerFragment()
            dialog.show(requireActivity().supportFragmentManager, null)
        }

        tvDateStart.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(DatePickerFragment.KEY_RESULT_DATE_START)
            dialog.show(requireActivity().supportFragmentManager, null)
        }

        tvDateEnd.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(DatePickerFragment.KEY_RESULT_DATE_END)
            dialog.show(requireActivity().supportFragmentManager, null)
        }

        btnSave.setOnClickListener {
            viewModel.saveProcedure(procedure)
            if (parentFragmentManager.backStackEntryCount > 0) {
                parentFragmentManager.popBackStack()
            }
        }

        etTitle.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                procedure?.title = etTitle.text.toString()
            }
        }

        etNotes.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                procedure?.note = etNotes.text.toString()
            }
        }
    }

    private fun setupProcedure() {
        if (procedure == null) {
            procedure = viewModel.procedure.value
        }

        if (procedure == null) {
            val c = Calendar.getInstance()
            val h = c.get(Calendar.HOUR_OF_DAY) + 1

            c.set(Calendar.HOUR_OF_DAY, 0)
            c.set(Calendar.MINUTE, 0)
            c.set(Calendar.SECOND, 0)
            c.set(Calendar.MILLISECOND, 0)

            val currentDay = c.timeInMillis

            c.set(Calendar.YEAR, 0)
            c.set(Calendar.MONTH, 0)
            c.set(Calendar.DAY_OF_MONTH, 0)
            c.set(Calendar.HOUR_OF_DAY, h)

            val nextHour = c.timeInMillis

            procedure = Procedure(
                id = 0,
                imageId = 0,
                title = "",
                person = Person(0, "", 0),
                note = "",
                timesOfIntake = listOf(TimeOfIntake(nextHour, false)),
                startDate = currentDay,
                endDate = currentDay
            )

            viewModel.procedure.value = procedure
        }
    }

    private fun setupAdapters() {
        setupPersonsAdapter(viewModel.persons.value ?: listOf())
        setupTimesAdapter(procedure?.timesOfIntake ?: emptyList())
    }

    private fun setupPersonsAdapter(persons: List<Person>) {
        val adapter = ArrayAdapter<String>(
                requireContext(),
                //android.R.layout.simple_spinner_item,
                R.layout.item_person,
                persons.map { person ->
                    person.name
                }
            )

        sPerson.adapter = adapter
    }

    private fun setupTimesAdapter(timesOfIntake: List<TimeOfIntake>) {
        Log.d("TApp", "setupTimesAdapter: ${timesOfIntake.size}")

        timeListAdapter = TimeListAdapter(timesOfIntake.toMutableList(), this)
        rvTimes.layoutManager = LinearLayoutManager(requireContext())
        rvTimes.adapter = timeListAdapter
    }

    private fun updateTimesAdapter(timesOfIntake: List<TimeOfIntake>) {
        Log.d("TApp", "updateTimesAdapter: ${timeListAdapter.items.size}")

        timeListAdapter.items.clear()
        timeListAdapter.items.addAll(timesOfIntake)

        Log.d("TApp", "updateTimesAdapter: ${timeListAdapter.items.size}")
        timeListAdapter.notifyDataSetChanged()
    }

    override fun onDeleteItemClick(timeOfIntake: TimeOfIntake) {
        viewModel.deleteTimeOfIntake(timeOfIntake, procedure)

        updateTimesAdapter(procedure?.timesOfIntake ?: emptyList())
    }

    private fun onTimeSet(key: String, bundle: BaseBundle) {
        val c = Calendar.getInstance()
        c.set(
            0,
            0,
            0,
            bundle.getInt(TimePickerFragment.BUNDLE_KEY_HOUR, 0),
            bundle.getInt(TimePickerFragment.BUNDLE_KEY_MINUTE, 0),
            0
        )
        c.set(Calendar.MILLISECOND, 0)

        viewModel.addTimeOfIntake(c.timeInMillis, procedure)

        updateTimesAdapter(procedure?.timesOfIntake ?: emptyList())
    }

    private fun onDateStartSet(key: String, bundle: BaseBundle) {
        procedure?.let { proc ->
            proc.startDate = getDateFromBundle(bundle)
            setupFieldFromDateStart()
        }
    }

    private fun onDateEndSet(key: String, bundle: BaseBundle) {
        procedure?.let { proc ->
            proc.endDate = getDateFromBundle(bundle)
            setupFieldFromDateEnd()
        }
    }

    private fun getDateFromBundle(bundle: BaseBundle): Long {
        val c = Calendar.getInstance()

        c.set(
            bundle.getInt(DatePickerFragment.BUNDLE_KEY_YEAR, 0),
            bundle.getInt(DatePickerFragment.BUNDLE_KEY_MONTH, 0),
            bundle.getInt(DatePickerFragment.BUNDLE_KEY_DAY_OF_MONTH, 0),
            0,
            0,
            0
        )

        return c.timeInMillis
    }

    private fun setupFieldsFromProcedure() {
        procedure?.let { proc ->
            if (proc.person.name.isNotEmpty()) {
                val arrayAdapter = (sPerson.adapter as ArrayAdapter<String>)
                var pos =
                    if (arrayAdapter.getPosition(proc.person.name) == -1) {
                        arrayAdapter.count - 1
                    } else {
                        0
                    }

                if (arrayAdapter.count > 0) {
                    sPerson.setSelection(pos)
                }
            }
        }

        etTitle.text = SpannableStringBuilder(procedure?.title ?: "")

        setupFieldFromDateStart()
        setupFieldFromDateEnd()

        etNotes.text = SpannableStringBuilder(procedure?.note ?: "")
    }

    private fun setupFieldFromDateStart() {
        tvDateStart.text = FormatHelper.getFormattedDate(procedure?.startDate ?: 0)
    }

    private fun setupFieldFromDateEnd() {
        tvDateEnd.text = FormatHelper.getFormattedDate(procedure?.endDate ?: 0)
    }

    companion object {
        fun newInstance(procedure: Procedure): EditProcedureFragment {
            val fragment = EditProcedureFragment()
            fragment.procedure = procedure

            return fragment
        }
    }
}

interface TimeItemClickListener {
    fun onDeleteItemClick(timeOfIntake: TimeOfIntake)
}