package raffle

import java.util.concurrent.TimeUnit

import org.scalajs.jquery._

import scala.concurrent.duration.FiniteDuration
import scala.scalajs.js.timers._

/**
 * Created by sebnozzi on 21/11/15.
 */
class ParticipantWidget(id: Int, container:JQuery, optName: Option[String] = None) {

  private var _id = id

  val participantDiv = jQuery( """<div class="participant"></div>""")
  val idDiv = jQuery( s"""<div class="participant-id">ID</div>""")
  val imgDiv = jQuery( """<img src="/assets/images/user1-256x256.png"/>""")
  val nameDiv = jQuery( s"""<div class="participant-name">NAME</div>""")

  participantDiv.append(idDiv).append(imgDiv).append(nameDiv)
  jQuery(container).append(participantDiv)
  setId(id)
  optName.foreach(setName(_))

  def getId: Int = _id

  def showTemporarilyActive(milliseconds: Int) = {
    showActive()
    setTimeout(FiniteDuration(milliseconds, TimeUnit.MILLISECONDS)) {
      showInactive()
    }
  }

  def remove() = {
    participantDiv.remove()
  }

  def setName(name: String): Unit = {
    nameDiv.text(name)
  }

  def setId(id: Int): Unit = {
    idDiv.text(s"$id")
    _id = id
  }

  def showAsWinner(): Unit = {
    removeClasses()
    jQuery(participantDiv).addClass("winner")
  }

  def showActive(): Unit = {
    removeClasses()
    jQuery(participantDiv).addClass("active")
  }

  def showInactive(): Unit = {
    removeClasses()
  }

  private def removeClasses(): Unit = {
    jQuery(participantDiv).removeClass("winner")
    jQuery(participantDiv).removeClass("active")
  }

}

