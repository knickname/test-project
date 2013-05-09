package kr.co.nyong.common.db;

import java.io.FileOutputStream;
import java.util.Properties;

import kr.co.nyong.common.exception.DAOException;

import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.PlatformData;

public class AlbumImageUpload {

	private Properties albumImageFileLocation;
	public void setAlbumImageFileLocation(Properties albumImageFileLocation) {
		this.albumImageFileLocation = albumImageFileLocation;
	}
	
	public void imgFileUpload(PlatformData inData){
		
		try{
			Dataset imgDs=inData.getDataset("ds_image");
			
			FileOutputStream fos=null;
			
				String workSrc = albumImageFileLocation.getProperty("workSrc");
				String tomSrc = albumImageFileLocation.getProperty("tomSrc");
				
				int imgDsRow=imgDs.getRowCount();
				String filename=null;
				for(int i=0; i<imgDsRow; i++){
					
					filename=imgDs.getColumn(i, "IMGFILENAME").asString();
					
					if(imgDs.getColumn(i, "IMGFILE").getBinary()!=null){
						fos = new FileOutputStream(workSrc+filename);
						fos.write(imgDs.getColumn(i, "IMGFILE").getBinary());
						fos.flush();
						
						fos = new FileOutputStream(tomSrc+filename);
						fos.write(imgDs.getColumn(i,"IMGFILE").getBinary());
						fos.flush();
						
						fos.close();
						fos = null;
					}
				}
		}catch (Exception e){
			throw new DAOException("ImageFile Save Error : "+e.getMessage());
		}
	}
}
