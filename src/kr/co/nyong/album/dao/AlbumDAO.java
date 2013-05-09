package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.artist.to.AlbumBean;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;
import kr.co.nyong.common.to.ConditionBean;

public interface AlbumDAO {
	public List<BaseBean> selectAlbumList(ConditionBean condition);

	public void saveAlbumInfo(AlbumBean albumBean);
}
