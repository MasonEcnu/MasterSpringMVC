package masterSpringMVC.user.api

import masterSpringMVC.error.EntityNotFoundException
import masterSpringMVC.user.User
import masterSpringMVC.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserApiController @Autowired
constructor(userRepository: UserRepository) {
  private val userRepository: UserRepository = userRepository

  @RequestMapping(value = "/users", method = arrayOf(RequestMethod.GET))
  fun findAll(): List<User> = userRepository.findAll()


  @RequestMapping(value = "/users", method = arrayOf(RequestMethod.POST))
  fun createUser(@RequestBody user: User): ResponseEntity<User?> {
    var status = HttpStatus.OK
    if (!userRepository.exists(user.email)) status = HttpStatus.CREATED
    val saved = userRepository.save(user)
    return ResponseEntity(saved, status)
  }

  @Throws(EntityNotFoundException::class)
  @RequestMapping(value = "/user/{email}", method = arrayOf(RequestMethod.PUT))
  fun updateUser(@PathVariable email: String, @RequestBody user: User): ResponseEntity<User?> {
    val saved = userRepository.update(email, user)
    return ResponseEntity(saved, HttpStatus.CREATED)
  }

  @Throws(EntityNotFoundException::class)
  @RequestMapping(value = "/user/{email}", method = arrayOf(RequestMethod.DELETE))
  fun deleteUser(@PathVariable email: String): ResponseEntity<User?> {
    userRepository.delete(email)
    return ResponseEntity(HttpStatus.OK)
  }
}