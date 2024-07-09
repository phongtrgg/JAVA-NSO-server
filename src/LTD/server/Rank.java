package LTD.server;

import LTD.real.ClanManager;
import LTD.io.SQLManager;
import LTD.io.Util;
import LTD.template.ItemTemplate;
import static java.awt.Color.red;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;

public class Rank {

    public static ArrayList<Entry>[] bangXH = new ArrayList[12];
    public static Timer t = new Timer(true);

    public static ArrayList<Entry2> bxhCaoThu = new ArrayList<>();
    public static ArrayList<Entry3> bxhBossTuanLoc = new ArrayList<>();
    public static ArrayList<Entry4> bxhBossChuot = new ArrayList<>();
    public static ArrayList<Entry4> bxhdiemhoa = new ArrayList<>();

    public static void updateCaoThu() {
        Rank.bxhCaoThu.clear();
        ResultSet red = null;
        try {
            synchronized (Rank.bxhCaoThu) {
                int i = 1;
                red = SQLManager.stat.executeQuery("SELECT `name`,`class`,`level`,`time` FROM `xep_hang_level` WHERE `level` = " + Manager.max_level_up + " ORDER BY `id` ASC LIMIT 20;");
                String name;
                int level;
                String nClass;
                String time;
                Entry2 bXHCaoThu;
                if (red != null) {
                    while (red.next()) {
                        name = red.getString("name");
                        level = red.getInt("level");
                        nClass = red.getString("class");
                        time = red.getString("time");
                        bXHCaoThu = new Entry2();
                        bXHCaoThu.name = name;
                        bXHCaoThu.index = i;
                        bXHCaoThu.level = level;
                        bXHCaoThu.nClass = nClass;
                        bXHCaoThu.time = time;
                        bxhCaoThu.add(bXHCaoThu);
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (red != null) {
                try {
                    red.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateBossChuot() {
        ResultSet red = null;
        try {
            Rank.bxhBossChuot.clear();
            synchronized (Rank.bxhBossChuot) {
                int i = 1;
                red = SQLManager.stat.executeQuery("SELECT `name`,`diemhoa` FROM `ninja` WHERE (`diemhoa` > 0) ORDER BY `diemhoa` DESC LIMIT 10;");
                if (red != null) {
                    Entry4 bXHBChuot;
                    String name;
                    int point;
                    while (red.next()) {
                        name = red.getString("name");
                        point = Integer.parseInt(red.getString("diemhoa"));
                        bXHBChuot = new Entry4();
                        bXHBChuot.name = name;
                        bXHBChuot.index = i;
                        bXHBChuot.point1 = point;
                        bxhBossChuot.add(bXHBChuot);
                        ++i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (red != null) {
                try {
                    red.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void updateBossTL() {
        ResultSet red = null;
        try {
            Rank.bxhBossTuanLoc.clear();
            synchronized (Rank.bxhBossTuanLoc) {
                int i = 1;
                red = SQLManager.stat.executeQuery("SELECT `name`,`pointBossTL` FROM `ninja` WHERE (`pointBossTL` > 0) ORDER BY `pointBossTL` DESC LIMIT 10;");
                if (red != null) {
                    Entry3 bXHBTL;
                    String name;
                    int point;
                    while (red.next()) {
                        name = red.getString("name");
                        point = Integer.parseInt(red.getString("pointBossTL"));
                        bXHBTL = new Entry3();
                        bXHBTL.name = name;
                        bXHBTL.index = i;
                        bXHBTL.point = point;
                        bxhBossTuanLoc.add(bXHBTL);
                        ++i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (red != null) {
                try {
                    red.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void init() {
        Rank.updateCaoThu();
        Rank.updateBossTL();
        for (int i = 0; i < Rank.bangXH.length; ++i) {
            Rank.bangXH[i] = new ArrayList<Entry>();
        }
        System.out.println("load BXH");
        for (int i = 0; i < Rank.bangXH.length; ++i) {
            initBXH(i);
        }
    }

    public static void initBXH(int type) {
        Rank.bangXH[type].clear();
        ArrayList<Entry> bxh = Rank.bangXH[type];
        switch (type) {
            case 0: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`yen`,`level` FROM `ninja` WHERE (`yen` > 0) ORDER BY `yen` DESC LIMIT 10;");
                    String name;
                    int coin;
                    int level;
                    Entry bXHE;
                    if (red != null) {
                        while (red.next()) {
                            name = red.getString("name");
                            coin = red.getInt("yen");
                            level = red.getInt("level");
                            bXHE = new Entry();
                            bXHE.nXH = new long[2];
                            bXHE.name = name;
                            bXHE.index = i;
                            bXHE.nXH[0] = coin;
                            bXHE.nXH[1] = level;
                            bxh.add(bXHE);
                            i++;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 1: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`exp`,`level`,`class` FROM `ninja` WHERE (`exp` > 0) ORDER BY `exp` DESC LIMIT 20;");
                    String name;
                    long exp;
                    int level2;
                    int nClass;
                    Entry bXHE2;
                    while (red.next()) {
                        name = red.getString("name");
                        exp = red.getLong("exp");
                        level2 = red.getInt("level");
                        nClass = red.getInt("class");
                        bXHE2 = new Entry();
                        bXHE2.nXH = new long[3];
                        bXHE2.name = name;
                        bXHE2.index = i;
                        bXHE2.nXH[0] = exp;
                        bXHE2.nXH[1] = level2;
                        bXHE2.nXH[2] = nClass;
                        bxh.add(bXHE2);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 2: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`level` FROM `clan` WHERE (`level` > 0) ORDER BY `level` DESC LIMIT 10;");
                    String name;
                    int level3;
                    Entry bXHE3;
                    while (red.next()) {
                        name = red.getString("name");
                        level3 = red.getInt("level");
                        bXHE3 = new Entry();
                        bXHE3.nXH = new long[1];
                        bXHE3.name = name;
                        bXHE3.index = i;
                        bXHE3.nXH[0] = level3;
                        bxh.add(bXHE3);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 3: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`bagCaveMax`,`itemIDCaveMax` FROM `ninja` WHERE (`bagCaveMax` > 0) ORDER BY `bagCaveMax` DESC LIMIT 10;");
                    String name;
                    int cave;
                    short id;
                    Entry bXHE;
                    while (red.next()) {
                        name = red.getString("name");
                        cave = red.getInt("bagCaveMax");
                        id = red.getShort("itemIDCaveMax");
                        bXHE = new Entry();
                        bXHE.nXH = new long[2];
                        bXHE.name = name;
                        bXHE.index = i;
                        bXHE.nXH[0] = cave;
                        bXHE.nXH[1] = id;
                        bxh.add(bXHE);
                        ++i;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 4: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`luongTN` FROM `ninja` WHERE (`luongTN` > 0) ORDER BY `luongTN` DESC LIMIT 10;");
                    while (red.next()) {
                        String name = red.getString("name");
                        int sk = red.getInt("luongTN");
                        Entry bXHE = new Entry();
                        bXHE.nXH = new long[2];
                        bXHE.name = name;
                        bXHE.index = i;
                        bXHE.nXH[0] = sk;
                        bxh.add(bXHE);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 5: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`diemhoa`,`diemhoado`,`diemhoavang`,`diemhoaxanh`,`sohoanhan` FROM `ninja` WHERE (`diemhoa` > 0) ORDER BY `diemhoa` DESC LIMIT 10;");
                    String name;
                    int point;
                    int point1;
                    int point2;
                    int point3;
                    int point4;
                    Entry bXHH;
                    while (red.next()) {
                        name = red.getString("name");
                        point = red.getInt("diemhoa");
                        point1 = red.getInt("diemhoado");
                        point2 = red.getInt("diemhoavang");
                        point3 = red.getInt("diemhoaxanh");
                        point4 = red.getInt("sohoanhan");
                        bXHH = new Entry();
                        bXHH.nXH = new long[5];
                        bXHH.name = name;
                        bXHH.index = i;
                        bXHH.nXH[0] = point;
                        bXHH.nXH[1] = point1;
                        bXHH.nXH[2] = point2;
                        bXHH.nXH[3] = point3;
                        bXHH.nXH[4] = point4;
                        bxh.add(bXHH);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 6: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`luongTN` FROM `ninja` WHERE (`luongTN` > 0) ORDER BY `luongTN` DESC LIMIT 10;");
                    String name;
                    int point;
                    Entry bXHHL;
                    while (red.next()) {
                        name = red.getString("name");
                        int sk = red.getInt("luongTN");
                        bXHHL = new Entry();
                        bXHHL.nXH = new long[1];
                        bXHHL.name = name;
                        bXHHL.index = i;
                        bXHHL.nXH[0] = sk;
                        bxh.add(bXHHL);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 7: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`bingo` FROM `ninja` WHERE (`bingo` > 0) ORDER BY `bingo` DESC LIMIT 10;");
                    String name;
                    int point;
                    Entry bXHHL;
                    while (red.next()) {
                        name = red.getString("name");
                        int sk = red.getInt("bingo");
                        bXHHL = new Entry();
                        bXHHL.nXH = new long[1];
                        bXHHL.name = name;
                        bXHHL.index = i;
                        bXHHL.nXH[0] = sk;
                        bxh.add(bXHHL);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 8: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`topgiaikhac` FROM `ninja` WHERE (`topgiaikhac` > 0) ORDER BY `topgiaikhac` DESC LIMIT 10;");
                    String name;
                    int point;
                    Entry bXHHL;
                    while (red.next()) {
                        name = red.getString("name");
                        int sk = red.getInt("topgiaikhac");
                        bXHHL = new Entry();
                        bXHHL.nXH = new long[1];
                        bXHHL.name = name;
                        bXHHL.index = i;
                        bXHHL.nXH[0] = sk;
                        bxh.add(bXHHL);
                        i++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            case 9: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `day`,`code`,`id` FROM `xoso` WHERE (`day` > 0) ORDER BY `day` DESC LIMIT 7;");
                    while (red.next()) {
                        String day = red.getString("day");
                        long code = red.getLong("code");
                        int id = red.getInt("id");
                        Entry bXHE = new Entry();
                        bXHE.nXH = new long[2];
                        bXHE.name = day;
                        bXHE.index = i;
                        bXHE.nXH[0] = code;
                        bXHE.nXH[1] = id;
                        bxh.add(bXHE);
                        i++;
                    }
                    red.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
            
            case 10: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `name`,`dame` FROM `ninja` WHERE (`dame` > 0) ORDER BY `dame` DESC LIMIT 10;");
                    String name;
                    long dame;
                    short id;
                    Entry bXHD;
                    while (red.next()) {
                        name = red.getString("name");
                        dame = red.getLong("dame");
                        bXHD = new Entry();
                        bXHD.nXH = new long[2];
                        bXHD.name = name;
                        bXHD.index = i;
                        bXHD.nXH[0] = dame;
                        bxh.add(bXHD);
                        ++i;
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
               case 11: {
                ResultSet red = null;
                try {
                    int i = 1;
                    red = SQLManager.stat.executeQuery("SELECT `username`,`tiennap` FROM `player` WHERE (`tiennap` > 0) ORDER BY `tiennap` DESC LIMIT 10;");
                    String username;
                    long tiennap;
                    short id;
                    Entry bXHD;
                    while (red.next()) {
                        username = red.getString("username");
                        tiennap = red.getLong("tiennap");
                        bXHD = new Entry();
                        bXHD.nXH = new long[2];
                        bXHD.name = username;
                        bXHD.index = i;
                        bXHD.nXH[0] = tiennap;
                        bxh.add(bXHD);
                        ++i;
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if(red != null) {
                        try {
                            red.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }
            
            
        }
    }

    public static Entry[] getBangXH(int type) {
        ArrayList<Entry> bxh = Rank.bangXH[type];
        Entry[] bxhA = new Entry[bxh.size()];
        for (int i = 0; i < bxhA.length; ++i) {
            bxhA[i] = bxh.get(i);
        }
        return bxhA;
    }

    public static String getStringBXH(int type) {
        String str = "";
        switch (type) {
            case 0: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                for (Entry bxh : Rank.bangXH[type]) {
                    str = str + bxh.index + ". " + bxh.name + ": " + Util.getFormatNumber(bxh.nXH[0]) + " yên - cấp: " + bxh.nXH[1] + "\n";
                }
                break;
            }
            case 1: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                if (Rank.bxhCaoThu.size() < 1) {
                    for (Entry bxh : Rank.bangXH[type]) {
                        str = str + bxh.index + ". " + bxh.name + ": " + Util.getFormatNumber(bxh.nXH[0]) + " kinh nghiệm - cấp: " + bxh.nXH[1] + " (" + Server.manager.NinjaS[(int) bxh.nXH[2]] + ")\n";
                    }
                } else {
                    for (Entry2 bxh : Rank.bxhCaoThu) {
                        str = str + bxh.index + ". " + bxh.name + " (" + bxh.nClass + ") đã đạt cấp độ " + bxh.level + " vào lúc " + bxh.time + ".\n";
                    }
                }
                break;
            }
            case 2: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                for (Entry bxh : Rank.bangXH[type]) {
                    ClanManager clan = ClanManager.getClanName(bxh.name);
                    if (clan != null) {
                        str = str + bxh.index + ". Gia tộc " + bxh.name + " trình độ cấp " + bxh.nXH[0] + " do " + clan.getmain_name() + " làm tộc trưởng, thành viên " + clan.members.size() + "/" + clan.getMemMax() + "\n";
                    } else {
                        str = str + bxh.index + ". Gia tộc " + bxh.name + " trình độ cấp " + bxh.nXH[0] + " đã bị giải tán\n";
                    }
                }
                break;
            }
            case 3: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                for (Entry bxh : Rank.bangXH[type]) {
                    str = str + bxh.index + ". " + bxh.name + " nhận được" + Util.getFormatNumber(bxh.nXH[0]) + " " + ItemTemplate.ItemTemplateId((int) bxh.nXH[1]).name + "\n";
                }
                break;
            }
            case 4: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". " + bxh.name + " đã tiêu " + Util.getFormatNumber(bxh.nXH[0]) + " lượng\n";
                    }
                }
                break;
            }
            case 5: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". " + bxh.name + " đã có " 
                                + "\ntổng điểm làm hoa: "+ Util.getFormatNumber(bxh.nXH[0]) 
                                + "\nđiểm làm hoa đỏ: "+ Util.getFormatNumber(bxh.nXH[1]) 
                                + "\nđiểm làm hoa vàng: "+ Util.getFormatNumber(bxh.nXH[2]) 
                                + "\nđiểm làm hoa xanh: "+ Util.getFormatNumber(bxh.nXH[3]) 
                                + "\nđiểm số hoa người khác tặng mình: "+ Util.getFormatNumber(bxh.nXH[4])+"\n" ;
                    }
                }
                break;
            }
            case 6: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". " + bxh.name + " đã có số lượng " + Util.getFormatNumber(bxh.nXH[0]) + " đã tiêu.\n";
                    }
                }
                break;
            }
            case 7: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". " + bxh.name + " hiện có " + Util.getFormatNumber(bxh.nXH[0]) + " điểm săn boss.\n";
                    }
                }
                break;
            }
            case 8: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". " + bxh.name + " hiện có " + Util.getFormatNumber(bxh.nXH[0]) + " Điểm Sự Kiện.\n";
                    }
                }
                break;
            }
            case 9: {
                if (bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                } else {
                    for (Entry bxh : bangXH[type]) {
                        str += bxh.index + ". Ngày " + bxh.name + " - Số trúng thưởng: " + Util.getFormatNumber(bxh.nXH[0]) + "\n";
                    }
                }
                break;
            }
            
             case 10: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                for (Entry bxh : Rank.bangXH[type]) {
                    str = str + bxh.index + ". " + bxh.name + " đạt " + Util.getFormatNumber(bxh.nXH[0]) + " sức mạnh." + "\n";
                }
                break;
            }
             case 11: {
                if (Rank.bangXH[type].isEmpty()) {
                    str = "Chưa có thông tin";
                    break;
                }
                for (Entry bxh : Rank.bangXH[type]) {
                    str = str + bxh.index + ". " + bxh.name + " đã nạp " + Util.getFormatNumber(bxh.nXH[0]) + " coin." + "\n";
                }
                break;
            }
            
        }
        return str;
    }

    public static class Entry {

        int index;
        String name;
        long[] nXH;
    }

    public static class Entry2 {

        int index;
        String name;
        int level;
        String nClass;
        String time;
    }

    public static class Entry3 {

        int index;
        String name;
        int point;
    }

    public static class Entry4 {

        int index;
        String name;
        int point1;
    }
}
