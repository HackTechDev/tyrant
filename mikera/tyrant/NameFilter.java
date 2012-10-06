package mikera.tyrant;


public class NameFilter implements IThingFilter {
    public boolean accept(Thing thing, String query) {
        if(query == null) return true;
        query = query.trim();
        String name = Describer.describe(null, thing, Description.ARTICLE_NONE);
        return name.indexOf(query) >= 0;
    }
}
