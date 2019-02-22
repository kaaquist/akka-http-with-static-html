import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * Created by kasper on 02/03/2017.
  */
object Main {
  def main(args: Array[String]) {

    implicit val actorSystem = ActorSystem("system")
    implicit val actorMaterializer = ActorMaterializer()
    def index = path("") {
      getFromResource("index.html")
    }
    def css = (pathPrefix("css") & path(Segment)) { resource => {
      println("what!")
      getFromResource(s"css/$resource")
      }
    }
    def js = (pathPrefix("js") & path(Segment)) { resource => getFromResource(s"js/$resource") }

    lazy val route: Route =
      pathEndOrSingleSlash {
        getFromResource("index.html")
      } ~ path("css" / Segment) { name =>
        getFromResource("css/" + name)
      } ~ path("js" / Segment) { name =>
        getFromResource("js/" + name)
      }

    Http().bindAndHandle(route,"localhost",8080)

    println("server started at 8080")
  }
}
