package kr.co.nyong.album.dao;

import java.util.List;

import kr.co.nyong.common.dao.IBatisSupportDAO;
import kr.co.nyong.common.exception.DAOException;
import kr.co.nyong.common.to.BaseBean;

public class ImageDAOImpl extends IBatisSupportDAO implements ImageDAO{

	@Override
	public void saveImage(List<BaseBean> imageList) throws DAOException {
		// TODO Auto-generated method stub
		for(BaseBean image : imageList){
			if(image.getStatus().equals("insert")){
				getSqlMapClientTemplate().insert("Image.insertSponsor", image);
			}
			if(image.getStatus().equals("update")){
				getSqlMapClientTemplate().update("Image.updateSponsor", image);
			}
			if(image.getStatus().equals("delete")){
				getSqlMapClientTemplate().delete("Image.deleteSponsor", image);
			}
		}
	}

}
