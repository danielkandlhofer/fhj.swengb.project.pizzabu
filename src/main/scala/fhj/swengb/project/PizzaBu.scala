package fhj.swengb.project

/**
  * Created by Stefan on 04.01.2016.
  */

/**
  * Fields for the Game
  *
  * |-----|-----|-----|
  * | f00   f01   f02 |
  * |-----|-----|-----|
  * | f10   f11   f12 |
  * |-----|-----|-----|
  * | f20   f21   f22 |
  * |-----|-----|-----|
  * | f30   f31   f32 |
  * |-----|-----|-----|
  * | f40   f41   f42 |
  * |-----|-----|-----|
  * */
sealed trait Field

case object f00 extends Field
case object f01 extends Field
case object f02 extends Field
case object f10 extends Field
case object f11 extends Field
case object f12 extends Field
case object f20 extends Field
case object f21 extends Field
case object f22 extends Field
case object f30 extends Field
case object f31 extends Field
case object f32 extends Field
case object f40 extends Field
case object f41 extends Field
case object f42 extends Field

sealed trait Move

case object Up extends Move
case object Down extends Move
case object Left extends Move
case object Right extends Move
case object Action extends Move

object PizzaBu {

  val playField:Array[Array[Field]] = Array(Array(f00,f01,f02),Array(f10,f11,f12),Array(f20,f21,f22),Array(f30,f31,f32),Array(f40,f41,f42))

  var row:Int = 2
  var col:Int = 2

  def main(args: Array[String]) {
    println(PizzaBu(Player()).asString)
    println(playField(2)(2))
    getInput

    def getInput:Unit = {
      readLine("EnterMove!").toLowerCase match {
        case "w" => move("w");println(row);println("UP");println(PizzaBu(Player(playField(row)(col))).asString());getInput
        case "a" => move("a");println(col);println("LEFT");println(PizzaBu(Player(playField(row)(col))).asString());getInput
        case "s" => move("s");println("RIGHT");println(PizzaBu(Player(playField(row)(col))).asString());getInput
        case "d" => move("d");println("DOWN");println(PizzaBu(Player(playField(row)(col))).asString());getInput
        case "x" => sys.exit()
        case _ => println("NOT VALID");getInput
      }
    }

    def move(move: String) = move match {
      case "w" if row!=0 => row-=1;println("GEHT")
      case "a" if col!=0 => col-=1;println("GEHT")
      case "s" if row!=5 => row+=1;println("GEHT")
      case "d" if col!=2 => col+=1;println("GEHT")
      case _ => "FUNZT NET"

    }

  }

  /**
    * Starts a new game of PizzaBu
    */
  def apply: PizzaBu = PizzaBu(Player())
}

case class PizzaBu(player: Player) {

  def asString():String = {

    def chkPF(field:Field):String = field match {
      case player.field => "  X  "
      case _ => "     "
    }

    "|-----|-----|-----|\n"+
    "|"+chkPF(f00)+"|"+chkPF(f01)+"|"+chkPF(f02)+"|\n"+
    "|-----|-----|-----|\n"+
    "|"+chkPF(f10)+"|"+chkPF(f11)+"|"+chkPF(f12)+"|\n"+
    "|-----|-----|-----|\n"+
    "|"+chkPF(f20)+"|"+chkPF(f21)+"|"+chkPF(f22)+"|\n"+
    "|-----|-----|-----|\n"+
    "|"+chkPF(f30)+"|"+chkPF(f31)+"|"+chkPF(f32)+"|\n"+
    "|-----|-----|-----|\n"+
    "|"+chkPF(f40)+"|"+chkPF(f41)+"|"+chkPF(f42)+"|\n"+
    "|-----|-----|-----|\n"
  }

}