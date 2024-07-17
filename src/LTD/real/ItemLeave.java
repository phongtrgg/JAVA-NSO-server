package LTD.real;

import LTD.io.Util;
import LTD.real.Item;
import LTD.real.ItemMap;
import LTD.real.Mob;
import LTD.real.Option;
import LTD.real.TileMap;
import LTD.server.Server;

public class ItemLeave {
    public static short[] arrSuKienLangCo = new short[]{1008, 1010,1009, 1002,1003,926,927,845,848};
    public static short[] arrItemThuLinh = new short[]{926,927};
    public static short[] arrItemSuKienCoHon = new short[]{976,975,974};
    //
    public static short[] arrTrangBiXeSoi = new short[]{439, 440, 441, 442, 488, 489, 487, 486};
    public static short[] arrExpXeSoi = new short[]{573, 574, 575, 576, 577, 578, 899};
    public static short[] arrItemOrther = new short[]{38, 648, 649, 650, 651, 851, 852, 853, 854, 879, 880};
    public static short[] arrItemSuKienHe = new short[]{428, 429, 430, 431};
    public static short[] arrItemSuKienXayDung = new short[]{883,884,885,886,};
    public static short[] arrItemSuKienTrungThu = new short[]{292, 293, 294, 295, 296, 297};
    public static short[] arrItemSuKienNoel = new short[]{666, 667, 668};
    public static short[] arrItemLv90 = new short[]{618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629, 630, 631, 632, 633, 634, 635, 636, 637};
    public static short[] arrItemSuKienTet = new short[]{638, 639, 641, 642};
    public static short[] arrItem8thang3 = new short[]{386, 387, 388 , 393,394,395};
    public static short[] arrItem10thang3 = new short[]{590, 591};
    public static short[] arrItemskgiaikhat= new short[]{924,925,929,928};
    public static short[] arrItemskAll = new short[]{ 38, 648, 649, 650, 651, 851, 852, 853, 854, 879, 880,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3,3,3,4,4,4,4,4,4,5,5,5,5,5,5,6,6,6,6};
    public static short[] arrItemBOSS = new short[]{8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 454, 454, 456, 456, 455, 455, 455, 455, 455, 276, 277, 278, 485, 454, 454, 456, 456, 455, 455, 455, 455, 455,926,927, 959, 959, 959, 959, 959,972,1002};
    public static short[] arrItemBOSSTuanLoc = new short[]{457, 457, 457, 457, 457, 457, 457, 457, 457, 457,926,927};
    public static short[] arrItemLDGT = new short[]{881, 881, 881, 881, 881, 881, 881, 881, 881, 881,881, 881, 881, 881, 881, 881, 881, 881, 881, 881,881, 881, 881, 881, 881, 881, 881, 881, 881, 881, 959, 959, 959, 959, 959, 970, 970, 971, 971, 970, 970, 971, 971, 970, 971};
    public static short[] arrSVC6x = new short[]{311, 312, 313, 314, 315, 316};
    public static short[] arrSVC7x = new short[]{375, 376, 377, 378, 379, 380};
    public static short[] arrSVC8x1 = new short[]{552, 553, 554};
    public static short[] arrSVC8x2 = new short[]{555, 556, 557};
    public static short[] arrSVC10x1 = new short[]{558, 559};
    public static short[] arrSVC10x2 = new short[]{560, 561};
    public static short[] arrSVC10x3 = new short[]{562, 563};
    public static short[] Dadv = new short[]{695, 696, 697, 698, 699, 700, 701, 702, 982};
    public static short[] Ltd = new short[]{922, 923};
    public static short[] Ltd2 = new short[]{970, 971, 959};
    public static short[] ItemBossTo = new short[]{455,455,455,455,456,456,456,456,457, 457, 457, 457, 457, 457, 457, 457, 457, 457,855,855,855,855,855,855,855,855,855,855,856,856,856,856,856,856,856,856,856,856,856,855,855,855,855,855,855,855,855,855,855,856,856,856,856,856,856,856,856,856,856,856,385,384,384,384,384,385,385,875,875,876,959,959,959,959,959,959,959,959,959,959,972,972,875,875,876,959,959,959,959,959,959,959,959,959,959,972,972,1002};

    public static void randomLeave(TileMap place, Mob mob3, int master, int per, int map) {
        switch (per) {
            case 1: {
                if (map == 1) {
                    ItemLeave.leaveEXPLangCo(place, mob3, master);
                    break;
                }
                if (map == 2) {
                    ItemLeave.leaveMapLTD1(place, mob3, master);
                    break;
                }
                if (map == 0) {
                    ItemLeave.leaveEXPVDMQ(place, mob3, master);
                    break;
                }
                if (map != 3) {
                    break;
                }
                ItemLeave.leaveChuyenSinh(place, mob3, master);
                break;
            }
            case 2: {
                if (map == 1) {
                    ItemLeave.leaveTTTLangCo(place, mob3, master);
                    break;
                }
                if (map == 0) {
                    ItemLeave.leaveTTTVDMQ(place, mob3, master);
                    break;
                }
                if (map == 2) {
                    ItemLeave.leaveMapLTD(place, mob3, master);
                    break;
                }
                if (map != 3) {
                    break;
                }
                ItemLeave.leaveChuyenSinh(place, mob3, master);
                break;
            }
            case 3: {
                if (map != 1) {
                    break;
                }
                ItemLeave.leaveTrangBiThuCuoiLangCo(place, mob3, master);
                break;
            }
        }
    }

    public static void addOption(Item item, int id, int param) {
        Option option = new Option(id, param);
        item.options.add(option);
    }

    public static void leaveItemSuKien(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        int per = Util.nextInt(5);
        try {
            switch (Server.manager.event) {
                case 1: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItemSuKienHe[Util.nextInt(arrItemSuKienHe.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 2: {
                    if (per >= 2) {
                        break;
                    }
                    im = place.LeaveItem(arrItemSuKienTrungThu[Util.nextInt(arrItemSuKienTrungThu.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 3: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItemSuKienNoel[Util.nextInt(arrItemSuKienNoel.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 4: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItemSuKienTet[Util.nextInt(arrItemSuKienTet.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 5: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItem8thang3[Util.nextInt(arrItem8thang3.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 6: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItem10thang3[Util.nextInt(arrItem10thang3.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
                case 7: {
                    if (per != 0) {
                        break;
                    }
                    im = place.LeaveItem(arrItemskgiaikhat[Util.nextInt(arrItemskgiaikhat.length)], mob3.x, mob3.y, mob3.templates.type, false);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.quantity = 1;
            im.item.isLock = false;
            im.master = master;
        }
    }
    public static short itemDropEvent(){
        short idI1 =0;
        switch (Server.manager.event) {
            case 1: {
                idI1 = ItemLeave.arrItemSuKienHe[Util.nextInt(ItemLeave.arrItemSuKienHe.length)];
                break;
            }
            case 2: {
                idI1 = ItemLeave.arrItemSuKienTrungThu[Util.nextInt(ItemLeave.arrItemSuKienNoel.length)];
                break;
            }
            case 3: {
                idI1 = ItemLeave.arrItemSuKienNoel[Util.nextInt(ItemLeave.arrItemSuKienNoel.length)];
                break;
            }
            case 4: {
                idI1 = ItemLeave.arrItemSuKienTet[Util.nextInt(ItemLeave.arrItemSuKienTet.length)];
                break;
            }
            case 5: {
                
                idI1 = ItemLeave.arrItem8thang3[Util.nextInt(ItemLeave.arrItem8thang3.length)];
                break;
            }
            case 6: {
                idI1 = ItemLeave.arrItem10thang3[Util.nextInt(ItemLeave.arrItem10thang3.length)];
                break;
            }
            case 7: {
                idI1 = ItemLeave.arrItemskgiaikhat[Util.nextInt(ItemLeave.arrItemskgiaikhat.length)];
                break;
            }
            case 8: {
                idI1 = ItemLeave.arrItemSuKienCoHon[Util.nextInt(ItemLeave.arrItemSuKienCoHon.length)];
                break;
            }
            case 9: {
                idI1 = ItemLeave.arrItemSuKienCoHon[Util.nextInt(ItemLeave.arrItemSuKienCoHon.length)];
                break;
            }
        }
        return idI1;
    }

    public static void leaveItemOrther(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        int percent = Util.nextInt(arrItemOrther.length);
        try {
            if (arrItemOrther[percent] != -1) {
                switch (arrItemOrther[percent]) {
                    case 6: {
                        im = place.LeaveItem((short) 6, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 38: {
                        im = place.LeaveItem((short) 38, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 648: {
                        im = place.LeaveItem((short) 648, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 649: {
                        im = place.LeaveItem((short) 649, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 650: {
                        im = place.LeaveItem((short) 650, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 651: {
                        im = place.LeaveItem((short) 651, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 851: {
                        im = place.LeaveItem((short) 851, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 852: {
                        im = place.LeaveItem((short) 852, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 853: {
                        im = place.LeaveItem((short) 853, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 854: {
                        im = place.LeaveItem((short) 854, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 879: {
                        im = place.LeaveItem((short) 879, mob3.x, mob3.y, mob3.templates.type, false);
                        break;
                    }
                    case 880: {
                        im = place.LeaveItem((short) 880, mob3.x, mob3.y, mob3.templates.type, false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.quantity = 1;
            im.item.isLock = false;
            im.master = master;
        }
    }

    public static void leaveYen(TileMap place, Mob mob3, int master) {
        try {
            ItemMap im = place.LeaveItem((short) 12, mob3.x, mob3.y, mob3.templates.type, mob3.isboss);
            if (im != null) {
                im.item.quantity = 0;
                im.item.isLock = false;
                im.master = master;
                im.checkMob = mob3.lvboss;
                if (mob3.isboss) {
                    im.checkMob = 4;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveChiaKhoa(TileMap place, Mob mob3, int master) {
        try {
            ItemMap im = place.LeaveItem((short) 260, mob3.x, mob3.y, mob3.templates.type, mob3.isboss);
            if (im != null) {
                im.item.quantity = 1;
                im.item.isLock = true;
                im.master = master;
                im.item.isExpires = true;
                im.item.expires = place.map.timeMap;
                im.checkMob = mob3.lvboss;
                if (mob3.isboss) {
                    im.checkMob = 4;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveLDGT(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            if (mob3.templates.id == 81) {
                int per = Util.nextInt(10);
                if (per < 4 && (im = place.LeaveItem((short) 261, mob3.x, mob3.y, mob3.templates.type, mob3.isboss)) != null) {
                    im.item.quantity = 1;
                    im.item.isLock = true;
                    im.master = master;
                    im.item.isExpires = true;
                    im.item.expires = place.map.timeMap;
                }
            } else if (mob3.templates.id == 82) {
                for (int i = 0; i < arrItemLDGT.length; ++i) {
                    im = place.LeaveItem(arrItemLDGT[i], mob3.x, mob3.y, mob3.templates.type, true);
                    if (im == null) {
                        continue;
                    }
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveTrangBiThuCuoiVDMQ(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentTB = Util.nextInt(100);
            int perCentArr = Util.nextInt(arrTrangBiXeSoi.length);
            if (perCentTB < 1) {
                im = place.LeaveItem(arrTrangBiXeSoi[perCentArr], mob3.x, mob3.y, mob3.templates.type, false);
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveTrangBiThuCuoiLangCo(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentTB = Util.nextInt(650);
            if (perCentTB == 0) {
                im = place.LeaveItem((short) 524, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTB == 1) {
                int perCentArr = Util.nextInt(arrTrangBiXeSoi.length);
                im = place.LeaveItem(arrTrangBiXeSoi[perCentArr], mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTB >= 5 && perCentTB < 10) {
                im = place.LeaveItem((short) 545, mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.quantity = 1;
            im.item.isLock = false;
            im.master = master;
        }
    }

    public static void leaveEXPLangCo(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentEXP = Util.nextInt(100);
            if (perCentEXP < 20) {
                im = place.LeaveItem(arrExpXeSoi[Util.nextInt(arrExpXeSoi.length)], mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }

    public static void leaveEXPVDMQ(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentEXP = Util.nextInt(375);
            if (perCentEXP < 5) {
                im = place.LeaveItem(arrExpXeSoi[Util.nextInt(arrExpXeSoi.length)], mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }

    public static void leaveTTTLangCo(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentTTT = Util.nextInt(410);
            if (perCentTTT <= 40) {
                im = place.LeaveItem((short) 455, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT <= 20) {
                im = place.LeaveItem((short) 456, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT <= 123) {
                im = place.LeaveItem((short) 903, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT <= 1) {
                im = place.LeaveItem((short) 454, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (Util.nextInt(2000) == 1999) {
                im = place.LeaveItem((short) 457, mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }

    public static void leaveChuyenSinh(TileMap place, Mob mob3, int master) {
        short idI = 0;
        ItemMap im = null;
        try {
            int perCentTTT = Util.nextInt(410);
            if (perCentTTT >= 100 && perCentTTT <= 105) {
                short[] arId = new short[]{445, 843};
                idI = arId[Util.nextInt(arId.length)];
                im = place.LeaveItem(idI, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT >= 190 && perCentTTT <= 194) {
                short[] arId = new short[]{226, 227, 228, 456};
                idI = arId[Util.nextInt(arId.length)];
                im = place.LeaveItem(idI, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT <= 1) {
                im = place.LeaveItem((short) 225, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (Util.nextInt(2000) == 1999) {
                short[] arId = new short[]{222, 223, 224, 838, 840, 789};
                idI = arId[Util.nextInt(arId.length)];
                im = place.LeaveItem(idI, mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }

    public static void leaveTTTT(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            if (Util.nextInt(10) < 3 && (im = place.LeaveItem((short) 456, mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) 455, mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) 903, mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 2;
                im.master = master;
            }
            if ((im = place.LeaveItem(arrExpXeSoi[Util.nextInt(arrExpXeSoi.length)], mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
            if ((im = place.LeaveItem(arrExpXeSoi[Util.nextInt(arrExpXeSoi.length)], mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveTTTVDMQ(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentTTT = Util.nextInt(2850);
            if (perCentTTT >= 100 && perCentTTT <= 115) {
                im = place.LeaveItem((short) 455, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT == 5 || perCentTTT == 10) {
                im = place.LeaveItem((short) 456, mob3.x, mob3.y, mob3.templates.type, false);
            } else if (perCentTTT == 1) {
                im = place.LeaveItem((short) 454, mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }

    public static void leaveItemBOSS(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int i;
            if (Util.nextInt(10) < 3 && (im = place.LeaveItem((short) 383, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if (Util.nextInt(10) < 3 && (im = place.LeaveItem((short) 743, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if (Util.nextInt(3000) < 1 && (im = place.LeaveItem((short) 960, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if (Util.nextInt(3200) < 1) {
                im = place.LeaveItem((short) 547, mob3.x, mob3.y, mob3.templates.type, true);
                if (im != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
                if ((im = place.LeaveItem((short) 545, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
                if ((im = place.LeaveItem((short) 545, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
            }
            switch (Util.nextInt(1, 4)) {
                case 1: {
                    for (i = 0; i < arrSVC6x.length; ++i) {
                        im = place.LeaveItem(arrSVC6x[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
                case 2: {
                    for (i = 0; i < arrSVC7x.length; ++i) {
                        im = place.LeaveItem(arrSVC7x[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
                case 3: {
                    for (i = 0; i < arrSVC8x1.length; ++i) {
                        im = place.LeaveItem(arrSVC8x1[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
                case 4: {
                    for (i = 0; i < arrSVC8x2.length; ++i) {
                        im = place.LeaveItem(arrSVC8x2[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
            }
            for (i = 0; i < arrItemBOSS.length; ++i) {
                im = place.LeaveItem(arrItemBOSS[i], mob3.x, mob3.y, mob3.templates.type, true);
                if (im == null) {
                    continue;
                }
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void leaveBossTo(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int i;
            if (Util.nextInt(10) < 3 && (im = place.LeaveItem((short) 743, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if (Util.nextInt(100) < 1) {
                im = place.LeaveItem((short) 960, mob3.x, mob3.y, mob3.templates.type, true);
                if (im != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
            }
            if (Util.nextInt(100) < 2) {
                im = place.LeaveItem((short) 1065, mob3.x, mob3.y, mob3.templates.type, true);
                if (im != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
            }
            switch (Util.nextInt(1, 3)) {
                case 1: {
                    for (i = 0; i < arrSVC10x1.length; ++i) {
                        im = place.LeaveItem(arrSVC10x1[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
                case 2: {
                    for (i = 0; i < arrSVC10x2.length; ++i) {
                        im = place.LeaveItem(arrSVC10x2[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
                case 3: {
                    for (i = 0; i < arrSVC10x3.length; ++i) {
                        im = place.LeaveItem(arrSVC10x3[i], mob3.x, mob3.y, mob3.templates.type, true);
                        if (im == null) {
                            continue;
                        }
                        im.item.quantity = 1;
                        im.item.isLock = false;
                        im.master = master;
                    }
                    break;
                }
            }
            for (i = 0; i < ItemBossTo.length; ++i) {
                im = place.LeaveItem(ItemBossTo[i], mob3.x, mob3.y, mob3.templates.type, true);
                if (im == null) {
                    continue;
                }
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveItemBOSSTuanLoc(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            if (Util.nextInt(200) < 1 && (im = place.LeaveItem((short) 383, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if (Util.nextInt(10) < 1) {
                im = place.LeaveItem((short) 547, mob3.x, mob3.y, mob3.templates.type, true);
                if (im != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
                if ((im = place.LeaveItem((short) 545, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                    im.item.quantity = 1;
                    im.item.isLock = false;
                    im.master = master;
                }
            }
            if (Util.nextInt(15) < 2 && (im = place.LeaveItem((short) 539, mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) Util.nextInt(275, 278), mob3.x, mob3.y, mob3.templates.type, true)) != null) {
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
            for (int i = 0; i < arrItemBOSSTuanLoc.length; ++i) {
                im = place.LeaveItem(arrItemBOSSTuanLoc[i], mob3.x, mob3.y, mob3.templates.type, true);
                if (im == null) {
                    continue;
                }
                im.item.quantity = 1;
                im.item.isLock = false;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void leaveMapLTD(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentEXP = Util.nextInt(300);
            if (perCentEXP < 120) {
                im = place.LeaveItem(Ltd[Util.nextInt(Ltd.length)], mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }
    public static void leaveMapLTD1(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            int perCentEXP = Util.nextInt(300);
            if (perCentEXP < 10) {
                im = place.LeaveItem(Dadv[Util.nextInt(Dadv.length)], mob3.x, mob3.y, mob3.templates.type, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (im != null) {
            im.item.isLock = false;
            im.item.quantity = 1;
            im.master = master;
        }
    }
    public static void leaveTLTT(TileMap place, Mob mob3, int master) {
        ItemMap im = null;
        try {
            im = place.LeaveItem((short) 703, mob3.x, mob3.y, mob3.templates.type, false);
            if (im != null) {
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) 545, mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 2;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) 704, mob3.x, mob3.y, mob3.templates.type, false))!= null){
                im.item.isLock = false;
                im.item.quantity = 1;
                im.master = master;
            }
            if ((im = place.LeaveItem((short) 982, mob3.x, mob3.y, mob3.templates.type, false)) != null) {
                im.item.isLock = false;
                im.item.quantity = 2;
                im.master = master;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
