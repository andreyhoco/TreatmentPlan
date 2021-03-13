package ru.andreyhoco.treatmentplan.presentation.ui

import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure
import ru.andreyhoco.treatmentplan.repository.modelEntities.ProcedureTimeGroup
import ru.andreyhoco.treatmentplan.repository.modelEntities.TimeOfIntake

abstract class ProcedureListItem {
    companion object {
        const val TYPE_GROUP = 0
        const val TYPE_ITEM = 1
    }

    var itemType: Int = TYPE_GROUP
}

data class ProcedureGroupItem(var procedureTimeGroup: ProcedureTimeGroup): ProcedureListItem() {
    init {
        itemType = TYPE_GROUP
    }
}

data class ProcedureItem(
    var procedure: Procedure,
    var timeOfIntake: TimeOfIntake
): ProcedureListItem() {
    init {
        itemType = TYPE_ITEM
    }
}