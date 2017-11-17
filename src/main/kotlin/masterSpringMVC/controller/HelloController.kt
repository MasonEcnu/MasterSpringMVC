package masterSpringMVC.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {


  // 该注解表示直接映射到特定模型
  //  @ResponseBody
  // 删除该注解表示，将字符串映射为试图名
//  @RequestMapping("/")
//  fun hello(model: Model): String {
//    model.addAttribute("message", "Hello from the controller!")
//    return "resultPage"
//  }

  // get请求方式
  @RequestMapping("/hello")
  fun hello(@RequestParam(defaultValue = "Wu",value = "name") userName: String, model: Model): String {
    model.addAttribute("message", "Hello $userName")
    return "resultPage"
  }
}
