package fromJavaToKotlin;

public class Java extends Exception {
  public Java(String message) {
    super(message);
  }

  public Java(String message, Throwable cause) {
    super(message, cause);
  }
}