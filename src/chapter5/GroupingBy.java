package chapter5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * P68 2-c 自己实现Collectors.groupingBy()
 * 可参考62页来实现
 * @author skywalker
 *
 * @param <T>
 * @param <K>
 */
public class GroupingBy<T, K> implements Collector<T, Map<K, List<T>>, Map<K, List<T>>> {
	
	//用来计算分组的依据
	//注意泛型的写法，第一个是参数，第二个是返回值，很有意思
	private final Function<? super T, ? extends K> function;
	
	public GroupingBy(Function<? super T, ? extends K> function) {
		this.function = function;
	}
	
	/**
	 * 这个是创建结果容器的工厂方法，相当于reduce的第一个参数
	 */
	@Override
	public Supplier<Map<K, List<T>>> supplier() {
		return () -> new HashMap<K, List<T>>();
	}

	/**
	 * 相当于reduce的第二个参数
	 */
	@Override
	public BiConsumer<Map<K, List<T>>, T> accumulator() {
		return (map, element) -> {
			K key = function.apply(element);
			List<T> list = map.computeIfAbsent(key, k -> new ArrayList<T>());
			list.add(element);
		};
	}

	/**
	 * 相当于reduce的第三个参数
	 */
	@Override
	public BinaryOperator<Map<K, List<T>>> combiner() {
		return ((m1, m2) -> {
			m1.putAll(m2);
			return m1;
		});
	}

	/**
	 * 此处直接返回map即可，无须转换
	 */
	@Override
	public Function<Map<K, List<T>>, Map<K, List<T>>> finisher() {
		return map -> map;
	}

	/**
	 * 暂未实现
	 */
	@Override
	public Set<java.util.stream.Collector.Characteristics> characteristics() {
		return null;
	}

}
