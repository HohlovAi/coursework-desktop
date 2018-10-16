package ru.kuraecode.controller

import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import ru.kuraecode.view.LoginScreen
import ru.kuraecode.view.MainComponentView
import ru.kuraecode.view.UserRegistration

class AuthController : Controller() {

    private val api: Rest by inject()
    val statusProperty = SimpleStringProperty("")
    private var status by statusProperty

    val registrationStatusProperty = SimpleStringProperty("")
    private var registrationStatus by registrationStatusProperty

    var authToken: String = ""

    init {
        api.baseURI = "http://localhost:8080/"
        api.engine.requestInterceptor = { request ->
            request.addHeader("Authorization", authToken)
        }
        api.engine.responseInterceptor = { response ->
            if (response.statusCode == 426)
                runLater {
                    authToken = ""
                    primaryStage.uiComponent<UIComponent>()?.replaceWith(LoginScreen::class, sizeToScene = true, centerOnScreen = true)
                }
        }
    }

    fun login(login: String, password: String) {
        val response = api.post("/account/signin", JsonBuilder().add("login", login).add("password", password).build())
        try {
            runLater {
                status = when (response.status) {
                    Rest.Response.Status.Unauthorized -> String(response.bytes())
                    else -> {
                        response.status.name
                    }
                }
            }
            runLater {
                if (response.ok()) {
                    val token = response.one().getString("token")
                    authToken = "Bearer $token"
                    find(LoginScreen::class).replaceWith(MainComponentView::class, centerOnScreen = true)
                }
            }
        } finally {
            response.consume()
        }
    }

    fun registration(login: String, password: String) {
        val jsonBuilder = JsonBuilder()
        jsonBuilder.add("login", login)
        jsonBuilder.add("password", password)
        val response = api.post("account/signup", jsonBuilder.build())
        try {
            runLater {
                registrationStatus = response.status.name
            }

            runLater {
                if (response.ok()) {
                    find(UserRegistration::class).replaceWith(LoginScreen::class, sizeToScene = true, centerOnScreen = true)
                } else {

                }
            }
        } finally {
            response.consume()
        }
    }
}