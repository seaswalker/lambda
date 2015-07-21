package chapter6;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class Exercise {

	/**
	 * 并行求平方和
	 */
	@Test
	public void paralleSumOfQuares() {
		IntStream intStream = IntStream.range(1, 11);
		int sum = intStream.parallel()
			.map(x -> x * x)
			.sum();
		System.out.println(sum);
	}
	
	/**
	 * 把列表中所有的数字相乘，最后的结果再乘以5
	 * 正确结果3000，错误结果1875000，是3000的625(5的4次方)倍
	 */
	@Test
	public void multiply() {
		//3000
		Stream<Integer> stream = Stream.of(1, 4, 3, 5, 10);
		int mul = stream.parallel().reduce(1, (result, num) -> result * num) * 5;
		System.out.println(mul);
	}
	
	/**
	 * 还是求平方和
	 * 简单的改动
	 * 3 ==>> 此题的意思是把链表改为数组，代码本身没问题
	 */
	@Test
	public void sumOfSquares() {
		//151
		Stream<Integer> stream = Stream.of(1, 4, 3, 5, 10);
		int result = stream.parallel()
				.map(x -> x * x)
				.reduce(0, Integer::sum);
		System.out.println(result);
	}
	
}
