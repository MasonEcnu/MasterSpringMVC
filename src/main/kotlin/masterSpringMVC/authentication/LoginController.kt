package masterSpringMVC.authentication

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class LoginController {
  @RequestMapping("/login")
  fun authenticate(): String = "/login"
}