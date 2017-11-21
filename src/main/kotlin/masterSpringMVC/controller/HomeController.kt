package masterSpringMVC.controller

import masterSpringMVC.profile.UserProfileSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HomeController @Autowired
constructor(userProfileSession: UserProfileSession) {
  private val userProfileSession: UserProfileSession = userProfileSession

  @RequestMapping("/")
  fun home(): String {
    val tastes = userProfileSession.tastes
    if (tastes.isEmpty()) return "redirect:/profile"
    return "redirect:/search/mixed;keywords=${userProfileSession.tastes.joinToString(",")}"
  }
}