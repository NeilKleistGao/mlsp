package mlsp

import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.{_}
import java.util.concurrent.CompletableFuture
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest

class Server() {
  val languageClient = new DelegateClient(DummyClient)

  def connectToLanguageClient(client: LanguageClient): Unit = {
    languageClient.underlying = client
  }

  @JsonRequest("initialize")
  def initialize(params: InitializeParams): CompletableFuture[InitializeResult] = {
    val capabilities = new ServerCapabilities()
    val serverInfo = new ServerInfo("MLSP")
    val result = new InitializeResult(capabilities, serverInfo)
    CompletableFuture.completedFuture(result)
  }

  @JsonRequest("initialized")
  def initialized(params: InitializedParams): CompletableFuture[Unit] =
    CompletableFuture.completedFuture()
}

object Server {
  def apply() = new Server()
}
