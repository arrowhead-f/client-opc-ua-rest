/*
 * Lulea University of Technology
 */
package eu.arrowhead.client.common.model;

/**
 *
 * @author Niklas Karvonen
 */
public class OPCVariableReadout {

    private String type;

    private String value;

    private long sourceTimestamp; // When the source (e.g. sensor) measured the value

    private long providerTimestamp; // When the provider sent the value

    private long statusCode; // The OPC-UA status for this variable reading

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(long sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public long getServerTimestamp() {
        return providerTimestamp;
    }

    public void setServerTimestamp(long serverTimestamp) {
        this.providerTimestamp = serverTimestamp;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

}
