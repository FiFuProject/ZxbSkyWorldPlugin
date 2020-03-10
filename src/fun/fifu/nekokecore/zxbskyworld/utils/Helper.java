package fun.fifu.nekokecore.zxbskyworld.utils;

import fun.fifu.nekokecore.zxbskyworld.Main;
import fun.fifu.nekokecore.zxbskyworld.IsLand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;


public class Helper {
    /**
     * 把玩家传送到岛上，脚下第四格永远是玻璃
     *
     * @param player
     * @param SkyLoc
     */
    public static void tpSkyLoc(Player player, String SkyLoc) {
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, getxxCentered(IsLand.getSkyX(SkyLoc)), 64, getyyCentered(IsLand.getSkyY(SkyLoc)));
        Location location1 = new Location(location.getWorld(), location.getX(), location.getY() - 4, location.getZ());
        Block block = location1.getBlock();
        block.setType(Material.BEDROCK);
        player.teleport(location);
        new SoundPlayer().playCat(player);

    }

    public static int getxxCentered(int SkyX) {
        return (IsLand.getrrEnd(SkyX) - IsLand.getrrForm(SkyX)) / 2 + IsLand.getrrForm(SkyX);
    }

    public static int getyyCentered(int SkyY) {
        return (IsLand.getrrEnd(SkyY) - IsLand.getrrForm(SkyY)) / 2 + IsLand.getrrForm(SkyY);
    }

    public static void goSpawn(Player player) {
        player.teleport(getSpawnLocation());
    }

    public static Location getSpawnLocation() {
        String world_str = DateAdmin.util_jsonObject.get("spawn_world").toString();
        int xx = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_xx").toString());
        int yy = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_yy").toString());
        int zz = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_zz").toString());
        int yaw = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_yaw").toString());
        int pitch = Integer.parseInt(DateAdmin.util_jsonObject.get("spawn_pitch").toString());
        World world = Bukkit.getWorld(world_str);
        return new Location(world, xx, yy, zz, yaw, pitch);
    }

    public static boolean inSpawn(int xx, int zz) {
        return inSkyWrold(xx, zz, "(0,0)");
    }

    public static boolean in(int now, int from, int end) {
        return now >= from && now <= end;
    }

    public static boolean inSkyWrold(int xx, int zz, String SkyLoc) {
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return in(xx, IsLand.getrrForm(SkyX), IsLand.getrrEnd(SkyX)) && in(zz, IsLand.getrrForm(SkyY), IsLand.getrrEnd(SkyY));
    }

    public static boolean inSkyWrold(int xx, int zz, int SkyX, int SkyY) {
        return in(xx, IsLand.getrrForm(SkyX), IsLand.getrrEnd(SkyX)) && in(zz, IsLand.getrrForm(SkyY), IsLand.getrrEnd(SkyY));
    }

    public static String toSkyLoc(int xx, int zz) {
        return "(" + getSkyR(xx) + "," + getSkyR(zz) + ")";
    }

    public static int getSkyR(int rr) {
        int SkyR = 0;
        if (rr > 0) {
            while (!in(rr, IsLand.getrrForm(SkyR), IsLand.getrrEnd(SkyR))) {
                if (SkyR > IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc正越界！");
                }
                SkyR++;
            }
        } else if (rr < 0) {
            while (!in(rr, IsLand.getrrForm(SkyR), IsLand.getrrEnd(SkyR))) {
                if (SkyR < -IsLand.MAXSKYLOC) {
                    throw new RuntimeException("R轴SkyLoc负越界！");
                }
                SkyR--;
            }
        }
        return SkyR;
    }

    public static String simplify(String SkyLoc) {
        SkyLoc = SkyLoc.replace("（", "(");
        SkyLoc = SkyLoc.replace("）", ")");
        SkyLoc = SkyLoc.replace("，", ",");
        SkyLoc = SkyLoc.replace(" ", "");
        int SkyX = IsLand.getSkyX(SkyLoc);
        int SkyY = IsLand.getSkyY(SkyLoc);
        return "(" + SkyX + "," + SkyY + ")";
    }

    public static boolean havePermission(Player player) {
        String UUID = player.getUniqueId().toString();
        JSONArray jsonArray;
        Location location = player.getLocation();
        int xx = location.getBlockX();
        int zz = location.getBlockZ();
        if (player.isOp()) {
            return true;
        } else if (inSpawn(xx, zz)) {
            return false;
        }
        String SkyLoc = Helper.toSkyLoc(xx, zz);
        for (Object obj : Main.dateAdmin.getOwnersList(SkyLoc)) {
            String uuid = (String) obj;
            if (UUID.equalsIgnoreCase(uuid)) {
                return true;
            }
        }
        for (Object obj : Main.dateAdmin.getMembersList(SkyLoc)) {
            String uuid = (String) obj;
            if (UUID.equalsIgnoreCase(uuid)) {
                return true;
            }
        }

        return false;
    }
}
