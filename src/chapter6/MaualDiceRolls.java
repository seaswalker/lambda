package chapter6;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


/**
 * P73 6-4 手动使用线程执行蒙特卡洛模拟
 * @author skywalker
 *
 */
public class MaualDiceRolls {

	private final ConcurrentMap<Integer, Double> result;
	private final double fraction;
	//使用的线程数
	private final int threads;
	//每一个线程执行多少次计算
	private final int workPerThread;
	private final ExecutorService executorService;
	
	public MaualDiceRolls(int n) {
		this.result = new ConcurrentHashMap<Integer, Double>();
		this.fraction = 1.0 / n;
		this.threads = Runtime.getRuntime().availableProcessors();
		this.workPerThread = n / this.threads;
		this.executorService = Executors.newFixedThreadPool(threads);
	}
	
	public void start() {
		for (int i = 0;i < threads;i ++) {
			executorService.submit(() -> {
				ThreadLocalRandom random = ThreadLocalRandom.current();
				for (int j = 0;j < workPerThread;j ++) {
					result.compute(twoDiceThrows(random), (key, previous) -> {
						return previous == null ? fraction : previous + fraction;
					});
				}
			});
		}
	}
	
	/**
	 * 执行一次掷骰子模拟
	 * 两个参数的nextInt()方法只有ThradLocalRandom才有
	 */
	public int twoDiceThrows(ThreadLocalRandom random) {
		int first = random.nextInt(1, 7);
		int second = random.nextInt(1, 7);
		return first + second;
	}
	
	public void await() throws InterruptedException {
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		MaualDiceRolls maualDiceRolls = new MaualDiceRolls(1000);
		maualDiceRolls.start();
		try {
			maualDiceRolls.await();
			maualDiceRolls.result.forEach((key, value) -> {
				System.out.println("key:" + key + " value:" + value);
			});
		} catch (InterruptedException e) {
			System.out.println("发生异常");
		}
	}
	
}
