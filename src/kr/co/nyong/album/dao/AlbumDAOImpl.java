package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.artist.to.AlbumBean;
import kr.co.nyong.common.dao.IBatisSupportDAO;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;
import kr.co.nyong.common.to.ConditionBean;

public class AlbumDAOImpl extends IBatisSupportDAO implements AlbumDAO{

	@Override
	public List<BaseBean> selectAlbumList(ConditionBean condition) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("Album.selectAlbumList", condition);
	}

	public void saveAlbumInfo(AlbumBean albumBean) {
		// TODO Auto-generated method stub
		if(albumBean.getStatus().equals("insert")){
			getSqlMapClientTemplate().insert("Album.insertAlbum", albumBean);
		}
		if(albumBean.getStatus().equals("update")){
			getSqlMapClientTemplate().update("Album.updateAlbum", albumBean);
		}
		if(albumBean.getStatus().equals("delete")){
			getSqlMapClientTemplate().delete("Album.deleteAlbum", albumBean);
		}
	}

}
