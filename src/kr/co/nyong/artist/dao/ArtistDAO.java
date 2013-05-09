package kr.co.nyong.artist.dao;

import java.util.List;

import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public interface ArtistDAO {
	public void saveArtist(List<BaseBean> artistList) throws DAOException;
}
