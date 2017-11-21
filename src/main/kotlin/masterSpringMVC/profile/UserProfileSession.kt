package masterSpringMVC.profile

import masterSpringMVC.model.ProfileForm
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.Serializable
import java.net.URL
import java.time.LocalDate

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserProfileSession : Serializable {
  var name: String? = null
  var email: String? = null
  var mobilePhone: String? = null
  var birthDate: LocalDate? = null
  var tastes: MutableList<String?> = mutableListOf()
  private var picturePath: URL? = null

  fun saveForm(profileForm: ProfileForm) {
    this.name = profileForm.name
    this.email = profileForm.email
    this.mobilePhone = profileForm.mobilePhone
    this.birthDate = profileForm.birthDate
    this.tastes = profileForm.tastes
  }

  fun toForm(): ProfileForm = ProfileForm().apply {
    name = this.name
    email = this.email
    mobilePhone = this.mobilePhone
    birthDate = this.birthDate
    tastes = this.tastes
  }

  @Throws(IOException::class)
  fun setPicturePath(picturePath: Resource) {
    this.picturePath = picturePath.url
  }

  fun getPicturePath(): Resource? = if (picturePath == null) null else UrlResource(picturePath)
}
