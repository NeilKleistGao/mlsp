package mlsp

import org.eclipse.lsp4j.services.LanguageClient
import org.eclipse.lsp4j.{_}
import java.util.concurrent.CompletableFuture
import org.eclipse.lsp4j.jsonrpc.services.{JsonRequest, JsonNotification}
import org.eclipse.lsp4j.DidOpenTextDocumentParams

class Server() {
  private val languageClient = new DelegateClient(DummyClient)
  private val cache = FilesCache()

  def connectToLanguageClient(client: LanguageClient): Unit = {
    languageClient.underlying = client
  }

  @JsonRequest("initialize")
  def initialize(params: InitializeParams): CompletableFuture[InitializeResult] = {
    val capabilities = new ServerCapabilities()

    val textDocumentSyncOptions = new TextDocumentSyncOptions()
    textDocumentSyncOptions.setChange(TextDocumentSyncKind.Full)
    textDocumentSyncOptions.setSave(new SaveOptions(true))
    textDocumentSyncOptions.setOpenClose(true)
    capabilities.setTextDocumentSync(textDocumentSyncOptions)

    capabilities.setDefinitionProvider(true)

    val serverInfo = new ServerInfo("MLSP")
    val result = new InitializeResult(capabilities, serverInfo)
    CompletableFuture.completedFuture(result)
  }

  @JsonRequest("initialized")
  def initialized(params: InitializedParams): CompletableFuture[Unit] =
    CompletableFuture.completedFuture()

  @JsonNotification("textDocument/didOpen")
  def didOpen(params: DidOpenTextDocumentParams): CompletableFuture[Unit] = {
    val path = params.getTextDocument().getUri()
    cache.openFile(path, params.getTextDocument().getText())
    CompletableFuture.completedFuture()
  }

  @JsonNotification("textDocument/didClose")
  def didClose(params: DidCloseTextDocumentParams): Unit = {
    val path = params.getTextDocument().getUri()
    cache.closeFile(path)
    CompletableFuture.completedFuture()
  }
}

object Server {
  def apply() = new Server()
}
