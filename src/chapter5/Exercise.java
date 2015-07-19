package chapter5;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 课后练习
 * 全部使用方法引用实现
 * @author skywalker
 *
 */
public class Exercise {

	/**
	 * 转为大写
	 */
	@Test
	public void upper() {
		Stream<String> stream = Stream.of("hello", "java", "groovy");
		List<String> list = stream.map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(list);
	}
	
	/**
	 * 使用reduce实现count()
	 */
	public <T> int count(Stream<T> stream) {
		int sum = stream.map(t -> 1)
				.reduce(0, Integer::sum);
		return sum;
	}
	
	@Test
	public void testCount() {
		Stream<String> stream = Stream.of("hello", "java", "groovy");
		System.out.println(count(stream));
	}
	
	/**
	 * 使用收集器得出最长的名字
	 */
	@Test
	public void collectLonger() {
		Stream<String> names = Stream.of("John Lennon", "Paul McCartney", "George Harrison", 
				"Ringo Starr", "Pete Best", "Stuart Sutcliffe");
		System.out.println(names.collect(Collectors.maxBy(Comparator.comparing(String::length))).get());
	}
	
	/**
	 * 使用高阶reduce实现上面的函数
	 */
	@Test
	public void reduceLonger() {
		Stream<String> names = Stream.of("Stuart Sutcliffe", "Paul McCartney", "George Harrison", 
				"Ringo Starr", "Pete Best", "John Lennon");
		String longest = names.reduce((pre, name) -> {
			return name.length() > pre.length() ? name : pre;
		}).get();
		System.out.println(longest);
	}
	
	/**
	 * 统计单词出现的次数
	 */
	@Test
	public void wordsCount() {
		Stream<String> stream = Stream.of("John", "Paul", "George", "John", "Paul", "John");
		Map<String, Long> groups = stream.collect(Collectors.groupingBy(word -> word, Collectors.counting()));
		groups.forEach((key, value) -> {
			System.out.println(key + " → " + value);
		});
	}
	
	//斐波那契数列缓存
	private Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
	{
		cache.put(1, 1);
		cache.put(2, 1);
	}
	
	/**
	 * 实现一个带缓存的斐波那契求值函数
	 */
	public int fibonacci(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("the argument can't be smaller than 1.");
		} else if (n <= 2) {
			return 1;
		} else {
			return cache.computeIfAbsent(n, i -> fibonacci(i - 1) + fibonacci(i - 2));
		}
	}
	
	@Test
	public void testFibonacci() {
		System.out.println(fibonacci(10));
	}
	
}
