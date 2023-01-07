package mlsp

import scala.collection.concurrent.TrieMap

class FilesCache {
  private val map: TrieMap[String, String] = TrieMap.empty

  def openFile(uri: String, content: String): Unit =
    map.put(uri, content)

  def closeFile(uri: String): Unit =
    map.remove(uri)
}

object FilesCache {
  def apply() = new FilesCache()
}
