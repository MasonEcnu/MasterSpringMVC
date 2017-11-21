package masterSpringMVC.profile

import masterSpringMVC.config.PictureUploadProperties
import masterSpringMVC.model.ProfileForm
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.*
import java.net.URLConnection
import java.util.*
import javax.servlet.http.HttpServletResponse

@Controller
@SessionAttributes("picturePath")
class PictureUploadController @Autowired
constructor(uploadProperties: PictureUploadProperties, messageSource: MessageSource, userProfileSession: UserProfileSession) {
  private val picturesDir: Resource? = uploadProperties.uploadPath
  private val anonymousPicture: Resource? = uploadProperties.anonymousPicture
  private val messageSource: MessageSource = messageSource
  private val userProfileSession: UserProfileSession = userProfileSession

  @RequestMapping("/upload")
  fun uploadPage(): String = "profile/uploadPage"

  @RequestMapping(value = "/profile", method = arrayOf(RequestMethod.POST))
  @Throws(IOException::class)
  fun onUpload(@RequestParam file: MultipartFile, redirectAttributes: RedirectAttributes, model: Model, locale: Locale, profileForm: ProfileForm): String {
    if (file.isEmpty || !isImage(file)) {
      redirectAttributes.addFlashAttribute("error", messageSource.getMessage("upload.file.type.error", null, locale))
      return "redirect:/profile"
    }
    val picturePath = copyFileToPictures(file)
    userProfileSession.setPicturePath(picturePath)
    model.addAttribute("success", messageSource.getMessage("upload.success", null, locale))
    return "redirect:/profile"
  }

  @Throws(IOException::class)
  private fun copyFileToPictures(file: MultipartFile): Resource {
    val fileExtension = getFileExtension(file.originalFilename)
    val tempFile = File.createTempFile("pic", fileExtension, picturesDir?.file)
    var input: InputStream? = null
    var output: OutputStream? = null
    try {
      input = file.inputStream
      output = FileOutputStream(tempFile)
      IOUtils.copy(input, output)
    } finally {
      if (input != null) input.close()
      if (output != null) output.close()
    }
    return FileSystemResource(tempFile)
  }

  @ModelAttribute("picturePath")
  fun getPicturePath(): Resource? = anonymousPicture

  @Throws(IOException::class)
  @RequestMapping(value = "/uploadedPicture")
  fun getUploadedPicture(response: HttpServletResponse) {
    var picturePath = userProfileSession.getPicturePath()
    if (picturePath == null) picturePath = anonymousPicture
    response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath?.filename))
    IOUtils.copy(picturePath?.inputStream, response.outputStream)
  }

  @ExceptionHandler(IOException::class)
  fun handleIOException(locale: Locale): ModelAndView {
    val modelAndView = ModelAndView("profile/profilePage")
    modelAndView.addObject("error", messageSource.getMessage("upload.io.exception", null, locale))
    modelAndView.addObject("profileForm", userProfileSession.toForm())
    return modelAndView
  }

  @RequestMapping(value = "/uploadError")
  fun onUploadError(locale: Locale): ModelAndView {
    val modelAndView = ModelAndView("profile/profilePage")
    modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale))
    modelAndView.addObject("profileForm", userProfileSession.toForm())
    return modelAndView
  }

  private fun isImage(file: MultipartFile): Boolean = file.contentType.startsWith("image")

  private fun getFileExtension(fileName: String?): String? = fileName?.substring(fileName.lastIndexOf("."))
}
