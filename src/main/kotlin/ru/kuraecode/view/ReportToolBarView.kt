package ru.kuraecode.view

import tornadofx.*

class ReportToolBarView: View("Report Toolbar") {

    override val root = hbox {
        button("Back") {
            action {
                find(ReportMainComponent::class).replaceWith(MainComponentView::class)
            }
        }
    }
}