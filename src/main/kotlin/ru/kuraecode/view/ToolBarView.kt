package ru.kuraecode.view

import ru.kuraecode.controller.BookKeepingController
import javafx.stage.StageStyle
import ru.kuraecode.model.PersonModel
import tornadofx.*

class ToolBarView : View("Toolbar") {

    private val bookKeepingController : BookKeepingController by inject()
    private val personModel: PersonModel by inject()


    override val root = hbox {
        button("Add Person") {
            action {
                find<PersonCreator>().openModal(stageStyle = StageStyle.UTILITY)
            }
        }
        button("Add Info") {
            enableWhen { !personModel.empty }
            action {
                Bookkeeping(personId = personModel.id.value).openModal(stageStyle = StageStyle.UTILITY)
            }
        }

        button("Generate Report") {
            action {
                find(MainComponentView::class).replaceWith(ReportMainComponent::class)
            }
        }

        button("Reload") {
            action {
                runAsync {
                    bookKeepingController.loadPerson()
                }
            }
        }

    }
}
