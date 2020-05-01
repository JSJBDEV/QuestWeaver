package gd.rf.acro.questweaver.quests;

import java.util.List;

public class EntryList
{
    private List<Quest> quests;
    private List<Connector> connectors;
    private List<String> categories;

    public List<Quest> getQuests() {
        return quests;
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public void setConnectors(List<Connector> connectors) {
        this.connectors = connectors;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }
}
