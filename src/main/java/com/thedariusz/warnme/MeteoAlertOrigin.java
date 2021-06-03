package com.thedariusz.warnme;

public class MeteoAlertOrigin {

    private final String sourceName;
    private final String sourceAuthorId;
    private final String originalId;

    private MeteoAlertOrigin(String sourceName, String sourceAuthorId, String originalId) {
        this.sourceName = sourceName;
        this.sourceAuthorId = sourceAuthorId;
        this.originalId = originalId;
    }

    public static MeteoAlertOrigin twitter(String sourceAuthorId, String originalId) {
        return new MeteoAlertOrigin("Twitter", sourceAuthorId, originalId);
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getOriginalId() {
        return originalId;
    }

    public String getSourceAuthorId() {
        return sourceAuthorId;
    }

    @Override
    public String toString() {
        return "MeteoAlertOrigin{" +
                "sourceName='" + sourceName + '\'' +
                ", sourceAuthorId='" + sourceAuthorId + '\'' +
                ", originalId='" + originalId + '\'' +
                '}';
    }
}
