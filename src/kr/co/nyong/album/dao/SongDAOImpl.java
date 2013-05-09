package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.dao.IBatisSupportDAO;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public class SongDAOImpl extends IBatisSupportDAO implements SongDAO{

	@Override
	public void saveSong(List<BaseBean> songList) throws DAOException {
		// TODO Auto-generated method stub
		for(BaseBean song : songList){
			if(song.getStatus().equals("insert")){
				getSqlMapClientTemplate().insert("Song.insertSponsor", song);
			}
			if(song.getStatus().equals("update")){
				getSqlMapClientTemplate().update("Song.updateSponsor", song);
			}
			if(song.getStatus().equals("delete")){
				getSqlMapClientTemplate().delete("Song.deleteSponsor", song);
			}
		}
	}

}
