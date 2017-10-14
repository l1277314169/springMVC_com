package cn.mobilizer.channel.comm.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Exceptions
{
  public static RuntimeException unchecked(Throwable ex)
  {
    if ((ex instanceof RuntimeException)) {
      return (RuntimeException)ex;
    }
    return new RuntimeException(ex);
  }

  public static String getStackTraceAsString(Throwable ex)
  {
    StringWriter stringWriter = new StringWriter();
    ex.printStackTrace(new PrintWriter(stringWriter));
    return stringWriter.toString();
  }

  public static String getErrorMessageWithNestedException(Throwable ex)
  {
    Throwable nestedException = ex.getCause();
    return ex.getMessage() + " nested exception is " + nestedException.getClass().getName() + ":" + nestedException.getMessage();
  }

  public static Throwable getRootCause(Throwable ex)
  {
    Throwable cause;
    while ((cause = ex.getCause()) != null) {
      ex = cause;
    }
    return ex;
  }

  public static boolean isCausedBy(Exception ex, Class<? extends Exception>[] causeExceptionClasses)
  {
    Throwable cause = ex;
    while (cause != null) {
      for (Class causeClass : causeExceptionClasses) {
        if (causeClass.isInstance(cause)) {
          return true;
        }
      }
      cause = cause.getCause();
    }
    return false;
  }
}