package ru.andreyhoco.treatmentplan.presentation.ui

import ru.andreyhoco.treatmentplan.repository.modelEntities.Procedure

abstract class ProcedureListItem {
    companion object {
        const val TYPE_GROUP = 0
        const val TYPE_ITEM = 1
    }

    var itemType: Int = TYPE_GROUP
}

data class GroupItem(var time: Long): ProcedureListItem() {
    init {
        itemType = TYPE_GROUP
    }
}

data class ProcedureItem(var procedure: Procedure): ProcedureListItem() {
    init {
        itemType = TYPE_ITEM
    }
}