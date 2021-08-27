package com.example.demo.common.myException;

/**
 * @author qingyun
 * @date 2021/2/18 4:49 下午
 */
public class ToUserException extends RuntimeException {
  public ToUserException() {}

  public ToUserException(String message) {
    super(message);
  }
}
