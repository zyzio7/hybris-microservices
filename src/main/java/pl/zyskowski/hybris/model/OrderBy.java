package pl.zyskowski.hybris.model;

public enum OrderBy {
    RATING,
    CATEGORY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
