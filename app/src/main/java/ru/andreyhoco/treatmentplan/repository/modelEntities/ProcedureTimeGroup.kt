package ru.andreyhoco.treatmentplan.repository.modelEntities

data class ProcedureTimeGroup(
    val startTime: Long,
    val endTime: Long,
    val procedures: List<Procedure>
) {
    constructor() : this(
        1,
        10,
        listOf(
            Procedure(
                1,
                0,
                "ингаляция",
                Person(
                    1,
                    "Даша",
                    0
                ),
                "",
                listOf(
                    System.currentTimeMillis() - 4
                ),
                0,
                0
            ),
            Procedure(
                    2,
            0,
            "Арбидол",
            Person(
                1,
                "Даша",
                0
            ),
            "",
                listOf(
                    System.currentTimeMillis() + 4
                ),
            0,
            0
            ),
            Procedure(
                3,
                0,
                "полоскание",
                Person(
                    1,
                    "Даша",
                    0
                ),
                "",
                listOf(
                    System.currentTimeMillis() - 1
                ),
                0,
                0
            ),
            Procedure(
                4,
                0,
                "ингаляция",
                Person(
                    2,
                    "Вася",
                    0
                ),
                "",
                listOf(
                    System.currentTimeMillis() + 1
                ),
                0,
                0
            )
        )
    )
}
