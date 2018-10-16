package ru.kuraecode.view

import ru.kuraecode.controller.BookKeepingController
import ru.kuraecode.domain.BookkeepingInfo
import ru.kuraecode.domain.Person
import javafx.geometry.Orientation
import ru.kuraecode.model.BookkeepingInfoModel
import ru.kuraecode.model.PersonModel
import tornadofx.*
import java.time.LocalDate


/**
 * FIO, Табельный номер, структурное подразделение, фикс оклалд, отработанных дней, норма дней по производственному коллендарю
*/


class TableView : View("Table") {

    private val personModel: PersonModel by inject()

    private val bookKeepingController: BookKeepingController by inject()

    private val bookkeepingInfoModel: BookkeepingInfoModel by inject()

    init {
        runAsync {
            bookKeepingController.loadPerson()
        }
    }

    override val root = tableview<Person>(bookKeepingController.persons) {

        column("ID", Person::id).pctWidth(10.0)
        column("Name", Person::name).makeEditable().pctWidth(30.0)
        column("Last Name", Person::lastName).makeEditable().pctWidth(30.0)
        column("Middle Name", Person::middleName).makeEditable().pctWidth(30.0)
        bindSelected(personModel)
        enableCellEditing()
        smartResize()
        regainFocusAfterEdit()
        onEditCommit {
            runAsyncWithProgress {
                bookKeepingController.updatePerson(person = personModel.generatePerson())
            }
        }
        rowExpander { person ->
            tableview(person.bookkeepingInfos) {
                column("Date Start", BookkeepingInfo::dayStart).makeEditable().pctWidth(20.0)
                column("Structural Subdivision", BookkeepingInfo::structuralSubdivision).makeEditable().pctWidth(20.0)
                column("Fixed Salary", BookkeepingInfo::fixedSalary).makeEditable().pctWidth(20.0)
                column("Days Worked", BookkeepingInfo::daysWorked).makeEditable().pctWidth(20.0)
                column("Day Worked Expect", BookkeepingInfo::dayWorkedExpect).makeEditable().pctWidth(20.0)
                onEditCommit {
                    runAsyncWithProgress {
                        bookKeepingController.updateBookkeeping(bookkeepingInfoModel.generateUpdateBookkeeping())
                    }
                }
                bindSelected(bookkeepingInfoModel)
                enableCellEditing()
                smartResize()
                regainFocusAfterEdit()
            }
        }
    }

}

class Bookkeeping(personId: Int): Fragment("Bookkeeping Creator") {

    private val bookkeepingInfoModel = BookkeepingInfoModel()
    private val bookKeepingController: BookKeepingController by inject()

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            fieldset("Day Start") {
                datepicker(bookkeepingInfoModel.dayStart) {
                    value = LocalDate.now()
                }.required()
            }

            fieldset("Structural Subdivision") {
                textfield(bookkeepingInfoModel.structuralSubdivision).required()
            }

            fieldset("Day Worked Expect") {
                textfield(bookkeepingInfoModel.dayWorkedExpect).validator {
                    when {
                        it.isNullOrBlank() -> error("The field cannot be null or empty")
                        !it!!.isInt() -> error("The field can pass only integer values")
                        it.toInt() > LocalDate.now().lengthOfMonth() -> error("Cannot be more than length of month")
                        else -> null
                    }
                }
            }

            fieldset("Day Worked") {
                textfield(bookkeepingInfoModel.daysWorked).validator {
                    when {
                        it.isNullOrBlank() -> error("The field cannot be null or empty")
                        !it!!.isInt() -> error("The field can pass only integer values")
                        it.toInt() > LocalDate.now().lengthOfMonth() -> error("Cannot be more than length of month")
                        else -> null
                    }
                }
            }

            fieldset("Fixed Salary") {
                textfield(bookkeepingInfoModel.fixedSalary).validator {
                    when {
                        it.isNullOrBlank() -> error("The field cannot be null or empty")
                        else -> null
                    }
                }
            }


            button("Save") {
                enableWhen(bookkeepingInfoModel.valid)
                action {
                    runAsyncWithProgress {
                        val generateBookkeeping = bookkeepingInfoModel.generateCreateBookkeeping()
                        generateBookkeeping.personId = personId
                        bookKeepingController.createBookkeeping(generateBookkeeping)
                    } ui {
                        close()
                    }
                }
            }
        }
    }

}

class PersonCreator : Fragment("Person Creator") {

    private val personModel = PersonModel()
    private val bookKeepingController: BookKeepingController by inject()

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            fieldset("Name") {
                textfield(personModel.name).required()
            }
            fieldset("Last Name") {
                textfield(personModel.lastName).required()
            }
            fieldset("Middle Name") {
                textfield(personModel.middleName).required()
            }
            button("Save") {
                enableWhen(personModel.valid)
                action {
                    runAsyncWithProgress {
                        bookKeepingController.createPerson(person = personModel.generatePerson())
                    } ui {
                        close()
                    }
                }
            }
        }
    }

}

