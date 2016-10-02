package pl.zyskowski.hybris.movielibrary.utils;

public enum OrderBy {
    RATING,
    CATEGORY;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
