package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.dao.IBatisSupportDAO;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public class AlbumArtistDAOImpl extends IBatisSupportDAO implements AlbumArtistDAO{

	@Override
	public void saveAlbumArtist(List<BaseBean> albumArtistList)
			throws DAOException {
		// TODO Auto-generated method stub
		for(BaseBean albumArtist : albumArtistList){
			if(albumArtist.getStatus().equals("insert")){
				getSqlMapClientTemplate().insert("AlbumArtist.insertSponsor", albumArtist);
			}
			if(albumArtist.getStatus().equals("update")){
				getSqlMapClientTemplate().update("AlbumArtist.updateSponsor", albumArtist);
			}
			if(albumArtist.getStatus().equals("delete")){
				getSqlMapClientTemplate().delete("AlbumArtist.deleteSponsor", albumArtist);
			}
		}
	}

}
