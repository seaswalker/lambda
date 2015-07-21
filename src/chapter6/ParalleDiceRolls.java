package chapter6;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * P73 6-3
 * 使用Lambda完成蒙特卡洛模拟
 * @author skywalker
 *
 */
public class ParalleDiceRolls {
	
	//n 模拟的次数
	public static Map<Integer, Double> paralleDiceRolls(int n) {
		//概率
		double fraction = 1D / n;
		return IntStream.range(0, n)
				.parallel()
				.mapToObj(i -> twoDiceThrows())
				.collect(Collectors.groupingBy(i -> i, Collectors.summingDouble(i -> fraction)));
		
	}
	
	/**
	 * 执行一次掷骰子模拟
	 */
	public static int twoDiceThrows() {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int first = random.nextInt(1, 7);
		int second = random.nextInt(1, 7);
		return first + second;
	}
	
	public static void main(String[] args) {
		Map<Integer, Double> result = paralleDiceRolls(1000);
		result.forEach((key, value) -> {
			System.out.println("key:" + key + " value:" + value);
		});
	}
	
}
