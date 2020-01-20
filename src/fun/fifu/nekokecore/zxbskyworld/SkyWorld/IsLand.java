package fun.fifu.nekokecore.zxbskyworld.SkyWorld;

import fun.fifu.nekokecore.zxbskyworld.Main;
import org.json.simple.JSONArray;

import java.util.Random;

import static fun.fifu.nekokecore.zxbskyworld.Main.jsonObject;


/**
 * @author NekokeCore
 */
public class IsLand extends IsLandNorm {
    public static void main(String[] args) {
        Main.initJson(Main.CONFIGPATH);
        IsLand isLand = new IsLand("NekokeCore");
        System.out.println(isLand.SkyX + "," + isLand.SkyY);
        System.out.println(isLand.getxxCentered() + ",," + isLand.getyyCentered());
    }

    @Override
    public String getSkyLoc() {
        return "(" + SkyX + "," + SkyY + ")";
    }

    @Override
    public void addSkyWorld(String Loc) {
        if (!jsonArray.contains(Loc)) {
            jsonArray.add(Loc);
            trim();
        }
    }

    @Override
    public String getMainIsland() {
        return "M(" + SkyX + "," + SkyY + ")";
    }

    public IsLand(String UUID) {
        //分配UUID
        this.UUID = UUID;
        //初始化JSON配置文件
        if (jsonObject == null) {
            throw new RuntimeException("配置文件异常，请仔细检查！！！");
        }
        if ((jsonArray = (JSONArray) jsonObject.get(UUID)) == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = (JSONArray) jsonObject.get(UUID);
        }

        //初始化主岛坐标
        String temp = "Miss Main IsLand!";
        //遍历查找主岛坐标
        for (Object obj : jsonArray) {
            temp = (String) obj;
            if ("M".equals(temp.substring(0, 1))) {
                break;
            }
            temp = (String) jsonArray.get(0);
            temp = String.format("M%s", temp);
        }
        //如果没有主岛，把第一个当成主岛,如果没有岛，就自动分配
        if (!jsonArray.contains(temp)) {
            if (jsonArray.size() == 0) {
                temp = "M" + allocationIsLand();
                jsonArray.add(0, temp);
            }
            jsonArray.remove(0);
            jsonArray.add(0, temp);
        }
        //赋值
        SkyX = getSkyX(temp);
        SkyY = getSkyY(temp);
        //整理并存档
        trim();
    }

    public static int getSkyX(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf('(') + 1, SkyLoc.indexOf(',')));
    }

    public static int getSkyY(String SkyLoc) {
        return Integer.parseInt(SkyLoc.substring(SkyLoc.indexOf(',') + 1, SkyLoc.indexOf(')')));
    }

    /**
     * 分配岛屿_SkyLoc
     *
     * @return
     */
    public String allocationIsLand() {
        String temp = jsonArray.toJSONString();
        Random random = new Random(System.currentTimeMillis());
        String tempSkyLoc = "Error";
        int tempxx;
        int tempyy;
        do {
            tempxx = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempyy = random.nextInt(MAXSKYLOC) - random.nextInt(MAXSKYLOC);
            tempSkyLoc = "(" + tempxx + "," + tempyy + ")";
        } while (temp.contains(tempSkyLoc));
        return tempSkyLoc;
    }
}
