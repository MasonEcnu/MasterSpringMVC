package fromJavaToKotlin

class Java : Exception {
  constructor(message: String) : super(message) {}

  constructor(message: String, cause: Throwable) : super(message, cause) {}
}