package fun.fifu.nekokecore.zxbskyworld.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveupDispose implements CommandExecutor {
    private static String nanoTime = "!IKnowWhatIDo!" + System.nanoTime();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("giveup".equalsIgnoreCase(command.getName())) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                return true;
            }
            Player player = (Player) commandSender;
            if (strings != null && strings.length > 0) {
                String input = strings[0];
                if (nanoTime.equalsIgnoreCase(input)) {
                    nanoTime = "!IKnowWhatIDo!" + System.nanoTime();
                    player.sendMessage("完成(测试)");
                    return true;
                }
            }
            player.sendMessage("[危]      请三思！     [危]");
            player.sendMessage("警告！你现在的操作十分危险！！");
            player.sendMessage("警告！你在试图清空岛屿！！！！");
            player.sendMessage("警告！你现在的操作十分危险！！");
            player.sendMessage("[危]      请三思！     [危]");
            nanoTime = "!IKnowWhatIDo!" + System.nanoTime();
            player.sendMessage("若要继续，请输入：/giveup " + nanoTime);
            return true;
        }


        return false;
    }
}