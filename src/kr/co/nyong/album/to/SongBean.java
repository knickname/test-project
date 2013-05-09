package kr.co.nyong.album.to;

public class SongBean {
	private String songId;
	private String albumId;
	private String songTitle;
	private String runtime;
	private String songGenre;

	public String getSongId() {
		return songId;
	}
	public void setSongId(String songId) {
		this.songId = songId;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getSongTitle() {
		return songTitle;
	}
	public void setSongTitle(String songTitle) {
		this.songTitle = songTitle;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getSongGenre() {
		return songGenre;
	}
	public void setSongGenre(String songGenre) {
		this.songGenre = songGenre;
	}
	
}
