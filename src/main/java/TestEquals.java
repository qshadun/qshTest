
public class TestEquals {

  /**
   * @param args
   */
  public static void main(String[] args) {
    Integer a = 111;
    Integer b = 111;
    System.out.println(a == b);
    a = new Integer(111);
    System.out.println(a == b);
    b = new Integer(111);
    System.out.println(a == b);
  }

}
