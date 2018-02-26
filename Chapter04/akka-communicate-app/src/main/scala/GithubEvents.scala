import com.satori.rtm._
import com.satori.rtm.model._
import java.util.concurrent._


object GithubEventsApp extends App {
  val endpoint = "wss://open-data.api.satori.com"
  val appkey = "2aFEefaCbEAf5C1e3db9bdF4BC0AaCb2"
  val channel = "github-events"

    val client = new RtmClientBuilder(endpoint, appkey).setListener(new RtmClientAdapter() {
      def onEnterConnected(client: Nothing): Unit = {
        System.out.println("Connected to RTM!")
      }
    }).build
    val success = new CountDownLatch(20)
    val listener = new SubscriptionAdapter() {
      override def onSubscriptionData(data: SubscriptionData): Unit = {
        import scala.collection.JavaConversions._
        for (json <- data.getMessages) {
          println("Got message: " + json)
        }
        success.countDown()
      }
    }
    client.createSubscription(channel, SubscriptionMode.SIMPLE, listener)
    client.start
    success.await(15, TimeUnit.SECONDS)
    client.shutdown
}