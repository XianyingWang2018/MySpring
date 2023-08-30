package org.springframework.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv){
        propertyValues.add(pv);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        if(propertyName == null){
            return null;
        }
        for(int i = 0 ; i < this.propertyValues.size() ; i++){
            PropertyValue pv = this.propertyValues.get(i);
            if(propertyName.equals(pv.getName())){
                return pv;
            }
        }
        return null;
    }
}
