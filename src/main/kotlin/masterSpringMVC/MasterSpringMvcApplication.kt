package masterSpringMVC

import masterSpringMVC.config.PictureUploadProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@SpringBootApplication
@EnableConfigurationProperties(PictureUploadProperties::class)
class MasterSpringMvcApplication : WebMvcConfigurerAdapter()

fun main(args: Array<String>) {
  SpringApplication.run(MasterSpringMvcApplication::class.java, *args)
}
