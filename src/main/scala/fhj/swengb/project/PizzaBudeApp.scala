package fhj.swengb.project

import java.net.URL
import java.util.ResourceBundle
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.fxml._
import javafx.scene.image.{ImageView, Image}
import javafx.scene.layout.AnchorPane
import javafx.scene.{Scene, Parent}
import javafx.scene.control.{Button}
import javafx.stage.Stage

import scala.collection.mutable
import scala.util.control.NonFatal

/**
  * Created by loete on 17.01.2016
  */
object PizzaBudeApp{
  def main(args: Array[String]) {
    Application.launch(classOf[PizzaBudeApp], args: _*)
  }
}

class PizzaBudeApp extends Application {

  val loader = new FXMLLoader(getClass.getResource("PizzaBude.fxml"))

  override def start(stage: Stage): Unit = try {
    stage.setTitle("PizzaBude")
    loader.load[Parent]()
    stage.setScene(new Scene(loader.getRoot[Parent]))
    stage.show()
    } catch {
    case NonFatal(e) => e.printStackTrace()
  }
}

case class GameLoop(game: PizzaBude,buttons: Map[Move, Button]) extends AnimationTimer{

  override def handle(now:Long):Unit = {

    PizzaBude.saveStartTime(now)

    PizzaOven.checkMachine(now, PizzaOven.t, PizzaOven.product)
    Drink.checkMachine(now, Drink.t, Drink.product)
    Pommes.checkMachine(now, Pommes.t, Pommes.product)

    val btnPizza_1: ImageView = new ImageView(new Image(getClass.getResourceAsStream("btnPizza_OvenRdy.png")))
    val btnPizza_2: ImageView = new ImageView(new Image(getClass.getResourceAsStream("btnPizza_OvenWorking.png")))
    val btnPizza_3: ImageView = new ImageView(new Image(getClass.getResourceAsStream("btnPizza_PizzaRdy.png")))


    if(PizzaOven.getReady) buttons(Product2).setGraphic(btnPizza_3)
    if(PizzaOven.getState) buttons(Product2).setGraphic(btnPizza_2)
    if(!PizzaOven.getState) buttons(Product2).setGraphic(btnPizza_1)



    Table2.checkTables(now)
    Table2.deliver()
    Table2.checkAngryLevel(now)

    if(PizzaBude.getStartTime+60000000000L < now) {
      Table3.checkTables(now)
      Table3.deliver()
      Table3.checkAngryLevel(now)
    }
    if(PizzaBude.getStartTime+120000000000L < now) {
      Table1.checkTables(now)
      Table1.deliver()
      Table1.checkAngryLevel(now)
    }
    if(PizzaBude.getStartTime+180000000000L < now) {
      Table4.checkTables(now)
      Table4.deliver()
      Table4.checkAngryLevel(now)
    }
    PizzaBude.checkGameOver()

  }
}

class PizzaBudeController extends Initializable {

  @FXML var btnPizza: Button = _
  @FXML var btnDrink: Button = _
  @FXML var btnPommes: Button = _
  @FXML var btnStart: Button = _
  @FXML var btnStop: Button = _
  @FXML var btnTable1: Button = _
  @FXML var btnTable2: Button = _
  @FXML var btnTable3: Button = _
  @FXML var btnTable4: Button = _

  @FXML var canvasAnchorPane: AnchorPane = _

  var game:GameLoop = _

  lazy val buttons: Map[Move, Button] = Map(
    Product1 -> btnDrink,
    Product2 -> btnPizza,
    Product3 -> btnPommes,
    Customer1 -> btnTable1,
    Customer2 -> btnTable2,
    Customer3 -> btnTable3,
    Customer4 -> btnTable4
  )

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    val machines = Seq(PizzaOven,Drink)
    val guests: mutable.Map[Guest, Seq[Product]] = mutable.Map()
    val g = PizzaBude.apply(guests,machines)

    val pane = canvasAnchorPane

    g.setGameState(g)
    game = GameLoop(g,buttons)

  }


  @FXML def pizza():Unit = if(!PizzaOven.getState) PizzaOven.setProperty(true)
  @FXML def drink():Unit = if(!Drink.getState) Drink.setProperty(true)
  @FXML def pommes():Unit = if(!Pommes.getState) Pommes.setProperty(true)
  @FXML def start():Unit = game.start()
  @FXML def stop():Unit = game.stop()

  @FXML def table1():Unit = Table1.setProperty(true)
  @FXML def table2():Unit = Table2.setProperty(true)
  @FXML def table3():Unit = Table3.setProperty(true)
  @FXML def table4():Unit = Table4.setProperty(true)

}