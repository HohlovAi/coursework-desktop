package ru.kuraecode.view

import tornadofx.*

class MainComponentView : View("Bookkeeping") {
    override val root = borderpane {
        top<ToolBarView>()
        center<TableView>()
    }

    override fun onDock() {
        currentStage?.isMaximized= true
    }
}
