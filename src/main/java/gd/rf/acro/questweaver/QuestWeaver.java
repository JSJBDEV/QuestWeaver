package gd.rf.acro.questweaver;

import gd.rf.acro.questweaver.items.QuestBookItem;
import gd.rf.acro.questweaver.quests.EntryList;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuestWeaver implements ModInitializer {
	public static final QuestBookItem QUEST_BOOK = new QuestBookItem(new Item.Settings().group(ItemGroup.MISC));
	public static EntryList entryList=null;

	public static final Identifier SEND_BOOK_PROCESS = new Identifier("questweaver","bookprocess");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		System.out.println("Hello Fabric world!");
		Registry.register(Registry.ITEM,new Identifier("questweaver","quest_book"),QUEST_BOOK);
		entryList=null;
		ServerSidePacketRegistry.INSTANCE.register(SEND_BOOK_PROCESS,((packetContext, packetByteBuf) ->
		{
			String[] full = packetByteBuf.readString().split(";;");
			String name = full[0];
			String data = full[1];
			String args = full[2];
			packetContext.getTaskQueue().execute(()->
			{
				String[] split = args.split(",");;
				PlayerEntity player = packetContext.getPlayer();
				System.out.println("packet received!");
				switch (data)
				{
					case "has":
						//args itemID,itemAmount
						if(player.inventory.countInInv(Utils.getItem(split[0]))>=Integer.parseInt(split[1]))
						{
							player.addScoreboardTag(name);
							player.sendMessage(new LiteralText("Quest Complete!"));
						}
						break;
					case "take":
						//args itemID,itemAmount
						if(player.inventory.countInInv(Utils.getItem(split[0]))>=Integer.parseInt(split[1]))
						{
							 Utils.reduceItem(player,Utils.getItem(split[0]),Integer.parseInt(split[0]));
							 player.addScoreboardTag(name);
							 player.sendMessage(new LiteralText("Quest Complete!"));
						}
						break;
					case "score":
						//args scorename, scoreamount
						Scoreboard scoreboard = player.getEntityWorld().getScoreboard();
						if(scoreboard.getPlayerScore(player.getEntityName(),scoreboard.getObjective(split[0])).getScore()>=Integer.parseInt(split[1]))
						{
							player.addScoreboardTag(name);
							player.sendMessage(new LiteralText("Quest Complete!"));
						}
						break;
					case "tag":
						//args tag
						if(player.getScoreboardTags().contains(args))
						{
							player.addScoreboardTag(name);
							player.sendMessage(new LiteralText("Quest Complete!"));
						}
				}
			});
		}));







	}
}
