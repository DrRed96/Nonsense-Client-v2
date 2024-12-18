package wtf.bhopper.nonsense.module.property;

import java.util.List;

public interface PropertyContainer {

    void addProperties(Property<?>... properties);

    List<Property<?>> getProperties();

    String getContainerId();

    default PropertyContainer getOwner() {
        return null;
    }

}
