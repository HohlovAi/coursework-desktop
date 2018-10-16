package ru.kuraecode.model

import ru.kuraecode.domain.Person
import tornadofx.*


class PersonModel : ItemViewModel<Person>() {
    val id = bind(Person::id)
    val name = bind(Person::nameProperty)
    val lastName = bind(Person::lastNameProperty)
    val middleName = bind(Person::middleNameProperty)


    fun generatePerson(): Person {
        val person = Person()
        person.id = this.id.value
        person.name = this.name.value
        person.lastName = this.lastName.value
        person.middleName = this.middleName.value
        return person
    }
}
