package mlsp

import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.{InitializeParams, InitializeResult, ServerCapabilities, ServerInfo}
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
}

object Server {
  def apply() = new Server()
}
