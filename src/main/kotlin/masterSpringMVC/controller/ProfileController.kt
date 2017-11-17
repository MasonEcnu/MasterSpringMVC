package masterSpringMVC.controller

import masterSpringMVC.dates.USLocalDateFormatter
import masterSpringMVC.model.ProfileForm
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.util.*

@Controller
class ProfileController {

  @RequestMapping(value = "/blog/profile")
  fun displayProfile(profileForm: ProfileForm): String = "profile/profilePage"

  @RequestMapping(value = "/blog/profile", method = arrayOf(RequestMethod.POST))
  fun saveProfile(profileForm: ProfileForm): String {
    println("Save OK: $profileForm")
    return "redirect:/blog/profile"
  }

  @ModelAttribute("dateFormat")
  fun localeFormat(locale: Locale?): String = USLocalDateFormatter.getPattern(locale)
}