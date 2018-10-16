package ru.kuraecode.view

import tornadofx.*

class ReportMainComponent : View("Report") {
    override val root = borderpane {
        top<ReportToolBarView>()
        center<ReportView>()
    }

    override fun onDock() {
        currentStage?.isMaximized = true
    }
}
