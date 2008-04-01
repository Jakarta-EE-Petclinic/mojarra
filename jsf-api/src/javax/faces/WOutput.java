package javax.faces;

import java.util.Hashtable;

/**
 * Class for representing a user-interface component which displays
 * output to the user.  This component type is not interactive -
 * a user cannot directly manipulate this component.
 */
public class WOutput extends WComponent {

    private static String TYPE = "Output";
    private Object value = null;

    // JV revisit later.
    private Hashtable ht = null;

    public WOutput() {
        ht = new Hashtable();
    }

    /** 
     * Returns a String representing the this component type.  
     *
     * @return a String object containing &quot;Output&quot;
     *         
     */
    public String getType() {
	return TYPE;
    }

    public Object getValue() {
	return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns the component attribute with the given name
     * within the specified render context or null if there is the
     * specified attribute is not set on this component.
     *
     * @param rc the render context used to render this component
     * @param attributeName a String specifying the name of the attribute
     * @return the Object bound to the attribute name, or null if the
     *          attribute does not exist.
     */
    public Object getAttribute(RenderContext rc, String attributeName) {
        return ht.get(attributeName);
    }

    /**
     * Binds an object to the specified attribute name for this component
     * within the specified render context.
     *
     * @param rc the render context used to render this component
     * @param attributeName a String specifying the name of the attribute
     * @param value an Object representing the value of the attribute
     */
    public void setAttribute(RenderContext rc, String attributeName,
        Object value) {
        if (attributeName != null && value != null) {
            ht.put(attributeName,value);
        }
    }

}
