package ar.edu.itba.tpHciMobile.ui.model

import ar.edu.itba.tpHciMobile.data.model.CompleteCycle
import ar.edu.itba.tpHciMobile.data.model.CycleExercise
import ar.edu.itba.tpHciMobile.data.model.Exercises

class CyclesDetail(
    val exercises: List<CycleExercise>? = null,
    val cycle: CompleteCycle? = null
)