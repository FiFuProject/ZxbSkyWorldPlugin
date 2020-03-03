package fun.fifu.nekokecore.zxbskyworld.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author NekokeCore
 */
public class HelpDispose implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if ("help".equalsIgnoreCase(command.getName())) {
            // 判断输入者的类型 为了防止出现 控制台或命令方块 输入的情况
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("你必须是一名玩家!");
                // 这里返回true只是因为该输入者不是玩家,并不是输入错指令,所以我们直接返回true即可
                return true;
            }
            Player player = (Player) commandSender;
            int index;
            try {
                index = Integer.parseInt(strings[0]);
            } catch (Exception e) {
                index = 1;
            }
            switch (index) {
                case 2:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /seclusion 使你所在的岛不能被他人传送");
                    player.sendMessage("|| /homes 显示包括分享给你的岛");
                    player.sendMessage("|| /share <Player> 和玩家分享你所在的岛");
                    player.sendMessage("|| /delete 清空你所在的岛              [危]");
                    player.sendMessage("|| /giveup 放弃你所在的岛              [危]");
                    player.sendMessage("|| /get 领取你所在的岛");
                    player.sendMessage("|| /help 查看帮助                       [2/3]");
                    player.sendMessage("||=================================|");
                    break;
                case 3:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /unshare <Player> 取消和某人分享当前岛");
                    player.sendMessage("|| /unshareall 取消所有分享的岛");
                    player.sendMessage("|| /seclusionpuls 在你的岛的周围修一圈墙");
                    player.sendMessage("|| /unseclusionpuls 拆掉那一圈墙");
                    player.sendMessage("|| /help 查看帮助                       [3/3]");
                    player.sendMessage("||=================================|");
                    break;
                default:
                    player.sendMessage("||=================================|");
                    player.sendMessage("||                      -帮助-");
                    player.sendMessage("||---------------------------------|");
                    player.sendMessage("|| /s 回到自己的岛，若没有，自动领取");
                    player.sendMessage("|| /goto 去一个岛");
                    player.sendMessage("|| /exple <Player> 把玩家从你的岛踢出");
                    player.sendMessage("|| /info 查看你所在的岛屿的信息");
                    player.sendMessage("|| /sethome 设置你所在的岛屿的出生点");
                    player.sendMessage("|| /help 查看帮助                       [1/3]");
                    player.sendMessage("||=================================|");
            }
            return true;
        }

        return false;
    }
}