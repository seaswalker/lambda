package chapter4;

import java.util.List;
import java.util.Optional;

import roles.Artist;

/**
 * P49 Á·Ï°3
 * @author skywalker
 *
 */
public class Artists {

	private List<Artist> artists;

	public Artists(List<Artist> artists) {
		this.artists = artists;
	}
	
	public Optional<Artist> getArtist(int index) {
		if (index  < 0 || index > artists.size()) {
			return Optional.empty();
		}
		return Optional.of(artists.get(index));
	}
	
	public String getArtistName(int index) {
		Optional<Artist> artist = getArtist(index);
		return artist.isPresent() ? artist.get().getHome() : "unkown";
	}
	
}
