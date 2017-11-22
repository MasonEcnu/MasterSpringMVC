package masterSpringMVC.user

import masterSpringMVC.error.EntityNotFoundException
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepository {
  private val userMap = ConcurrentHashMap<String, User>()

  @Throws(EntityNotFoundException::class)
  fun update(email: String, user: User): User? {
    if (!exists(email)) throw EntityNotFoundException("User $email cannot be found!")
    user.email = email
    return userMap.put(email, user)
  }

  fun save(user: User): User? = userMap.put(user.email, user)

  fun findOne(email: String): User? {
    if (!exists(email)) throw EntityNotFoundException("User $email cannot be found!")
    return userMap[email]
  }

  fun findAll(): List<User> = userMap.values.toList()

  fun delete(email: String) {
    if (!exists(email)) throw EntityNotFoundException("User $email cannot be found!")
    userMap.remove(email)
  }

  fun exists(email: String): Boolean = userMap.containsKey(email)
}