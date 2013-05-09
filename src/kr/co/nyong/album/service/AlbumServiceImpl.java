package kr.co.nyong.album.service;

import java.util.List;

import kr.co.nyong.album.dao.AlbumArtistDAO;
import kr.co.nyong.album.dao.AlbumDAO;
import kr.co.nyong.album.dao.ImageDAO;
import kr.co.nyong.album.dao.SongDAO;
import kr.co.nyong.album.dao.SponsorDAO;
import kr.co.nyong.album.to.AlbumArtistBean;
import kr.co.nyong.artist.to.AlbumBean;
import kr.co.nyong.common.to.BaseBean;
import kr.co.nyong.common.to.ConditionBean;

public class AlbumServiceImpl implements AlbumService{

	private AlbumArtistDAO subAlbumArtistDAO;
	private AlbumDAO subAlbumDAO;
	private ImageDAO subImageDAO;
	private SongDAO subSongDAO;
	private SponsorDAO subSponsorDAO;
	
	public AlbumArtistDAO getSubAlbumArtistDAO() {
		return subAlbumArtistDAO;
	}

	public void setSubAlbumArtistDAO(AlbumArtistDAO subAlbumArtistDAO) {
		this.subAlbumArtistDAO = subAlbumArtistDAO;
	}

	public AlbumDAO getSubAlbumDAO() {
		return subAlbumDAO;
	}

	public void setSubAlbumDAO(AlbumDAO subAlbumDAO) {
		this.subAlbumDAO = subAlbumDAO;
	}

	public ImageDAO getSubImageDAO() {
		return subImageDAO;
	}

	public void setSubImageDAO(ImageDAO subImageDAO) {
		this.subImageDAO = subImageDAO;
	}

	public SongDAO getSubSongDAO() {
		return subSongDAO;
	}

	public void setSubSongDAO(SongDAO subSongDAO) {
		this.subSongDAO = subSongDAO;
	}

	public SponsorDAO getSubSponsorDAO() {
		return subSponsorDAO;
	}

	public void setSubSponsorDAO(SponsorDAO subSponsorDAO) {
		this.subSponsorDAO = subSponsorDAO;
	}
	
	@Override
	public List<BaseBean> selectAlbumList(ConditionBean condition) {
		// TODO Auto-generated method stub
		return subAlbumDAO.selectAlbumList(condition);
	}

	@Override
	public void saveAlbumInfo(AlbumBean albumBean) {
		// TODO Auto-generated method stub
		subAlbumDAO.saveAlbumInfo(albumBean);
	}

	@Override
	public void saveAlbumArtist(AlbumArtistBean albumArtistBean) {
		// TODO Auto-generated method stub
		
	}

}
