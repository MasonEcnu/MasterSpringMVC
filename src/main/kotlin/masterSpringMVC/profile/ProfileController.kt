package masterSpringMVC.profile

import masterSpringMVC.date.USLocalDateFormatter
import masterSpringMVC.model.ProfileForm
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class ProfileController {

  @RequestMapping(value = "/profile")
  fun displayProfile(profileForm: ProfileForm): String = "profile/profilePage"

  @RequestMapping(value = "/profile", params = arrayOf("save"), method = arrayOf(RequestMethod.POST))
  fun saveProfile(@Valid profileForm: ProfileForm, bindingResult: BindingResult): String {
    if (bindingResult.hasErrors()) return "profile/profilePage"
    println("Save OK: $profileForm")
    return "redirect:/profile"
  }

  @RequestMapping(value = "/profile", params = arrayOf("addTaste"))
  fun addRow(profileForm: ProfileForm): String {
    profileForm.tastes.add(null)
    return "profile/profilePage"
  }

  @RequestMapping(value = "/profile", params = arrayOf("removeTaste"))
  fun removeRow(profileForm: ProfileForm, request: HttpServletRequest): String {
    val rowId = request.getParameter("removeTaste")
    profileForm.tastes.removeAt(rowId.toInt())
    return "profile/profilePage"
  }

  @ModelAttribute("dateFormat")
  fun localeFormat(locale: Locale?): String = USLocalDateFormatter.getPattern(locale)
}