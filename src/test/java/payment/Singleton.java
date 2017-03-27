package payment;

//单例模式
public class Singleton {

  private static Singleton instance = new Singleton();

  public static Singleton getinstance() {
    System.err.println("a");
    return instance;
  }

  private Singleton() {
    System.err.println("b");
  }

  public static void main(String[] args) {
    Singleton.getinstance();
  }
}