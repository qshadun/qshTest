
public class TestConstructorSequence {
	private static class Parent {
		public	Parent() {
			System.out.println("Parent Constructor");
		}
	}
	
	private static class Child extends Parent {
		private Something some = new Something();
		public Child(){
			System.out.println("Child Constructor");
		}
	}
	
	private static class Something {
		public Something() {
			System.out.println("Something Construcotr");
		}
	}
	
	public static void main(String[] args) {
		new Child();
	}
}
