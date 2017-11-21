package masterSpringMVC.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource

import java.io.IOException

@ConfigurationProperties(prefix = "upload.pictures")
class PictureUploadProperties {
  var uploadPath: Resource? = null
    private set
  var anonymousPicture: Resource? = null
    private set

  @Throws(IOException::class)
  fun setAnonymousPicture(anonymousPicture: String) {
    this.anonymousPicture = DefaultResourceLoader().getResource(anonymousPicture)
    val file = this.anonymousPicture?.file
    if (file == null || !file.isFile) {
      throw IOException("File $anonymousPicture does not exist")
    }
  }

  @Throws(IOException::class)
  fun setUploadPath(uploadPath: String) {
    this.uploadPath = DefaultResourceLoader().getResource(uploadPath)
    val file = this.uploadPath?.file
    if (file == null || !file.isDirectory) {
      throw IOException("Directory $uploadPath does not exist")
    }
  }
}
