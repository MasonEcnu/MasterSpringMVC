package masterSpringMVC.search.api

import masterSpringMVC.search.SearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/search")
class SearchApiController @Autowired
constructor(searchService: SearchService) {
  private val searchService: SearchService = searchService

  @RequestMapping(value = "/{searchType}", method = arrayOf(RequestMethod.GET))
  fun search(@PathVariable searchType: String, @MatrixVariable keywords: List<String>): List<String> =
      searchService.search(searchType, keywords)
}