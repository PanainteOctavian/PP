package facade

import GetRequest
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.web.WebView
import javafx.stage.Stage
import proxy.CleanGetRequest
import javafx.scene.layout.VBox
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.web.WebHistory
import java.net.URI

class KidsBrowser : Application() {
   override fun start(stage : Stage) {
      val webView = WebView()
      val urlField = TextField()
      urlField.setOnAction{
         val url = urlField.text
         if (url.isNotEmpty()) {
            webView.engine.load(if (url.startsWith("http://") || url.startsWith("https://")) url else "http://$url")
         }
      }
      webView.engine.isJavaScriptEnabled = true

      val backButton = Button("Back")
      backButton.setOnAction {
         val history: WebHistory = webView.engine.history
         if (history.currentIndex > 0) {
            history.go(-1)
         }
      }

      val forwardButton = Button("Forward")
      forwardButton.setOnAction {
         val history: WebHistory = webView.engine.history
         if (history.currentIndex < history.entries.size - 1) {
            history.go(1)
         }
      }

      webView.engine.locationProperty().addListener { _, _, newValue ->
         Platform.runLater{
            urlField.text = newValue
         }
         try {
            val cleanGetRequest = CleanGetRequest(GetRequest(URI(newValue).normalize().toString(), mapOf("key1" to "value1", "keyn" to "valuen"), 5000))
            cleanGetRequest.getResponse()
            webView.engine.load(URI(newValue).normalize().toString())
         }
         catch (e: Exception) {
            Platform.runLater{
               webView.engine.loadContent("<html><body><h1>This site is not allowed by parental control!</h1></body></html>")
            }
         }
      }

      val layout = VBox(urlField, backButton, forwardButton, webView)
      val scene = Scene(layout, 500.0, 500.0)
      webView.prefHeightProperty().bind(scene.heightProperty())
      webView.prefWidthProperty().bind(scene.widthProperty())
      stage.scene = scene
      stage.show()
   }
}