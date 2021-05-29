package com.thedariusz.warnme;

public class MeteoAlertOrigin {

    private String sourceName;
    private String sourceAuthorId;
    private String originalId;

    public MeteoAlertOrigin(String sourceName, String sourceAuthorId, String originalId) {
        this.sourceName = sourceName;
        this.sourceAuthorId = sourceAuthorId;
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

    public String getSourceAuthorId() {
        return sourceAuthorId;
    }

    public void setSourceAuthorId(String sourceAuthorId) {
        this.sourceAuthorId = sourceAuthorId;
    }

    @Override
    public String toString() {
        return "AlertOrigin{" +
                "source='" + sourceName + '\'' +
                ", description='" + sourceAuthorId + '\'' +
                ", originalId='" + originalId + '\'' +
                '}';
    }
}
