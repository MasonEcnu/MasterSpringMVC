package masterSpringMVC.profile

import masterSpringMVC.config.PictureUploadProperties
import masterSpringMVC.date.USLocalDateFormatter
import masterSpringMVC.model.ProfileForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.net.URLConnection
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Controller
class ProfileController @Autowired
constructor(userProfileSession: UserProfileSession, uploadProperties: PictureUploadProperties) {

  private val userProfileSession = userProfileSession
  private val anonymousPicture: Resource? = uploadProperties.anonymousPicture

  @RequestMapping(value = "/profile")
  fun displayProfile(profileForm: ProfileForm): String = "profile/profilePage"

  @ModelAttribute
  fun getProfileForm(): ProfileForm = userProfileSession.toForm()

  @RequestMapping(value = "/profile", params = arrayOf("save"), method = arrayOf(RequestMethod.POST))
  fun saveProfile(@Valid profileForm: ProfileForm, bindingResult: BindingResult): String {
    if (bindingResult.hasErrors()) return "profile/profilePage"
    userProfileSession.saveForm(profileForm)
    return "redirect:/search/mixed;keywords=${profileForm.tastes.joinToString(",")}"
  }

  @RequestMapping(value = "/profile", params = arrayOf("addTaste"))
  fun addRow(profileForm: ProfileForm, response: HttpServletResponse): String {
    profileForm.tastes.add(null)
    var picturePath = userProfileSession.getPicturePath()
    if (picturePath == null) picturePath = anonymousPicture
    response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath?.filename))
    return "profile/profilePage"
  }

  @RequestMapping(value = "/profile", params = arrayOf("removeTaste"))
  fun removeRow(profileForm: ProfileForm, request: HttpServletRequest, response: HttpServletResponse): String {
    val rowId = request.getParameter("removeTaste")
    profileForm.tastes.removeAt(rowId.toInt())
    var picturePath = userProfileSession.getPicturePath()
    if (picturePath == null) picturePath = anonymousPicture
    response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath?.filename))
    return "profile/profilePage"
  }

  @ModelAttribute("dateFormat")
  fun localeFormat(locale: Locale?): String = USLocalDateFormatter.getPattern(locale)
}