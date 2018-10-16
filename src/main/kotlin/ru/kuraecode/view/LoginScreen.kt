package ru.kuraecode.view

import ru.kuraecode.controller.AuthController
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Orientation
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class LoginScreen : View("Login") {

    private val authController: AuthController by inject()
    private val model = ViewModel()
    private val userName = model.bind { SimpleStringProperty() }
    private val password = model.bind { SimpleStringProperty() }

    override fun onDock() {
        userName.value = ""
        password.value = ""
        model.clearDecorators()
        authController.authToken = ""
    }

    override val root = form {
        fieldset(labelPosition = Orientation.VERTICAL) {

            fieldset("Username") {
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
                        it.isNullOrBlank() -> error("The username field is required")
                        it?.length!! < 4 -> error("The username length should be 4 and more")
                        else -> null
                    }
                }
            }

            button("Sign in") {
                enableWhen(model.valid)
                useMaxHeight = true
                isDefaultButton = true
                action {
                    runAsyncWithProgress {
                        authController.login(userName.value, password.value)
                    }
                }
                gridpaneConstraints {
                    columnSpan = 2
                    marginBottom = 10.0
                }
            }

            button("Sign up") {
                action {
                    runLater {
                        replaceWith(UserRegistration::class)
                    }
                }
                gridpaneConstraints {
                    columnSpan = 2
                    marginBottom = 10.0
                }
            }

            label(authController.statusProperty) {
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