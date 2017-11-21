package masterSpringMVC.controller

import masterSpringMVC.search.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.http.HttpServletRequest

@Controller
class SearchController @Autowired
constructor(searchService: SearchService) {

  private val searchService: SearchService? = searchService

//  @RequestMapping(value = "/search")
//  fun home(): String = "searchPage"

  @RequestMapping("/search/{searchType}")
  fun search(@PathVariable searchType: String, @MatrixVariable keywords: List<String>): ModelAndView {
    val results = searchService?.search(searchType, keywords)
    val modelAndView = ModelAndView("resultPage")
    modelAndView.addObject("results", results)
    modelAndView.addObject("keywords", keywords.joinToString(","))
    return modelAndView
  }
//
//  @RequestMapping(value = "/postSearch", method = arrayOf(RequestMethod.POST))
//  fun postSearch(request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
//    val search = request.getParameter("search")
//    if (search.toLowerCase().contains("struts")) {
//      redirectAttributes.addFlashAttribute("error", "Try using spring instead")
//      return "redirect:/"
//    }
//    redirectAttributes.addAttribute("search", search)
//    return "redirect:searchResult"
//  }
}