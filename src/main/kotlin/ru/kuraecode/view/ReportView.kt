package ru.kuraecode.view

import ru.kuraecode.controller.BookKeepingController
import ru.kuraecode.domain.ReportBookkeepingInfo
import ru.kuraecode.domain.ReportEntity
import tornadofx.*


class ReportView: Fragment("Report View") {

    private val bookKeepingController: BookKeepingController by inject()

    override val root = tableview<ReportEntity> {

        whenDocked {
            asyncItems { bookKeepingController.loadReport() }
        }
        column("ID", ReportEntity::personId).pctWidth(10.0)
        column("First Name", ReportEntity::firstName).pctWidth(45.0)
        column("Last Name", ReportEntity::lastName).pctWidth(45.0)
        rowExpander(true) { reportEntity ->
            tableview(reportEntity.info) {
                smartResize()
                column("Date", ReportBookkeepingInfo::date).pctWidth(20.0)
                column("Gross Salary", ReportBookkeepingInfo::grossSalary).pctWidth(20.0)
                column("NDFL", ReportBookkeepingInfo::ndfl).pctWidth(20.0)
                column("Insurance Premiums", ReportBookkeepingInfo::insurancePremiums).pctWidth(20.0)
                column("Actually Gross Salary", ReportBookkeepingInfo::actuallyGrossSalary).pctWidth(20.0)
            }
        }
        smartResize()


    }

    override fun onDock() {
        currentStage?.isMaximized = true
    }
}