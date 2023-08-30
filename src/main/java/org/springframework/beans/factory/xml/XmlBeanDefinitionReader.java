package org.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.beans.BeanException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private static final String BEAN_ELEMENT = "bean";
    private static final String PROPERTY_ELEMENT = "property";
    private static final String ID_ATTRIBUTE = "id";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String CLASS_ATTRIBUTE = "class";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String REF_ATTRIBUTE = "ref";


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry, new DefaultResourceLoader());
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        try{
            InputStream inputStream = resource.getInputStream();
            try{
                doLoadBeanDefinitions(inputStream);
            }finally {
                inputStream.close();
            }
        }catch (IOException e){
            throw new BeanException("IOException parsing XML document from" + resource, e);
        }
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for(int i = 0 ; i < childNodes.getLength() ; i++){
            if(childNodes.item(i) instanceof Element){
                if(BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())){
                    // 解析bean标签
                    Element bean = (Element) childNodes.item(i);
                    String id = bean.getAttribute(ID_ATTRIBUTE);
                    String name = bean.getAttribute(NAME_ATTRIBUTE);
                    String className = bean.getAttribute(CLASS_ATTRIBUTE);

                    // 加载class
                    Class<?> clazz = null;
                    try{
                        clazz = Class.forName(className);
                    }catch (ClassNotFoundException e){
                        throw new BeanException("Cannot find class ["+ className + "]",e);
                    }
                    // 命名时候id先于name
                    String beanName = StrUtil.isNotEmpty(id) ? id : name;
                    if(StrUtil.isEmpty(beanName)){
                        // 这时候使用beanClass的名字，首字母小写
                        beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                    }
                    // 创建beanDefinition
                    BeanDefinition beanDefinition = new BeanDefinition(clazz);
                    // 解析property属性
                    for(int j = 0 ;  j < bean.getChildNodes().getLength(); j++){
                        if(bean.getChildNodes().item(j) instanceof Element){
                            if(PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())){
                                Element property = (Element) bean.getChildNodes().item(j);
                                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                                String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                                if(StrUtil.isEmpty(nameAttribute)){
                                    throw new BeanException("The name attribute cannot be null or empty");
                                }

                                Object value = valueAttribute;
                                if(StrUtil.isNotEmpty(refAttribute)){
                                    value = new BeanReference(refAttribute);
                                }
                                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                            }
                        }
                    }
                    if(getRegistry().containsBeanDefinition(beanName)){
                        // beanName不能重名 (或者覆盖，这里规定不能重名)
                        throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
                    }
                    // 注册beanDefinition
                    getRegistry().registryBeanDefinition(beanName, beanDefinition);
                }
            }





        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }
}
