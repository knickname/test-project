package kr.co.nyong.album.service;

import java.util.List;

import kr.co.nyong.album.to.AlbumArtistBean;
import kr.co.nyong.artist.to.AlbumBean;
import kr.co.nyong.common.to.BaseBean;
import kr.co.nyong.common.to.ConditionBean;

public interface AlbumService {
	
	public List<BaseBean> selectAlbumList(ConditionBean condition);
	public void saveAlbumInfo(AlbumBean albumBean);
	public void saveAlbumArtist(AlbumArtistBean albumArtistBean);
}
