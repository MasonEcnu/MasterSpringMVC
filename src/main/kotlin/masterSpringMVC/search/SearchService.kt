package masterSpringMVC.search

import org.springframework.stereotype.Service

@Service
class SearchService {
  val msgs = mutableListOf<String>(
      "ls:显示文件或目录",
      "mkdir:创建目录",
      "cd:切换目录",
      "touch:创建空文件",
      "echo:创建带有内容的文件",
      "cat:查看文件内容",
      "cp:拷贝",
      "mv:移动或重命名",
      "rm:删除文件",
      "find:在文件系统中搜索某文件",
      "wc:统计文本中行数、字数、字符数",
      "grep:在文本文件中查找某个字符串",
      "rmdir:删除空目录",
      "tree:树形结构显示目录，需要安装tree包",
      "pwd:显示当前目录",
      "ln:创建链接文件",
      "more、less:分页显示文本文件内容",
      "head、tail:显示文件头、尾内容",
      "ctrl+alt+F1:命令行全屏模式"
  )

  fun search(searchType: String, keywords: List<String>): List<String> {
    val result = mutableListOf<String>()
    val set = mutableSetOf<String>()
    keywords.forEach {
      val key = it
      msgs.forEach {
        if (it.contains(key)) set.add(it)
      }
    }
    if (set.size == 0) result.add("")
    else {
      set.forEach {
        result.add(it)
      }
    }
    return result
  }
}