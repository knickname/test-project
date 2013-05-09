package kr.co.nyong.album.to;

public class AlbumArtistBean {
	private String number;
	private String albumId;
	private String artistId;
	private String artistName;
	private String artistAgent;
	
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistAgent() {
		return artistAgent;
	}
	public void setArtistAgent(String artistAgent) {
		this.artistAgent = artistAgent;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getArtistId() {
		return artistId;
	}
	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}
	
}
