package pl.zyskowski.hybris.model;

public enum OrderBy {
    RATING,
    CATEGORY;

    @Override
    public String toString() {
        switch(this) {
            case RATING:
                return "averageRating";
            case CATEGORY:
                return "category";
            default:
                return this.name();
        }
    }
}
