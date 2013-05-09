package kr.co.nyong.common.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kr.co.nyong.common.to.BaseBean;

import org.springframework.core.io.ClassPathResource;

import com.tobesoft.platform.PlatformResponse;
import com.tobesoft.platform.data.Dataset;
import com.tobesoft.platform.data.DatasetList;
import com.tobesoft.platform.data.Variant;

@SuppressWarnings("rawtypes")
public class DatasetBeanMapper {

    // Dataset => Bean 변환시 사용할 Map들을 저장하고 있는 Table
    private Map<String, Map<String, String>> beansPropertyNameTable = new HashMap<String, Map<String, String>>();
    // Bean => Dataset 변환시 사용할 Map들을 저장하고 있는 Table
    private Map<String, Map<String, String>> datasetColumnNameTable = new HashMap<String, Map<String, String>>();

    public DatasetBeanMapper(Properties pathMap) throws IOException {

        Map<String, String> beansPropertyNameMap = null; // HashMap<'dataset 컬럼명', 'bean 멤버변수명'>
        Map<String, String> datasetColumnNameMap = null; // HashMap<'bean 멤버변수명', 'dataset 컬럼명'>

        Properties mappingProperties = new Properties();
        for (String className : pathMap.stringPropertyNames()) {
            // 'Bean 멤버변수명' = 'Dataset 컬럼명' 매핑 정보를 가지고 있는 properties 로딩
            mappingProperties.load(new ClassPathResource(pathMap.getProperty(className)).getInputStream());

            beansPropertyNameMap = new HashMap<String, String>(); // HashMap<'dataset 컬럼명', 'bean 멤버변수명'>
            datasetColumnNameMap = new HashMap<String, String>(); // HashMap<'bean 멤버변수명', 'dataset 컬럼명'>
            for (String beanPropertyName : mappingProperties.stringPropertyNames()) {
                beansPropertyNameMap.put(mappingProperties.getProperty(beanPropertyName), beanPropertyName);
                datasetColumnNameMap.put(beanPropertyName, mappingProperties.getProperty(beanPropertyName));
            }

            beansPropertyNameTable.put(className, beansPropertyNameMap);
            datasetColumnNameTable.put(className, datasetColumnNameMap);

            mappingProperties.clear();
        }
    }

    /**
     * dataset => beanList
     * @param dataset
     * @param klass Java Beans Class
     * @return ArrayList<BaseBean>
     * @throws Exception
     */
    public List<BaseBean> datasetToBeans(Dataset dataset, Class klass) throws Exception {

        // 반복문 내에서 사용될 변수 선언 및 초기화
        BaseBean bean = null;
        Variant columnValue = null;
        List<BaseBean> beanList = new ArrayList<BaseBean>();

        // Bean 클래스명을 키값으로 입력하여 Bean 멤버변수명을 값으로 갖는 HashMap<'dataset 컬럼명', 'bean 멤버변수명'>을 호출
        Map<String, String> beansPropertyNameMap = beansPropertyNameTable.get(klass.getName());

        // 위 HashMap에서 KeySet인 dataset 컬럼명을 String 배열로 추출
        Object[] columnNameArray = beansPropertyNameMap.keySet().toArray();

        // dataset의 레코드 수만큼 bean을 생성
        int rowCount = dataset.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            // Bean 객체를 생성하고 insert 또는 update 상태값을 저장
            bean = (BaseBean) klass.newInstance();
            bean.setStatus(dataset.getRowStatus(rowIndex));

            // dataset 컬럼명에 해당하는 Bean setter 메소드를 모두 호출하여 값을 저장
            for (Object columnName : columnNameArray) {
                // dataset 컬럼값을 호출
                columnValue = dataset.getColumn(rowIndex, (String) columnName);

                if (columnValue != null) {
                    // setter 메소드를 호출하여 값 저장
                    callSetterMethod(bean, beansPropertyNameMap.get(columnName), columnValue);
                }
            }

            beanList.add(bean);
        }

        // status가 delete인 레코드를 bean으로 변환. deleteColumn을 얻어온다는 것만 다름
        rowCount = dataset.getDeleteRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            bean = (BaseBean) klass.newInstance();
            bean.setStatus("delete");

            for (Object columnName : columnNameArray) {
                columnValue = dataset.getDeleteColumn(rowIndex, (String) columnName);

                if (columnValue != null) {
                    callSetterMethod(bean, beansPropertyNameMap.get(columnName), columnValue);
                }
            }

            beanList.add(bean);
        }

        return beanList;
    }

    /**
     * dataset => bean
     * @param dataset
     * @param klass Java Beans Class
     * @return BaseBean
     * @throws Exception
     */
    public BaseBean datasetToBean(Dataset dataset, Class klass) throws Exception {

        Map<String, String> beansPropertyNameMap = beansPropertyNameTable.get(klass.getName());
        Object[] columnNameArray = beansPropertyNameMap.keySet().toArray();

        BaseBean bean = (BaseBean) klass.newInstance();
        if (dataset.getDeleteRowCount() == 1) {
            bean.setStatus("delete");

        } else {
            bean.setStatus(dataset.getRowStatus(0));

        }

        Variant columnValue = null;
        for (Object columnName : columnNameArray) {
            if (dataset.getDeleteRowCount() == 1) {
                columnValue = dataset.getDeleteColumn(0, (String) columnName);

            } else {
                columnValue = dataset.getColumn(0, (String) columnName);

            }

            if (columnValue != null) {
                callSetterMethod(bean, beansPropertyNameMap.get(columnName), columnValue);
            }
        }

        return bean;
    }

    /**
     * beanList => dataset
     * @param datasetList
     * @param beanList
     * @param datasetName
     * @throws Exception
     */
    public void beansToDataset(DatasetList datasetList, String datasetName, List<BaseBean> beanList, Class klass) throws Exception {

        Dataset dataset = new Dataset(datasetName, PlatformResponse.CHARSET_EUC_KR, false, false);
        datasetList.addDataset(dataset);

        Map<String, String> datasetColumnNameMap = datasetColumnNameTable.get(klass.getName());
        Object[] beanPropertyNameArray = datasetColumnNameMap.keySet().toArray();

        // dataset column header 생성
        for (Object beanPropertyName : beanPropertyNameArray) {
            dataset.addStringColumn(datasetColumnNameMap.get(beanPropertyName));
        }

        if (beanList.isEmpty()) return;

        int rowIndex = -1;
        Variant value = null;
        int beanListSize = beanList.size();
        for (int i = 0; i < beanListSize; i++) {
            // dataset에 row 추가
            rowIndex = dataset.appendRow();

            // 각 컬럼에 값을 저장
            for (Object beanPropertyName : beanPropertyNameArray) {
                // 타입에 상관없이 저장하기 위해 Variant를 이용
                value = new Variant(callGetterMethod(beanList.get(i), (String) beanPropertyName));
                dataset.setColumn(rowIndex, datasetColumnNameMap.get(beanPropertyName), value);
            }
        }

    }

    /**
     * bean => dataset
     * @param datasetList
     * @param bean
     * @param datasetName
     * @throws Exception
     */
    public void beanToDataset(DatasetList datasetList, String datasetName, BaseBean bean) throws Exception {

        Dataset dataset = new Dataset(datasetName, PlatformResponse.CHARSET_EUC_KR, false, false);
        datasetList.addDataset(dataset);

        Map<String, String> datasetColumnNameMap = datasetColumnNameTable.get(bean.getClass().getName());
        Object[] beanPropertyNameArray = datasetColumnNameMap.keySet().toArray();

        for (Object beanPropertyName : beanPropertyNameArray) {
            dataset.addStringColumn(datasetColumnNameMap.get(beanPropertyName));
        }

        Variant value = null;
        int rowIndex = dataset.appendRow();
        for (Object beanPropertyName : beanPropertyNameArray) {
            value = new Variant(callGetterMethod(bean, (String) beanPropertyName));
            dataset.setColumn(rowIndex, datasetColumnNameMap.get(beanPropertyName), value);
        }
    }

    /**
     * setter 메소드를 생성하여 호출
     * @param bean
     * @param beansPropertyName
     * @param columnValue
     * @throws Exception
     */
    private void callSetterMethod(BaseBean bean, String beansPropertyName, Variant columnValue) throws Exception {

        // Class.getMethod('메소드명', '메소드에 넘겨줄 파라미터 타입들').invoke('실제 메소드를 호출할 인스턴스', '메소드에 넘겨줄 파라미터 값들');
        bean.getClass().getMethod(getMethodName("set", beansPropertyName), String.class).invoke(bean, columnValue.getString());
    }

    /**
     * getter 메소드를 생성하여 호출하고 값을 리턴
     * @param bean
     * @param beansPropertyName
     * @return
     * @throws Exception
     */
    private Object callGetterMethod(BaseBean bean, String beansPropertyName) throws Exception {

//        bean.getClass().getDeclaredMethods()[0].getReturnType()
        return bean.getClass().getMethod(getMethodName("get", beansPropertyName), new Class[0]).invoke(bean, new Object[0]);
    }

    private String getMethodName(String prefix, String beanPropertyName) {

        // 메소드명 준비
        StringBuffer methodName = new StringBuffer(beanPropertyName);

        // 첫문자를 대문자로 바꿈
        methodName.replace(0, 1, methodName.substring(0, 1).toUpperCase());

        // prefix를 앞에 붙여 메소드명을 완성하여 리턴
        return methodName.insert(0, prefix).toString();
    }

}