package kr.co.nyong.artist.dao;

import java.util.List;

import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public interface ArtistAlbumDAO {
	public void saveArtistAlbum(List<BaseBean> artistAlbumList) throws DAOException;
}
