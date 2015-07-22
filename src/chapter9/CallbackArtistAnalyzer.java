package chapter9;

import java.util.function.Consumer;
import java.util.function.Function;

import roles.Artist;

/**
 * 使用回调函数重构阻塞代码
 * @author skywalker
 *
 */
public class CallbackArtistAnalyzer implements ArtistAnalyzer {
	
	private final Function<String, Artist> artistLookUpService;
	
	public CallbackArtistAnalyzer(Function<String, Artist> artistLookUpService) {
		this.artistLookUpService = artistLookUpService;
	}

	@Override
	public void isLargerGroup(String artistName, String otherArtistName,
			Consumer<Boolean> handler) {
		new Thread(() -> {
			//调用回调函数
			handler.accept(getNumberOfMembers(artistName) > getNumberOfMembers(otherArtistName));
		}).start();
	}
	
	//此处使用艺术家的年龄代替
	private int getNumberOfMembers(String name) {
		return artistLookUpService.apply(name).getAge();
	}

}
