package o1.adventure


/**
 * The class `Adventure` represents text adventure games. An adventure consists of a player and 
 * a number of areas that make up the game world. It provides methods for playing the game one
 * turn at a time and for checking the state of the game.
 * 
 * N.B. This version of the class has a lot of "hard-coded" information which pertain to a very 
 * specific adventure game that involves a small trip through a twisted forest. All newly created 
 * instances of class `Adventure` are identical to each other. To create other kinds of adventure 
 * games, you will need to modify or replace the source code of this class. 
 */
class Adventure {

  /** The title of the adventure game. */
  val title = "Aalto Royale 2025"
    
  private val smokki      = new Area("Smökki", "Smökin rauniot ovat harmaat ja masentavat. Ne ovat rapistuneet, eikä AYY halua maksaa uudelleenrakennusta.")
  private val rantsu = new Area("Rantsu", "Rantsulla on ollut bileet, jotka ovat päättyneet verisesti. Ruumiita on kaikkialla. Katso, löydätkö aseita keneltäkään")
  private val tuas = new Area("TUAS-talo", "TUAS-talon Amica tarjoaa huoltopalvelua. Muista varoa vihollisia ja lähde ajoissa liikkeelle")
  private val lafka    = new Area("Päälafka", "Päälafka on taisteluiden raunioittama. Vain ylimmässä kerroksessa loistaa valo. Katso löytyykö sieltä aseita!")
  private val maari      = new Area("Maarintalo", "Maarintalolla loistaa valo. Nörtti astuu ovesta ulos ja näkee sinut. Hän lähtee karkuun, mutta kompastuu omiin kengännauhoihinsa. Voi raukkaa..")
  private val turvapaikka        = new Area(" Otakaari 20", "turvapaikka saavutettu. Peli päättyy tänne, kun saat kaikki viholliset tuhottua!")
  private val destination = turvapaikka    

       smokki.setNeighbors(Vector("north" -> rantsu, "east" -> maari, "south" -> tuas, "west" -> lafka   ))
  rantsu.setNeighbors(Vector(                        "east" -> maari, "south" -> smokki,      "west" -> lafka   ))
  tuas.setNeighbors(Vector("north" -> smokki,      "east" -> maari, "south" -> tuas, "west" -> lafka   ))
     lafka.setNeighbors(Vector("north" -> rantsu, "east" -> smokki, "south" -> tuas, "west" -> rantsu))
       maari.setNeighbors(Vector("north" -> rantsu, "east" -> turvapaikka,   "south" -> tuas, "west" -> rantsu))
         turvapaikka.setNeighbors(Vector(                                                                  "west" -> maari     ))

  // TODO: place these two items in lafka and tuas, respectively
  lafka.addItem(new Item("battery", "It's a small battery cell. Looks new."))   
  tuas.addItem(new Item("remote", "It's the remote control for your TV.\nWhat it was doing in the forest, you have no idea.\nProblem is, there's no battery."))

  /** The character that the player controls in the game. */
  val player = new Player(smokki)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40 


  /**
   * Determines if the adventure is complete, that is, if the player has won. 
   */
  def isComplete = this.player.location == this.destination && this.player.pelaajanEsineet.size == 2 

  /**
   * Determines whether the game is over.
   * 
   * @return `true` if the player has won, lost or quit; `false` otherwise
   */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit


  /**
   * Returns a message that is to be displayed to the player at the beginning of the game.
   */
  
  
  def welcomeMessage = "Valtio on päättänyt vähentää Aalto-yliopiston rahoitusta vuoden 2024 tapahtumien seurauksena.Syyskuussa 2024 uudet kauppatieteen opiskelijat, opiskelijaslangissa mursut, osallistuivat tekniikan opiskelijoiden vuosittaiseen jäynäkilpailuun räjäyttämällä Smökin.Tehostaakseen toimintaansa ja mukautuakseen vähentyneeseen rahoitukseen on Aalto-yliopisto päättänyt järjestää leikkimielisen kilpailun, Aalto Royalen, opintojen motivoimiseksi.Vuonna 2025 tapahtuvaan Aalto Royaleen valitaan satunnaisesti kaksisataa lukuvuonna 2024 aloittanutta heikoiten opinnoissaan menestynyttä kauppatieteilijää.Kilpailussa Otaniemi muuttuu kuukauden ajaksi taistelukentäksi, jossa - jatkuu."
  
  
    
  /**
   * Returns a message that is to be displayed to the player at the end of the game. 
   * The message will be different depending on whether or not the player has completed the quest.
   */
  def goodbyeMessage = {
    if (this.isComplete) {
      "turvapaikka at last... and phew, just in time! Well done!"
    } else if (this.turnCount == this.timeLimit) {
      "Oh no! Time's up. Starved of entertainment, you collapse and weep like a child.\nGame over!"
    } else { // game over due to player quitting
      "Quitter!" 
    }
  }

  
  /**
   * Plays a turn by executing the given in-game command.
   * (No turns elapse if the command is unknown.)
   * 
   * @param command  an in-game command such as "go west"
   * @return a textual report of what happened, or an error message if the command was unknown 
   */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) { 
      this.turnCount += 1 
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }
  
  
}

