package ru.kuraecode.controller

import ru.kuraecode.domain.BookkeepingInfo
import ru.kuraecode.domain.Person
import ru.kuraecode.domain.ReportEntity
import javafx.collections.FXCollections
import tornadofx.*

class BookKeepingController : Controller() {

    val rest: Rest by inject()

    val persons = FXCollections.observableArrayList<Person>()

    val reports = FXCollections.observableArrayList<ReportEntity>()

    init {
        rest.baseURI = "http://localhost:8080"
    }

    fun loadReport(): List<ReportEntity> {
        val reportOut = rest.get("api/persons/report").list().toModel<ReportEntity>()
        reports.setAll(reportOut)
        return reportOut
    }

    fun loadPerson(): List<Person> {
        val toModel = rest.get("api/persons").list().toModel<Person>()
        persons.setAll(toModel)
        return toModel
    }

    fun createPerson(person: Person): Person {
        val toModel = rest.post("api/persons", person.toJSON()).one().toModel<Person>()
        persons.add(toModel)
        return toModel
    }

    fun updatePerson(person: Person) {
        rest.put("persons/${person.id}", person.toJSON())
    }

    fun createBookkeeping(bookkeepinginfo: BookkeepingInfo): BookkeepingInfo {
        val toModel = rest.post("api/bookkeeping/${bookkeepinginfo.personId}", bookkeepinginfo.toJSON()).one().toModel<BookkeepingInfo>()
        persons.find { it.id!! == bookkeepinginfo.personId }?.bookkeepingInfos?.add(toModel)
        return toModel
    }

    fun updateBookkeeping(updateBookkeeping: BookkeepingInfo) {
        rest.put("api/bookkeeping/${updateBookkeeping.id}", updateBookkeeping.toJSON()).status
    }
}