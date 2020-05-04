package gd.rf.acro.questweaver.quests;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestAssembly {
    public static EntryList assemble()
    {
        List<Quest> quests = new ArrayList<>();
        List<Connector> connectors = new ArrayList<>();
        List<String> catergories = new ArrayList<>();
        try {
            List<String> questStrings = FileUtils.readLines(new File("./config/QuestWeaver/quests.qw"),"utf-8");
            questStrings.forEach(entry->{
                if(entry.charAt(0)=='!')
                {
                    String noex = entry.substring(1);
                    String[] split =noex.split(";;");
                    Connector connector = new Connector(split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]),split[3],split[4]);
                    connectors.add(connector);
                    if(!catergories.contains(split[4]))
                    {
                        catergories.add(split[4]);
                    }

                }
                else if(entry.charAt(0)!='#')
                {
                    String[] split = entry.split(";;");
                    Quest nquest = new Quest(split[0],split[1],split[2],split[3],split[4],split[5],Integer.parseInt(split[6]),Integer.parseInt(split[7]),split[8],split[9]);
                    quests.add(nquest);
                    if(!catergories.contains(split[9]))
                    {
                        catergories.add(split[9]);
                    }
                }
            });

        } catch (IOException e) {
            System.out.println("the file was not found!");
        }
        EntryList list = new EntryList();
        list.setCategories(catergories);
        list.setQuests(quests);
        list.setConnectors(connectors);
        return list;
    }


}
