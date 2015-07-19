package roles;

/**
 * “’ ıº“
 * @author skywalker
 *
 */
public class Artist {

	private String home;
	private int age;
	
	public Artist(String home, int age) {
		this.home = home;
		this.age = age;
	}
	
	public boolean isFrom(String home) {
		return this.home.equals(home);
	}

	public String getHome() {
		return home;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Artist [home=" + home + ", age=" + age + "]";
	}
	
}
