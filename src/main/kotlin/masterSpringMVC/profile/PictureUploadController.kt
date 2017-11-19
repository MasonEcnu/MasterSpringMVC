package masterSpringMVC.profile

import masterSpringMVC.config.PictureUploadProperties
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.*
import java.net.URLConnection
import java.nio.file.Files
import java.nio.file.Path
import javax.servlet.http.HttpServletResponse

@Controller
@SessionAttributes("picturePath")
class PictureUploadController {
  companion object {
    val PICTURES_DIR = FileSystemResource("./pictures")
  }

  private lateinit var picturesDir: Resource
  private lateinit var anonymousPicture: Resource

  @Autowired
  constructor(uploadProperties: PictureUploadProperties) {
    val tempDir = uploadProperties.uploadPath
    if (tempDir != null) this.picturesDir = tempDir
    val tempPic = uploadProperties.anonymousPicture
    if (tempPic != null) this.anonymousPicture = tempPic
  }


  @RequestMapping("/upload")
  fun uploadPage(): String = "profile/uploadPage"


  @RequestMapping(value = "/upload", method = arrayOf(RequestMethod.POST))
  fun onUpload(file: MultipartFile, redirectAttributes: RedirectAttributes, model: Model): String {
    if (file.isEmpty || !isImage(file)) {
      redirectAttributes.addFlashAttribute("error", "error")
      return "redirect:/upload"
    }
    val picturePath = copyFileToPictures(file)
    model.addAttribute("picturePath", picturePath)
    return "/profile/uploadPage"
  }

  @Throws(IOException::class)
  private fun copyFileToPictures(file: MultipartFile): Resource {
    val fileExtension = getFileExtension(file.originalFilename)
    val tempFile = File.createTempFile("pic", fileExtension, PICTURES_DIR.file)
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
  fun getPicturePath(): Resource = anonymousPicture

  @Throws(IOException::class)
  @RequestMapping(value = "/uploadedPicture")
  fun getUploadedPicture(response: HttpServletResponse, @ModelAttribute("picturePath") picturePath: Resource) {
    response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.filename))
    //IOUtils.copy(anonymousPicture.inputStream, response.outputStream)
    IOUtils.copy(picturePath.inputStream, response.outputStream)
  }

  private fun isImage(file: MultipartFile): Boolean = file.contentType.startsWith("image")

  private fun getFileExtension(fileName: String?): String? = fileName?.substring(fileName.lastIndexOf("."))
}