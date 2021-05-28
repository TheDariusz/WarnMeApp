package com.thedariusz.warnme;

public class MeteoAlertOrigin {

    private String sourceName;
    private String sourceDesc;
    private String originalId;

    public MeteoAlertOrigin(String sourceName, String sourceDesc, String originalId) {
        this.sourceName = sourceName;
        this.sourceDesc = sourceDesc;
        this.originalId = originalId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    @Override
    public String toString() {
        return "AlertOrigin{" +
                "source='" + sourceName + '\'' +
                ", description='" + sourceDesc + '\'' +
                ", originalId='" + originalId + '\'' +
                '}';
    }
}
