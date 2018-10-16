package ru.kuraecode.model

import ru.kuraecode.domain.BookkeepingInfo
import tornadofx.*

class BookkeepingInfoModel : ItemViewModel<BookkeepingInfo>() {

    val id = bind(BookkeepingInfo::idProperty)
    val fixedSalary = bind(BookkeepingInfo::fixedSalaryProperty)
    val daysWorked = bind(BookkeepingInfo::daysWorkedProperty)
    val dayWorkedExpect = bind(BookkeepingInfo::dayWorkedExpectProperty)
    val dayStart = bind(BookkeepingInfo::dayStartProperty)
    val structuralSubdivision = bind(BookkeepingInfo::structuralSubdivisionProperty)
    val personId = bind(BookkeepingInfo::personIdProperty)

    fun generateCreateBookkeeping(): BookkeepingInfo {
        val bookkeepingInfo = BookkeepingInfo()
        bookkeepingInfo.fixedSalary = fixedSalary.value.toDouble()
        bookkeepingInfo.daysWorked = daysWorked.value.toInt()
        bookkeepingInfo.dayWorkedExpect = dayWorkedExpect.value.toInt()
        bookkeepingInfo.dayStart = dayStart.value
        bookkeepingInfo.structuralSubdivision = structuralSubdivision.value
        return bookkeepingInfo
    }

    fun generateUpdateBookkeeping(): BookkeepingInfo {
        val bookkeepingInfo = BookkeepingInfo()
        bookkeepingInfo.id = id.value.toLong()
        bookkeepingInfo.fixedSalary = fixedSalary.value.toDouble()
        bookkeepingInfo.daysWorked = daysWorked.value.toInt()
        bookkeepingInfo.dayWorkedExpect = dayWorkedExpect.value.toInt()
        bookkeepingInfo.dayStart = dayStart.value
        bookkeepingInfo.structuralSubdivision = structuralSubdivision.value
        bookkeepingInfo.personId = personId.value.toInt()
        return bookkeepingInfo
    }
}
