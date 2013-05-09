package kr.co.nyong.common.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.nyong.common.mapper.DatasetBeanMapper;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.tobesoft.platform.PlatformRequest;
import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.PlatformData;
import com.tobesoft.platform.data.VariableList;

public abstract class AbstractMiPlatformController implements Controller{
	
protected DatasetBeanMapper datasetBeanMapper;
	
	public void setDatasetBeanMapper(DatasetBeanMapper datasetBeanMapper){
		this.datasetBeanMapper=datasetBeanMapper;
	}
	public abstract void doExecute(PlatformData inData, PlatformData outData) throws Exception;
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PlatformRequest pRequest=new PlatformRequest(request,"euc-kr");
		pRequest.receiveData();
		PlatformData inData=pRequest.getPlatformData();
		PlatformData outData=new PlatformData(new VariableList(),new DatasetList());

		try{

			doExecute(inData,outData);

			outData.getVariableList().addStr("ErrorCode", "0");
			outData.getVariableList().addStr("ErrorMsg","success");

		}catch(Exception e){
			e.printStackTrace();
			outData.getVariableList().addStr("ErrorCode","-1");
			outData.getVariableList().addStr("ErrorMsg",e.getMessage());
		}

		PlatformResponse pResponse=new PlatformResponse(response,PlatformRequest.XML,"euc-kr");
		pResponse.sendData(outData);
		outData=null;
		return null;
	}
}

