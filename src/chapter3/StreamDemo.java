package chapter3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import roles.Artist;

/**
 * 测试集合的"流"操作
 * @author skywalker
 *
 */
public class StreamDemo {
	
	private List<Artist> artists;

	@Before
	public void before() {
		artists = new ArrayList<Artist>();
		artists.add(new Artist("London", 20));
		artists.add(new Artist("Shanghai", 19));
		artists.add(new Artist("Qingdao", 25));
		artists.add(new Artist("Beijing", 30));
		artists.add(new Artist("Henan", 27));
	}
	
	@Test
	public void test() {
		artists.stream().filter(artist -> {
			System.out.println(artist.getHome());
			return artist.isFrom("London");
		})
		.count();
	}
	
	/**
	 * 流操作的结果转为集合
	 */
	@Test
	public void collect() {
		List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
		System.out.println(list);
	}
	
	/**
	 * map()方法用于把一种类型变为另一种类型
	 * map方法接收的是一个Function类型
	 * 方法签名: R apply(T t);
	 * Lambda表达式后面如果有代码快{}，那么应该显示的使用return，否则->后面的就是返回值
	 * 下面有两种等效的写法:
	 */
	@Test
	public void map() {
		System.out.println(Stream.of("a", "hello", "test")
				.map(str -> {
					return str.toUpperCase();
				})
				.collect(Collectors.toList()));
		//等价于:
		System.out.println(Stream.of("a", "hello", "test").map(str -> str.toUpperCase())
				.collect(Collectors.toList()));
	}
	
	@Test
	public void flatMap() {
		System.out.println(Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6))
				.flatMap(numbers -> numbers.stream()).count());
	}
	
	/**
	 * Lambda的Compator对象生成
	 */
	@Test
	public void compare() {
		//artist -> artist.getAge()等同于Artist::getAge
		System.out.println(artists.stream()
				.min(Comparator.comparing(artist -> artist.getAge())).get().getHome());
	}
	
	/**
	 * P24 "reduce"模式
	 * Integer的sum、min、max函数就是从JDK1.8加入的
	 */
	@Test
	public void reduce() {
		//int sum = Stream.of(1, 2, 3).reduce(0, (acc, num) -> acc + num);
		//或
		int sum = Stream.of(1, 2, 3).reduce(0, Integer::sum);
		System.out.println(sum);
	}
	
}
