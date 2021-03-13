package ru.andreyhoco.treatmentplan.repository.modelEntities

data class IntakeProcedureTimeGroup(
    val startTime: Long,
    val endTime: Long,
    val procedures: List<IntakeProcedure>
) {
    constructor() : this(
            1,
            10,
            listOf(
                    IntakeProcedure(
                            1,
                            0,
                            "ингаляция",
                            Person(
                                    1,
                                    "Даша",
                                    0
                            ),
                            "",
                            TimeOfIntake(
                                    System.currentTimeMillis() - 4,
                                    true
                            ),
                            0,
                            0
                    ),
                    IntakeProcedure(
                            2,
                            0,
                            "Арбидол",
                            Person(
                                    1,
                                    "Даша",
                                    0
                            ),
                            "",
                            TimeOfIntake(
                                    System.currentTimeMillis() + 4,
                                    true
                            ),
                            0,
                            0
                    ),
                    IntakeProcedure(
                            3,
                            0,
                            "полоскание",
                            Person(
                                    1,
                                    "Даша",
                                    0
                            ),
                            "",
                            TimeOfIntake(
                                    System.currentTimeMillis() + 1,
                                    true
                            ),
                            0,
                            0
                    ),
                    IntakeProcedure(
                            4,
                            0,
                            "ингаляция",
                            Person(
                                    2,
                                    "Вася",
                                    0
                            ),
                            "",
                            TimeOfIntake(
                                    System.currentTimeMillis() -1,
                                    true
                            ),
                            0,
                            0
                    )
            )
    )
}

