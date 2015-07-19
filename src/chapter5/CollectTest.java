package chapter5;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import roles.Artist;

public class CollectTest {

	/**
	 * 把流转变为任意的集合
	 */
	@Test
	public void toCollect() {
		//等效于传递TreeSet::new
		TreeSet<Integer> set = Stream.of(1, 2, 4, 5)
				.collect(Collectors.toCollection(() -> new TreeSet<Integer>()));
		System.out.println(set.size());
	}
	
	/**
	 * 转换成任意的值
	 * 泛型问题真太TM恶心了
	 * 发现一个问题，只有包装类型的时候在需要显式的声明泛型，其它情况声明了反而报错，原因未知
	 */
	@Test
	public void toValue() {
		Stream<Integer> stream = Stream.of(1, 2, 5, 10, 98);
		//求最大值
		System.out.println(stream.collect(Collectors.maxBy(Integer::compare)).get());
		//求平均值
		stream = Stream.of(1, 2, 5, 10, 98);
		System.out.println(stream.collect(Collectors.<Integer>averagingInt(i -> i.intValue())));
	}
	
	/**
	 * 数据分块
	 */
	@Test
	public void partition() {
		Stream<Integer> stream = Stream.of(1, 2, 4, 7, 10, 11, 14, 15);
		Map<Boolean, List<Integer>> result = stream.collect(
				Collectors.<Integer>partitioningBy(i -> i > 10));
		System.out.println(result.get(Boolean.TRUE));
		System.out.println(result.get(Boolean.FALSE));
	}
	
	/**
	 * 数据分组，貌似是一种比分块更灵活的方式
	 * 我觉得适用于多对一的情况
	 */
	@Test
	public void group() {
		List<Artist> artists = Arrays.asList(new Artist("London", 12), new Artist("London", 22), new Artist("Beijing", 24));
		Map<String, List<Artist>> groups = artists.stream()
				.collect(Collectors.groupingBy(artist -> artist.getHome()));
		for (String key : groups.keySet()) {
			System.out.println(groups.get(key));
		}
	}
	
	/**
	 * 把流转换为字符串
	 */
	@Test
	public void join() {
		String result = Stream.of(1, 2, 3, 4, 5, 6, 7)
				//此处需要转化一次除非of的参数是String
				.map(i -> i.toString())
				.collect(Collectors.joining(",", "[", "]"));
		//joining()没有参数结果就是1234567
		System.out.println(result);
	}
	
	/**
	 * 组合收集器, key为地址，value为年龄
	 */
	@Test
	public void combineCollect() {
		List<Artist> artists = Arrays.asList(new Artist("London", 12), new Artist("London", 22), new Artist("Beijing", 24));
		Map<String, List<Integer>> result = artists.stream()
				.collect(Collectors.groupingBy(Artist::getHome, Collectors.mapping(Artist::getAge, Collectors.toList())));
		for (Map.Entry<String, List<Integer>> entry : result.entrySet()) {
			System.out.println(entry.getValue());
		}
	}
	
	/**
	 * 1.8加入的java.util.StringJoiner类
	 * 然而这个类的实现和Lambda没有半毛钱关系
	 */
	@Test
	public void stringJoiner() {
		StringJoiner sj = new StringJoiner(":", "[", "]");
		sj.add("Tom").add("Jack").add("Blosh");
		//[Tom:Jack:Blosh]
		System.out.println(sj.toString());
	}
	
	/**
	 * 1.8加入的新的迭代map的方式
	 * 内部怎么实现的你应该可以猜出来，猜不出来看源码
	 */
	@Test
	public void forEachMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Tom");
		map.put(2, "Jack");
		map.put(3, "Jerry");
		map.putIfAbsent(4, "Four");
		map.forEach((key, value) -> {
			System.out.println("key:" + key + " value:" + value);
		});
	}
	
}
