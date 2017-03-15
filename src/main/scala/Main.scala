import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

/**
  * Created by kasper on 02/03/2017.
  */
object Main {
  def main(args: Array[String]) {

    implicit val actorSystem = ActorSystem("system")
    implicit val actorMaterializer = ActorMaterializer()


    val route =
      pathSingleSlash {
        get {
          getFromResource("index.html")
        }
      }
    Http().bindAndHandle(route,"localhost",8080)

    println("server started at 8080")
  }
}
