package raffle.participant

import org.scalajs.dom.location
import raffle.communication.Server
import raffle.communication.routes.SocketRoutes
import raffle.participant.ui.ParticipantUI
import shared.SharedSerializationClasses._

import scala.scalajs.js.annotation.JSExport


@JSExport
object ParticipantApp {

  private var server: Server = _
  private var ui: ParticipantUI = _

  @JSExport
  def main(): Unit = {
    val socketURL = SocketRoutes.participantSocketURL

    ui = new ParticipantUI() {
      def onNameChanged(newName: String): Unit =
        server ! ParticipantNameChangedEvent(newName)
    }
    ui.init()

    server = new Server(socketURL + location.search) {

      override def onConnect(): Unit = {
        ui.updateConnectionStatus(connected = true)
      }

      override def onDisconnect(): Unit =
        ui.updateConnectionStatus(connected = false)

      def receive = {
        case AssignDataCmd(id, optName) =>
          ui.updateIdAndName(id, optName)
        case ParticipantWonEvent(id) =>
          ui.showWon(id)
      }
    }

  }


}
