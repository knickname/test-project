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

    // Dataset => Bean ��ȯ�� ����� Map���� �����ϰ� �ִ� Table
    private Map<String, Map<String, String>> beansPropertyNameTable = new HashMap<String, Map<String, String>>();
    // Bean => Dataset ��ȯ�� ����� Map���� �����ϰ� �ִ� Table
    private Map<String, Map<String, String>> datasetColumnNameTable = new HashMap<String, Map<String, String>>();

    public DatasetBeanMapper(Properties pathMap) throws IOException {

        Map<String, String> beansPropertyNameMap = null; // HashMap<'dataset �÷���', 'bean ���������'>
        Map<String, String> datasetColumnNameMap = null; // HashMap<'bean ���������', 'dataset �÷���'>

        Properties mappingProperties = new Properties();
        for (String className : pathMap.stringPropertyNames()) {
            // 'Bean ���������' = 'Dataset �÷���' ���� ������ ������ �ִ� properties �ε�
            mappingProperties.load(new ClassPathResource(pathMap.getProperty(className)).getInputStream());

            beansPropertyNameMap = new HashMap<String, String>(); // HashMap<'dataset �÷���', 'bean ���������'>
            datasetColumnNameMap = new HashMap<String, String>(); // HashMap<'bean ���������', 'dataset �÷���'>
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

        // �ݺ��� ������ ���� ���� ���� �� �ʱ�ȭ
        BaseBean bean = null;
        Variant columnValue = null;
        List<BaseBean> beanList = new ArrayList<BaseBean>();

        // Bean Ŭ�������� Ű������ �Է��Ͽ� Bean ����������� ������ ���� HashMap<'dataset �÷���', 'bean ���������'>�� ȣ��
        Map<String, String> beansPropertyNameMap = beansPropertyNameTable.get(klass.getName());

        // �� HashMap���� KeySet�� dataset �÷����� String �迭�� ����
        Object[] columnNameArray = beansPropertyNameMap.keySet().toArray();

        // dataset�� ���ڵ� ����ŭ bean�� ����
        int rowCount = dataset.getRowCount();
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            // Bean ��ü�� �����ϰ� insert �Ǵ� update ���°��� ����
            bean = (BaseBean) klass.newInstance();
            bean.setStatus(dataset.getRowStatus(rowIndex));

            // dataset �÷��� �ش��ϴ� Bean setter �޼ҵ带 ��� ȣ���Ͽ� ���� ����
            for (Object columnName : columnNameArray) {
                // dataset �÷����� ȣ��
                columnValue = dataset.getColumn(rowIndex, (String) columnName);

                if (columnValue != null) {
                    // setter �޼ҵ带 ȣ���Ͽ� �� ����
                    callSetterMethod(bean, beansPropertyNameMap.get(columnName), columnValue);
                }
            }

            beanList.add(bean);
        }

        // status�� delete�� ���ڵ带 bean���� ��ȯ. deleteColumn�� ���´ٴ� �͸� �ٸ�
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

        // dataset column header ����
        for (Object beanPropertyName : beanPropertyNameArray) {
            dataset.addStringColumn(datasetColumnNameMap.get(beanPropertyName));
        }

        if (beanList.isEmpty()) return;

        int rowIndex = -1;
        Variant value = null;
        int beanListSize = beanList.size();
        for (int i = 0; i < beanListSize; i++) {
            // dataset�� row �߰�
            rowIndex = dataset.appendRow();

            // �� �÷��� ���� ����
            for (Object beanPropertyName : beanPropertyNameArray) {
                // Ÿ�Կ� ������� �����ϱ� ���� Variant�� �̿�
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
     * setter �޼ҵ带 �����Ͽ� ȣ��
     * @param bean
     * @param beansPropertyName
     * @param columnValue
     * @throws Exception
     */
    private void callSetterMethod(BaseBean bean, String beansPropertyName, Variant columnValue) throws Exception {

        // Class.getMethod('�޼ҵ��', '�޼ҵ忡 �Ѱ��� �Ķ���� Ÿ�Ե�').invoke('���� �޼ҵ带 ȣ���� �ν��Ͻ�', '�޼ҵ忡 �Ѱ��� �Ķ���� ����');
        bean.getClass().getMethod(getMethodName("set", beansPropertyName), String.class).invoke(bean, columnValue.getString());
    }

    /**
     * getter �޼ҵ带 �����Ͽ� ȣ���ϰ� ���� ����
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

        // �޼ҵ�� �غ�
        StringBuffer methodName = new StringBuffer(beanPropertyName);

        // ù���ڸ� �빮�ڷ� �ٲ�
        methodName.replace(0, 1, methodName.substring(0, 1).toUpperCase());

        // prefix�� �տ� �ٿ� �޼ҵ���� �ϼ��Ͽ� ����
        return methodName.insert(0, prefix).toString();
    }

}