package kr.co.nyong.album.to;

import kr.co.nyong.common.to.BaseBean;

public class SponsorBean extends BaseBean{
	private String sponsorId;
	private String albumId;
	private String sponsorName;
	private String sponsorTel;
	private String sponsorAmount;
	private String donateDate;
	
	public String getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getSponsorName() {
		return sponsorName;
	}
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}
	public String getSponsorTel() {
		return sponsorTel;
	}
	public void setSponsorTel(String sponsorTel) {
		this.sponsorTel = sponsorTel;
	}
	public String getSponsorAmount() {
		return sponsorAmount;
	}
	public void setSponsorAmount(String sponsorAmount) {
		this.sponsorAmount = sponsorAmount;
	}
	public String getDonateDate() {
		return donateDate;
	}
	public void setDonateDate(String donateDate) {
		this.donateDate = donateDate;
	}
	
}
