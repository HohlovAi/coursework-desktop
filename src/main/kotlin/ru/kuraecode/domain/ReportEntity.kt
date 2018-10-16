package ru.kuraecode.domain

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.time.LocalDate
import javax.json.JsonObject


class ReportEntity : JsonModel {

    val personIdProperty = SimpleIntegerProperty()
    var personId : Int? by personIdProperty

    val firstNameProperty = SimpleStringProperty()
    var firstName by firstNameProperty

    val lastNameProperty = SimpleStringProperty()
    var lastName by lastNameProperty

    val info = FXCollections.observableArrayList<ReportBookkeepingInfo>()

    override fun updateModel(json: JsonObject) {
        with(json) {
            personId = int("personId")
            firstName = string("firstName")
            lastName = string("lastName")
            getJsonArray("info")?.let {
                info.setAll(it.toModel())
            }
        }
    }
}

class ReportBookkeepingInfo : JsonModel {

    val dateProperty = SimpleObjectProperty<LocalDate>()
    var date: LocalDate by dateProperty

    val grossSalaryProperty = SimpleDoubleProperty()
    var grossSalary: Double? by grossSalaryProperty

    val ndflProperty = SimpleDoubleProperty()
    var ndfl: Double? by ndflProperty

    val insurancePremiumsProperty = SimpleDoubleProperty()
    var insurancePremiums: Double? by insurancePremiumsProperty

    val actuallyGrossSalaryProperty = SimpleDoubleProperty()
    var actuallyGrossSalary: Double? by actuallyGrossSalaryProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            date = date("date")!!
            grossSalary = double("grossSalary")
            ndfl = double("ndfl")
            insurancePremiums = double("insurancePremiums")
            actuallyGrossSalary = double("actuallyGrossSalary")
        }
    }
}