package ru.kuraecode.domain

import javafx.beans.property.*
import javafx.collections.FXCollections
import tornadofx.*
import java.time.LocalDate
import javax.json.JsonObject

/**
 * FIO, Табельный номер, структурное подразделение, фикс оклалд, отработанных дней, норма дней по производственному коллендарю
 */

class Person : JsonModel {


    val idProperty = SimpleIntegerProperty()
    var id : Int? by idProperty

    val nameProperty = SimpleStringProperty()
    var name by nameProperty

    val lastNameProperty = SimpleStringProperty()
    var lastName by lastNameProperty

    val middleNameProperty = SimpleStringProperty()
    var middleName by middleNameProperty

    val bookkeepingInfos = FXCollections.observableArrayList<BookkeepingInfo>()

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = int("id")
            name = string("name")
            lastName = string("lastName")
            middleName = string("middleName")
            getJsonArray("bookKeepings")?.let {
                bookkeepingInfos.setAll(it.toModel())
            }


        }
    }
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("name", name)
            add("lastName", lastName)
            add("middleName", middleName)
        }
    }
}
class BookkeepingInfo: JsonModel {

    val idProperty = SimpleLongProperty()
    var id by idProperty

    val fixedSalaryProperty = SimpleDoubleProperty()
    var fixedSalary by fixedSalaryProperty

    val daysWorkedProperty = SimpleIntegerProperty()
    var daysWorked by daysWorkedProperty

    val dayWorkedExpectProperty = SimpleIntegerProperty()
    var dayWorkedExpect by dayWorkedExpectProperty


    val dayStartProperty = SimpleObjectProperty<LocalDate>()
    var dayStart by dayStartProperty

    val structuralSubdivisionProperty = SimpleStringProperty()
    var structuralSubdivision by structuralSubdivisionProperty

    val personIdProperty = SimpleIntegerProperty()
    var personId by personIdProperty

    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id") ?: -1
            fixedSalary = double("fixSalary") ?: -1.0
            daysWorked = int("dayWorks") ?: -1
            dayWorkedExpect = int("dayWorksExpect") ?: -1
            dayStart = date("dayStart")
            personId = int("personId") ?: -1
            structuralSubdivision = string("structuralSubdivision")
        }
    }

    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("fixSalary", fixedSalary)
            add("dayWorks", daysWorked)
            add("structuralSubdivision", structuralSubdivision)
            add("dayWorksExpect", dayWorkedExpect)
            add("dayStart", dayStart)
            add("personId", personId)
        }
    }
}


