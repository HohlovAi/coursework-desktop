package ru.kuraecode.view

import ru.kuraecode.controller.AuthController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class UserRegistration : View("User Registration") {

    private val authController: AuthController by inject()
    private val model = ViewModel()
    private val userName = model.bind { SimpleStringProperty() }
    private val password = model.bind { SimpleStringProperty() }
    private val confirmPassword = model.bind { SimpleStringProperty() }

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {
            fieldset("Login") {
                textfield(userName).validator {
                    when {
                        it.isNullOrBlank() -> error("The username field is required")
                        it?.length!! < 4 -> error("The username length should be 4 and more")
                        else -> null
                    }
                }
            }
            fieldset("Password") {
                passwordfield(password).validator {
                    when {
                        it.isNullOrBlank() -> error("The password field is required")
                        it?.length!! < 4 -> error("The password length should be 4 and more")
                        else -> null
                    }
                }
            }
            fieldset("Confirm Password") {
                passwordfield(confirmPassword).validator {
                    when {
                        it.isNullOrBlank() -> error("The password field is required")
                        it?.length!! < 4 -> error("The password length should be 4 and more")
                        it != password.value -> error("Password and confirm must be same")
                        else -> null
                    }
                }
            }
            button("Sign Up") {
                enableWhen { model.valid }
                isDefaultButton = true
                useMaxHeight = true
                action {
                    runAsyncWithProgress {
                        authController.registration(login = userName.value, password = password.value)
                    }
                }
            }
            button("Back") {
                action {
                    runLater {
                        replaceWith(LoginScreen::class)
                    }
                }
            }
            label(authController.registrationStatusProperty) {
                style {
                    paddingTop = 10
                    fontWeight = FontWeight.BOLD
                    textFill = Color.BLUE
                }
            }
        }

        style(append = true) {
            prefWidth = 300.px
            prefHeight = 400.px
        }
    }
}
