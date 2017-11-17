package masterSpringMVC.controller

import masterSpringMVC.searchData.SearchData
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
class BlogController {

  @RequestMapping(value = "/blog")
  fun home(): String = "searchPage"

  @RequestMapping("/blog/searchResult")
  fun getSearch(@RequestParam(defaultValue = "masterSpringMVC4", value = "search") search: String, model: Model): String {
    val msgs = SearchData.getSearchResults(search)
    model.addAttribute("msgs", msgs)
    model.addAttribute("search", search)
    return "resultPage"
  }

  @RequestMapping(value = "/blog/postSearch", method = arrayOf(RequestMethod.POST))
  fun postSearch(request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
    val search = request.getParameter("search")
    if (search.toLowerCase().contains("struts")) {
      redirectAttributes.addFlashAttribute("error", "Try using spring instead")
      return "redirect:/blog"
    }
    redirectAttributes.addAttribute("search", search)
    return "redirect:searchResult"
  }

}