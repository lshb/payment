package payment;

public class SingletonTest {

  private volatile static SingletonTest st = null;

  private SingletonTest() {
  }

  public static SingletonTest getSingleton() {
    if (st == null) {
      synchronized (st) {
        if (st == null) {
          st = new SingletonTest();
        }
      }
    }
    return st;
  }

}
