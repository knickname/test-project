 package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.dao.IBatisSupportDAO;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public class SponsorDAOImpl extends IBatisSupportDAO implements SponsorDAO{

	@Override
	public void saveSponsor(List<BaseBean> sponsorList) throws DAOException {
		// TODO Auto-generated method stub
		for(BaseBean sponsor : sponsorList){
			if(sponsor.getStatus().equals("insert")){
				getSqlMapClientTemplate().insert("Sponsor.insertSponsor", sponsor);
			}
			if(sponsor.getStatus().equals("update")){
				getSqlMapClientTemplate().update("Sponsor.updateSponsor", sponsor);
			}
			if(sponsor.getStatus().equals("delete")){
				getSqlMapClientTemplate().delete("Sponsor.deleteSponsor", sponsor);
			}
		}
	}

}
