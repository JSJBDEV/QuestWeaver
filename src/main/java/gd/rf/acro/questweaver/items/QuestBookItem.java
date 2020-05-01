package gd.rf.acro.questweaver.items;

import gd.rf.acro.questweaver.QuestWeaver;
import gd.rf.acro.questweaver.quests.QuestAssembly;
import gd.rf.acro.questweaver.screens.QuestScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class QuestBookItem extends Item {
    private Set<String> playersTags;
    public QuestBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(hand==Hand.MAIN_HAND && world.isClient && QuestWeaver.entryList==null)
        {
            QuestWeaver.entryList= QuestAssembly.assemble();
        }


        if(hand==Hand.MAIN_HAND && world.isClient)
        {

            MinecraftClient.getInstance().openScreen(new QuestScreen(new LiteralText("screen"),playersTags,"intro"));
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(!world.isClient)
        {
            this.playersTags=entity.getScoreboardTags();
            if(!world.getScoreboard().getTeamNames().contains("qw_setup"))
            {
                doSetup(world);
            }
        }
    }

    private void doSetup(World world)
    {
        try {
            if(!world.isClient()){
                List<String> scoreboards = FileUtils.readLines(new File("./saves/setup.qw"),"utf-8");
                scoreboards.forEach(command->
                {
                    world.getServer().getCommandManager().execute(world.getServer().getCommandSource(),command);
                });
            }

        }
        catch (IOException e){ System.out.println("the file was not found!");}
        world.getScoreboard().addTeam("qw_setup");
        System.out.println("world setup done");
    }
}
