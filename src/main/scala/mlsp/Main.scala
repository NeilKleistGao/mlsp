package mlsp

import java.util.concurrent.Executors
import org.eclipse.lsp4j.jsonrpc.Launcher
import org.eclipse.lsp4j.services.LanguageClient

object Main {
  def main(args: Array[String]): Unit = {
    val exec = Executors.newCachedThreadPool()
    val server = Server()

    val launcher = new Launcher.Builder[LanguageClient]()
      .setExecutorService(exec)
      .setInput(System.in)
      .setOutput(System.out)
      .setRemoteInterface(classOf[LanguageClient])
      .setLocalService(server)
      .create()

    val clientProxy = launcher.getRemoteProxy
    server.connectToLanguageClient(clientProxy)
    launcher.startListening().get()
  }
}
