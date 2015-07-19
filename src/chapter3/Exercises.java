package chapter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import roles.Artist;

/**
 * P32 练习
 * @author skywalker
 *
 */
public class Exercises {
	
	/**
	 * 求和函数
	 */
	public int addUp(Stream<Integer> stream) {
		if (stream == null) {
			throw new IllegalArgumentException();
		}
		return stream.reduce(0, (acc, num) -> acc + num);
	}
	
	@Test
	public void testAddUp() {
		Stream<Integer> stream = Stream.of(1, 4, 10, 13);
		System.out.println(addUp(stream));
	}
	
	/**
	 * 返回一个包含艺术家年龄和国籍的字符串列表
	 */
	public List<String> getAgeAndHome(List<Artist> artists) {
		if (artists == null) {
			throw new IllegalArgumentException();
		}
		return artists.stream()
				.map(artist -> artist.getAge() + artist.getHome())
				.collect(Collectors.toList());
	}
	
	@Test
	public void testGetAgeAndHome() {
		List<Artist> artists = new ArrayList<Artist>();
		artists = new ArrayList<Artist>();
		artists.add(new Artist("London", 20));
		artists.add(new Artist("Shanghai", 19));
		artists.add(new Artist("Qingdao", 25));
		artists.add(new Artist("Beijing", 30));
		artists.add(new Artist("Henan", 27));
		System.out.println(getAgeAndHome(artists));
	}
	
	/**
	 * 求年龄总和
	 */
	public int getAgeTotal(List<Artist> artists) {
		if (artists == null) {
			throw new IllegalArgumentException();
		}
		return artists.stream()
				.map(artist -> artist.getAge())
				.reduce(0, Integer::sum);
	}
	
	/**
	 * 统计字符串中小写字母的个数
	 */
	public long lowerCount(String str) {
		return str.chars()
				.filter(c -> c >= 'a' && c <= 'z')
				.count();
	}
	
	@Test
	public void testLowerCount() {
		System.out.println(lowerCount("HelloWorLD"));
	}
	
	/**
	 * 找出包含最多小写字母的字符串 
	 */
	public String mostLowers(List<String> strs) {
		Optional<String> result = strs.stream()
				.max(Comparator.comparingLong(str -> lowerCount(str)));
		return result.isPresent() ? result.get() : null;
	}
	
	@Test
	public void testMostLowers() {
		//System.out.println(mostLowers(Arrays.asList("ABc", "aBc", "ABC", "abc")));
		System.out.println(mostLowers(Collections.emptyList()));
	}
	
	/**
	 * 实现map
	 * 难度略大
	 * <I> 输入类型
	 * <O> 输出(转换成为)的类型
	 */
	public <I, O> Stream<O> map(Stream<I> stream, Function<I, O> mapper) {
		//初始类型必须用List而不是O，因为后面acc的类型和初始类型是一致的，那样的话acc类型是O，那还怎么加
		return stream.reduce(new ArrayList<O>(),
				//每次相加
				(acc, x) -> {
					List<O> result = new ArrayList<O>();
					result.add(mapper.apply(x));
					acc.addAll(result);
					return result;
				}, 
				//合并器
				(List<O> left, List<O> right) -> {
					List<O> result = new ArrayList<O>(left);
					result.addAll(right);
					return result;
				}).stream();
	}
	
	/**
	 * 自己实现filter
	 */
	public <T> Stream<T> filter(Stream<T> stream, Predicate<T> predicate) {
		return stream.reduce(new ArrayList<T>(),
				//每次相加
				(acc, x) -> {
					List<T> result = new ArrayList<T>(acc);
					if (predicate.test(x)) {
						result.add(x);
					}
					return result;
				}, 
				//合并器
				(List<T> left, List<T> right) -> {
					List<T> result = new ArrayList<T>(left);
					result.addAll(right);
					return result;
				}).stream();
	}
	
	@Test
	public void testFilter() {
		Stream<Integer> input = Stream.of(1, 4, 5, 10, 14, 12, 11, 7);
		input = filter(input, i -> i > 10);
		//明白了吗，完全可以用匿名类代替
		/*input = filter(input, new Predicate<Integer>() {
			@Override
			public boolean test(Integer t) {
				return t > 10;
			}
		});*/
		input.forEach(System.out::println);
	}
	
}
