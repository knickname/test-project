package kr.co.nyong.artist.to;

import java.util.List;

import kr.co.nyong.common.to.BaseBean;

public class AlbumBean extends BaseBean{
	private String albumId;
	private String artistId;
	private String albumType;
	private String albumTitle;
	private String launchingDate;
	private String producer;
	private String distributor;
	private List<BaseBean> songList;
	private List<BaseBean> atistList;
	private List<BaseBean> sponsorList;
	private List<BaseBean> imageList;
	
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
	public String getAlbumType() {
		return albumType;
	}
	public void setAlbumType(String albumType) {
		this.albumType = albumType;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getLaunchingDate() {
		return launchingDate;
	}
	public void setLaunchingDate(String launchingDate) {
		this.launchingDate = launchingDate;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getDistributor() {
		return distributor;
	}
	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	public List<BaseBean> getSongList() {
		return songList;
	}
	public void setSongList(List<BaseBean> songList) {
		this.songList = songList;
	}
	public List<BaseBean> getAtistList() {
		return atistList;
	}
	public void setAtistList(List<BaseBean> atistList) {
		this.atistList = atistList;
	}
	public List<BaseBean> getSponsorList() {
		return sponsorList;
	}
	public void setSponsorList(List<BaseBean> sponsorList) {
		this.sponsorList = sponsorList;
	}
	public List<BaseBean> getImageList() {
		return imageList;
	}
	public void setImageList(List<BaseBean> imageList) {
		this.imageList = imageList;
	}
	
}
