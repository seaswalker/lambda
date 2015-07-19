package chapter4;

/**
 * 测试接口默认方法
 * @author skywalker
 *
 */
public class DefaultTest {

	interface Parent {
		public default void welcome() {
			System.out.println("Parent welcomes");
		}
	}
	
	interface Child extends Parent {
		@Override
		public default void welcome() {
			System.out.println("Child welcome");
		}
	}
	
	class ParentImpl implements Parent {
		@Override
		public void welcome() {
			System.out.println("ParentImpl welcomes");
		}
	}
	
	public static void main(String[] args) {
		DefaultTest dt = new DefaultTest();
		dt.new ParentImpl().welcome();
	}
	
}
