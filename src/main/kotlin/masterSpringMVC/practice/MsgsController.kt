package masterSpringMVC.practice

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MsgsController {

  @RequestMapping("/msgs")
  fun hello(@RequestParam(defaultValue = "masterSpringMVC4", value = "search") search: String, model: Model): String {
    val msgs = mutableListOf<String>()
    msgs.add("1")
    msgs.add("2")
    msgs.add("3")
    msgs.add("4")
    msgs.add("5")
    model.addAttribute("msgs", msgs)
    model.addAttribute("search", search)
    return "resultPage"
  }
}