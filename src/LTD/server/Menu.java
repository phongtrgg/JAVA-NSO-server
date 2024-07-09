package LTD.server;

import LTD.io.Session;
import LTD.stream.Client;
import LTD.stream.TuTien;
import LTD.stream.ChienTruong;
import LTD.stream.Cave;
import LTD.stream.SaveData;
import LTD.stream.LanhDiaGiaToc;
import LTD.template.NpcTemplate;
import LTD.template.MapTemplate;
import LTD.template.DanhVongTemplate;
import LTD.template.ItemTemplate;
import LTD.template.ShinwaTemplate;
import LTD.template.MobTemplate;
import LTD.real.Admission;
import LTD.real.TileMap;
import LTD.real.DunListWin;
import LTD.real.CheckCLXu;
import LTD.real.Option;
import LTD.real.CheckTXXu;
import LTD.real.Skill;
import LTD.real.CheckRHB;
import LTD.real.Level;
import LTD.real.Player;
import LTD.real.Language;
import LTD.real.Char;
import LTD.real.Map;
import LTD.real.ClanManager;
import LTD.real.UseItem;
import LTD.real.Item;
import LTD.io.Message;
import LTD.io.SQLManager;
import LTD.io.Util;
import LTD.thiendiabang.ThienDiaData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import LTD.real.CheckCLCoin;
import LTD.real.CheckCLLuong;
import LTD.real.CheckTXCoin;
import LTD.real.CheckTXLuong;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
//import sun.audio.AudioPlayer;

public class Menu {

//    static void doMenuArray(AudioPlayer player, String[] string) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public void sendMessMenuNhiemVu(Player p, byte npcid, byte menuId, String str) throws IOException {
        NpcTemplate npc = (NpcTemplate) Manager.npcs.get(npcid);
        Message mss = new Message(39);
        DataOutputStream ds = mss.writer();
        ds.writeShort(npcid);
        ds.writeUTF(str);
        ds.writeByte(npc.menu[menuId].length);

        for (int i = 1; i < npc.menu[menuId].length; ++i) {
            ds.writeUTF(npc.menu[menuId][i]);
        }

        ds.flush();
        p.conn.sendMessage(mss);
        mss.cleanup();
    }

    public static void doMenuArray(Player p, String[] menu) {
        Message m = null;
        try {
            m = new Message(63);
            for (byte i = 0; i < menu.length; ++i) {
                m.writer().writeUTF(menu[i]);
            }
            m.writer().flush();
            p.conn.sendMessage(m);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (m != null) {
                m.cleanup();
            }
        }

    }

    public static void sendWrite(Player p, short type, String title) {
        Message m = null;
        try {
            m = new Message(92);
            m.writer().writeUTF(title);
            m.writer().writeShort(type);
            m.writer().flush();
            p.conn.sendMessage(m);
            m.cleanup();
        } catch (IOException var5) {
            var5.printStackTrace();
        } finally {
            if (m != null) {
                m.cleanup();
            }
        }

    }

    public static void menuId(Player p, Message ms) {
        try {
            short npcId = ms.reader().readShort();
            ms.cleanup();
            p.c.typemenu = 5;
            p.typemenu = npcId;
            if (npcId == 33) {
                switch (Server.manager.event) {
                    case 1: {
                        Menu.doMenuArray(p, new String[]{"Diều giấy", "Diều vải"});
                        break;
                    }
                    case 2: {
                        Menu.doMenuArray(p, new String[]{"Hộp bánh thường", "Hộp bánh thượng hạng", "Bánh thập cẩm", "Bánh dẻo", "Bánh đậu xanh", "Bánh pía"});
                        break;
                    }
                    case 3: {
                        Menu.doMenuArray(p, new String[]{"Bánh Chocolate", "Bánh dâu tây", "Đổi mặt nạ", "Đổi pet", "BXH Diệt Boss TL", "Hướng dẫn"});
                        break;
                    }
                    case 4: {
                        Menu.doMenuArray(p, new String[]{"Bánh Chưng", "Bánh Tét", "Lì xì", "Làm Pháo", "Top diệt chuột", "Hướng dẫn"});
                        break;
                    }
                    case 5: {
                        Menu.doMenuArray(p, new String[]{"Làm Hoa Hồng Đỏ", "Làm Hoa Hồng Vàng", "Làm Hoa Hồng Xanh", "Làm Giỏ Hoa", "Tặng Hoa Hồng Đỏ", "Tặng Hoa Hồng Vàng", "Tặng Hoa Hồng Xanh", "Tặng Giỏ Hoa", "HD Kết Hoa", "BXH Tặng Hoa","BXH cá nhân","Đổi rương","Đổi rương (nữ)"});
                         break;
                    }
                    case 6: {
                        Menu.doMenuArray(p, new String[]{"làm tre xanh", "làm tre vàng", "Hướng dẫn"});
                        break;
                    }
                    case 7: {
                        Menu.doMenuArray(p, new String[]{"Bánh Chưng", "Bánh Tét", "Đổi Điểm Boss", "Hướng dẫn"});
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } else if (npcId == 40) {
                switch (p.c.mapid) {
                    case 117: {
                        Menu.doMenuArray(p, new String[]{"Rời khỏi nơi này", "Đặt cược", "Hướng dẫn"});
                        break;
                    }
                    case 118:
                    case 119: {
                        Menu.doMenuArray(p, new String[]{"Rời khỏi nơi này", "Thông tin"});
                        break;
                    }
                }
            }

            ms = new Message((byte) 40);
            ms.writer().flush();
            p.conn.sendMessage(ms);
            ms.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ms != null) {
                ms.cleanup();
            }
        }
    }

    public static void menu(Player p, Message ms) {
        try {
            byte npcId = ms.reader().readByte();
            byte menuId = ms.reader().readByte();
            byte b3 = ms.reader().readByte();
            if (ms.reader().available() > 0) {
                byte var6 = ms.reader().readByte();
            }
            ms.cleanup();
            if ((p.typemenu == -1 || p.typemenu == 0) && p.typemenu != npcId) {
                switch (npcId) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;

                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9: {
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    }
                    case 10: {
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    }
                    case 11: {
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    }
                    case 12: {
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    }
                    case 16: {
                        Menu.npcThayLoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 18: {
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    }
                    case 19: {
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    }
                    case 20: {
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    }
                    case 21: {
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    }
                    case 22: {
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    }
                    case 23: {
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    }
                    case 24: {
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    }
                    case 25: {
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    }
                    case 26: {
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    }
                    case 27: {
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    }
                    case 28: {
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    }
                    case 29: {
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    }
                    case 30: {
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    }
                    case 31: {
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    }
                    case 32: {
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    }
                    case 33: {
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    }
                    case 34: {
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    }
                    case 35: {
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    }
                    case 36: {
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;
                    }
                    case 37: {
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;
                    }
                    case 38: {
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    }
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                    case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                    case 42: {
                        Menu.npcXebeTong(p, npcId, menuId, b3);
                        break;
                    }
                    case 44: {
                        Menu.npcHienNhan(p, npcId, menuId, b3);
                        break;
                    }
                    case 45: {
                        Menu.npcBachhao(p, npcId, menuId, b3);
                        break;
                    }
                    case 46: {
                        Menu.Luyenbikiep(p, npcId, menuId, b3);
                        break;
                    }
                    case 47: {
                        Menu.npcChauBac(p, npcId, menuId, b3);
                        break;
                    }
                    case 48: {
                        Menu.npcDeTu(p, npcId, menuId, b3);
                        break;
                    }
                    case 49: {
                        Menu.Nangyy(p, npcId, menuId, b3);
                        break;
                    }
                    case 50: {
                        Menu.npcTsu(p, npcId, menuId, b3);
                        break;
                    }
                    case 51: {
                        Menu.npcATMLTD(p, npcId, menuId, b3);
                        break;
                    }
                    case 52: {
                        Menu.npcNangBua(p, npcId, menuId, b3);
                        break;
                    }
                    case 53: {
                        Menu.npcNangNhan(p, npcId, menuId, b3);
                        break;
                    }
                    case 54: {
                        Menu.NangBoiDay(p, npcId, menuId, b3);
                        break;
                    }
                    case 56: {
                        Menu.npcdoiqua(p, npcId, menuId, b3);
                        break;
                    }
                    case 57: {
                        Menu.npcCocLTD(p, npcId, menuId, b3);
                        break;
                    }
                    case 58: {
                        Menu.npcDoiNgoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 59: {
                        Menu.npcBiNgo(p, npcId, menuId, b3);
                        break;
                    }
                    case 60: {
                        Menu.NhanThuat(p, npcId, menuId, b3);
                        break;
                    }
                    case 61: {
                        Menu.npcHacAm(p, npcId, menuId, b3);
                        break;
                    }
                    case 62: {
                        Menu.npcshopcoin(p, npcId, menuId, b3);
                        break;
                    }
                    case 63: {
                        Menu.npcshopjarai(p, npcId, menuId, b3);
                        break;
                    }
                    case 64: {
                        Menu.npcdaide(p, npcId, menuId, b3);
                        break;
                    }
                    case 65: {
                        Menu.BXH(p, npcId, menuId, b3);
                        break;
                    }
                    case 66: {
                        Menu.npcloidien(p, npcId, menuId, b3);
                        break;
                    }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        Menu.doMenuArray(p, new String[]{"Thông tin", "Luật chơi"});
                        break;
                    case 93:
                        if (menuId == 0) {
                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay vip", "Tham gia đi, xem luật làm gì");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay thường", "Tham gia đi xem luật lm gì");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p, menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Chức năng này đang được cập nhật");
                        break;
                    }
                }
            } else if (p.typemenu == npcId) {
                switch (p.typemenu) {
                    case 0:
                        Menu.npcKanata(p, npcId, menuId, b3);
                        break;
                    case 1:
                        Menu.npcFuroya(p, npcId, menuId, b3);
                        break;
                    case 2:
                        Menu.npcAmeji(p, npcId, menuId, b3);
                        break;
                    case 3:
                        Menu.npcKiriko(p, npcId, menuId, b3);
                        break;
                    case 4:
                        Menu.npcTabemono(p, npcId, menuId, b3);
                        break;
                    case 5:
                        Menu.npcKamakura(p, npcId, menuId, b3);
                        break;
                    case 6:
                        Menu.npcKenshinto(p, npcId, menuId, b3);
                        break;
                    case 7:
                        Menu.npcUmayaki_Lang(p, npcId, menuId, b3);
                        break;
                    case 8:
                        Menu.npcUmayaki_Truong(p, npcId, menuId, b3);
                        break;
                    case 9: {
                        Menu.npcToyotomi(p, npcId, menuId, b3);
                        break;
                    }
                    case 10: {
                        Menu.npcOokamesama(p, npcId, menuId, b3);
                        break;
                    }
                    case 11: {
                        Menu.npcKazeto(p, npcId, menuId, b3);
                        break;
                    }
                    case 12: {
                        Menu.npcTajima(p, npcId, menuId, b3);
                        break;
                    }
                    case 16: {
                        Menu.npcThayLoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 18: {
                        Menu.npcRei(p, npcId, menuId, b3);
                        break;
                    }
                    case 19: {
                        Menu.npcKirin(p, npcId, menuId, b3);
                        break;
                    }
                    case 20: {
                        Menu.npcSoba(p, npcId, menuId, b3);
                        break;
                    }
                    case 21: {
                        Menu.npcSunoo(p, npcId, menuId, b3);
                        break;
                    }
                    case 22: {
                        Menu.npcGuriin(p, npcId, menuId, b3);
                        break;
                    }
                    case 23: {
                        Menu.npcMatsurugi(p, npcId, menuId, b3);
                        break;
                    }
                    case 24: {
                        Menu.npcOkanechan(p, npcId, menuId, b3);
                        break;
                    }
                    case 25: {
                        Menu.npcRikudou(p, npcId, menuId, b3);
                        break;
                    }
                    case 26: {
                        Menu.npcGoosho(p, npcId, menuId, b3);
                        break;
                    }
                    case 27: {
                        Menu.npcTruCoQuan(p, npcId, menuId, b3);
                        break;
                    }
                    case 28: {
                        Menu.npcShinwa(p, npcId, menuId, b3);
                        break;
                    }
                    case 29: {
                        Menu.npcChiHang(p, npcId, menuId, b3);
                        break;
                    }
                    case 30: {
                        Menu.npcRakkii(p, npcId, menuId, b3);
                        break;
                    }
                    case 31: {
                        Menu.npcLongDen(p, npcId, menuId, b3);
                        break;
                    }
                    case 32: {
                        Menu.npcKagai(p, npcId, menuId, b3);
                        break;
                    }
                    case 33: {
                        Menu.npcTienNu(p, npcId, menuId, b3);
                        break;
                    }
                    case 34: {
                        Menu.npcCayThong(p, npcId, menuId, b3);
                        break;
                    }
                    case 35: {
                        Menu.npcOngGiaNoen(p, npcId, menuId, b3);
                        break;
                    }
                    case 36: {
                        Menu.npcVuaHung(p, npcId, menuId, b3);
                        break;
                    }
                    case 37: {
                        Menu.npcKanata_LoiDai(p, npcId, menuId, b3);
                        break;
                    }
                    case 38: {
                        Menu.npcAdmin(p, npcId, menuId, b3);
                        break;
                    }
                    case 39: {
                        Menu.npcRikudou_ChienTruong(p, npcId, menuId, b3);
                        break;
                    }
                    case 40: {
                        Menu.npcKagai_GTC(p, npcId, menuId, b3);
                        break;
                    }
                    case 42: {
                        Menu.npcXebeTong(p, npcId, menuId, b3);
                        break;
                    }
                    case 44: {
                        Menu.npcHienNhan(p, npcId, menuId, b3);
                        break;
                    }
                    case 45: {
                        Menu.npcBachhao(p, npcId, menuId, b3);
                        break;
                    }
                    case 46: {
                        Menu.Luyenbikiep(p, npcId, menuId, b3);
                        break;
                    }
                    case 47: {
                        Menu.npcChauBac(p, npcId, menuId, b3);
                        break;
                    }
                    case 48: {
                        Menu.npcDeTu(p, npcId, menuId, b3);
                        break;
                    }
                    case 49: {
                        Menu.Nangyy(p, npcId, menuId, b3);
                        break;
                    }
                    case 50: {
                        Menu.npcTsu(p, npcId, menuId, b3);
                        break;
                    }
                    case 51: {
                        Menu.npcATMLTD(p, npcId, menuId, b3);
                        break;
                    }
                    case 52: {
                        Menu.npcNangBua(p, npcId, menuId, b3);
                        break;
                    }
                    case 53: {
                        Menu.npcNangNhan(p, npcId, menuId, b3);
                        break;
                    }
                    case 54: {
                        Menu.NangBoiDay(p, npcId, menuId, b3);
                        break;
                    }
                    case 56: {
                        Menu.npcdoiqua(p, npcId, menuId, b3);
                        break;
                    }
                    case 57: {
                        Menu.npcCocLTD(p, npcId, menuId, b3);
                        break;
                    }
                    case 58: {
                        Menu.npcDoiNgoc(p, npcId, menuId, b3);
                        break;
                    }
                    case 59: {
                        Menu.npcBiNgo(p, npcId, menuId, b3);
                        break;
                    }
                    case 60: {
                        Menu.NhanThuat(p, npcId, menuId, b3);
                        break;
                    }
                    case 61: {
                        Menu.npcHacAm(p, npcId, menuId, b3);
                        break;
                    }
                    case 62: {
                        Menu.npcshopcoin(p, npcId, menuId, b3);
                        break;
                    }
                    case 63: {
                        Menu.npcshopjarai(p, npcId, menuId, b3);
                        break;
                    }
                    case 64: {
                        Menu.npcdaide(p, npcId, menuId, b3);
                        break;
                    }
                    case 65: {
                        Menu.BXH(p, npcId, menuId, b3);
                        break;
                    }
                    case 66: {
                        Menu.npcloidien(p, npcId, menuId, b3);
                        break;
                    }
                    case 92:
                        p.typemenu = menuId == 0 ? 93 : 94;
                        doMenuArray(p, new String[]{"Thông tin", "Luật chơi"});
                        break;
                    case 93:
                        if (menuId == 0) {

                            Server.manager.rotationluck[0].luckMessage(p);
                        } else if (menuId == 1) {

                            Server.manager.sendTB(p, "Vòng xoay vip", "Tham gia đi, xem luật làm gì");
                        }
                        break;
                    case 94:
                        if (menuId == 0) {
                            Server.manager.rotationluck[1].luckMessage(p);
                        } else if (menuId == 1) {
                            Server.manager.sendTB(p, "Vòng xoay thường", "Tham gia đi xem luật lm gì");
                        }
                    case 95:
                        break;
                    case 120: {
                        if (menuId > 0 && menuId < 7) {
                            Admission.Admission(p, (byte) menuId);
                        }
                    }
                    default: {
                        Service.chatNPC(p, (short) npcId, "Chức năng này đang được cập nhật");
                        break;
                    }
                }
            } else {
                switch (p.typemenu) {
                    case -125: {
                        Menu.doiGiayVun(p, npcId, menuId, b3);
                        break;
                    }
                    case 92: {
                        switch (menuId) {
                            case 0: {
                                Server.manager.rotationluck[0].luckMessage(p);
                                break;
                            }
                            case 1: {
                                Server.manager.rotationluck[1].luckMessage(p);
                                break;
                            }
                        }
                        break;
                    }
                    //Send xu
                    case 125:
                        if (p.id != 1) {
                            Service.chatNPC(p, (short) npcId, "Bạn Không Có Quyền");
                            break;
                        } else {
                            Service.sendInputDialog(p, (short) 55, "Nhập IGAME người nhận:");
                            break;
                        }
                    //Send Lượng
                    case 126:
                        if (p.id != 1) {
                            Service.chatNPC(p, (short) npcId, "Bạn Không Có Quyền");
                            break;
                        } else {
                            Service.sendInputDialog(p, (short) 57, "Nhập IGAME người nhận:");
                            break;
                        }
                    //Send Yên    
                    case 127:
                        if (p.id != 1) {
                            Service.chatNPC(p, (short) npcId, "Bạn Không Có Quyền");
                            break;
                        } else {
                            Service.sendInputDialog(p, (short) 59, "Nhập IGAME người nhận:");
                            break;
                        }

                    //Send Item
                    case 128:
                        if (p.id != 1) {
                            Service.chatNPC(p, (short) npcId, "Bạn Không Có Quyền");
                            break;
                        } else {
                            Service.sendInputDialog(p, (short) 61, "Nhập IGAME người nhận:");
                            break;
                        }
                    //Send Mess
                    case 129:
                        if (p.id != 1) {
                            Service.chatNPC(p, (short) npcId, "Bạn Không Có Quyền");
                            break;
                        } else {
                            Service.sendInputDialog(p, (short) 64, "Nhập IGAME người nhận:");
                            break;
                        }
                    //Mảnh top vk    
                    case 839: {
                        Menu.menuDoiVK(p, npcId, menuId, b3);
                        break;
                    }
                    case 9998: {
                        Menu.menuVHDATM(p, npcId, menuId, b3);
                        break;
                    }
                    case 9999: {
                        Menu.menuAdmin(p, npcId, menuId, b3);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            p.typemenu = 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ms != null) {
                ms.cleanup();
            }
        }
    }

    public static void npctieuvien(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                } else if (p.c.quantityItemyTotal(454) >= 20 || p.c.level >= 60 || p.luong >= 500) {
                    if (p.c.level < 60) {
                        p.conn.sendMessageLog("Cấp độ của ngươi chưa đạt 60");
                        return;
                    }
                    if (p.luong < 500) {
                        p.conn.sendMessageLog("Không đủ lượng");
                        return;
                    }
                    if (p.c.quantityItemyTotal(454) < 20) {
                        p.conn.sendMessageLog("Ngươi không đủ chuyển tinh thạch");
                        return;
                    } else {
                        Item itemUp = new Item();
                        itemUp.id = (short) (396 + GameSrc.PickClass((byte) p.c.get().nclass));
                        itemUp.quantity = 1;
                        itemUp.isExpires = false;
                        itemUp.isLock = true;
                        p.c.removeItemBags(454, 20);
                        p.upluongMessage(-500);
                        p.c.addItemBag(false, itemUp);
                        if (itemUp.id == (short) (396 + GameSrc.PickClass((byte) p.c.get().nclass))) {
                            switch (Util.nextInt(6)) {
                                case 1: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option option33 = new Option(82, Util.nextInt(300, 600));
                                            itemUp.options.add(option33);
                                            break;
                                        }
                                        case 2: {
                                            Option option32 = new Option(83, Util.nextInt(300, 600));
                                            itemUp.options.add(option32);
                                            break;
                                        }
                                    }
                                }
                                case 2: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option option3 = new Option(95, Util.nextInt(20, 50));
                                            itemUp.options.add(option3);
                                            break;
                                        }
                                        case 2: {
                                            Option option31 = new Option(84, Util.nextInt(30, 70));
                                            itemUp.options.add(option31);
                                            break;
                                        }
                                    }
                                }
                                case 3: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option op = new Option(97, Util.nextInt(20, 50));
                                            itemUp.options.add(op);
                                            break;
                                        }
                                        case 2: {
                                            Option op2 = new Option(86, Util.nextInt(10, 60));
                                            itemUp.options.add(op2);
                                            break;
                                        }
                                    }
                                }
                                case 4: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option op3 = new Option(80, Util.nextInt(50, 100));
                                            itemUp.options.add(op3);
                                            break;
                                        }
                                        case 2: {
                                            Option option3 = new Option(96, Util.nextInt(20, 50));
                                            itemUp.options.add(option3);
                                            break;
                                        }
                                    }
                                }
                                case 5: {
                                    switch (Util.nextInt(1, 3)) {
                                        case 1: {
                                            Option op = new Option(88, Util.nextInt(300, 500));
                                            itemUp.options.add(op);
                                            break;
                                        }
                                        case 2: {
                                            Option op2 = new Option(89, Util.nextInt(300, 500));
                                            itemUp.options.add(op2);
                                            break;
                                        }
                                        case 3: {
                                            Option op1 = new Option(90, Util.nextInt(300, 500));
                                            itemUp.options.add(op1);
                                            break;
                                        }
                                    }
                                }
                                case 6: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option op1 = new Option(81, Util.nextInt(50, 70));
                                            itemUp.options.add(op1);
                                            break;
                                        }
                                        case 2: {
                                            Option option31 = new Option(92, Util.nextInt(10, 50));
                                            itemUp.options.add(option31);
                                            break;
                                        }
                                    }
                                }
                                case 7: {
                                    switch (Util.nextInt(1, 2)) {
                                        case 1: {
                                            Option option32 = new Option(87, Util.nextInt(200, 400));
                                            itemUp.options.add(option32);
                                            break;
                                        }
                                        case 2: {
                                            Option opz = new Option(94, Util.nextInt(5, 15));
                                            itemUp.options.add(opz);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.ItemBody[15] == null) {
                    Service.chatNPC(p, (short) npcid, "Hãy mang Bí Kíp vào");
                    return;
                }
                if (p.c.ItemBody[15].upgrade >= 10) {
                    Service.chatNPC(p, (short) npcid, "Đã nâng đến cấp độ tối đa");
                    return;
                }
                Service.startYesNoDlg(p, (byte) 14, "Bạn có muốn nâng cấp " + ItemTemplate.ItemTemplateId(p.c.ItemBody[15].id).name + " với " + GameSrc.coinUpBK[p.c.ItemBody[15].upgrade] + " yên hoặc xu với tỷ lệ thành công là " + GameSrc.percentUpBK[p.c.ItemBody[15].upgrade] + "% không?");
                break;
            }
            case 2: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Luyện Bí Kíp: Để luyên Bí Kíp ngươi cần 20 viên chuyển tinh thạch và 500 lượng cho 1 lần luyện" + "\n"
                        + "- Cấp độ tối đa khi nâng cấp là 10");
                break;
            }
        }
    }

  public static void npcAdmin(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException, InterruptedException {
        switch (menuId) {
            
            case 0: {
             if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.coin > 0 ) {
                    p.conn.sendMessageLog("con đã nhận rồi . tham quá đel tốt nhe con");
                    return;
                }
                Server.manager.sendTB(p, "Hướng dẫn", "- con hãy mạng coin của ta mà đi mua đồ đi nhé " + "\n"
                        + "- còn mua ở đâu thì : "
                        + "\n 1 là con lên web shop tại nsoblow.com"
                        + "\n 2 là con chạy qua bên trái 1 tý là thấy nhé"
                        + "\n giờ thì đếm 1 đến 10s ta sẽ đá con ra ngoài để cộng coin nhé bye");
                p.c.coin += 1;
                p.coin += 180000;
                SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                Service.chatKTG( "Hệ Thống " +"Wao npc BLOW vừa tặng "+ p.c.name + " 180k coin . mng hãy đến npc BLOW nhận đi nào");
                int TimeSeconds =10 ;
                    while (TimeSeconds > 0) {
                            TimeSeconds--;
                            Thread.sleep(1000);
                            }
                            Client.gI().kickSession(p.conn);
            break;
                    }
            
            case 1: {
                Locale localeEN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeEN);
                Server.manager.sendTB(p, "NsoBlow",
                        " - TÀI KHOẢN : " + p.username
                        + "\n+ tên nhân vật : " + p.c.name
                        + "\n+ Level : " + p.c.level
                        + "\n+ Hp : " + en.format(p.c.getMaxHP())
                        + "\n+ Mp : " + en.format(p.c.getMaxMP())
                        + "\n- TẤN CÔNG : " + en.format(p.c.dameMin()) + " - " + en.format(p.c.dameMax())
                        + "\n+ số hành trang có  : " + p.c.maxluggage
                        + "\n+ coin : " + en.format(p.coin)
                        + "\n+ Lượng : " + en.format(p.luong)
                        + "\n+ Xu : " + en.format(p.c.xu)
                        + "\n+ Yên : " + en.format(p.c.yen)
                        + "\n - TIỀN NĂNG\n"
                        + "    + Sức mạnh : " + en.format(p.c.potential0)
                        + "\n    + Thân pháp : " + en.format(p.c.potential1)
                        + "\n    + Thể lực : " + en.format(p.c.potential2)
                        + "\n    + Charka : " + en.format(p.c.potential3)
                        + "\n+ Chính Xác : " + en.format(p.c.Exactly())
                        + "\n+ Né : " + en.format(p.c.Miss())
                );
                break;
            }
            
            case 2: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.isDiemDanh == 0) {
                    if (p.status == 1) {
                        p.upluongMessage(250L);
                        p.c.luongTN += 250L;
                    } else {
                        p.upluongMessage(50000L);
                    }
                    p.c.isDiemDanh = 1;
                    Service.chatNPC(p, Short.valueOf(npcid), "Điểm danh thành công, con nhận được 50000 lượng.");
                    break;
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay con đã điểm danh rồi, hãy quay lại vào ngày hôm sau nha!");
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.isQuaHangDong == 1) {
                    Service.chatNPC(p, (short) npcid, "Con đã nhận thưởng hôm nay rồi!");
                    return;
                }

                if (p.c.countHangDong >= 2) {
                    if (p.status == 1) {
                        p.upluongMessage(750L);
                        p.c.luongTN += 750;
                    } else {
                        p.upluongMessage(10000L);
                    }
                    p.c.isQuaHangDong = 1;
                    Service.chatNPC(p, (short) npcid, "Nhận quà hoàn thành hang động thành công, con nhận được 10000 lượng.");
                } else if (p.c.countHangDong < 2) {
                    Service.chatNPC(p, (short) npcid, "Con chưa hoàn thành đủ 2 lần đi hang động, hãy hoàn thành đủ 2 lần và quay lại gặp ta đã nhận thường");
                }
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                if (p.c.getBagNull() < 6) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                    return;
                }

                if (p.c.level == 1) {
                    p.updateExp(Level.getMaxExp(10));
                    if (p.status == 1) {
                        p.upluongMessage(100000L);
                        p.c.upxuMessage(25000000L);
                        p.c.upyenMessage(25000000L);
                        p.c.luongTN += 10000;
                        p.c.yenTN += 50000000;
                        p.c.xuTN += 50000000;
                        p.c.addItemBag(false, ItemTemplate.itemDefault(222, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    } else {
                        p.upluongMessage(20000L);
                        p.c.upxuMessage(50000000L);
                        p.c.upyenMessage(50000000L);
                        p.c.addItemBag(false, ItemTemplate.itemDefault(222, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(540, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, true));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                        p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    }
                    Service.chatNPC(p, (short) npcid, "Con đã nhận quà tân thủ thành công, chúc con trải nghiệm game vui vẻ.");
                } else {
                    Service.chatNPC(p, (short) npcid, "Con đã nhận quà tân thủ trước đó rồi, không thể nhận lại lần nữa!");
                }
                break;
            }
            case 5: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.level == 1) {
                    p.conn.sendMessageLog("Không thể thực hiện thao tác này..");
                    return;
                }
                if (p.c.get().exptype == 1) {
                    p.c.get().exptype = 0;
                    p.sendAddchatYellow("Đã tắt nhận exp.");
                } else {
                    p.c.get().exptype = 1;
                    p.sendAddchatYellow("Đã bật nhận exp.");
                }
                break;
            }
            case 6: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (p.luong < 2000000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng mới có thể nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(840) < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con phải sở hữu mắt 11 để nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(848) < 5000) {
                            p.conn.sendMessageLog("Bạn phải thu thập đủ 5000 mảnh ghép để cho Obito xem");
                            break;
                        }
                        if (p.c.quantityItemyTotal(916) < 100) {
                            p.conn.sendMessageLog("Bạn phải có đủ 100 viên đá danh vọng cấp 12");
                            break;
                        }
                        p.c.removeItemBags(848, 5000);
                        p.c.removeItemBags(840, 1);
                        p.c.removeItemBags(916, 100);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Bạn đã thành công khiến Obito thức tỉnh Mangekyo Sharingan nhận được phần quà là mắt cấp 12");
                        Item itemup = ItemTemplate.itemDefault(846);
                        itemup.upgrade = (byte) 12;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 1: {
                        if (p.luong < 2000000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng mới có thể nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(846) < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con phải cất mắt 12 vào hành trang để nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(982) < 10000) {
                            p.conn.sendMessageLog("Bạn phải thu thập đủ 10000 mảnh tinh vân");
                            break;
                        }
                        if (p.c.quantityItemyTotal(917) < 100) {
                            p.conn.sendMessageLog("Bạn phải có đủ 100 viên đá danh vọng cấp 13");
                            break;
                        }
                        if (Util.nextInt(100) <= 70) {
                            p.c.removeItemBags(982, 999);
                            p.c.removeItemBags(917, 10);
                            p.upluongMessage(-1000000L);
                            p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                            break;
                        }
                        p.c.removeItemBags(982, 10000);
                        p.c.removeItemBags(846, 1);
                        p.c.removeItemBags(917, 100);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Chúc mừng con đã nâng thành công mắt cấp 13");
                        Item itemup = ItemTemplate.itemDefault(841);
                        itemup.upgrade = (byte) 13;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 2: {
                        if (p.luong < 3000000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 3 triệu lượng mới có thể nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(841) < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con phải cất mắt 13 vào hành trang để nâng cấp");
                            break;
                        }
                        if (p.c.quantityItemyTotal(982) < 20000) {
                            p.conn.sendMessageLog("Bạn phải thu thập đủ 20000 mảnh tinh vân");
                            break;
                        }
                        if (p.c.quantityItemyTotal(918) < 100) {
                            p.conn.sendMessageLog("Bạn phải có đủ 100 viên đá danh vọng cấp 14");
                            break;
                        }
                        if (Util.nextInt(100) <= 70) {
                            p.c.removeItemBags(982, 1999);
                            p.c.removeItemBags(918, 10);
                            p.upluongMessage(-1500000L);
                            p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                            break;
                        }
                        p.c.removeItemBags(982, 20000);
                        p.c.removeItemBags(841, 1);
                        p.c.removeItemBags(918, 100);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Chúc mừng con đã nâng thành công mắt cấp 14");
                        Item itemup = ItemTemplate.itemDefault(838);
                        itemup.upgrade = (byte) 14;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3: {
                        Server.manager.sendTB(p, "Nâng Cấp Mắt", "Bạn phải gom đủ nguyên liệu để nâng cấp"
                                + "\n>Nâng Mắt 12<"
                                + "\n-Cần 5000 mảnh ghép Rin bị Kakashi xiên"
                                + "\n-Cần 2 triệu lượng"
                                + "\n-Cần mắt 11"
                                + "\n-Cần 100 viên đá danh vọng cấp 12"
                                + "\n-Sau khi nâng cấp mắt 12 sẽ bị KHÓA"
                                + "\n-Mắt 13 cần có mắt 12"
                                + "\n-2 triệu lượng"
                                + "\n-100 đá danh vọng cấp 13"
                                + "\n-10000 đá tinh vân"
                                + "\n-Tỷ lệ nâng cấp mắt 13 là 30%: Xịt sẽ mất 50% lượng, 999 đá tinh vân và 10 đá danh vọng cấp 13"
                                + "\n-Mắt 14 cần có mắt 13"
                                + "\n-3 triệu lượng"
                                + "\n-100 đá danh vọng cấp 14"
                                + "\n-20000 đá tinh vân"
                                + "\n-Tỷ lệ nâng cấp mắt 14 là 30%: Xịt sẽ mất 50% lượng, 1999 đá tinh vân và 10 đá danh vọng cấp 14");
                        break;
                    }
                }
                break;
            }
            case 7: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.clone == null) {
                    Service.chatNPC(p, (short) npcid, "Con không có phân thân để sử dụng chức năng này.");
                    return;
                }
                Service.startYesNoDlg(p, (byte) 5, "Sau khi lựa chọn, tất cả dữ liệu như trang bị, thú cưỡi, điểm,... của phân thân sẽ bị reset về ban đầu. Hãy chắc chắn rằng bạn đã tháo toàn bộ trang bị của phân thân và xác nhận muốn reset.");
                break;
            }
            case 8: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Vừa vào chơi, hãy đến chỗ ta nhận quà tân thủ bao gồm: 50tr xu, 20k lượng, 50tr yên \n- Mỗi ngày con được điềm danh hàng ngày 1 lần và nhận 50000 lượng \n- Nếu mỗi ngày hoàn thành hang động đủ 2 lần con hãy đến chỗ ta và Nhận quà hang động để nhận 10000 lượng\n\n** Lưu ý, nếu là tài khoản trải nghiệm, con chỉ có thể nhận được 1 nửa phần thường từ ta.");
                break;
            }
        }
    }


    public static void npcHoadao(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.quantityItemyTotal(646) < 1) {
                    Service.chatNPC(p, (short) npcid, "Con không có Bùa May mắn để Xin Lộc nhé");
                    return;
                } else {
                    if (p.c.getBagNull() == 0) {
                        p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                        return;
                    }
                    short[] arId = new short[]{12, 12, 12, 8, 9, 8, 9, 275, 276, 277, 278, 275, 276, 277, 278, 548, 12, 548, 381, 382, 381, 382, 381, 682, 682, 682, 228, 227, 226, 225, 224, 223, 222, 283, 436, 438, 437, 436, 437, 419, 403, 419, 403, 407, 407, 12, 254, 255, 256, 12, 254, 255, 256, 7, 8, 9, 436, 437, 438, 682, 383, 382, 381, 222, 223, 224, 225, 226, 227, 228, 251, 308, 309, 548, 275, 276, 277, 278, 539, 540, 674, 695, 696, 697, 698, 699, 674, 700, 701, 702, 703, 704, 733, 734, 735, 736, 737, 738, 739, 674, 740, 741, 760, 761, 762, 674, 763, 764, 765, 766, 767, 768, 674, 695, 696, 697, 698, 699, 674, 700, 701, 702, 703, 704, 733, 734, 735, 736, 737, 738, 739, 674, 740, 741, 760, 761, 762, 674, 763, 764, 765, 766, 767, 768, 674, 695, 696, 697, 698, 699, 674, 700, 701, 702, 703, 704, 733, 734, 735, 736, 737, 738, 739, 674, 740, 741, 760, 761, 762, 674, 763, 764, 765, 766, 767, 768, 289, 290, 291, 289, 290, 291, 289, 290, 291};
                    short idI = arId[Util.nextInt(arId.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = false;
                    itemup.isExpires = true;
                    itemup.expires = Util.TimeDay(7);
                    p.c.removeItemBags(646, 1);
                    p.c.addItemBag(false, itemup);
                    p.sendAddchatYellow("Bạn nhận được " + itemup);
                    p.updateExp(10000000L);
                }
                break;
            }

            case 1: {
                Server.manager.sendTB(p, "Hướng Dẫn", "Bạn cần 1 Bùa May Mắn để Xin Lộc Đầu Xuân và sẽ nhận được EXP và những món quà bất ngờ.");
                break;
            }
        }
    }

    public static void menuAdmin(Player p, byte npcid, byte menuId, byte b3) {
        Player player;
        int i;
        switch (menuId) {
            
            case 0: {
                Service.sendInputDialog(p, (short) 41_0, "Nhập tên nhân vật :");
                break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 41_1, "Nhập tên nhân vật :");
                break;
            }
            
            case 2: {
                Service.sendInputDialog(p, (short) 9998, "Nhập số phút muốn bảo trì 0->10 (0: ngay lập tức)");
                break;
            }
            case 3: {
                Service.KhoaTaiKhoan(p);
                break;
            }
            case 4: {
                String chat = "MapID: " + p.c.mapid + " - X: " + p.c.get().x + " - Y: " + p.c.get().y;
                p.conn.sendMessageLog(chat);
                break;
            }
            case 5: {
                Service.AutoSaveData();
                p.sendAddchatYellow("Update thành công");
                break;
            }
            
            case 6: {
                Service.sendInputDialog(p, (short) 9996, "Nhập số xu muốn cộng (có thể nhập số âm)");
                break;
            }
            case 7: {
                Service.sendInputDialog(p, (short) 9995, "Nhập số lượng muốn cộng (có thể nhập số âm)");
                break;
            }
            case 8: {
                Service.sendInputDialog(p, (short) 9997, "Nhập số yên muốn cộng (có thể nhập số âm)");
                break;
            }
            case 9: {
                Service.sendInputDialog(p, (short) 9994, "Nhập số level muốn tăng (có thể nhập số âm)");
                break;
            }
            case 10: {
                Service.sendInputDialog(p, (short) 9993, "Nhập số tiềm năng muốn tăng (có thể nhập số âm)");
                break;
            }
            case 11: {
                Service.sendInputDialog(p, (short) 9992, "Nhập số kỹ năng muốn tăng (có thể nhập số âm)");
                break;
            }
            case 12: {
                SaveData saveData = new SaveData();
                saveData.player = p;
                Thread t1 = new Thread(saveData);
                t1.start();
                if (!Manager.isSaveData) {
                    player = null;
                    t1 = null;
                    saveData = null;
                }
                break;
            }
            case 13: {
                Service.sendInputDialog(p, (short) 9991, "Nhập nội dung");
                break;
            }
            case 14: {
                try {
                    Server.manager.sendTB(p, "Kiểm tra", "- Tổng số kết nối: " + Client.gI().conns_size() + "\n\n- Số người đăng nhập: " + Client.gI().players_size() + "\n\n- TỔNG SỐ NGƯỜI CHƠI THỰC TẾ: " + Client.gI().ninja_size());
                } catch (Exception var11) {
                    var11.printStackTrace();
                }
                break;
            }
            case 15: {
                synchronized (Client.gI().conns) {
                    for (i = 0; i < Client.gI().conns.size(); ++i) {
                        Session conn = (Session) Client.gI().conns.get(i);
                        if (conn != null) {
                            player = conn.player;
                            if (player != null) {
                                if (player.c == null) {
                                    Client.gI().kickSession(conn);
                                }
                            } else if (player == null) {
                                Client.gI().kickSession(conn);
                            }
                        }
                    }
                }

                p.conn.sendMessageLog("Dọn clone thành công!");
                break;
            }
            case 16: {
                synchronized (Client.gI().conns) {
                    for (i = 0; i < Client.gI().conns.size(); ++i) {
                        player = ((Session) Client.gI().conns.get(i)).player;
                        if (player != null && player != p) {
                            Client.gI().kickSession(player.conn);
                        }
                    }
                }

                p.conn.sendMessageLog("Dọn Session thành công!");
                break;
            }
            case 17: {
                Service.sendInputDialog(p, (short) 9990, "Nhập giá trị cần thay đổi");
                break;
            }
            case 18: {
                try {
                    String a = "";
                    int i2 = 1;
                    for (CheckRHB check : CheckRHB.checkRHBArrayList) {
                        a += i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                        i2++;
                    }
                    Server.manager.sendTB(p, "Check RHB", a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 19: {
                try {
                    ResultSet red = SQLManager.stat.executeQuery("SELECT * FROM `alert` WHERE `id` = 1;");
                    if (red != null && red.first()) {
                        String alert = red.getString("content");
                        Manager.alert.setAlert(alert);
                        red.close();
                    }
                    p.sendAddchatYellow("Cập nhật thông báo thành công");
                    Manager.alert.sendAlert(p);
                } catch (Exception e) {
                    p.conn.sendMessageLog("Lỗi cập nhật!");
                }
                break;
            }
            case 20: {
                try {
                    Manager.chatKTG("Người chơi " + p.c.name + " sử dụng Bánh khúc cây dâu tây đã nhận được " + ItemTemplate.ItemTemplateId(385).name);
                } catch (Exception e) {
                    p.conn.sendMessageLog("Lỗi cập nhật!");
                }
                break;
            }
            case 21: {
                try {
                    Manager.chatKTG("Người chơi " + p.c.name + " sử dụng Bánh khúc cây dâu tây đã nhận được " + ItemTemplate.ItemTemplateId(384).name);
                } catch (Exception e) {
                    p.conn.sendMessageLog("Lỗi cập nhật!");
                }
                break;
            }
            case 22: {
                Service.sendInputDialog(p, (short) 9989, "Nhập giá trị cần thay đổi");
                break;
            }
           
        }

    }

    public static void menuVHDATM(Player p, byte npcid, byte menuId, byte b3) throws InterruptedException {
        Player player;
        p.c.typemenu = 0;
        int i;
        switch (menuId) {
            case 0: {
                Locale localeEN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeEN);
                Server.manager.sendTB(p, "Huy Dat",
                        " - TÀI KHOẢN : " + p.username
                        + "\n+ tên nhân vật : " + p.c.name
                        + "\n+ Level : " + p.c.level
                        + "\n+ số hành trang có  : " + p.c.maxluggage
                        + "\n+ coin : " + en.format(p.coin) + " VHD "
                        + "\n+ Lượng : " + en.format(p.luong)
                        + "\n+ Xu : " + en.format(p.c.xu)
                        + "\n+ Yên : " + en.format(p.c.yen)
                        + "\n - TIỀN NĂNG\n"
                        + "    + Sức mạnh : " + en.format(p.c.potential0)
                        + "\n    + Thân pháp : " + en.format(p.c.potential1)
                        + "\n    + Thể lực : " + en.format(p.c.potential2)
                        + "\n    + Charka : " + en.format(p.c.potential3)
                        + "\n- TẤN CÔNG : " + en.format(p.c.dameMin()) + " - " + en.format(p.c.dameMax())
                        + "\n+ Hp : " + en.format(p.c.getMaxHP())
                        + "\n+ Mp : " + en.format(p.c.getMaxMP())
                        + "\n+ Chính Xác : " + en.format(p.c.Exactly())
                        + "\n+ Né : " + en.format(p.c.Miss())
                );
                break;

            }
            case 1: {

                Service.sendInputDialog(p, (short) 16, "Nhập tên nhân vật muốn tặng :");
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 9, "Nhập số COIN muốn đổi sang lượng .");
                break;
            }
            case 3: {
                Service.sendInputDialog(p, (short) 12, "Nhập số COIN muốn đổi sang xu.");
                break;
            }
            case 4: { //hành trang
                if (p.c.maxluggage >= 120) {
                    p.conn.sendMessageLog("Bạn chỉ có thể nâng tối đa 120 ô hành trang");
                    return;
                }
                if (p.c.levelBag < 4) {
                    p.conn.sendMessageLog("con hãy cắn túi vải cấp 4 rồi đến gặp ta");
                    return;
                }
                if (p.luong < 10000) {
                    p.conn.sendMessageLog("Bạn Cần 10000 Lượng");
                    return;
                } else {
                    p.c.maxluggage += 5;
                    p.upluongMessage(-10000L);
                    p.conn.sendMessageLog("Hành trang đã mở thêm 6 ô, Số Ô Hành Trang Của Bạn Là " + p.c.maxluggage + " Vui lòng thoát game vào lại để update hành trang ");
                    Service.chatNPC(p, (short) npcid, "nâng hàng trang thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds = 5;
                    while (TimeSeconds > 0) {
                        TimeSeconds--;
                        Thread.sleep(1000);
                    }
                    Client.gI().kickSession(p.conn);
                    break;
                }

            }
            case 5: {
                if (p.luong < 50000) {
                    p.conn.sendMessageLog("bạn cần tối thiểu 50k lượng");
                    break;
                }
                p.upluongMessage(-50000);
                Service.startYesNoDlg(p, (byte) 13, "Trùm sẽ xoá sạch rương đồ của chính mình?");
                Service.chatKTG("Trùm " + p.c.name + " đã xoá sạch rương đồ của chính mình.");
                break;
            }
            case 6: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.level < 30) {
                    p.conn.sendMessageLog("Dưới level 30 không thể thực hiện thao tác này..");
                    return;
                }
                if (p.c.get().exptype == 1) {
                    p.c.get().exptype = 0;
                    p.sendAddchatYellow("Đã tắt nhận exp.");
                } else {
                    p.c.get().exptype = 1;
                    p.sendAddchatYellow("Đã bật nhận exp.");
                }
                break;
            }
            case 7: {
                try {
                    ResultSet red = SQLManager.stat.executeQuery("SELECT `vetangluong` FROM `player` WHERE `id` = " + p.id + ";");
                    if (red != null && red.first()) {
                        int vetangluong = Integer.parseInt(red.getString("vetangluong"));
                        p.conn.sendMessageLog("Bạn đang có : " + vetangluong + " vé.");
                    }
                    p.flush();
                    red.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    p.conn.sendMessageLog("Lỗi đọc dữ liệu.");
                }

            }
            break;
        }
    }

    public static void doiGiayVun(Player p, byte npcid, byte menuId, byte b3) {
        switch (menuId) {
            case 0: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 250);
                p.c.addItemBag(false, ItemTemplate.itemDefault(252, false));
                break;
            }
            case 1: {
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 300);
                p.c.addItemBag(false, ItemTemplate.itemDefault(253, false));
                break;
            }
            case 2: {
                if (p.c.quantityItemyTotal(251) < 1500) {
                    p.conn.sendMessageLog("Bạn không có đủ 1500 mảnh giấy vụn");
                    return;
                }
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 1500);
                p.c.addItemBag(false, ItemTemplate.itemDefault(1007, false));
                break;
            }
            case 3: {
                if (p.c.quantityItemyTotal(251) < 900) {
                    p.conn.sendMessageLog("Bạn không có đủ 900 mảnh giấy vụn");
                    return;
                }
                p.c.removeItemBag(p.c.getIndexBagid(251, false), 900);
                p.c.addItemBag(false, ItemTemplate.itemDefault(1006, false));
                break;
            }
        }

    }

    //Đổi vk top
    public static void menuDoiVK(Player p, byte npcid, byte menuId, byte b3) {
        int[] ids = {0, 632, 633, 634, 635, 636, 637};
        switch (menuId) {
            case 0: {
                if (p.c.get().nclass == 0) {
                    p.conn.sendMessageLog("Bạn cần nhập học để sử dụng");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.c.quantityItemyTotal(839) < 300) {
                    p.conn.sendMessageLog("Bạn không có 300 mảnh Thần Binh");
                    return;
                }
                Item itemup = ItemTemplate.itemDefault(ids[p.c.get().nclass]);
                itemup.NhanVKTop(300);
                itemup.sys = p.c.getSys();
                itemup.upgradeNext((byte) 16);
                p.c.addItemBag(false, itemup);
                p.c.removeItemBags(839, 300);
                break;
            }
            case 1: {
                if (p.c.get().nclass == 0) {
                    p.conn.sendMessageLog("Bạn cần nhập học để sử dụng");
                    return;
                }
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.c.quantityItemyTotal(839) < 500) {
                    p.conn.sendMessageLog("Bạn không có 500 mảnh Thần Binh");
                    return;
                }
                Item itemup = ItemTemplate.itemDefault(ids[p.c.get().nclass]);
                itemup.NhanVKTop(500);
                p.c.addItemBag(false, itemup);
                itemup.sys = p.c.getSys();
                itemup.upgradeNext((byte) 16);
                p.c.removeItemBags(839, 500);
                break;
            }
        }
    }

    public static void npcKanata(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                p.requestItem(2);
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (!p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hiện tại con đã có gia tộc, không thể thành lập gia tộc được nữa.");
                            return;
                        }

                        if (p.luong < 100000) {
                            Service.chatNPC(p, (short) npcid, "Để thành lập gia tộc, con phải có ít nhất 100000 lượng trong người.");
                            return;
                        }
                        Menu.sendWrite(p, (short) 50, "Tên gia tộc");
                        return;
                    }
                    case 1: {
                        if (p.c.clan.clanName.isEmpty()) {
                            Service.chatNPC(p, (short) npcid, "Hiện tại con chưa có gia tộc, không thể mở Lãnh địa gia tộc.");
                            return;
                        }

                        LanhDiaGiaToc lanhDiaGiaToc = null;
                        if (p.c.ldgtID != -1) {
                            if (LanhDiaGiaToc.ldgts.containsKey(p.c.ldgtID)) {
                                lanhDiaGiaToc = LanhDiaGiaToc.ldgts.get(p.c.ldgtID);
                                if (lanhDiaGiaToc != null && lanhDiaGiaToc.map[0] != null && lanhDiaGiaToc.map[0].area[0] != null) {
                                    if (lanhDiaGiaToc.ninjas.size() <= 24) {
                                        p.c.mapKanata = p.c.mapid;
                                        p.c.tileMap.leave(p);
                                        lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                        return;
                                    } else {
                                        p.sendAddchatYellow("Số thành viên tham gia Lãnh Địa Gia Tộc đã đạt tối đa.");
                                    }
                                }
                            }
                        }
                        if (lanhDiaGiaToc == null) {
                            if (p.c.clan.typeclan < 3) {
                                Service.chatNPC(p, (short) npcid, "Con không phải tộc trưởng hoặc tộc phó, không thể mở Lãnh địa gia tộc.");
                                return;
                            }
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, "Hành trang của con không đủ chỗ trống để nhận Chìa khoá LDGT");
                                return;
                            }
                            ClanManager clan = ClanManager.getClanName(p.c.clan.clanName);
                            if (clan != null && p.c.clan.typeclan >= 3) {
                                if (clan.openDun <= 0) {
                                    Service.chatNPC(p, (short) npcid, "Số lần vào LDGT tuần này đã hết.");
                                    return;
                                }
                                if (clan.ldgtID != -1) {
                                    Service.chatNPC(p, (short) npcid, "Lãnh địa gia tộc của con đang được mở rồi.");
                                    return;
                                }
                                clan.openDun--;
                                clan.flush();
                                lanhDiaGiaToc = new LanhDiaGiaToc();

                                Item itemup = ItemTemplate.itemDefault(260);
                                itemup.quantity = 1;
                                itemup.expires = System.currentTimeMillis() + 600000L;
                                itemup.isExpires = true;
                                itemup.isLock = true;
                                if (p.c.quantityItemyTotal(260) > 0) {
                                    p.c.removeItemBags(260, p.c.quantityItemyTotal(260));
                                }
                                p.c.addItemBag(false, itemup);
                                p.c.ldgtID = lanhDiaGiaToc.ldgtID;
                                clan.ldgtID = lanhDiaGiaToc.ldgtID;
                                lanhDiaGiaToc.clanManager = clan;
                                p.c.mapKanata = p.c.mapid;
                                p.c.tileMap.leave(p);
                                lanhDiaGiaToc.map[0].area[0].EnterMap0(p.c);
                                return;
                            }

                        }
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog("Chức năng này không dành cho phân thân");
                            return;
                        }
                        if (p.c.quantityItemyTotal(262) < 200) {
                            Service.chatNPC(p, (short) npcid, "Con cần có 200 Đồng tiền gia tộc để đổi lấy Túi quà gia tộc.");
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        p.c.removeItemBags(262, 200);
                        Item itemup = ItemTemplate.itemDefault(263);
                        itemup.quantity = 1;
                        itemup.isLock = false;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3:
                    default: {
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật");
                        break;
                    }
                }
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog("Chức năng này không dành cho phân thân");
                    return;
                }

                //Trả thưởng
                if (b3 == 0) {
                    Service.evaluateCave(p.c);
                    return;
                }

                Cave cave = null;
                if (p.c.caveID != -1) {
                    if (Cave.caves.containsKey(p.c.caveID)) {
                        cave = Cave.caves.get(p.c.caveID);
                        if (cave != null && cave.map[0] != null && cave.map[0].area[0] != null) {
                            p.c.mapKanata = p.c.mapid;
                            p.c.tileMap.leave(p);
                            cave.map[0].area[0].EnterMap0(p.c);
                        }
                    }
                } else if (p.c.party != null && p.c.party.cave == null && p.c.party.charID != p.c.id) {
                    p.conn.sendMessageLog("Chỉ có nhóm trưởng mới được phép mở cửa hang động");
                    return;
                }

                if (cave == null) {
                    if (p.c.nCave <= 0) {
                        Service.chatNPC(p, (short) npcid, "Số lần vào hang động của con hôm nay đã hết, hãy quay lại vào ngày mai.");
                        return;
                    }
                    if (b3 == 1) {
                        if (p.c.level < 30 || p.c.level > 39) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 30 || p.c.party.aChar.get(i).level > 39) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(3);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(3);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 2) {
                        if (p.c.level < 40 || p.c.level > 49) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 40 || p.c.party.aChar.get(i).level > 49) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(4);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(4);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 3) {
                        if (p.c.level < 50 || p.c.level > 59) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 50 || p.c.party.aChar.get(i).level > 59) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(5);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(5);
                        }
                        p.c.caveID = cave.caveID;
                    }
                    if (b3 == 4) {
                        if (p.c.level < 60 || p.c.level > 69) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null && p.c.party.aChar.size() > 1) {
                            p.conn.sendMessageLog("Hoạt động này chỉ được phép 1 mình.");
                            return;
                        }
                        cave = new Cave(6);
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 1;
                    }
                    if (b3 == 5) {
                        if (p.c.level < 70 || p.c.level > 89) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 70 || p.c.party.aChar.get(i).level > 89) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(7);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(7);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 6) {
                        if (p.c.level < 90 || p.c.level > 150) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 90 || p.c.party.aChar.get(i).level > 150) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(9);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(9);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (b3 == 7) {
                        if (p.c.level < 130 || p.c.level > 150) {
                            p.conn.sendMessageLog("Trình độ không phù hợp");
                            return;
                        }
                        if (p.c.party != null) {
                            synchronized (p.c.party.aChar) {
                                for (byte i = 0; i < p.c.party.aChar.size(); ++i) {
                                    if (p.c.party.aChar.get(i).level < 130 || p.c.party.aChar.get(i).level > 151) {
                                        p.conn.sendMessageLog("Thành viên trong nhóm có trình độ không phù hợp");
                                        return;
                                    }
                                }
                            }
                        }
                        if (p.c.party != null) {
                            if (p.c.party.cave == null) {
                                cave = new Cave(8);
                                p.c.party.openCave(cave, p.c.name);
                            } else {
                                cave = p.c.party.cave;
                            }
                        } else {
                            cave = new Cave(8);
                        }
                        p.c.caveID = cave.caveID;
                        p.c.isHangDong6x = 0;
                    }
                    if (cave != null) {
                        p.c.nCave--;
                        p.c.pointCave = 0;

                        if (p.c.party != null && p.c.party.charID == p.c.id) {
                            if (p.c.party.aChar != null && p.c.party.aChar.size() > 0) {
                                synchronized (p.c.party.aChar) {
                                    Char _char;
                                    for (int i = 0; i < p.c.party.aChar.size(); i++) {
                                        if (p.c.party.aChar.get(i) != null) {
                                            _char = p.c.party.aChar.get(i);
                                            if (_char.id != p.c.id && p.c.tileMap.getNinja(_char.id) != null && _char.nCave > 0 && _char.caveID == -1 && _char.level >= 30 && (int) _char.level / 10 == cave.x) {
                                                _char.nCave--;
                                                _char.pointCave = 0;
                                                _char.caveID = p.c.caveID;
                                                _char.isHangDong6x = p.c.isHangDong6x;
                                                _char.mapKanata = _char.mapid;
                                                _char.countHangDong++;
                                                if (_char.pointUydanh < 5000) {
                                                    _char.pointUydanh += 5;
                                                }
                                                _char.tileMap.leave(_char.p);
                                                cave.map[0].area[0].EnterMap0(_char);
                                                _char.p.setPointPB(_char.pointCave);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                        p.c.mapKanata = p.c.mapid;
                        p.c.countHangDong++;
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 5;
                        }
                        p.c.tileMap.leave(p);
                        cave.map[0].area[0].EnterMap0(p.c);
                    }
                }
                p.setPointPB(p.c.pointCave);
                break;
            }
            case 3: {
//                Service.chatNPC(p, (short) npcid, "Chức năng đang bảo trì, vui lòng quay lại sau!");
//                return;
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }

                        if (p.c.party != null && p.c.party.charID != p.c.id) {
                            Service.chatNPC(p, (short) npcid, "Con không phải trưởng nhóm, không thể thực hiện gửi lời mời lôi đài cho người/nhóm khác");
                            return;
                        }

                        Service.sendInputDialog(p, (short) 2, "Nhập tên đối thủ của con");
                        return;
                    }
                    case 1: {
                        Service.sendLoiDaiList(p.c);
                        return;
                    }
                    case 2: {
                        String alert = "";

                        for (int i = 0; i < DunListWin.dunList.size(); ++i) {
                            int temp = i + 1;
                            alert = alert + temp + ". Phe " + ((DunListWin) DunListWin.dunList.get(i)).win + " thắng Phe " + ((DunListWin) DunListWin.dunList.get(i)).lose + ".\n";
                        }
                        Server.manager.sendTB(p, "Kết quả", alert);
                        return;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 4: {
                Service.chatNPC(p, (short) npcid, "Vũ khí của ta cực sắc bén. Nếu muốn tỷ thí thì cứ đến chỗ ta!");
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật");
                break;
            }
        }
    }

    public static void npcFuroya(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                switch (b3) {
                    case 0:
                        p.requestItem(21 - p.c.gender);
                        return;
                    case 1:
                        p.requestItem(23 - p.c.gender);
                        return;
                    case 2:
                        p.requestItem(25 - p.c.gender);
                        return;
                    case 3:
                        p.requestItem(27 - p.c.gender);
                        return;
                    case 4:
                        p.requestItem(29 - p.c.gender);
                        return;
                    default:
                        Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                        return;
                }
            case 1:
                Service.chatNPC(p, (short) npcid, "Tan bán quần áo, mũ nón, găng tay và giày siêu bền, siêu rẻ!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    static void npccasino(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 10000000) {
                            p.c.upxuMessage(-10000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(19000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 20.000.000 xu của Quy Lão Kame nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 10.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 10000000) {
                            p.c.upxuMessage(-10000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(19000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 20.000.000 xu của Quy Lão Kame nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 10.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 50000000) {
                            p.c.upxuMessage(-50000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(90000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 100.000.000 xu của Quy Lão Kame nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 50000000) {
                            p.c.upxuMessage(-50000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(90000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 100.000.000 xu của Quy Lão Kame nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.xu > 100000000) {
                            p.c.upxuMessage(-100000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(190000000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 200.000.000 xu của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 50.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.c.xu > 100000000) {
                            p.c.upxuMessage(-100000000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.c.upxuMessage(190000000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 200.000.000 xu của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 100.000.000 tr xu Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có xu mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 10000) {
                            p.upluongMessage(-10000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(19000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 20.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẽ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 10.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 10000) {
                            p.upluongMessage(-10000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(19000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 19.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 10.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 4: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 50000) {
                            p.upluongMessage(-50000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(90000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 100.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẽ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 50.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 50000) {
                            p.upluongMessage(-50000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(90000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 100.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 50.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }
            case 5: {
                switch (b3) {
                    case 0: {
                        if (p.luong > 100000) {
                            p.upluongMessage(-100000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(190000);
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa hốt 200.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 100.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (p.luong > 100000) {
                            p.upluongMessage(-100000);
                            int x = Util.nextInt(2);
                            if (x == 1) {
                                p.upluongMessage(190000);
                                Manager.chatKTG("Về Lẻ con nghiện " + p.c.name + " vừa hốt 200.000 lượng của Quy Lão Kame Luộc nhân phẩm tốt");
                                break;
                            } else {
                                Manager.chatKTG("Về Chẵn con nghiện " + p.c.name + " vừa bị Quy Lão Kame Luộc 100.000 lượng Còn cái nịt");
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Không có lượng mà đòi chơi");
                            break;
                        }
                        break;
                    }
                }
                break;
            }

            case 6: {
                switch (b3) {
                    case 0: {
                        if (p.c.quantityItemyTotal(632) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Vô Cực Kiếm");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(632, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Kiếm rồi");
                                final Item itemup = ItemData.itemDefault(397);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(632, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 1: {
                        if (p.c.quantityItemyTotal(633) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Thiên Hỏa Tiêu");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(633, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Tiêu rồi");
                                final Item itemup = ItemData.itemDefault(398);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(633, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 2: {
                        if (p.c.quantityItemyTotal(636) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Chiến Lục Đao");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(636, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Đao rồi");
                                final Item itemup = ItemData.itemDefault(401);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(636, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 3: {
                        if (p.c.quantityItemyTotal(637) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Hoàng Phong Phiến");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(637, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Quạt rồi");
                                final Item itemup = ItemData.itemDefault(402);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(637, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 4: {
                        if (p.c.quantityItemyTotal(634) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Táng Hồn Dao");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(634, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Kunai rồi");
                                final Item itemup = ItemData.itemDefault(399);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(634, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                    case 5: {
                        if (p.c.quantityItemyTotal(635) < 10) {
                            Service.chatNPC(p, (short) npcid, "Cần 10 Thái Dương Băng Thần Cung");
                            break;
                        } else if (p.luong < 10000) {
                            Service.chatNPC(p, (short) npcid, "Cần 10000 lượng");
                            break;
                        } else {
                            int tl = Util.nextInt(3);
                            if (tl != 1) {
                                Service.chatNPC(p, (short) npcid, "Số con đen như bản mặt con vậy");
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(635, 10);
                                break;
                            } else {
                                Service.chatNPC(p, (short) npcid, "Cuối cùng con cũng có bí kíp Cung rồi");
                                final Item itemup = ItemData.itemDefault(400);
                                p.upluongMessage(-10000);
                                p.c.removeItemBags(635, 10);
                                p.c.addItemBag(false, itemup);
                                break;
                            }
                        }
                    }
                }
            }
            default: {
                Server.manager.sendTB(p, "Hướng dẫn", "Khoắng cây bút viết thơ tặng bạn Chúc Tân Niên có vạn niềm vui Bao nhiêu vất vả đẩy lùi Thay vào là những ngọt bùi yêu thương Hôm nay là Tết Dương lịch đó Gửi lời chúc nhờ gió chuyển cho Mong mọi người hết sầu lo Bình an hạnh phúc chuyến đò nhân gian Một... hai... ba, cùng san sẻ Tết Ta nâng ly quên hết buồn đời Chúc cho cuộc sống tuyệt vời Tình bạn tri kỷ người ơi giữ gìn Hãy đặt những niềm tin yêu quý Sống chân thành, hoan hỷ mỗi ngày Thế sự có lắm đổi thay Tâm ta bất biến, thẳng ngay mà làm Gửi chúc người Việt Nam yêu dấu Năm Tân sửu phấn đấu mọi điều Làm những công việc mình yêu Để cho cuộc sống thêm nhiều bình yên -Tết 2022 Chúc Mọi Người May Mắn !!");
                break;
            }
        }
    }

    public static void npcCLXTCoin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 44_0_0, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 44_0_1, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXCoin check : CheckTXCoin.checkTXCoinArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 44_1_0, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 44_1_1, "Nhập số coin đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLCoin check : CheckCLCoin.checkCLCoinArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 2: {
                try {
                    synchronized (Server.LOCK_MYSQL) {
                        ResultSet red = SQLManager.stat.executeQuery("SELECT `coin` FROM `player` WHERE `id` = " + p.id + ";");
                        if (red != null && red.first()) {
                            p.coin = red.getInt("coin");
                            p.conn.sendMessageLog("Bạn đang có : " + p.coin + ". Hãy thoát ra vào lại để cập nhật coin mới nhất nếu nạp.");
                            break;
                        }
                    }
                } catch (SQLException var17) {
                    var17.printStackTrace();
                    p.conn.sendMessageLog("Lỗi.");
                }
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Đây là NPC chơi CLTX bằng coin."
                        + "\nMỗi lần đặt cược giá trị phải là bội số của 10 (20,30,40,...)\n"
                        + "Nếu may mắn chiến thắng bạn sẽ nhận được 1,9 số coin cược.\n"
                        + "Nếu thua bạn méo được gì.\n"
                        + "Để có coin chơi bạn cần lên web teamobi.cf nạp coin hoặc ib admin.\n"
                        + "Chúc Bạn Sớm Trở Thành Trùm VIP!");
                break;
            }
        }
    }

    public static void npcKame(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.sendRealLove != 0) {
                    Char toSend = Client.gI().getNinja(p.c.senderLove);

                    Player sendMsLove = Client.gI().getPlayer(toSend.p.username);
                    sendMsLove.c.senderLove = "kết hôn với " + p.c.name;
                    p.c.senderLove = "kết hôn với " + p.c.senderLove;
                    p.c.sendRealLove = 2;
                    sendMsLove.c.sendRealLove = 2;
                    Service.sendInfoPlayers(p, p);
                    Service.sendInfoPlayers(sendMsLove, sendMsLove);
                    Map ma = Manager.getMapid(169);
                    TileMap area;
                    int var8;
                    for (var8 = 0; var8 < ma.area.length; ++var8) {
                        area = ma.area[var8];
                        if (area.numplayers < ma.template.maxplayers) {
                            p.c.tileMap.leave(p);
                            area.EnterMap0WithXY(p.c, (short) 645, (short) 480);
                            sendMsLove.c.tileMap.leave(sendMsLove);
                            area.EnterMap0WithXY(sendMsLove.c, (short) 550, (short) 480);
                            Manager.serverChat("server ", sendMsLove.c.name + " đã kết hôn với " + p.c.name + " đến npc kame để tham dự nào !!!!");
                            return;
                        }
                    }

                    break;
                } else {
                    Service.sendInputDialog(p, (short) 1699, "Nhập tên người thương");
                    break;
                }
            }
            case 1: {
                Map ma = Manager.getMapid(169);
                TileMap area;
                int var8;

                for (var8 = 0; var8 < ma.area.length; ++var8) {
                    area = ma.area[var8];
                    if (area.numplayers < ma.template.maxplayers) {
                        p.c.tileMap.leave(p);
                        int random = (int) Util.nextInt(0, 11);
                        if (random == 0) {
                            area.EnterMap0WithXY(p.c, (short) 439, (short) 144);
                            return;
                        } else if (random == 1) {
                            area.EnterMap0WithXY(p.c, (short) 385, (short) 216);
                            return;
                        } else if (random == 2) {
                            area.EnterMap0WithXY(p.c, (short) 347, (short) 288);
                            return;
                        } else if (random == 3) {
                            area.EnterMap0WithXY(p.c, (short) 385, (short) 216);
                            return;
                        } else if (random == 4) {
                            area.EnterMap0WithXY(p.c, (short) 262, (short) 360);
                            return;
                        } else if (random == 5) {
                            area.EnterMap0WithXY(p.c, (short) 212, (short) 432);
                            return;
                        } else if (random == 6) {
                            area.EnterMap0WithXY(p.c, (short) 758, (short) 216);
                            return;
                        } else if (random == 7) {
                            area.EnterMap0WithXY(p.c, (short) 794, (short) 216);
                            return;
                        } else if (random == 8) {
                            area.EnterMap0WithXY(p.c, (short) 844, (short) 288);
                            return;
                        } else if (random == 9) {
                            area.EnterMap0WithXY(p.c, (short) 852, (short) 360);
                            return;
                        } else if (random == 10) {
                            area.EnterMap0WithXY(p.c, (short) 794, (short) 216);
                            return;
                        } else if (random == 11) {
                            area.EnterMap0WithXY(p.c, (short) 920, (short) 432);
                            return;
                        }
                    }
                }
            }
            break;
        }

    }

    public static void npcTsu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 4400, "Nhập số coin đặt (phải là bội số của 10) :");
                        return;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 4401, "Nhập số coin đặt (phải là bội số của 10) :");
                        return;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXCoin check : CheckTXCoin.checkTXCoinArrayList) {
                                a = a + i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                ++i2;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 4410, "Nhập số coin đặt (phải là bội số của 10) :");
                        return;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 4411, "Nhập số coin đặt (phải là bội số của 10) :");
                        return;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLCoin check : CheckCLCoin.checkCLCoinArrayList) {
                                a = a + i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                ++i2;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return;
            }
            case 2: {
                try {
                    Object e = Server.LOCK_MYSQL;
                    synchronized (e) {
                        ResultSet red = SQLManager.stat.executeQuery("SELECT `coin` FROM `player` WHERE `id` = " + p.id + ";");
                        if (red == null || !red.first()) {
                            return;
                        }
                        p.coin = red.getInt("coin");
                        p.conn.sendMessageLog("Bạn đang có : " + p.coin + ". Hãy thoát ra vào lại để cập nhật coin mới nhất.");
                        return;
                    }
                } catch (SQLException var17) {
                    var17.printStackTrace();
                    p.conn.sendMessageLog("Lỗi.");
                }
                return;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Đây là NPC chơi CLTX bằng coin.\nMỗi lần đặt cược giá trị phải là bội số của 10 (20,30,40,...)\nNếu may mắn chiến thắng bạn sẽ nhận được 1,9 số coin cược.\nNếu thua bạn méo được gì.\nĐể có coin chơi bạn hãy ib zalo của admin.\nChúc Bạn Ăn nên làm ra!");
                return;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Con phụ hồ được nhận lương chưa? Nhận rồi thì làm ván đi đợi gì nữa?");
            }
        }
    }

    public static void npcAmeji(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        p.requestItem(16);
                        break block0;
                    }
                    case 1: {
                        p.requestItem(17);
                        break block0;
                    }
                    case 2: {
                        p.requestItem(18);
                        break block0;
                    }
                    case 3: {
                        p.requestItem(19);
                        break block0;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        int type;
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level < 35) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cấp độ của con không đủ để nhận nhiệm vụ này");
                            return;
                        }
                        if (p.c.countTaskDanhVong < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Số lần nhận nhiệm vụ của con hôm nay đã hết");
                            return;
                        }
                        if (p.c.isTaskDanhVong == 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trước đó con đã nhận nhiệm vụ rồi, hãy hoàn thành đã nha");
                            return;
                        }
                        p.c.taskDanhVong[0] = type = DanhVongTemplate.randomNVDV();
                        p.c.taskDanhVong[1] = 0;
                        p.c.taskDanhVong[2] = DanhVongTemplate.targetTask(type);
                        p.c.isTaskDanhVong = 1;
                        --p.c.countTaskDanhVong;
                        if (p.c.isTaskDanhVong != 1) {
                            break block0;
                        }
                        String nv = "NHIỆM VỤ LẦN NÀY: \n" + String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]], p.c.taskDanhVong[1], p.c.taskDanhVong[2]) + "\n\n- Số lần nhận nhiệm vụ còn lại là: " + p.c.countTaskDanhVong;
                        Server.manager.sendTB(p, "Nhiệm vụ", nv);
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        if (p.c.taskDanhVong[1] < p.c.taskDanhVong[2]) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }
                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không đủ chỗ trống để nhận thưởng");
                            return;
                        }
                        int point = 50;
                        if (p.c.taskDanhVong[0] == 9) {
                            point = 50;
                        }
                        p.c.isTaskDanhVong = 0;
                        p.c.taskDanhVong = new int[]{-1, -1, -1, 0, p.c.countTaskDanhVong};
                        Item item = ItemTemplate.itemDefault(DanhVongTemplate.randomDaDanhVong(), false);
                        item.quantity = 1;
                        item.isLock = false;
                        if (p.c.pointUydanh < 5000) {
                            ++p.c.pointUydanh;
                        }
                        p.c.addItemBag(true, item);
                        int type = Util.nextInt(10);
                        if (p.c.avgPointDanhVong(p.c.getPointDanhVong(type))) {
                            int i = 0;
                            while (i < 10 && p.c.avgPointDanhVong(p.c.getPointDanhVong(type = i++))) {
                            }
                        }
                        p.c.plusPointDanhVong(type, point);
                        if (p.c.countTaskDanhVong % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 739 : 766, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(1, 2);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 740 : 767, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(1, 2);
                            p.c.addItemBag(true, itemUp);
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskDanhVong == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        Service.startYesNoDlg(p, (byte) 2, "Con có chắc chắn muốn huỷ nhiệm vụ lần này không?");
                        break;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.checkPointDanhVong(1)) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không đủ chỗ trống để nhận thưởng");
                                return;
                            }
                            Item item = ItemTemplate.itemDefault(685, true);
                            item.quantity = 1;
                            item.upgrade = 1;
                            item.isLock = true;
                            Option op = new Option(6, 1000);
                            item.options.add(op);
                            op = new Option(87, 500);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Con chưa đủ điểm để nhận mắt");
                        break;
                    }
                    case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Hãy đeo mắt vào người trước rồi nâng cấp nhé.");
                            return;
                        }
                        if (p.c.ItemBody[14] == null) {
                            return;
                        }
                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Mắt của con đã đạt cấp tối đa");
                            return;
                        }
                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa đủ điểm danh vọng để thực hiện nâng cấp");
                            return;
                        }
                        ItemTemplate data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 0, "Bạn có muốn nâng cấp " + data.name + " với " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " yên hoặc xu với tỷ lệ thành công là " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] + "% không?");
                        break;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.ItemBody[14] == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Hãy đeo mắt vào người trước rồi nâng cấp nhé.");
                            return;
                        }
                        if (p.c.ItemBody[14].upgrade >= 10) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Mắt của con đã đạt cấp tối đa");
                            return;
                        }
                        if (!p.c.checkPointDanhVong(p.c.ItemBody[14].upgrade)) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa đủ điểm danh vọng để thực hiện nâng cấp");
                            return;
                        }
                        ItemTemplate data = ItemTemplate.ItemTemplateId(p.c.ItemBody[14].id);
                        Service.startYesNoDlg(p, (byte) 1, "Bạn có muốn nâng cấp " + data.name + " với " + GameSrc.coinUpMat[p.c.ItemBody[14].upgrade] + " yên hoặc xu và " + GameSrc.goldUpMat[p.c.ItemBody[14].upgrade] + " lượng với tỷ lệ thành công là " + GameSrc.percentUpMat[p.c.ItemBody[14].upgrade] * 2 + "% không?");
                        break;
                    }
                    case 6: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        String nv = "- Hoàn thành nhiệm vụ. Hãy gặp Ameji để trả nhiệm vụ.\n- Hôm nay có thể nhận thêm " + p.c.countTaskDanhVong + " nhiệm vụ trong ngày.\n- Hôm nay có thể sử dụng thêm " + p.c.useDanhVongPhu + " Danh Vọng Phù để nhận thêm 5 lần làm nhiệm vụ.\n- Hoàn thành nhiệm vụ sẽ nhận ngẫu nhiên 1 viên đá danh vọng cấp 1-10.\n- Khi đủ mốc 100 điểm mỗi loại có thể nhận mắt và nâng cấp mắt.";
                        if (p.c.isTaskDanhVong == 1) {
                            nv = "NHIỆM VỤ LẦN NÀY: \n" + String.format(DanhVongTemplate.nameNV[p.c.taskDanhVong[0]], p.c.taskDanhVong[1], p.c.taskDanhVong[2]) + "\n\n" + nv;
                        }
                        Server.manager.sendTB(p, "Nhiệm vụ", nv);
                        break;
                    }
                }
                break;
            }
            case 2: {
                Service.chatNPC(p, Short.valueOf(npcid), "Ta bán các loại trang sức lấp lánh!");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcKiriko(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(7);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(6);
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTabemono(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                p.requestItem(9);
                break;
            case 1:
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                p.requestItem(8);
                break;
            case 2: {
                Service.chatNPC(p, (short) npcid, "3 đời nhà ta bán thức ăn chưa ai bị đau bụng cả!");
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "Đang trong thời gian tổng kết. Hiện tại không thể đăng ký.");
                            return;
                        }
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name) || ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký trước đó rồi");
                            return;
                        }
                        if (p.c.get().level >= 50 && p.c.get().level < 70) {
                            ThienDiaBangManager.diaBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankDiaBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký tham gia trang tài Địa bảng thành công.");
                        } else if (p.c.get().level >= 70) {
                            ThienDiaBangManager.thienBangList.put(p.c.name, new ThienDiaData(p.c.name, ThienDiaBangManager.rankThienBang++, 1));
                            Service.chatNPC(p, (short) npcid, "Con đã đăng ký tham gia tranh tài Thiên bảng thành công.");
                        } else {
                            Service.chatNPC(p, (short) npcid, "Trình độ của con không phù hợp để đăng ký tham gia tranh tài.");
                        }
                        break;
                    }
                    case 1: {
                        if (!ThienDiaBangManager.register) {
                            Service.chatNPC(p, (short) npcid, "Đang trong thời gian tổng kết. Hiện tại không thể thi đấu.");
                            return;
                        }
                        ArrayList<ThienDiaData> list = new ArrayList<>();
                        if (ThienDiaBangManager.diaBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.diaBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListDiaBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() < rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else if (ThienDiaBangManager.thienBangList.containsKey(p.c.name)) {
                            ThienDiaData rank = ThienDiaBangManager.thienBangList.get(p.c.name);
                            for (ThienDiaData data : ThienDiaBangManager.getListThienBang()) {
                                if (data != null) {
                                    if (rank.getRank() < 10 && (data.getRank() - rank.getRank()) < 20) {
                                        list.add(data);
                                    } else if (data.getRank() <= rank.getRank() & (rank.getRank() - data.getRank()) < 10) {
                                        list.add(data);
                                    }
                                }
                            }
                        } else {
                            Service.chatNPC(p, (short) npcid, "Con chưa đăng ký tham gia thi đấu.");
                            return;
                        }
                        Service.SendChinhPhuc(p, list);
                        return;
                    }
                    case 2: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.thienBangList.values()))) {
                            if (count < 11) {
                                res += "Hạng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "Thiên bảng", res);
                        return;
                    }
                    case 3: {
                        String res = "";
                        int count = 1;
                        for (ThienDiaData data : ThienDiaBangManager.getListSortAsc(new ArrayList<ThienDiaData>(ThienDiaBangManager.diaBangList.values()))) {
                            if (count < 11) {
                                res += "Hạng " + count + ": " + data.getName() + ".\n";
                                count++;
                            }
                        }
                        Server.manager.sendTB(p, "Địa bảng", res);
                        return;
                    }
                    case 4: {
                        ResultSet res = null;
                        try {
                            if (true) {//if(Manager.nhanquatdb == 0){
                                Service.chatNPC(p, (short) npcid, "Chỉ nhận quà được vào thứ 2.");
                                return;
                            }
                            if (p.c.rankTDB > 0) {
                                if (p.c.isGiftTDB == 1) {
                                    if (p.c.rankTDB > 20) {
                                        p.upluongMessage(500);
                                        p.c.upxuMessage(500000);
                                    } else {
                                        switch (p.c.rankTDB) {
                                            case 1: {
                                                if (p.c.getBagNull() < 10) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 10 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308, false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true, pl);

                                                pl = ItemTemplate.itemDefault(309, false);
                                                pl.quantity = 2;
                                                p.c.addItemBag(true, pl);

                                                p.c.addItemBag(false, ItemTemplate.itemDefault(540, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(540, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));

                                                p.c.addItemBag(false, ItemTemplate.itemDefault(384, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));

                                                p.upluongMessage(20000);
                                                p.c.upxuMessage(20000000);
                                                break;
                                            }
                                            case 2: {
                                                if (p.c.getBagNull() < 7) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 7 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                Item pl = ItemTemplate.itemDefault(308, false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true, pl);

                                                pl = ItemTemplate.itemDefault(309, false);
                                                pl.quantity = 1;
                                                p.c.addItemBag(true, pl);

                                                p.c.addItemBag(false, ItemTemplate.itemDefault(540, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));

                                                p.c.addItemBag(false, ItemTemplate.itemDefault(384, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));

                                                p.upluongMessage(10000);
                                                p.c.upxuMessage(10000000);
                                                break;
                                            }
                                            case 3: {
                                                if (p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 4 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(540, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                                                p.upluongMessage(5000);
                                                p.c.upxuMessage(5000000);
                                                break;
                                            }
                                            case 4:
                                            case 5:
                                            case 6:
                                            case 7:
                                            case 8:
                                            case 9:
                                            case 10: {
                                                if (p.c.getBagNull() < 4) {
                                                    Service.chatNPC(p, (short) npcid, "Con cần ít nhất 2 chỗ trống trong hành trang để nhận thưởng.");
                                                    return;
                                                }
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(539, false));
                                                p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                                                p.upluongMessage(3000);
                                                p.c.upxuMessage(3000000);
                                                break;
                                            }
                                            case 11:
                                            case 12:
                                            case 13:
                                            case 14:
                                            case 15:
                                            case 16:
                                            case 17:
                                            case 18:
                                            case 19:
                                            case 20: {
                                                p.upluongMessage(1000);
                                                p.c.upxuMessage(1000000);
                                                break;
                                            }

                                        }
                                    }
                                    p.c.isGiftTDB = 0;
                                } else {
                                    Service.chatNPC(p, (short) npcid, "Con đã nhận thưởng tuần rồi.");
                                    return;
                                }
                            } else {
                                Service.chatNPC(p, (short) npcid, "Tuần trước con chưa tham gia Thiên Địa bảng và chưa có rank, con chưa thể nhận thường.");
                                return;
                            }
                        } catch (Exception e) {
                            p.conn.sendMessageLog("Lỗi nhận thưởng, vui lòng thử lại sau!");
                            return;
                        } finally {
                            if (res != null) {
                                try {
                                    res.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    }
                    case 5: {
                        Server.manager.sendTB(p, "Hướng dẫn", "- Thiên Địa Bảng sẽ được mở hàng tuần. Bắt đầu từ thứ 2 và tổng kết vào chủ nhật.\n"
                                + "- Thiên Địa Bảng sẽ được mở đăng ký và chính phục từ 00h05' đến 23h45' hàng ngày. Mỗi ngày sẽ có 20p để tổng kết ngày, trong thời gian này sẽ không thể đăng ký và chinh phục\n"
                                + "- Trong thời gian tổng kết nếu chiến thắng trong Chinh phục sẽ không được tính rank."
                                + "- Vào ngày thường sẽ không giới hạn lượt thách đấu.\n"
                                + "- Vào Thứ 7 và Chủ Nhật mỗi Ninja sẽ có 5 lượt thách đấu, Thắng sẽ không bị mất lượt, thua sẽ bị trừ 1 lần thách đấu."
                                + "- Địa Bảng dành cho ninja từ cấp độ 50-69.\n"
                                + "- Thiên Bảng dành cho ninja từ cấp độ trên 70\n"
                                + "- Sau khi đăng ký thành công, hãy Chinh Phục ngay để giành lấy vị trí top đầu.\n"
                                + "- Mỗi lần chiến thắng, nếu vị trí của đối thủ trước bạn, bạn sẽ đổi vị trí của mình cho đối thủ, còn không vị trí của bạn sẽ được giữ nguyên.\n"
                                + "- Phần thưởng sẽ được trả thưởng vào mỗi tuần mới (Lưu ý: Hãy nhận thưởng ngay trong tuần mới đó, nếu sang tuần sau phần thưởng sẽ bị reset).\n\n"
                                + "- PHẦN THƯỞNG: \n"
                                + "Top 1: Hào quang Rank 1 + 2 Bánh Phong Lôi, 2 Bánh Băng Hoả, 2 Nấm x4, 3 Nấm x3, 1 Rương bạch ngân, 2 Bát bảo, 20,000 Lượng, 20,000,000 xu.\n\n"
                                + "Top 2: Hào quang Rank 2 + 1 Bánh Phong Lôi, 1 Bánh Băng Hoả, 1 Nấm x4, 2 Nấm x3, 1 Rương bạch ngân, 1 Bát bảo, 10,000 Lượng, 10,000,000 xu.\n\n"
                                + "Top 3: Hào quang Rank 3 + 1 Nấm x4, 1 Nấm x3, 2 Bát bảo, 5,000 Lượng, 5,000,000 xu.\n\n"
                                + "Top 4-10: 1 Nấm x3, 1 Bát bảo, 3,000 Lượng, 3,000,000 xu.\n\n"
                                + "Top 11-20: 1,000 Lượng, 1,000,000 xu.\n\n"
                                + "Còn lại: 500 Lượng, 500,000 xu.");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKamakura(Player p, byte npcid, byte menuId, byte b3) {
        try {
//            if (p.c.isNhanban) {
//                p.conn.sendMessageLog("Chức năng này không dành cho phân thân.");
//                return;
//            }
            switch (menuId) {
                case 0:
                    //p.requestItem(4);
                    switch (b3) {
                        case 0: {
                            Service.openMenuBox(p);
                            break;
                        }
                        case 1: {
                            Service.openMenuBST(p);
                            break;
                        }
                        case 2: {
                            Service.openMenuCaiTrang(p);
                            break;
                        }
                        case 3: {
                            //Tháo cải trang
                            p.c.caiTrang = -1;
                            Message m = new Message(11);
                            m.writer().writeByte(-1);
                            m.writer().writeByte(p.c.get().speed());
                            m.writer().writeInt(p.c.get().getMaxHP());
                            m.writer().writeInt(p.c.get().getMaxMP());
                            m.writer().writeShort(p.c.get().eff5buffHP());
                            m.writer().writeShort(p.c.get().eff5buffMP());
                            m.writer().flush();
                            p.conn.sendMessage(m);
                            m.cleanup();
                            Service.CharViewInfo(p, false);
                            p.endLoad(true);
                            break;
                        }
                    }
                    break;
                case 1:
                    if (p.c.tileMap.map.getXHD() != -1 || p.c.tileMap.map.LangCo() || p.c.tileMap.map.mapBossTuanLoc() || p.c.tileMap.map.mapLDGT() || p.c.tileMap.map.mapGTC() || p.c.tileMap.map.id == 111 || p.c.tileMap.map.id == 113) {
                        p.c.mapLTD = 22;
                    } else {
                        p.c.mapLTD = p.c.tileMap.map.id;
                    }
                    Service.chatNPC(p, (short) npcid, "Lưu toạ độ thành công! Khi chết con sẽ được vác xác về đây.");
                    break;
                case 2:
                    switch (b3) {
                        case 0:
                            if (p.c.level < 60) {
                                p.conn.sendMessageLog("Chức năng này yên cầu trình độ 60");
                                return;
                            }

                            Map ma = Manager.getMapid(139);
                            TileMap area;
                            int var8;
                            for (var8 = 0; var8 < ma.area.length; ++var8) {
                                area = ma.area[var8];
                                if (area.numplayers < ma.template.maxplayers) {
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                            }
                            return;
                        case 1:
                            Server.manager.sendTB(p, "Hướng dẫn", "- Hướng dẫn vùng đất ma quỷ");
                            return;
                        default:
                            return;
                    }
                case 3:
                    Service.chatNPC(p, (short) npcid, "Ta giữ đồ chưa bao giờ bị mất thứ gì cả!");
                    break;
                default: {
                    Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void npcKenshinto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if (p.c.isNhanban) {
            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
            return;
        }
        switch (menuId) {
            case 0: {
                if (b3 == 0) {
                    p.requestItem(10);
                    break;
                }
                if (b3 == 1) {
                    p.requestItem(31);
                    break;
                }
                if (b3 != 2) {
                    break;
                }
                Server.manager.sendTB(p, "Hướng dẫn", "- Hướng dẫn nâng cấp trang bị");
                break;
            }
            case 1: {
                if (b3 == 0) {
                    p.requestItem(12);
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                p.requestItem(11);
                break;
            }
            case 2: {
                p.requestItem(13);
                break;
            }
            case 3: {
                p.requestItem(33);
                break;
            }
            case 4: {
                p.requestItem(46);
                break;
            }
            case 5: {
                p.requestItem(47);
                break;
            }
            case 6: {
                p.requestItem(49);
                break;
            }
            case 7: {
                p.requestItem(50);
                break;
            }
            case 8: {
                if (b3 == 0) {
                    if (p.c.quantityItemyTotal(704) < 1000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 1000 viên đá danh vọng cấp 10");
                        break;
                    }
                    if (p.luong < 120000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 120k lượng");
                        break;
                    }
                    p.upluongMessage(-120000L);
                    p.c.removeItemBags(704, 1000);
                    Item itemup = ItemTemplate.itemDefault(916);
                    itemup.quantity = 100;
                    p.c.addItemBag(false, itemup);
                    p.conn.sendMessageLog("Bạn nhận được 100 đá danh vọng 12");
                    break;
                }
                if (b3 == 1) {
                    if (p.c.quantityItemyTotal(704) < 10000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 10000 viên đá danh vọng cấp 10");
                        break;
                    }
                    if (p.luong < 1200000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 1200k lượng");
                        break;
                    }
                    p.upluongMessage(-1200000L);
                    p.c.removeItemBags(704, 10000);
                    Item itemup = ItemTemplate.itemDefault(916);
                    itemup.quantity = 1000;
                    p.c.addItemBag(false, itemup);
                    p.conn.sendMessageLog("Bạn nhận được 1000 đá danh vọng 12");
                    break;
                }
                if (b3 == 2) {
                    if (p.c.quantityItemyTotal(704) < 20000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 20000 viên đá danh vọng cấp 10");
                        break;
                    }
                    if (p.luong < 2400000) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu 4 lượng");
                        break;
                    }
                    p.upluongMessage(-2400000L);
                    p.c.removeItemBags(704, 20000);
                    Item itemup = ItemTemplate.itemDefault(916);
                    itemup.quantity = 2000;
                    p.c.addItemBag(false, itemup);
                    p.conn.sendMessageLog("Bạn nhận được 2000 đá danh vọng 12");
                    break;
                }
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcUmayaki_Lang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                Service.chatNPC(p, (short) npcid, "Ta kéo xe qua các làng với tốc độ ánh sáng!");
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                TileMap[] var5 = Manager.getMapid(Map.arrLang[menuId - 1]).area;
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrLang[menuId - 1]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcUmayaki_Truong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
            case 1:
            case 2:
                TileMap[] var5 = Manager.getMapid(Map.arrTruong[menuId]).area;
                int var6 = var5.length;

                for (int var7 = 0; var7 < var6; ++var7) {
                    TileMap area = var5[var7];
                    if (area.numplayers < Manager.getMapid(Map.arrTruong[menuId]).template.maxplayers) {
                        p.c.tileMap.leave(p);
                        area.EnterMap0(p.c);
                        return;
                    }
                }

                return;
            case 3:
                Service.chatNPC(p, (short) npcid, "Ta kéo xe qua các trường, không qua quán net đâu!");
                return;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcToyotomi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    }
                    case 1: {
                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    }
                    case 3: {
                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    }
                }
                return;
            }
            case 1: {
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                    break;
                }
                if (b3 == 0) {
                    Admission.Admission(p, (byte) 1);
                } else {
                    if (b3 != 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
                        break;
                    }
                    Admission.Admission(p, (byte) 2);
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                break;
            }
            case 2: {
                if (p.c.get().nclass != 1 && p.c.get().nclass != 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 3: {
                if (p.c.lvhoa != 0) {
                    p.conn.sendMessageLog("Tao lại BAN CMM ACC bây giờ!");
                    return;
                }
                if (p.c.lvbang == 1) {
                    p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Trong thoáng chốc tạo ra một ngọn lửa mang hàn tính cực mạnh, khiến cho mục tiêu bị đóng băng trong 1,5 giây");
                    return;
                }
                if (p.c.lvbang == 2) {
                    p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV2\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Trong thoáng chốc tạo ra một ngọn lửa mang hàn tính cực mạnh, khiến cho mục tiêu bị đóng băng trong 1,5 giây");
                    return;
                }
                if (p.c.lvbang == 3) {
                    p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV3\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Trong thoáng chốc tạo ra một ngọn lửa mang hàn tính cực mạnh, khiến cho mục tiêu bị đóng băng trong 1,5 giây");
                    return;
                }
                if (p.c.get().nclass != 1 && p.c.get().nclass != 2) {
                    p.conn.sendMessageLog("Mày đéo phải hỏa ninja, tuổi lồn học skill này nhé con trai");
                    return;
                }
                if (p.c.quantityItemyTotal(856) < 10000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 10000 băng linh châu và 1 triệu lượng tao mới dạy cho");
                    return;
                }
                p.c.lvbang = 1;
                p.c.removeItemBags(856, 10000);
                p.upluongMessage(-1000000L);
                p.conn.sendMessageLog("Con đã học thành công băng phong thiên lý");
                break;
            }
            case 4: {
                if (p.c.lvbang != 1) {
                    p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(856) < 2500) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 2500 băng linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(914) < 1) {
                    p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp băng phong đến đây");
                    return;
                }
                p.c.lvbang = 2;
                p.c.removeItemBags(856, 2500);
                p.c.removeItemBags(914, 1);
                p.upluongMessage(-2000000L);
                p.conn.sendMessageLog("Con đã học thành công băng phong LV2");
                break;
            }
            case 5: {
                if (p.c.lvbang != 2) {
                    p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(856) < 5000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 5000 băng linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(914) < 2) {
                    p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp băng phong đến đây");
                    return;
                }
                p.c.lvbang = 3;
                p.c.removeItemBags(856, 5000);
                p.c.removeItemBags(914, 2);
                p.upluongMessage(-3000000L);
                p.conn.sendMessageLog("Con đã học thành công băng phong LV3");
                break;
            }
            case 6: {
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcOokamesama(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    }
                    case 1: {
                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    }
                    case 3: {
                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    }
                }
                return;
            }
            case 1: {
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                    break;
                }
                if (b3 == 0) {
                    Admission.Admission(p, (byte) 3);
                } else {
                    if (b3 != 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
                        break;
                    }
                    Admission.Admission(p, (byte) 4);
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                break;
            }
            case 2: {
                if (p.c.get().nclass != 3 && p.c.get().nclass != 4) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 3: {
                if (p.c.lvbang != 0) {
                    p.conn.sendMessageLog("Mày làm thế này ban acc đấy nhá!");
                    return;
                }
                if (p.c.lvhoa == 1) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Khi linh hồn băng giá đạt đến giới hạn sẽ gây ra bỏng lạnh cho mục tiêu trong 2 giây");
                    return;
                }
                if (p.c.lvhoa == 2) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV2\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Khi linh hồn băng giá đạt đến giới hạn sẽ gây ra bỏng lạnh cho mục tiêu trong 2 giây");
                    return;
                }
                if (p.c.lvhoa == 3) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV3\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Khi linh hồn băng giá đạt đến giới hạn sẽ gây ra bỏng lạnh cho mục tiêu trong 2 giây");
                    return;
                }
                if (p.c.get().nclass != 3 && p.c.get().nclass != 4) {
                    p.conn.sendMessageLog("Mày đéo phải đệ của băng hoàng, tuổi lồn học skill này nhé con trai");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 10000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 10000 hỏa linh châu và 1 triệu lượng tao mới dạy cho");
                    return;
                }
                p.c.lvhoa = 1;
                p.c.removeItemBags(855, 10000);
                p.upluongMessage(-1000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo");
                break;
            }
            case 4: {
                if (p.c.lvhoa != 1) {
                    p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 2500) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 2500 hỏa linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(915) < 1) {
                    p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp thiên hỏa đến đây");
                    return;
                }
                p.c.lvhoa = 2;
                p.c.removeItemBags(855, 2500);
                p.c.removeItemBags(915, 1);
                p.upluongMessage(-2000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV2");
                break;
            }
            case 5: {
                if (p.c.lvhoa != 2) {
                    p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 5000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 5000 hỏa linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(915) < 2) {
                    p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp thiên hỏa đến đây");
                    return;
                }
                p.c.lvhoa = 3;
                p.c.removeItemBags(855, 5000);
                p.c.removeItemBags(915, 2);
                p.upluongMessage(-3000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV3");
                break;
            }
            case 6: {
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcKazeto(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Server.manager.sendTB(p, "Top đại gia yên", Rank.getStringBXH(0));
                        return;
                    }
                    case 1: {
                        Server.manager.sendTB(p, "Top cao thủ", Rank.getStringBXH(1));
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Top gia tộc", Rank.getStringBXH(2));
                        return;
                    }
                    case 3: {
                        Server.manager.sendTB(p, "Top hang động", Rank.getStringBXH(3));
                        return;
                    }
                }
                return;
            }
            case 1: {
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                    break;
                }
                if (b3 == 0) {
                    Admission.Admission(p, (byte) 5);
                } else if (b3 == 1) {
                    Admission.Admission(p, (byte) 6);
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Hãy chăm chỉ luyện tập, có làm thì mới có ăn con nhé.");
                break;
            }
            case 2: {
                if (p.c.get().nclass != 5 && p.c.get().nclass != 6) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải học sinh của trường này, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm tiềm năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 3: {
                if (p.c.lvbang != 0) {
                    p.conn.sendMessageLog("Mày làm thế này ban acc đấy nhá!");
                    return;
                }
                if (p.c.lvhoa == 1) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Trong cơn lốc của phong thần ẩn chứa dị hỏa khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                    return;
                }
                if (p.c.lvhoa == 2) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV2\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Trong cơn lốc của phong thần ẩn chứa dị hỏa khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                    return;
                }
                if (p.c.lvhoa == 3) {
                    p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV3\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Trong cơn lốc của phong thần ẩn chứa dị hỏa khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                    return;
                }
                if (p.c.get().nclass != 5 && p.c.get().nclass != 6) {
                    p.conn.sendMessageLog("Mày đéo phải đệ tử phong thần, tuổi lồn học skill này nhé con trai");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 10000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 10000 hỏa linh châu và 1 triệu lượng tao mới dạy cho");
                    return;
                }
                p.c.lvhoa = 1;
                p.c.removeItemBags(855, 10000);
                p.upluongMessage(-1000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo");
                break;
            }
            case 4: {
                if (p.c.lvhoa != 1) {
                    p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 2500) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 2500 hỏa linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(915) < 1) {
                    p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp thiên hỏa đến đây");
                    return;
                }
                p.c.lvhoa = 2;
                p.c.removeItemBags(855, 2500);
                p.c.removeItemBags(915, 1);
                p.upluongMessage(-2000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV2");
                break;
            }
            case 5: {
                if (p.c.lvhoa != 2) {
                    p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                    return;
                }
                if (p.c.quantityItemyTotal(855) < 5000) {
                    p.conn.sendMessageLog("mày phải thu thập đủ 5000 hỏa linh châu");
                    return;
                }
                if (p.c.quantityItemyTotal(915) < 2) {
                    p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp thiên hỏa đến đây");
                    return;
                }
                p.c.lvhoa = 3;
                p.c.removeItemBags(855, 5000);
                p.c.removeItemBags(915, 2);
                p.upluongMessage(-3000000L);
                p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV3");
                break;
            }
            case 6: {
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đang hơi mệt xíu, ta sẽ giao chiến với con sau nha! Bye bye...");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcTajima(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch (menuId) {
            case 0:
                Server.manager.sendTB(p, "Tân Thủ LTD", "Lưu ý khi chơi game:"
                        + "\n-->Khi vào game nâng 4 túi vải rồi đến gặp ta để nâng tiếp hành trang lên 120 ô, giá nâng 20k lượng/6 ô"
                        + "\n-Dùng coin đổi lượng ở Vua Hùng, rút qua xu ở máy atm tại tone"
                        + "\n-Kiếm và đao khi nâng 1 sức mạnh sẽ nhận 1,5 tấn công và 7 máu (không thể cộng thể lực)"
                        + "\n-Quạt và cung khi nâng 1 Chakra sẽ nhận 1,5 điểm tấn công và mp"
                        + "\n-Kunai và tiêu khi nâng 1 điểm thân pháp sẽ tăng 1 tấn công, 1.2 né, 1.2 chính xác"
                        + "\n-ngoài đao kiếm mỗi phái khi cộng 1 thể lực sẽ tăng 10 máu"
                        + "\n-Ngoài kunai và tiêu khi tăng 1 thân pháp sẽ nhận được 1,5 chính xác và 1,5 né"
                        + "\n-Gia tăng giới hạn skill cấp 15 và 35 của phái quạt từ 6 lên 8, chiêu cấp 30,50, 100 từ 12 lên 15 cấp"
                        + "\n-Gia tăng giới hạn skill cấp 35 của phái cung từ 6 lên 8, chiêu 60 từ 15 lên 17 câp, chiêu 30,50,100 từ 12 lên 15 cấp"
                        + "\n-Anh em chơi game vui vẻ thắc mắc liên hệ ADMIN");
                break;
            case 1:
                if (p.c.maxluggage >= 120) {
                    p.conn.sendMessageLog("Bạn chỉ có thể nâng tối đa 120 ô hành trang");
                    return;
                }
                if (p.c.levelBag < 4) {
                    p.conn.sendMessageLog("Mày phải sử dụng túi vải cấp 4 rồi mới đc nâng");
                    return;
                }
                if (p.luong < 20000) {
                    p.conn.sendMessageLog("Bạn Cần 20000 Lượng");
                    return;
                } else {
                    p.c.maxluggage += 6;
                    p.upluongMessage(-20000L);
                    p.conn.sendMessageLog("Hành trang đã mở thêm 6 ô, Số Ô Hành Trang Của Bạn Là " + p.c.maxluggage + " Vui lòng thoát game vào lại để update hành trang ");
                    Service.chatNPC(p, (short) npcid, "nâng hàng trang thành công. Tự động thoát sau 5 giây");
                    int TimeSeconds = 5;
                    while (TimeSeconds > 0) {
                        TimeSeconds--;
                        Thread.sleep(1000);
                    }
                    Client.gI().kickSession(p.conn);
                    break;
                }
            case 3:
                if (p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.toNhanBan();
                } else {
                    Service.chatNPC(p, (short) npcid, "Con không có phân thân để sử dụng chức năng này!");
                }
                break;
            case 2:
                if (!p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, "Con không phải phân thân để sử dụng chức năng này!");
                    return;
                }
                if (!p.c.clone.isDie && p.c.timeRemoveClone > System.currentTimeMillis()) {
                    p.exitNhanBan(true);
                }
                break;
            case 4:
                Server.manager.sendTB(p, "Top Sự Kiện Tết", Rank.getStringBXH(8));
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcRei(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                Service.chatNPC(p, (short) npcid, "Ngươi đến đây làm gì, không có nhiệm vụ cho ngươi đâu!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcKirin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                Service.chatNPC(p, (short) npcid, "Ngươi đến đây làm gì, không có nhiệm vụ cho ngươi đâu!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcSoba(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                Service.chatNPC(p, (short) npcid, "Ta sẽ sớm có nhiệm vụ cho con!");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcSunoo(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                Service.chatNPC(p, (short) npcid, "Khụ khụ...");
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcGuriin(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcMatsurugi(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcOkanechan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Để nạp tiền hoặc mua đồ, con hãy lên Website hoặc THAM GIA BOX ZALO của game để nạp nhé!");
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.pointUydanh < 200) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Không Đủ 200 Điểm Hoạt Động, Điểm hoạt động của con phải trên 200");
                    return;
                }
                if (p.luong < 1000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Không Đủ 1000 Lượng");
                    return;
                }
                p.upluongMessage(-2000L);
                p.c.pointUydanh -= 200;
                p.c.upxuMessage(50000000L);
                p.c.upyenMessage(100000000L);
                Service.chatNPC(p, Short.valueOf(npcid), "Con đã đổi xu, yên thành công hãy đi cày điểm hoạt động rồi đổi tiếp nhé!");
                return;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 10 && p.c.checkLevel[0] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(223, true));
                            if (p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000L;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[0] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 20 && p.c.checkLevel[1] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(224, true));
                            if (p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000L;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[1] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 30 && p.c.checkLevel[2] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(225, true));
                            if (p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000L;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[2] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 40 && p.c.checkLevel[3] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(226, true));
                            if (p.status == 1) {
                                p.upluongMessage(1000L);
                                p.c.luongTN += 1000L;
                            } else {
                                p.upluongMessage(2000L);
                            }
                            p.c.checkLevel[3] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 50 && p.c.checkLevel[4] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(227, true));
                            if (p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500L;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[4] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getBagNull() < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.level >= 70 && p.c.checkLevel[5] == 0) {
                            p.c.addItemBag(false, ItemTemplate.itemDefault(228, true));
                            if (p.status == 1) {
                                p.upluongMessage(1500L);
                                p.c.luongTN += 1500L;
                            } else {
                                p.upluongMessage(3000L);
                            }
                            p.c.checkLevel[5] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 6: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level >= 90 && p.c.checkLevel[6] == 0) {
                            if (p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500L;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[6] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 7: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này không dành cho phân thân!");
                            return;
                        }
                        if (p.c.level >= 110 && p.c.checkLevel[7] == 0) {
                            if (p.status == 1) {
                                p.upluongMessage(2500L);
                                p.c.luongTN += 2500L;
                            } else {
                                p.upluongMessage(5000L);
                            }
                            p.c.checkLevel[7] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 8: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level >= 130 && p.c.checkLevel[8] == 0) {
                            if (p.status == 1) {
                                p.upluongMessage(3500L);
                                p.c.luongTN += 3500L;
                            } else {
                                p.upluongMessage(7000L);
                            }
                            p.c.checkLevel[8] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 9: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level >= 140 && p.c.checkLevel[9] == 0) {
                            p.c.addItemBag(true, ItemTemplate.itemDefault(994, true));
                            p.c.addItemBag(true, ItemTemplate.itemDefault(994, true));
                            p.upluongMessage(140000L);
                            p.c.upxuMessage(100000000);
                            p.c.checkLevel[9] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                    case 10: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level >= 150 && p.c.checkLevel[10] == 0) {
                            p.c.addItemBag(true, ItemTemplate.itemDefault(994, true));
                            p.c.addItemBag(true, ItemTemplate.itemDefault(994, true));
                            p.upluongMessage(300000L);
                            p.c.upxuMessage(200000000);
                            p.c.checkLevel[10] = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Chúc mừng con đã đạt đến cấp độ mới!");
                        } else {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trình độ của con không đủ hoặc con đã nhận thưởng rồi!");
                        }
                        return;
                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chào mừng con đã đến với LTD khi lên cấp hãy đến gặp ta nhận quà nhé!");
            }
        }
    }

    public static void npcCLXTXu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 46_0_0, "Nhập số XU đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 46_0_1, "Nhập số XU đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckTXXu check : CheckTXXu.checkTXXuArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        Service.sendInputDialog(p, (short) 46_1_0, "Nhập số XU đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 1: {
                        Service.sendInputDialog(p, (short) 46_1_1, "Nhập XU  đặt (phải là bội số của 10) :");
                        break;
                    }
                    case 2: {
                        try {
                            String a = "";
                            int i2 = 1;
                            for (CheckCLXu check : CheckCLXu.checkCLXuArrayList) {
                                a += i2 + ". " + check.name + " - " + check.item + " - " + check.time + ".\n";
                                i2++;
                            }
                            Server.manager.sendTB(p, "Soi Cầu", a);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    public static void npcRikudou(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (menuId) {
            case 0: {
                Service.chatNPC(p, Short.valueOf(npcid), "Hãy chăm chỉ lên nha.");
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        MapTemplate map;
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level < 10) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con cần đạt cấp độ 10 để có thể nhận nhiệm vụ.");
                            return;
                        }
                        if (p.c.isTaskHangNgay != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giao nhiệm vụ cho con trước đó rồi");
                            return;
                        }
                        if (p.c.countTaskHangNgay >= 20) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con đã hoàn thành hết nhiệm vụ ngày hôm nay rồi, ngày mai hãy quay lại nha.");
                            return;
                        }
                        MobTemplate mob = Service.getMobIdByLevel(p.c.level);
                        if (mob == null || (map = Service.getMobMapId(mob.id)) == null) {
                            break block0;
                        }
                        p.c.taskHangNgay[0] = 0;
                        p.c.taskHangNgay[1] = 0;
                        p.c.taskHangNgay[2] = Util.nextInt(10, 25);
                        p.c.taskHangNgay[3] = mob.id;
                        p.c.taskHangNgay[4] = map.id;
                        p.c.isTaskHangNgay = 1;
                        ++p.c.countTaskHangNgay;
                        Service.getTaskOrder(p.c, (byte) 0);
                        Service.chatNPC(p, Short.valueOf(npcid), "Đây là nhiệm vụ thứ " + p.c.countTaskHangNgay + "/20 trong ngày của con.");
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        p.c.isTaskHangNgay = 0;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, --p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte) 0);
                        Service.chatNPC(p, Short.valueOf(npcid), "Con đã huỷ nhiệm vụ lần này.");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskHangNgay == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        if (p.c.getBagNull() == 0) {
                            p.conn.sendMessageLog(Language.NOT_ENOUGH_BAG);
                            return;
                        }
                        if (p.c.taskHangNgay[1] < p.c.taskHangNgay[2]) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }
                        p.c.isTaskHangNgay = 0;
                        p.c.taskHangNgay = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskHangNgay};
                        Service.clearTaskOrder(p.c, (byte) 0);
                        long luongUp = Util.nextInt(2000, 4000);
                        if (p.status == 1) {
                            p.c.upxuMessage(150000L);
                            p.upluongMessage(luongUp /= 2L);
                            p.c.xuTN += 150000L;
                            p.c.luongTN += luongUp;
                        } else {
                            p.c.upxuMessage(35000L);
                            p.upluongMessage(luongUp);
                        }
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 2;
                        }
                        if (p.c.countTaskHangNgay % 2 == 0) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 733 : 760, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(2, 3);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 734 : 761, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(2, 3);
                            p.c.addItemBag(true, itemUp);
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.taskHangNgay[4] != -1) {
                            Map ma = Manager.getMapid(p.c.taskHangNgay[4]);
                            for (int var8 = 0; var8 < ma.area.length; ++var8) {
                                TileMap area = ma.area[var8];
                                if (area.numplayers >= ma.template.maxplayers) {
                                    continue;
                                }
                                p.c.tileMap.leave(p);
                                area.EnterMap0(p.c);
                                return;
                            }
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                        break;
                    }
                }
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        MapTemplate map;
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level < 30) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con cần đạt cấp độ 30 để có thể nhận nhiệm vụ tà thú.");
                            return;
                        }
                        if (p.c.isTaskTaThu != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giao nhiệm vụ cho con trước đó rồi");
                            return;
                        }
                        if (p.c.countTaskTaThu >= 2) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con đã hoàn thành hết nhiệm vụ ngày hôm nay rồi, ngày mai hãy quay lại nha.");
                            return;
                        }
                        MobTemplate mob = Service.getMobIdTaThu(p.c.level);
                        if (mob == null || (map = Service.getMobMapIdTaThu(mob.id)) == null) {
                            break block0;
                        }
                        p.c.taskTaThu[0] = 1;
                        p.c.taskTaThu[1] = 0;
                        p.c.taskTaThu[2] = 1;
                        p.c.taskTaThu[3] = mob.id;
                        p.c.taskTaThu[4] = map.id;
                        p.c.isTaskTaThu = 1;
                        ++p.c.countTaskTaThu;
                        Service.getTaskOrder(p.c, (byte) 1);
                        Service.chatNPC(p, Short.valueOf(npcid), "Hãy hoàn thành nhiệm vụ và trở về đây nhận thưởng.");
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        Service.clearTaskOrder(p.c, (byte) 1);
                        p.c.isTaskTaThu = 0;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, --p.c.countTaskTaThu};
                        Service.chatNPC(p, Short.valueOf(npcid), "Con đã huỷ nhiệm vụ lần này.");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isTaskTaThu == 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa nhận nhiệm vụ nào cả!");
                            return;
                        }
                        if (p.c.taskTaThu[1] < p.c.taskTaThu[2]) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con chưa hoàn thành nhiệm vụ ta giao!");
                            return;
                        }
                        if (p.c.getBagNull() < 2) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không đủ chỗ trống để nhận thưởng");
                            return;
                        }
                        p.c.isTaskTaThu = 0;
                        p.c.taskTaThu = new int[]{-1, -1, -1, -1, -1, 0, p.c.countTaskTaThu};
                        Service.clearTaskOrder(p.c, (byte) 1);
                        if (p.c.pointUydanh < 5000) {
                            p.c.pointUydanh += 3;
                        }
                        Item item = ItemTemplate.itemDefault(251, false);
                        item.quantity = Util.nextInt(3, 5);
                        item.isLock = false;
                        p.c.addItemBag(true, item);
                        Item item1 = ItemTemplate.itemDefault(959, false);
                        item1.quantity = Util.nextInt(1, 2);
                        item1.isLock = false;
                        p.c.addItemBag(true, item1);
                        if (p.c.countTaskTaThu == 1) {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 737 : 764, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(20, 30);
                            p.c.addItemBag(true, itemUp);
                        } else {
                            Item itemUp = ItemTemplate.itemDefault(p.c.gender == 1 ? 738 : 765, false);
                            itemUp.isLock = true;
                            itemUp.isExpires = false;
                            itemUp.expires = -1L;
                            itemUp.quantity = Util.nextInt(20, 30);
                            p.c.addItemBag(true, itemUp);
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Con hãy nhận lấy phần thưởng của mình.");
                        break;
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Chiến trường chưa được tổ chức.");
                            return;
                        }
                        if (ChienTruong.chienTruong != null) {
                            if (ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Bây giờ là thời gian chiến trường cho cấp độ từ 30 đến 49. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if (ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Bây giờ là thời gian chiến trường cho cấp độ lớn hơn hoặc bằng 50. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if ((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == 1) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Con đã điểm danh phe Hắc giả trước đó rồi.");
                                return;
                            }
                            if (ChienTruong.start && p.c.pheCT == -1) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Chiến trường đã bắt đầu, không thể báo danh.");
                                return;
                            }
                            if ((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 0;
                                p.c.pointCT = 0;
                                p.c.isTakePoint = 0;
                                p.c.typepk = (byte) 4;
                                Service.ChangTypePkId(p.c, (byte) 4);
                                Service.updatePointCT(p.c, 0);
                                if (p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if (!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers >= ma.template.maxplayers) {
                                        continue;
                                    }
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                                return;
                            }
                            p.c.typepk = (byte) 4;
                            Service.ChangTypePkId(p.c, (byte) 4);
                            Service.updatePointCT(p.c, 0);
                            if (p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if (!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[0].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers >= ma.template.maxplayers) {
                                    continue;
                                }
                                p.c.tileMap.leave(p);
                                area.EnterMap0(p.c);
                                return;
                            }
                        }
                        return;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (ChienTruong.chienTruong == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Chiến trường chưa được tổ chức.");
                            return;
                        }
                        if (ChienTruong.chienTruong != null) {
                            if (ChienTruong.chienTruong30 && (p.c.level < 30 || p.c.level >= 50)) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Bây giờ là thời gian chiến trường cho cấp độ từ 30 đến 49. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if (ChienTruong.chienTruong50 && p.c.level < 50) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Bây giờ là thời gian chiến trường cho cấp độ lớn hơn hoặc bằng 50. Trình độ của con không phù hợp để tham gia.");
                                return;
                            }
                            if (ChienTruong.start && p.c.pheCT == -1) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Chiến trường đã bắt đầu, không thể báo danh.");
                                return;
                            }
                            if ((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == 0) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Con đã điểm danh phe Bạch giả trước đó rồi.");
                                return;
                            }
                            if ((ChienTruong.chienTruong30 || ChienTruong.chienTruong50) && p.c.pheCT == -1) {
                                if (p.c.pointUydanh < 5000) {
                                    p.c.pointUydanh += 10;
                                }
                                p.c.pheCT = 1;
                                p.c.pointCT = 0;
                                p.c.typepk = (byte) 5;
                                p.c.isTakePoint = 0;
                                Service.ChangTypePkId(p.c, (byte) 5);
                                Service.updatePointCT(p.c, 0);
                                if (p.c.party != null) {
                                    p.c.party.removePlayer(p.c.id);
                                }
                                if (!ChienTruong.bxhCT.containsKey(p.c)) {
                                    ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                                } else {
                                    ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                                }
                                Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                                for (TileMap area : ma.area) {
                                    if (area.numplayers >= ma.template.maxplayers) {
                                        continue;
                                    }
                                    p.c.tileMap.leave(p);
                                    area.EnterMap0(p.c);
                                    return;
                                }
                                return;
                            }
                            p.c.typepk = (byte) 5;
                            Service.ChangTypePkId(p.c, (byte) 5);
                            Service.updatePointCT(p.c, 0);
                            if (p.c.party != null) {
                                p.c.party.removePlayer(p.c.id);
                            }
                            if (!ChienTruong.bxhCT.containsKey(p.c)) {
                                ChienTruong.bxhCT.put(p.c, p.c.pointCT);
                            } else {
                                ChienTruong.bxhCT.replace(p.c, p.c.pointCT);
                            }
                            Map ma = Manager.getMapid(ChienTruong.chienTruong.map[6].id);
                            for (TileMap area : ma.area) {
                                if (area.numplayers >= ma.template.maxplayers) {
                                    continue;
                                }
                                p.c.tileMap.leave(p);
                                area.EnterMap0(p.c);
                                return;
                            }
                        }
                        return;
                    }
                    case 2: {
                        if (ChienTruong.finish) {
                            Service.evaluateCT(p.c);
                            break block0;
                        }
                        Server.manager.sendTB(p, "Kết quả", "Chưa có thông tin.");
                        break block0;
                    }
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Chiến trường được mở 2 lần mỗi ngày.\n- Chiến trường lv30: giành cho nhân vật level từ 30 đến 45, điểm danh vào lúc 19h và bắt đầu từ 19h30' đến 20h30'.\n- Chiến trường lv50: giành cho nhân vật level từ 50 trở lên, điểm danh vào lúc 21h và bắt đầu từ 21h30' đến 22h30'.\n\n+ Top1: 10v đan mỗi loại + 3tr xu.\n+ Top 2: 7v đan mỗi loại + 2tr xu.\n+ Top 3: 5v đan mỗi loại + 1tr xu.\n+ Phe thắng: 1v đan mỗi loại + 500k xu.");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcGoosho(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0:
                p.requestItem(14);
                break;
            case 1:
                p.requestItem(15);
                break;
            case 2:
                p.requestItem(32);
                break;
            case 3:
                p.requestItem(34);
                break;
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTruCoQuan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.quantityItemyTotal(260) < 1) {
                    p.sendAddchatYellow("Không có chìa khoá để mở cửa này.");
                    return;
                }
                if (p.c.tileMap.map.lanhDiaGiaToc != null && p.c.tileMap.map.mapLDGT()) {
                    switch (p.c.tileMap.map.id) {
                        case 80: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(1, p);
                            break;
                        }
                        case 81: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(2, p);
                            break;
                        }
                        case 82: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(3, p);
                            break;
                        }
                        case 83: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(4, p);
                            break;
                        }
                        case 84: {

                            p.c.tileMap.map.lanhDiaGiaToc.openMap(5, p);
                            break;
                        }
                        case 85: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(6, p);
                            break;
                        }
                        case 86: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(7, p);
                            break;
                        }
                        case 87: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(8, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ "
                                    + "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        case 88: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(9, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ "
                                    + "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        case 89: {
                            p.c.tileMap.map.lanhDiaGiaToc.openMap(10, p);
                            Server.manager.sendTB(p, "Ghi chú", "Con đường này sẽ dẫn đến cánh cửa nơi ở của một nhân vật huyền bí đã bị lời nguyền cổ "
                                    + "xưa yểm bùa rằng sẽ không ai có thể đánh bại được nhân vật huyền bí này. Bạn hãy mau tìm cách hoá giải lời nguyền.");
                            break;
                        }
                        default: {
                            break;
                        }

                    }
                }
                break;
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcShinwa(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
//            case 0: {
//                p.menuIdAuction = b3;
//                final List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int) b3);
//                final Message mess = new Message(103);
//                mess.writer().writeByte(b3);
//                if (itemShinwas != null) {
//                    mess.writer().writeInt(itemShinwas.size());
//                    ShinwaTemplate item;
//                    for (int i = 0; i < itemShinwas.size(); i++) {
//                        item = itemShinwas.get(i);
//                        if (item != null) {
//                            mess.writer().writeInt(i);
//                            mess.writer().writeInt(item.getRemainTime());
//                            mess.writer().writeShort(item.getItem().quantity);
//                            mess.writer().writeUTF(item.getSeller());
//                            mess.writer().writeInt((int) item.getPrice());
//                            mess.writer().writeShort(item.getItem().id);
//                        } else {
//                            mess.writer().writeInt(i);
//                            mess.writer().writeInt(-1);
//                            mess.writer().writeShort(0);
//                            mess.writer().writeUTF("");
//                            mess.writer().writeInt(999999999);
//                            mess.writer().writeShort(12);
//                        }
//                    }
//                } else {
//                    mess.writer().writeInt(0);
//                }
//                mess.writer().flush();
//                p.conn.sendMessage(mess);
//                mess.cleanup();
//                break;
//            }
//            case 1: {
//                final int itemShinwa = ShinwaManager.entrys.size();
//                System.out.println("Số lượng " + itemShinwa);
//                if (itemShinwa > 30000) {
//                    p.conn.sendMessageLog("Gian hàng đã full vật phẩm");
//                    break;
//                }
//                p.menuIdAuction = -2;
//                p.requestItem(36);
//                break;
//            }
//            case 2: {
//                try {
//                    synchronized (ShinwaManager.entrys.get((int) -1)) {
//                        List<ShinwaTemplate> itemShinwas = ShinwaManager.entrys.get((int) -1);
//                        System.out.print(itemShinwas.size());
//                        List<Integer> ind = new ArrayList<>();
//                        ShinwaTemplate item;
//                        for (int i = itemShinwas.size() - 1; i >= 0; i--) {
//                            item = itemShinwas.get(i);
//                            if (item != null && item.getSeller().equals(p.c.name)) {
//                                if (p.c.getBagNull() == 0) {
//                                    Service.chatNPC(p, (short) npcid, "Hành trang không đủ chỗ trống để nhận thêm vật phẩm!");
//                                    break;
//                                }
//                                p.c.addItemBag(true, item.getItem());
//                                ind.add(i);
//                                itemShinwas.remove(i);
//                            }
//                        }
//                        if (ind.size() < 1) {
//                            Service.chatNPC(p, (short) npcid, "Con không có đồ để nhận lại!");
//                            return;
//                        }
//                        for (int i : ind) {
//                            itemShinwas.remove(i);
//                        }
//                    }
//                } catch (Exception e) {
//                    p.conn.sendMessageLog("Có lỗi, vui lòng thử lại sau!");
//                }
//                break;
//            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng nay đang đóng nhé bạn hiền!");
                break;
            }
        }
    }

    public static void npcChiHang(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcRakkii(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                p.requestItem(38);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    return;
                }

                Service.sendInputDialog(p, (short) 4, "Nhập Gift Code tại đây");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[0].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Vòng xoay vip", "Hãy đặt cược xu và thử vận may của mình trong 2 phút nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0:
                    case 1: {
                        Server.manager.rotationluck[1].luckMessage(p);
                        return;
                    }
                    case 2: {
                        Server.manager.sendTB(p, "Vòng xoay thường", "Hãy đặt cược xu và thử vận may của mình trong 2 phút nha.");
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcLongDen(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }
    }

    public static void npcKagai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.clan == null) {
                            Service.chatNPC(p, (short) npcid, "Con chưa có Gia tộc.");
                            return;
                        }
                        if (p.c.clan != null && p.c.clan.typeclan != 4) {
                            Service.chatNPC(p, (short) npcid, "Con không phải tộc trưởng, không thể mời gia tộc chiến.");
                            return;
                        }
                        //Service.sendInputDialog(p, (short) 5, "Nhập tên gia tộc đối phương");
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            }
            case 3: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                } else {
                    Item it;
                    Char var6;
                    switch (b3) {
                        case 0:
                            if (p.c.pointUydanh < 300) {
                                Service.chatNPC(p, (short) npcid, "Con cần 300 điểm hoạt động để để lấy bí kíp 3 ngày.");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 300;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 259200000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(3000000);
                                }

                                return;
                            }
                        case 1: {
                            if (p.c.pointUydanh < 700) {
                                Service.chatNPC(p, (short) npcid, "Con cần 700 điểm hoạt động để để lấy bí kíp 7 ngày.");
                                return;
                            } else {
                                if (p.c.getBagNull() < 1) {
                                    Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                                } else {
                                    var6 = p.c;
                                    var6.pointUydanh -= 700;
                                    it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                    it.isLock = false;
                                    it.quantity = 1;
                                    it.isExpires = true;
                                    it.expires = System.currentTimeMillis() + 432000000L;
                                    p.c.addItemBag(false, it);
                                    p.c.upxuMessage(5000000);
                                }
                                return;
                            }

                        }
                        case 2: {
                            if (p.c.pointUydanh < 2000) {
                                Service.chatNPC(p, (short) npcid, "Con cần 2000 điểm hoạt động để để lấy bí kíp 15 ngày.");
                            } else if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, (short) npcid, Language.NOT_ENOUGH_BAG);
                            } else {
                                var6 = p.c;
                                var6.pointUydanh -= 2000;
                                it = ItemTemplate.itemDefault(396 + p.c.nclass);
                                it.isLock = false;
                                it.quantity = 1;
                                it.isExpires = true;
                                it.expires = System.currentTimeMillis() + 1296000000L;
                                p.c.addItemBag(false, it);
                                p.c.upxuMessage(10000000);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, (short) npcid, Language.NOT_FOR_PHAN_THAN);
                    break;
                } else {
                    switch (b3) {
                        case 0: {
                            p.requestItem(43);
                            break;
                        }
                        case 1: {
                            p.requestItem(44);
                            break;
                        }
                        case 2: {
                            p.requestItem(45);
                            break;
                        }
                        case 3: {
                            Server.manager.sendTB(p, "Hướng dẫn", "- Tinh luyện...");
                            break;
                        }
                        default: {
                            Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                            break;
                        }
                    }
                }
                break;
            }
            case 0:
            case 2:
            default: {
                Service.chatNPC(p, (short) npcid, "Chức năng này đang cập nhật!");
                break;
            }
        }

    }

    public static void npcTienNu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        if (p.typemenu == 33) {
            block0:
            switch (Server.manager.event) {
                case 1: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        break;
                    }
                    switch (menuId) {
                        case 0: {
                            if (p.c.quantityItemyTotal(432) >= 1 && p.c.quantityItemyTotal(428) >= 3 && p.c.quantityItemyTotal(429) >= 2 && p.c.quantityItemyTotal(430) >= 3) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(434);
                                    p.c.addItemBag(true, it);
                                    p.c.removeItemBags(432, 1);
                                    p.c.removeItemBags(428, 3);
                                    p.c.removeItemBags(429, 2);
                                    p.c.removeItemBags(430, 3);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 1: {
                            if (p.c.quantityItemyTotal(433) >= 1 && p.c.quantityItemyTotal(428) >= 2 && p.c.quantityItemyTotal(429) >= 3 && p.c.quantityItemyTotal(431) >= 2) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    break;
                                }
                                Item it = ItemTemplate.itemDefault(435);
                                p.c.addItemBag(true, it);
                                p.c.removeItemBags(433, 1);
                                p.c.removeItemBags(428, 2);
                                p.c.removeItemBags(429, 3);
                                p.c.removeItemBags(431, 2);
                                break;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                        }
                    }
                    break;
                }
                case 2: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        break;
                    }
                    switch (menuId) {
                        case 0: {
                            if (p.c.quantityItemyTotal(304) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(302);
                                    p.c.addItemBag(true, it);
                                    p.c.removeItemBags(304, 1);
                                    p.c.removeItemBags(298, 1);
                                    p.c.removeItemBags(299, 1);
                                    p.c.removeItemBags(300, 1);
                                    p.c.removeItemBags(301, 1);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 1: {
                            if (p.c.quantityItemyTotal(305) >= 1 && p.c.quantityItemyTotal(298) >= 1 && p.c.quantityItemyTotal(299) >= 1 && p.c.quantityItemyTotal(300) >= 1 && p.c.quantityItemyTotal(301) >= 1) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(303);
                                    p.c.addItemBag(true, it);
                                    p.c.removeItemBags(305, 1);
                                    p.c.removeItemBags(298, 1);
                                    p.c.removeItemBags(299, 1);
                                    p.c.removeItemBags(300, 1);
                                    p.c.removeItemBags(301, 1);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 2: {
                            if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 3 && p.c.quantityItemyTotal(293) >= 2 && p.c.quantityItemyTotal(294) >= 3) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(298);
                                    p.c.addItemBag(true, it);
                                    p.c.upyenMessage(-10000L);
                                    p.c.removeItemBags(292, 3);
                                    p.c.removeItemBags(293, 2);
                                    p.c.removeItemBags(294, 3);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 3: {
                            if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(294) >= 2) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(299);
                                    p.c.addItemBag(true, it);
                                    p.c.upyenMessage(-10000L);
                                    p.c.removeItemBags(292, 2);
                                    p.c.removeItemBags(295, 3);
                                    p.c.removeItemBags(294, 2);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 4: {
                            if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(295) >= 3 && p.c.quantityItemyTotal(297) >= 3) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                } else {
                                    Item it = ItemTemplate.itemDefault(300);
                                    p.c.addItemBag(true, it);
                                    p.c.upyenMessage(-10000L);
                                    p.c.removeItemBags(292, 2);
                                    p.c.removeItemBags(295, 3);
                                    p.c.removeItemBags(297, 3);
                                }
                                return;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                            break;
                        }
                        case 5: {
                            if (p.c.yen >= 10000 && p.c.quantityItemyTotal(292) >= 2 && p.c.quantityItemyTotal(296) >= 2 && p.c.quantityItemyTotal(297) >= 3) {
                                if (p.c.getBagNull() == 0) {
                                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                                    break;
                                }
                                Item it = ItemTemplate.itemDefault(301);
                                p.c.addItemBag(true, it);
                                p.c.upyenMessage(-10000L);
                                p.c.removeItemBags(292, 2);
                                p.c.removeItemBags(296, 2);
                                p.c.removeItemBags(297, 3);
                                break;
                            }
                            Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của con không có đủ nguyên liệu");
                        }
                    }
                    break;
                }
                case 3: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 6, "Nhập số lượng bánh Chocolate muốn làm.");
                            break block0;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 7, "Nhập số lượng bánh  Dâu tây muốn làm.");
                            break block0;
                        }
                        case 2: {
                            if (p.c.pointNoel < 3500) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Con cần ít nhất 3500 điểm để đổi mặt nạ 7 ngày.");
                                return;
                            }
                            p.c.pointNoel -= 3500;
                            Item it = ItemTemplate.itemDefault(p.c.gender == 1 ? 407 : 408);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break block0;
                        }
                        case 3: {
                            if (p.c.pointNoel < 5000) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Con cần ít nhất 5000 điểm để đổi pet Hoả long 7 ngày.");
                                return;
                            }
                            p.c.pointNoel -= 5000;
                            Item it = ItemTemplate.itemDefault(583);
                            it.isLock = false;
                            it.quantity = 1;
                            it.isExpires = true;
                            it.expires = System.currentTimeMillis() + 604800000L;
                            p.c.addItemBag(false, it);
                            break block0;
                        }
                        case 4: {
                            String a = "";
                            if (Rank.bxhBossTuanLoc.isEmpty()) {
                                a = "Chưa có thông tin.";
                            }
                            for (Rank.Entry3 item : Rank.bxhBossTuanLoc) {
                                a = a + item.index + ". " + item.name + ": " + item.point + " điểm\n";
                            }
                            Server.manager.sendTB(p, "BXH Diệt Boss", a);
                            break block0;
                        }
                        case 5: {
                            Server.manager.sendTB(p, "Hướng dẫn", "- Số điểm hiện tại của bạn là: " + p.c.pointNoel + "\n- Kiểm điểm sự kiện bằng cách nhận quà hàng ngày tại Cây thông (+1 điểm), trang trí cây thông (+10 điểm), giết boss Tuần lộc (+1 điểm).\n- Dùng điểm để dổi lấy vật phẩm quý giá: Mặt nạ Super Broly/Onna Bugeisha 7 ngày (3500 điểm), Pet Hoả long 7 ngày (5000 điểm).\n- Bánh Chocolate: 2 Bơ + 2 Kem + 3 Đường + 1 Chocolate + 5000 yên.\n- Bánh Dâu tây: 3 Bơ + 3 Kem + 4 Đường + 1 Dâu tây + 10000 yên.\n");
                            break block0;
                        }
                    }
                    break;
                }
                case 4: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 110, "Nhập số lượng bánh Chưng muốn làm :");
                            break block0;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 111, "Nhập số lượng bánh Tét muốn làm :");
                            break block0;
                        }
                        case 2: {
                            if (p.luong < 500) {
                                p.conn.sendMessageLog("Bạn không đủ 500 lượng để thực hiện điều này.");
                                return;
                            }
                            Service.sendInputDialog(p, (short) 113, "Nhập tên nhân vật nhận:");
                            break block0;
                        }
                        case 3: {
                            Service.sendInputDialog(p, (short) 112, "Nhập số lượng Pháo muốn làm :");
                            break block0;
                        }
                        case 4: {
                            String a = "";
                            if (Rank.bxhBossChuot.isEmpty()) {
                                a = "Chưa có thông tin.";
                            }
                            for (Rank.Entry4 item : Rank.bxhBossChuot) {
                                a = a + item.index + ". " + item.name + ": " + item.point1 + " điểm\n";
                            }
                            Server.manager.sendTB(p, "BXH Diệt Boss Chuột", a);
                            break block0;
                        }
                        case 5: {
                            Server.manager.sendTB(p, "Hướng dẫn", "----------------- Làm Bánh Chưng -----------------\n +, 3 lá dong + 5 nếp + 1 thịt heo + 3 đậu xanh + 2 lạt tre + 50.000 xu + 50.000 yên.\n----------------- Làm Bánh Tét -----------------\n +,  2 lá dong + 4 nếp + 2 đậu xanh + 4 lạt tre + 40.000 xu + 40.000 yên.\n----------------- Làm Pháo -----------------\n +, Ghép 10 mảnh Pháo + 30k xu + 30k yên thành 1 dây Pháo, sử dụng và nhận quà bất ngờ.\n -Bạn có thể mua tại Goosho hoặc tham gia Lôi Đài để nhận Mảnh Pháo ( cứ chiến thắng 5 trận cộng dồn bạn sẽ nhận được 1 mảnh Pháo ).\n----------------- Săn Boss Sự Kiện -----------------\n +, Trong quá trình diễn ra sự kiện Tết ,Boss Chuột sẽ xuất hiện ngẫu nhiên tại cạnh các Trường ,hãy nhanh tay tiêu diệt chúng và nhận điểm ( cứ giết 1 em bạn sẽ nhận được 1 điểm ) để đổi những Phần Quà có Giá Trị nhé.\n");
                            break block0;
                        }
                        case 9: {
                            String a = "";
                            if (Rank.bxhdiemhoa.isEmpty()) {
                                a = "Chưa có thông tin.";
                            }
                            for (Rank.Entry4 item : Rank.bxhdiemhoa) {
                                a = a + item.index + ". " + item.name + ": " + item.point1 + " điểm\n";
                            }
                            Server.manager.sendTB(p, "BXH Diệt Boss Chuột", a);
                            break block0;
                        }
                    }
                    break;
                }
                case 5: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 119, "Nhập số hoa muốn làm");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 120, "Nhập số hoa muốn làm");
                            break;
                        }
                        case 2: {
                            Service.sendInputDialog(p, (short) 121, "Nhập số hoa muốn làmt");
                            break;
                        }
                        case 3: {
                            Service.sendInputDialog(p, (short) 122, "Nhập số hoa muốn làm");
                            break;
                        }
                        case 4: {
                            Service.sendInputDialog(p, (short) 123, "Nhập tên nhân vật");
                            break;
                        }
                        case 5: {
                            Service.sendInputDialog(p, (short) 124, "Nhập tên nhân vật");
                            break;
                        }
                        case 6: {
                            Service.sendInputDialog(p, (short) 125, "Nhập tên nhân vật");
                            break;
                        }
                        case 7: {
                            Service.sendInputDialog(p, (short) 118, "Nhập tên nhân vật");
                            break;
                        }
                        case 8: {
                            Server.manager.sendTB(p, "Hướng dẫn", "Cách ghép hoa: \n  - Bó hoa hồng đỏ = 8 Hoa hồng đỏ + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n - Bó hoa hồng vàng = 8 Hoa hồng vàng + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n - Bó hoa hồng xanh = 8 Hoa hồng xanh + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n - Giỏ hoa = 8 Hoa hồng đỏ + 8 Hoa hồng vàng + 8 Hoa hồng xanh + 1 Giấy màu + 1 Ruy băng + 1 Khung tre\n");
                            break;
                        }
                        case 9: {
                           
                            Server.manager.sendTB(p, "Top Hoa", Rank.getStringBXH(5));
                            break;
                        }
                    case 10: {
                             Locale localeEN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeEN);
                Server.manager.sendTB(p, "NsoBlow",
                         "\nTên Nhân Vật : "+ p.c.name
                        +"\ntổng điểm làm hoa: "+ p.c.diemhoa 
                        + "\nđiểm làm hoa đỏ: "+ p.c.diemhoado
                        + "\nđiểm làm hoa vàng: "+ p.c.diemhoavang
                        + "\nđiểm làm hoa xanh: "+ p.c.diemhoaxanh
                        + "\nđiểm số hoa người khác tặng mình: "+ p.c.sohoanhan );
                       break;
                        }
                    case 11: {
                            if (p.luong < 10000) {
                                p.conn.sendMessageLog("Cần 10k lượng để đổi");
                                break;
                            }
                            if (p.c.diemhoa < 5000) {
                                p.conn.sendMessageLog("Bạn cần có 5000 tổng điểm làm hoa");
                                break;
                            }
                            p.c.diemhoa -= 5000;
                            p.upluongMessage(-10000);
                            p.conn.sendMessageLog("Bạn đã đổi thành công rương vip");
                            Item itemup = ItemTemplate.itemDefault(1046);
                            itemup.isLock = true;
                            itemup.quantity = 1;
                            p.c.addItemBag(true, itemup);
                            break;
                        }
                    case 12: {
                            if (p.luong < 10000) {
                                p.conn.sendMessageLog("Cần 10k lượng để đổi");
                                break;
                            }
                            if (p.c.gender !=0) {
                                p.conn.sendMessageLog("chỉ dành cho bạn nữ");
                                break;
                            }
                            if (p.c.sohoanhan < 7000) {
                                p.conn.sendMessageLog("Bạn cần có 7000 điểm số hoa người khác tặng mình");
                                break;
                            }
                            p.c.sohoanhan -= 7000;
                            p.upluongMessage(-10000);
                            p.conn.sendMessageLog("Bạn đã đổi thành công rương vip");
                            Item itemup = ItemTemplate.itemDefault(1046);
                            itemup.isLock = true;
                            itemup.quantity = 1;
                            p.c.addItemBag(true, itemup);
                            break;
                        }
                    }
                    break;
                }
                case 6: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 250, "Nhập số tre xanh muốn làm");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 251, "Nhập số tre xanh muốn làm");
                            break;
                        }
                        case 2: {
                            Server.manager.sendTB(p, "Hướng dẫn", "  - tre xanh trăm đốt = 100 đốt tre xanh + 50000 xu\n - tre xanh vàng đốt = 100 đốt vàng xanh + 100 lượng\n");
                        }
                    }
                    break;
                }
                case 7: {
                    if (p.c.isNhanban) {
                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                        return;
                    }
                    switch (menuId) {
                        case 0: {
                            Service.sendInputDialog(p, (short) 110, "Nhập số lượng bánh Chưng muốn làm :");
                            break;
                        }
                        case 1: {
                            Service.sendInputDialog(p, (short) 111, "Nhập số lượng bánh Tét muốn làm :");
                            break;
                        }
                        case 2: {
                            if (p.luong < 10000) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                                break;
                            }
                            if (p.c.bingo < 100) {
                                Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 100 điểm boss");
                                break;
                            }
                            p.c.bingo -= 100;
                            p.upluongMessage(-10000);
                            p.conn.sendMessageLog("Bạn đã đổi thành công mặt nạ Jack Hollow");
                            Item itemup = ItemTemplate.itemDefault(771);
                            itemup.upgrade = (byte) 16;
                            p.c.addItemBag(true, itemup);
                            break;
                        }
                        case 3: {
                            Server.manager.sendTB(p, "Hướng dẫn", "----------------- Làm Bánh Chưng -----------------\n +, 5 lá dong + 5 nếp + 5 thịt heo + 5 đậu xanh + 5 lạt tre + 50.000 xu + 50.000 yên +100 lượng.\n----------------- Làm Bánh Tét -----------------\n +,  5 lá dong + 5 nếp + 5 đậu xanh + 3 lạt tre + 40.000 xu + 40.000 yên + 50 lượng.\n ---->Bạn đang có: " + p.c.bingo + "Điểm Boss");
                            break;
                        }
                    }
                    break;
                }
//                case 7: {
//                    if (p.c.isNhanban) {
//                        Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
//                        return;
//                    }
//                    switch (menuId) {
//                        case 0: {
//                            Service.sendInputDialog(p, (short) 252, "Nhập số lượng Nước Ép Dưa Hấu muốn làm :");
//                            break;
//                        }
//                        case 1: {
//                            Service.sendInputDialog(p, (short) 253, "Nhập số lượng Nước Ép Mía muốn làm :");
//                            break;
//                        }
//                        case 2: {
//                            Server.manager.sendTB(p, "Hướng dẫn", "  - Nước Ép Dưa Hấu = 5 Dưa hấu + 5 Đá viên + 3 Ly Thủy Tinh + 50000 yên + 40000 xu+ 100 lượng\n - Nước Ép Mía = 5 Mía + 3 Đá viên + 3 Ly Thủy Tinh + 50000 yên + 30000 xu+ 50 lượng");
//                        }
//                    }
//                }
                default: {
                    Service.chatNPC(p, Short.valueOf(npcid), "Chúc mọi người vui vẻ!");
                }
                break;
            }
        }
    }

    public static void BXH(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            
            case 0: {
                switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top Đại gia", Rank.getStringBXH(0));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP ĐẠI GIA:"
                          + "\nngười nào đạt 2 tỷ yên mới tính"
                          + "\nTOP1: 3tr lượng"
                          + "\nTOP2: 2tr lượng"
                          + "\nTOP3: 1tr lượng");
                   break;
                }
                }
                break;}
            
            case 1: {
                switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top Cao Thủ", Rank.getStringBXH(1));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP LEVEL:"
                          + "\nngười nào đạt lv150 mới tính"
                          + "\nTOP1: 1m coin và 1 bí kip tăng 50k dame 50k hpmp 5k né"
                          + "\nTOP2: 700k coin và 1 bí kip tăng 35k dame 35k hpmp 5k né"
                          + "\nTOP3: 500k coin và 1 bí kip tăng 20k dame 20k hpmp 2k né");
                   break;
                }
                }
            break;
            }
            
            case 2: {switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top Gia Tộc", Rank.getStringBXH(2));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP GT:"
                          + "\nHIỆn TẠI CHƯA MỞ"
                          + "\nSẽ MỞ VÀo Ngày 27/2/2023"
                        );
                   break;
                }
                }
                break;
            }
            
            case 3: {
                switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top Hang Động", Rank.getStringBXH(3));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP HD:"
                          + "\nHIỆn TẠI CHƯA MỞ"
                          + "\nSẽ MỞ VÀo Ngày 27/2/2023"
                        );
                   break;
                }
                }
                break;
            }
            
            case 4: {
                switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top sự kiện", Rank.getStringBXH(8));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP SK:"
                          + "\nHIỆn TẠI CHƯA MỞ"
                          + "\nSẽ MỞ VÀo Ngày 27/2/2023"
                        );
                   break;
                }
                }
                break;
            }
            
            case 5: {
                switch (b3) {
                    case 0: {
                Server.manager.sendTB(p, "Top dame", Rank.getStringBXH(10));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP DAME:"
                          + "\nSẽ Chốt Vào Ngày 25/2/2023"
                          + "\nTOP1: 1m coin  và vào phái đại đế và 1 mắt tăng 70k dame 70k hpmp 70k né"
                          + "\nTOP2: 700k coin và 1 mắt tăng 50k dame 50k hpmp 50k né"
                          + "\nTOP3: 500k coin và 1 mắt tăng 30k dame 30k hpmp 30k né");
                   break;
                }
                }
                break;
            }
            
            case 6: {
                switch (b3) {
                    case 0: {
                 Server.manager.sendTB(p, "Top nap", Rank.getStringBXH(11));
                break;
            }
           case 1: {
                  Server.manager.sendTB(p, "BXH", "TOP Nap:"
                          + "\nSẽ Chốt Vào Ngày 25/2/2023"
                          + "\nTOP1: 100m lượng  và vào phái đại đế và 1 ntgt vip tăng 70k dame 70k hpmp 70k né"
                          + "\nTOP2: 70m lượng và 1 mắt tăng 50k dame 50k hpmp 50k né"
                          + "\nTOP3: 50m coin và 1 mắt tăng 30k dame 30k hpmp 30k né");
                   break;
                }
                }
                break;
            }
            
            
        }
    }

    public static void npcCayThong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.level < 40) {
                    p.conn.sendMessageLog("Nhân vật phải trên level 40 mới có thể nhận quà và trang trí.");
                    return;
                }
                if (p.c.isNhanQuaNoel < 1) {
                    p.conn.sendMessageLog("Hôm nay bạn đã nhận quà rồi.");
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.isNhanQuaNoel = 0;
                ++p.c.pointNoel;
                int random = Util.nextInt(0, 2);
                switch (random) {
                    case 0: {
                        int yen = Util.nextInt(500000, 1000000);
                        if (p.status == 1) {
                            p.c.yenTN += (long) (yen /= 2);
                        }
                        p.c.upyenMessage(yen);
                        p.sendAddchatYellow("Bạn nhận được " + yen + " yên.");
                        break block0;
                    }
                    case 1: {
                        int xu = Util.nextInt(100000, 300000);
                        if (p.status == 1) {
                            p.c.xuTN += (long) (xu /= 2);
                        }
                        p.c.upxuMessage(xu);
                        p.sendAddchatYellow("Bạn nhận được " + xu + " xu.");
                        break block0;
                    }
                    case 2: {
                        int luong = Util.nextInt(50, 150);
                        if (p.status == 1) {
                            p.c.luongTN += (long) (luong /= 2);
                        }
                        p.upluongMessage(luong);
                        break block0;
                    }
                }
                break;
            }
            case 1: {
                Item it;
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.level < 40) {
                    p.conn.sendMessageLog("Nhân vật phải trên level 40 mới có thể nhận quà và trang trí.");
                    return;
                }
                if (p.c.quantityItemyTotal(673) < 1) {
                    p.conn.sendMessageLog("Bạn không có đủ Quà trang trí để trang trí cây thông Noel.");
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống để nhận quà");
                    return;
                }
                p.c.pointNoel += 10;
                p.c.removeItemBag(p.c.getIndexBagid(673, false), 1);
                int per = Util.nextInt(300);
                if (per < 1) {
                    it = ItemTemplate.itemDefault(383);
                } else if (per >= 1 && per <= 3) {
                    it = ItemTemplate.itemDefault(775);
                } else {
                    per = Util.nextInt(UseItem.idItemCayThong.length);
                    it = ItemTemplate.itemDefault(UseItem.idItemCayThong[per]);
                }
                it.isLock = false;
                it.quantity = 1;
                p.c.addItemBag(true, it);
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcOngGiaNoen(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        int coin = 3000;
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1008) < 500) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 500 Hoa tuyết tím");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1008, 500);
                        p.coin = p.coin + coin;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        p.conn.sendMessageLog("Bạn đã đổi thành công 500 bí ngô hắc ám ra 3000 coin hãy thoát ra vào lại để cập nhật số coin");
                        break;
                    }
                    case 1: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1008) < 300) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 300 Hoa tuyết tím");
                            break;
                        }
                        p.c.removeItemBags(1008, 300);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công lồng đèn cá voi");
                        Item itemup = ItemTemplate.itemDefault(933);
                        itemup.upgrade = (byte) 16;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 2: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1008) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 Hoa tuyết tím");
                            break;
                        }
                        p.c.removeItemBags(1008, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Malachite");
                        Item itemup = ItemTemplate.itemDefault(970);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1008) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 Hoa tuyết tím");
                            break;
                        }
                        p.c.removeItemBags(1008, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Opal");
                        Item itemup = ItemTemplate.itemDefault(971);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 4: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1008) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 Hoa tuyết tím");
                            break;
                        }
                        p.c.removeItemBags(1008, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Tinh Hoa");
                        Item itemup = ItemTemplate.itemDefault(959);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 5:
                        Server.manager.sendTB(p, "Top Hoa Tuyết Tím", Rank.getStringBXH(7));
                        return;
                    case 6: {
                        Server.manager.sendTB(p, "Hoa Tuyết Tím", "Sau khi Drop boss sẽ rớt 3 Hoa Tuyết Tím, hỏa thạch vương sẽ cho kẻ hạ gục mình 10 Hoa Tuyết Tím"
                                + "\n>Hoa Tuyết Tím sẽ được đưa thẳng vào hành trang người chơi chứ không rơi ra đất tránh trường hợp bị ks<"
                                + "\n-Đổi 3000 coin = 500 Hoa Tuyết Tím"
                                + "\n-Đổi pet cá voi cực vip bằng 300 Hoa Tuyết Tím"
                                + "\n-Đổi 100 đá Malachite = 200 Hoa Tuyết Tím"
                                + "\n-Đổi 100 đá Opal = 200 Hoa Tuyết Tím"
                                + "\n-Đổi 100 đá Tinh Hoa = Hoa Tuyết Tím"
                                + "\n-Khi nhận mỗi Hoa Tuyết Tím bạn sẽ nhận được 1 điểm"
                                + "\n-10 nhẫn giả xuất sắc có tên trên bxh mỗi tuần sẽ nhận được Coin tương ứng"
                                + "\n-Top 1: 50k coin"
                                + "\n-Top 2: 40k coin"
                                + "\n-Top 3: 30k coin"
                                + "\n-Top 4-6: 20k coin"
                                + "\n-Top 7-10: 10k coin"
                                + "\n-Chúc anh em chơi game vui vẻ");
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1010) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 Huyết Băng Hoa");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1010, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công chim băng");
                        Item itemup = ItemTemplate.itemDefault(828);
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 1: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1010) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 Huyết Băng Hoa");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1010, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Malachite");
                        Item itemup = ItemTemplate.itemDefault(970);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 2: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1010) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 Huyết Băng Hoa");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1010, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Opal");
                        Item itemup = ItemTemplate.itemDefault(971);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1010) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 Huyết Băng Hoa");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1010, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Tinh Hoa");
                        Item itemup = ItemTemplate.itemDefault(959);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 4: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1010) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 Huyết Băng Hoa");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1010, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công lồng đèn cá voi");
                        Item itemup = ItemTemplate.itemDefault(933);
                        itemup.upgrade = (byte) 16;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 5: {
                        Server.manager.sendTB(p, "Huyết Băng Hoa", "Những nhẫn giả đạt level 60 trở lên khi farm quái sẽ có tỷ lệ nhỏ rơi ra mảnh ghép huyết băng"
                                + "\n>Tinh anh thủ lĩnh sẽ có tỷ lệ cao rơi mảnh ghép huyết băng<"
                                + "\n-Khi nhấn sử dụng sẽ ghép lại với tỷ lệ 100 mảnh ghép = 1 bí ngô huyết băng hoa"
                                + "\n-Đổi phượng hoàng băng = 5000 huyết băng hoa"
                                + "\n-Đổi 500 đá Malachite = 5000 huyết băng hoa"
                                + "\n-Đổi 500 đá Opal = 5000 huyết băng hoa"
                                + "\n-Đổi 500 đá Tinh Hoa = 5000 huyết băng hoa"
                                + "\n-Đổi pet vip = 5000 huyết băng hoa"
                                + "\n-Chúc anh em chơi game vui vẻ");
                        break;
                    }
                }
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 254, "Nhập số hộp quà muốn làm");
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Khi đánh quái sẽ rơi những nguyên liệu làm hộp quà"
                        + "\n-Mang chúng đến gặp ta để đổi những hộp quà noel"
                        + "\n-Sử dụng hộp quà để nhận những phần quà giá trị"
                        + "\n-Công thức:"
                        + "\n-5 Trái châu"
                        + "\n-5 Dây kim tuyến"
                        + "\n-5 Thiệp giáng sinh"
                        + "\n-4 Bít tất may mắn"
                        + "\n-50k yên, 50k xu, 100 lượng"
                        + "\n-Bít tất may mắn được bán tại npc Gosho"
                        + "\n-Mỗi khi làm hộp quà sẽ được tính 1 điểm top sự kiện"
                        + "\n-Xem top tại npc trưởng làng, những nhẫn giả có tên trên bxh sẽ nhận những phần quà quý giá");
                break;
            }
        }
    }

    public static void npcVuaHung(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200k lượng mới có thể đổi");
                    break;
                }
                if (p.c.quantityItemyTotal(926) < 10000) {
                    p.conn.sendMessageLog("Con phải mang 10k nước mía đến cho ta");
                    break;
                }
                if (p.c.quantityItemyTotal(927) < 10000) {
                    p.conn.sendMessageLog("Con phải mang 10k nước dưa hấu đến cho ta");
                    break;
                }
                p.c.removeItemBags(926, 10000);
                p.c.removeItemBags(927, 10000);
                p.upluongMessage(-200000L);
                p.sendAddchatYellow("Bạn nhận được phượng hoàng băng");
                Item itemup = ItemTemplate.itemDefault(828);
                p.c.addItemBag(true, itemup);
                break;
            }
            case 1: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng mới có thể nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(694) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con phải mang mắt 10 đến để đổi mắt 11");
                    break;
                }
                if (p.c.quantityItemyTotal(845) < 5000) {
                    p.conn.sendMessageLog("Bạn phải thu thập đủ 5000 mảnh ghép để triệu hồi quạ bí ẩn");
                    break;
                }
                p.c.removeItemBags(845, 5000);
                p.c.removeItemBags(694, 1);
                p.upluongMessage(-1000000L);
                p.sendAddchatYellow("Bạn nhận được con mắt mangekyou sharingan của Shisui Uchiha");
                Item itemup = ItemTemplate.itemDefault(840);
                itemup.upgrade = 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 2: {
                if (p.c.quantityItemyTotal(648) < 10000 || p.c.quantityItemyTotal(649) < 10000 || p.c.quantityItemyTotal(650) < 10000 || p.c.quantityItemyTotal(651) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại Huy chương chiến công 10.000 chiếc");
                    break;
                }
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(652);
                p.upluongMessage(-500000L);
                itemup.upgrade = 10;
                p.c.removeItemBags(648, 10000);
                p.c.removeItemBags(649, 10000);
                p.c.removeItemBags(650, 10000);
                p.c.removeItemBags(651, 10000);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 3: {
                if (p.c.quantityItemyTotal(648) < 10000 || p.c.quantityItemyTotal(649) < 10000 || p.c.quantityItemyTotal(650) < 10000 || p.c.quantityItemyTotal(651) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại Huy chương chiến công 10.000 chiếc");
                    break;
                }
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(653);
                p.upluongMessage(-500000L);
                itemup.upgrade = 10;
                p.c.removeItemBags(648, 10000);
                p.c.removeItemBags(649, 10000);
                p.c.removeItemBags(650, 10000);
                p.c.removeItemBags(651, 10000);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 4: {
                if (p.c.quantityItemyTotal(648) < 10000 || p.c.quantityItemyTotal(649) < 10000 || p.c.quantityItemyTotal(650) < 10000 || p.c.quantityItemyTotal(651) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại Huy chương chiến công 10.000 chiếc");
                    break;
                }
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(654);
                p.upluongMessage(-500000L);
                itemup.upgrade = 10;
                p.c.removeItemBags(648, 10000);
                p.c.removeItemBags(649, 10000);
                p.c.removeItemBags(650, 10000);
                p.c.removeItemBags(651, 10000);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 5: {
                if (p.c.quantityItemyTotal(648) < 10000 || p.c.quantityItemyTotal(649) < 10000 || p.c.quantityItemyTotal(650) < 10000 || p.c.quantityItemyTotal(651) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại Huy chương chiến công 10.000 chiếc");
                    break;
                }
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(655);
                p.upluongMessage(-500000L);
                itemup.upgrade = 10;
                p.c.removeItemBags(648, 10000);
                p.c.removeItemBags(649, 10000);
                p.c.removeItemBags(650, 10000);
                p.c.removeItemBags(651, 10000);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 6: {
                if (p.c.quantityItemyTotal(851) < 2000 || p.c.quantityItemyTotal(852) < 2000 || p.c.quantityItemyTotal(853) < 2000 || p.c.quantityItemyTotal(854) < 2000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại châu 2.000 viên");
                    break;
                }
                if (p.c.quantityItemyTotal(647) < 500) {
                    p.conn.sendMessageLog("Bạn cần có 500 rương ma quái");
                    break;
                }
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100k lượng");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(875);
                p.upluongMessage(-100000L);
                p.c.removeItemBags(851, 2000);
                p.c.removeItemBags(852, 2000);
                p.c.removeItemBags(853, 2000);
                p.c.removeItemBags(854, 2000);
                p.c.removeItemBags(647, 500);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 7: {
                if (p.c.quantityItemyTotal(851) < 10000 || p.c.quantityItemyTotal(852) < 10000 || p.c.quantityItemyTotal(853) < 10000 || p.c.quantityItemyTotal(854) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần mỗi loại châu 10.000 viên");
                    break;
                }
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k củ lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(647) < 1500) {
                    p.conn.sendMessageLog("Bạn cần có 1500 rương ma quái");
                    break;
                }
                Item itemup = ItemTemplate.itemDefault(876);
                p.upluongMessage(-500000L);
                p.c.removeItemBags(851, 10000);
                p.c.removeItemBags(852, 10000);
                p.c.removeItemBags(853, 10000);
                p.c.removeItemBags(854, 10000);
                p.c.removeItemBags(647, 1500);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 8: {
                Server.manager.sendTB(p, "Hướng dẫ nè đọc đi", "Bạn phải tích đủ mảnh quạ thông qua việc ăn sự kiện\n>1: Nâng Mắt 11<\n-5000 mảnh quạ bí ẩn\n-1 triệu lượng\n-1 mắt 10\n>2: Đổi ngọc 10<\n>Huy chương chiến công đồng 10k<\n-Huy chương chiến công bạc 10k\n-Huy chương chiến công vàng 10k\n-Huy chương chiến công bạch kim 10k\n-Lượng 500.000\n-Sau khi đổi sẽ nhận được ngọc cấp 10\n>3: rương trang bị<\n>Huyết châu 2k<\n-Thanh Châu 2k\n-Tử Châu 2k\n-Kim Châu 2k\n-rương ma quái 500\n-Lượng 100.000\n-Sau khi đổi sẽ nhận được rương trang bị 11x\n>4: rương Vũ Khí<\n>Huyết châu 10k<\n-Thanh Châu 10k\n-Tử Châu 10k\n-Kim Châu 10k\n-rương ma quái 1500\n-Lượng 500.000\n-Sau khi đổi sẽ nhận được rương vũ khí 11x");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcThanhGiong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.nvtruyentin == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Ngươi ở đâu đến đây ? Ta không quen.");
                    return;
                }
                if (p.c.nvtruyentin != 1) {
                    break;
                }
                p.c.nvtruyentin = 2;
                Service.chatNPC(p, Short.valueOf(npcid), "Ồ. Cảm ơn ngươi đã báo tin cho ta. Hãy về gặp Vua Hùng nhận lấy phấn thưởng.");
            }
        }
    }

    public static void npcSOXO(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                if (p.c.quantityItemyTotal(944) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Thẻ ATM Đâu mà dùng");
                    return;
                }
                Calendar cd = Calendar.getInstance();
                int gio = cd.get(11);
                if (gio > 7 && gio < 17) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Đặt tùy ý từ 0 - 99, nếu con đoán đúng phần thưởng sẽ được x80 lần số tiền con bỏ ra");
                    Service.sendInputDialog(p, (short) 1406, " Mời bạn đặt số");
                    return;
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Thời gian đặt cược XSMB từ 07 - 17 giờ hàng ngày, có kết quả vào lúc 18h50 con nhé");
                break;
            }
            case 1: {
                Server.manager.sendTB(p, "Kết quả XSMB", Rank.getStringBXH(9));
                break;
            }
            case 2: {
                if (p.c.quantityItemyTotal(944) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Thẻ ATM Đâu mà dùng");
                    return;
                }
                String DATE_FORMAT_FILE = "yyyy-MM-dd";
                SimpleDateFormat dateFormatFile = null;
                dateFormatFile = new SimpleDateFormat(DATE_FORMAT_FILE);
                Calendar calender = Calendar.getInstance();
                java.util.Date date = calender.getTime();
                Date dt = null;
                ResultSet red = SQLManager.stat.executeQuery("SELECT * FROM `xoso` WHERE `day`='" + dateFormatFile.format(date) + "' LIMIT 1;");
                if (!red.first()) {
                    p.conn.sendMessageLog("Chưa thống kê \nkết quả ngày " + dateFormatFile.format(date) + " \nvui lòng quay lại sau");
                    return;
                }
                dt = red.getDate("day");
                int rs = red.getInt("code");
                red.close();
                if (((java.util.Date) dt).toString().equals(dateFormatFile.format(date).toString())) {
                    red = SQLManager.stat.executeQuery("SELECT `xoso` FROM `player` WHERE `username`='" + p.username + "' LIMIT 1;");
                    red.first();
                    int numXS = red.getInt("xoso");
                    red = SQLManager.stat.executeQuery("SELECT `coinXS` FROM `player` WHERE `username`='" + p.username + "' LIMIT 1;");
                    red.first();
                    int numcoinXS = red.getInt("coinXS");
                    if (numXS == rs) {
                        p.conn.sendMessageLog("Chúc mừng con đã trúng thưởng số " + numXS);
                        Manager.chatKTG("" + p.c.name + " nhân phẩm thượng thừa đã đoán trúng KQXS ngày " + dateFormatFile.format(date) + " \nvề số " + numXS + ".");
                        p.upluongMessage(numcoinXS * 80);
                        p.xoso = -1;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `xoso`='" + p.xoso + "' WHERE `id` ='" + p.id + "' LIMIT 1;");
                        p.coinXS = -1;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coinXS`='" + p.coinXS + "' WHERE `id` ='" + p.id + "' LIMIT 1;");
                    } else {
                        p.conn.sendMessageLog("Rất tiếc, chúc con may mắn lần sau!");
                        p.xoso = -1;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `xoso`='" + p.xoso + "' WHERE `id` ='" + p.id + "' LIMIT 1;");
                        p.coinXS = -1;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coinXS`='" + p.coinXS + "' WHERE `id` ='" + p.id + "' LIMIT 1;");
                    }
                    if (numXS == -1) {
                        p.conn.sendMessageLog("Con đã nhận kết quả rồi, ngày mai quay trở lại đặt cược tiếp tục nhé!");
                    }
                } else {
                    p.conn.sendMessageLog("Lỗi không xác định!");
                }
                return;
            }
            case 3: {
                Locale localeEN = new Locale("en", "EN");
                NumberFormat en = NumberFormat.getInstance(localeEN);
                Server.manager.sendTB(p, "kết quả XSMB", " - TÀI KHOẢN : " + p.username + "\n+ tên nhân vật : " + p.c.name + "\n+ số đặt : " + p.xoso + "\n+ số lượng đặt : " + p.coinXS + "\n kết quả sẽ có sau 18 giờ 30 phút hàng ngày <3 \n chúc bạn may mắn <3 ");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcATMLTD(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                Service.sendInputDialog(p, (short) 12, "Nhập số coin muốn đổi qua xu - Tỷ lệ 1 coin : 50k xu");
                break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 9, "Nhập số COIN muốn đổi qua lượng - Tỷ lệ: 1 coin = 80 lượng");
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 13, "Nhập số COIN muốn đổi qua yên - Tỷ lệ: 1 coin = 100k yên");
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Thông Tin", " - TÀI KHOẢN : " + p.username + "\n+ tên nhân vật : " + p.c.name + "\n+ số coin : " + p.coin + "\n+ Tỷ lệ đổi xu: 1 coin = 50k xu" + "\n +Tỷ lệ đổi lượng: 10k coin = 800k lượng \n +Tỷ lệ đổi yên: 10k coin = 1 tỷ yên \n Chúc anh em chơi game vui vẻ ");
                break;
            }
        }
    }

    public static void npcLTD(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException {
        switch (menuId) {
            case 0: {
                p.passold = "";
                Service.sendInputDialog(p, (short) 10, "Nhập mật khẩu cũ");
                break;
            }
            case 1: {
                if (p.c.maxluggage >= 120) {
                    p.conn.sendMessageLog("Bạn chỉ có thể nâng tối đa 120 ô hành trang");
                    return;
                }
                if (p.c.levelBag < 3) {
                    p.conn.sendMessageLog("con hãy cắn túi vải cấp 4 rồi đến gặp ta");
                    return;
                }
                if (p.luong < 20000) {
                    p.conn.sendMessageLog("Bạn Cần 20000 Lượng");
                    return;
                }
                p.c.maxluggage = (byte) (p.c.maxluggage + 6);
                p.upluongMessage(-20000L);
                p.conn.sendMessageLog("Hành trang đã mở thêm 6 ô, Số Ô Hành Trang Của Bạn Là " + p.c.maxluggage + " Vui lòng thoát game vào lại để update hành trang ");
                Service.chatNPC(p, Short.valueOf(npcid), "nâng hàng trang thành công. Tự động thoát sau 5 giây để tránh mấy ông đéo đọc hướng dẫn");
                for (int TimeSeconds = 5; TimeSeconds > 0; --TimeSeconds) {
                    Thread.sleep(1000L);
                }
                Client.gI().kickSession(p.conn);
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getEffId(34) == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Phải sử dụng thí luyện thép mới có thể vào.");
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào làng Las khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        p.c.tileMap.leave(p);
                        Map map = Server.maps[165];
                        for (int k = 0; k < map.area.length; k = (int) ((byte) (k + 1))) {
                            if (map.area[k].numplayers >= map.template.maxplayers) {
                                continue;
                            }
                            map.area[k].EnterMap0(p.c);
                            break;
                        }
                        p.endLoad(true);
                        break;
                    }
                    case 1: {
                        if (p.c.isNhanban) {
                            p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.getEffId(34) == null) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Phải sử dụng thí luyện thép mới có thể vào.");
                            return;
                        }
                        if (p.c.pk > 0) {
                            p.sendAddchatYellow("Không thể vào làng Las khi có điểm hiếu chiến lớn hơn 0");
                            return;
                        }
                        p.c.tileMap.leave(p);
                        Map map = Server.maps[169];
                        for (int k = 0; k < map.area.length; k = (int) ((byte) (k + 1))) {
                            if (map.area[k].numplayers >= map.template.maxplayers) {
                                continue;
                            }
                            map.area[k].EnterMap0(p.c);
                            break;
                        }
                        p.endLoad(true);
                        break;
                    }
                }
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcKanata_LoiDai(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    p.c.party.removePlayer(p.c.id);
                }
                p.c.dunId = -1;
                p.c.isInDun = false;
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapKanata);
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.party != null && p.c.party.charID != p.c.id) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải nhóm trưởng, không thể đặt cược");
                    return;
                }
                Service.sendInputDialog(p, (short) 3, "Đặt tiền cược (lớn hơn 1000 xu và chia hết cho 50)");
                break;
            }
            case 2: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Mời đối thủ vào lôi đài\n\n- Đặt tiền cược (Lớn hơn 1000 xu và chia hết cho 50)\n\n- Khi cả 2 đã đặt tiền cược, và số tiền phải thống nhất bằng nhau thì trận so tài mới có thể bắt đầu.\n\n- Khi đã đặt tiền cược, nhưng thoát, mất kết nối hoặc thua cuộc, thì người chơi còn lại sẽ giành chiến thắng\n\n- Số tiền thắng sẽ nhận được sẽ bị trừ phí 5%\n\n- Nếu hết thời gian mà chưa có ai giành chiến thắng thì cuộc so tài sẽ tính hoà, và mỗi người sẽ nhận lại số tiền của mình với mức phí bị trừ 1%");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcdauthan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (menuId) {
            case 0: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.quantityItemyTotal(944) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Thẻ ATM Đâu mà dùng");
                    return;
                }
                if (p.c.isDiemDanh == 0) {
                    if (p.status == 1) {
                        p.upluongMessage(250L);
                        p.c.luongTN += 250L;
                    } else {
                        p.upluongMessage(500L);
                    }
                    p.c.isDiemDanh = 1;
                    Service.chatNPC(p, Short.valueOf(npcid), "Điểm danh thành công, con nhận được 500 lượng.");
                    break;
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay con đã điểm danh rồi, hãy quay lại vào ngày hôm sau nha!");
                break;
            }
            case 1: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.quantityItemyTotal(944) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Thẻ ATM Đâu mà dùng");
                    return;
                }
                if (p.c.isQuaHangDong == 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã nhận thưởng hôm nay rồi!");
                    return;
                }
                if (p.c.countHangDong >= 2) {
                    if (p.status == 1) {
                        p.upluongMessage(750L);
                        p.c.luongTN += 750L;
                    } else {
                        p.upluongMessage(1500L);
                    }
                    p.c.isQuaHangDong = 1;
                    Service.chatNPC(p, Short.valueOf(npcid), "Nhận quà hoàn thành hang động thành công, con nhận được 1500 lượng.");
                    break;
                }
                if (p.c.countHangDong >= 2) {
                    break;
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Con chưa hoàn thành đủ 2 lần đi hang động, hãy hoàn thành đủ 2 lần và quay lại gặp ta đã nhận thường");
                break;
            }
            case 2: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                    return;
                }
                if (p.c.level >= 1 && p.c.checkLevel[0] == 0) {
                    p.updateExp(Level.getMaxExp(10));
                    p.upluongMessage(5000L);
                    p.c.upyenMessage(100000000L);
                    p.c.addItemBag(false, ItemTemplate.itemDefault(222, true));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(539, true));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    p.c.addItemBag(false, ItemTemplate.itemDefault(383, false));
                    Manager.chatKTG("Chúc Mừng người chơi " + p.c.name + " Đã  nhận quà tân thủ  Thành Công ");
                    p.c.checkLevel[0] = 1;
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã nhận quà tân thủ thành công, chúc con trải nghiệm game vui vẻ.");
                } else {
                    Service.chatNPC(p, Short.valueOf(npcid), "con đã nhận quà tân thủ trước đó rồi, không thể nhận lại lần nữa!");
                }
                return;
            }
            case 3: {
                Service.chatNPC(p, Short.valueOf(npcid), "hãy ra ngân hàng để dùng.");
                return;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.status == 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Tài khoản của con chưa được nâng cấp lên CHÍNH THỨC, không thể nhận lại phần thưởng.");
                    return;
                }
                switch (b3) {
                    case 0: {
                        if (p.c.yenTN <= 0L) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con không có yên lưu trữ để nhận lại.");
                            return;
                        }
                        p.c.upyenMessage(p.c.yenTN);
                        p.c.yenTN = 0L;
                        break block0;
                    }
                    case 1: {
                        if (p.c.xuTN <= 0L) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con không có xu lưu trữ để nhận lại.");
                            return;
                        }
                        p.c.upxuMessage(p.c.xuTN);
                        p.c.xuTN = 0L;
                        break block0;
                    }
                    case 2: {
                        if (p.c.luongTN <= 0L) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con không có lượng lưu trữ để nhận lại.");
                            return;
                        }
                        p.upluongMessage(p.c.luongTN);
                        p.c.luongTN = 0L;
                        break block0;
                    }
                    case 3: {
                        if (p.c.expTN <= 0L) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Con không có kinh nghiệm lưu trữ để nhận lại.");
                            return;
                        }
                        p.updateExp(p.c.expTN);
                        p.c.expTN = 0L;
                        break block0;
                    }
                }
                break;
            }
            case 5: {
                if (p.c.isNhanban) {
                    p.conn.sendMessageLog(Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.clone == null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không có phân thân để sử dụng chức năng này.");
                    return;
                }
                Service.startYesNoDlg(p, (byte) 5, "Sau khi lựa chọn, tất cả dữ liệu như trang bị, thú cưỡi, điểm,... của phân thân sẽ bị reset về ban đầu. Hãy chắc chắn rằng bạn đã tháo toàn bộ trang bị của phân thân và xác nhận muốn reset.");
                break;
            }
            case 6: {
                Server.manager.sendTB(p, "Hướng dẫn", "- Vừa vào chơi, hãy đến chỗ ta nhận quà tân thủ bao gồm: 100tr xu, 20k lượng, 100tr yên \n- Mỗi ngày con được điềm danh hàng ngày 1 lần và nhận 500 lượng \n- Nếu mỗi ngày hoàn thành hang động đủ 2 lần con hãy đến chỗ ta và Nhận quà hang động để nhận 1000 lượng\n\n** Lưu ý, nếu là tài khoản trải nghiệm, con chỉ có thể nhận được 1 nửa phần thường từ ta.");
            }
        }
    }

    public static void npcRikudou_ChienTruong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                p.c.typepk = 0;
                Service.ChangTypePkId(p.c, (byte) 0);
                p.c.tileMap.leave(p);
                p.restCave();
                p.changeMap(p.c.mapLTD);
                break;
            }
            case 1: {
                Service.evaluateCT(p.c);
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcKagai_GTC(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (p.c.mapid) {
            case 117: {
                switch (menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte) 0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break block0;
                    }
                    case 1: {
                        Service.chatNPC(p, Short.valueOf(npcid), "Đặt cược");
                        Service.sendInputDialog(p, (short) 8, "Đặt tiền cược (lớn hơn 1000 xu và chia hết cho 50)");
                        break block0;
                    }
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
                break;
            }
            case 118:
            case 119: {
                switch (menuId) {
                    case 0: {
                        p.c.typepk = 0;
                        Service.ChangTypePkId(p.c, (byte) 0);
                        p.c.tileMap.leave(p);
                        p.restCave();
                        p.changeMap(p.c.mapLTD);
                        break block0;
                    }
                    case 1: {
                        Service.evaluateCT(p.c);
                        break block0;
                    }
                }
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcVip(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        short[] nam = new short[]{712, 713, 746, 747, 748, 749, 750, 751, 752};
        short[] nu = new short[]{715, 716, 753, 754, 755, 756, 757, 758, 759};
        block0:
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        if (p.coinnap >= 20000 && p.vip < 1) {
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.gender == 1) {
                                p.c.addItemBag(false, ItemTemplate.itemDefault(712));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(713));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(746));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(747));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(748));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(749));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(750));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(751));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(752));
                            } else {
                                p.c.addItemBag(false, ItemTemplate.itemDefault(715));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(716));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(753));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(754));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(755));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(756));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(757));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(758));
                                p.c.addItemBag(false, ItemTemplate.itemDefault(759));
                            }
                            p.upluongMessage(100000L);
                            p.c.upxuMessage(100000L);
                            p.vip = 1;
                            p.conn.sendMessageLog("Bạn đã nhận vip 1 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 1: {
                        if (p.coinnap >= 50000 && p.vip == 1 && p.vip < 2) {
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.gender == 1) {
                                for (int i = 0; i < 9; i = (int) ((byte) (i + 1))) {
                                    Item itemup = ItemTemplate.itemDefault(nam[i]);
                                    itemup.upgradeNext((byte) 8);
                                    p.c.addItemBag(false, itemup);
                                }
                            } else {
                                for (int i = 0; i < 9; i = (int) ((byte) (i + 1))) {
                                    Item itemup = ItemTemplate.itemDefault(nu[i]);
                                    itemup.upgradeNext((byte) 8);
                                    p.c.addItemBag(false, itemup);
                                }
                            }
                            p.vip = 2;
                            p.conn.sendMessageLog("Bạn đã nhận vip 2 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 2: {
                        if (p.coinnap >= 100000 && p.vip == 2 && p.vip < 3) {
                            if (p.c.getBagNull() < 10) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.gender == 1) {
                                for (int i = 0; i < 9; i = (int) ((byte) (i + 1))) {
                                    Item itemup = ItemTemplate.itemDefault(nam[i]);
                                    itemup.upgradeNext((byte) 16);
                                    p.c.addItemBag(false, itemup);
                                }
                            } else {
                                for (int i = 0; i < 9; i = (int) ((byte) (i + 1))) {
                                    Item itemup = ItemTemplate.itemDefault(nu[i]);
                                    itemup.upgradeNext((byte) 16);
                                    p.c.addItemBag(false, itemup);
                                }
                            }
                            p.vip = 3;
                            p.conn.sendMessageLog("Bạn đã nhận vip 3 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 3: {
                        if (p.coinnap >= 200000 && p.vip == 3 && p.vip < 4) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(786));
                            p.vip = 4;
                            p.conn.sendMessageLog("Bạn đã nhận vip 4 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 4: {
                        short[] ngokhong = new short[]{835, 836};
                        if (p.coinnap >= 500000 && p.vip == 4 && p.vip < 5) {
                            if (p.c.getBagNull() < 4) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            if (p.c.get().nclass == 0) {
                                p.conn.sendMessageLog("Trùm cần nhập học để nhận vip 5");
                                return;
                            }
                            for (int i = 0; i < 2; i = (int) ((byte) (i + 1))) {
                                Item itemup = ItemTemplate.itemDefault(ngokhong[i]);
                                itemup.upgradeNext((byte) 16);
                                p.c.addItemBag(false, itemup);
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(837));
                            if (p.c.get().nclass == 1 || p.c.get().nclass == 3 || p.c.get().nclass == 5) {
                                Item itemup = ItemTemplate.itemDefault(833);
                                if (p.c.get().nclass == 1) {
                                    itemup.sys = 1;
                                } else if (p.c.get().nclass == 3) {
                                    itemup.sys = (byte) 2;
                                } else if (p.c.get().nclass == 5) {
                                    itemup.sys = (byte) 3;
                                }
                                itemup.upgradeNext((byte) 16);
                                p.c.addItemBag(false, itemup);
                            }
                            if (p.c.get().nclass == 2 || p.c.get().nclass == 4 || p.c.get().nclass == 6) {
                                Item itemup = ItemTemplate.itemDefault(834);
                                if (p.c.get().nclass == 2) {
                                    itemup.sys = 1;
                                } else if (p.c.get().nclass == 4) {
                                    itemup.sys = (byte) 2;
                                } else if (p.c.get().nclass == 6) {
                                    itemup.sys = (byte) 3;
                                }
                                itemup.upgradeNext((byte) 16);
                                p.c.addItemBag(false, itemup);
                            }
                            p.vip = 5;
                            p.conn.sendMessageLog("Trùm đã nhận vip 5 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                        break;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.coinnap >= 1000000 && p.vip == 5 && p.vip < 6) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            Item itemup = ItemTemplate.itemDefault(828);
                            p.c.addItemBag(false, itemup);
                            p.vip = 6;
                            p.conn.sendMessageLog("Trùm đã nhận vip 6 thành công");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Bạn không đủ điều kiện nhận VIP");
                    }
                }
                break;
            }
            case 1: {
                switch (p.vip) {
                    case 1: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 1) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 10;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(500L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Báo danh VIP 1 thành công, con nhận được 500 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        break;
                    }
                    case 2: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 2) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 20;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(1000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm báo danh VIP 2 thành công, trùm nhận được 1000 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        break;
                    }
                    case 3: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 3) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 30;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(5000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm báo danh VIP 3 thành công, trùm nhận được 5000 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        break;
                    }
                    case 4: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 2) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 40;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(10000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm báo danh VIP 4 thành công, trùm nhận được 5000 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        break;
                    }
                    case 5: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 6) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 50;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(20000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm báo danh VIP 5 thành công, trùm nhận được 5000 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                        break;
                    }
                    case 6: {
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.isDiemDanh == 0) {
                            if (p.c.getBagNull() < 6) {
                                Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_ENOUGH_BAG);
                                return;
                            }
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            p.c.addItemBag(false, ItemTemplate.itemDefault(540));
                            Item itemup = ItemTemplate.itemDefault(789);
                            itemup.quantity = 100;
                            p.c.addItemBag(false, itemup);
                            p.upluongMessage(40000L);
                            p.c.isDiemDanh = 1;
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm báo danh VIP 6 thành công, trùm nhận được 5000 lượng.");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Hôm nay trùm đã Báo danh VIP, hãy quay lại vào ngày hôm sau nha!");
                    }
                }
                break;
            }
            case 2: {
                switch (p.vip) {
                    case 1: {
                        if (p.c.yen >= 500000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-500000L);
                            p.c.upxuMessage(500000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                        break;
                    }
                    case 2: {
                        if (p.c.yen >= 1000000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-1000000L);
                            p.c.upxuMessage(1000000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                        break;
                    }
                    case 3: {
                        if (p.c.yen >= 2000000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-2000000L);
                            p.c.upxuMessage(2000000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                        break;
                    }
                    case 4: {
                        if (p.c.yen >= 2000000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-5000000L);
                            p.c.upxuMessage(5000000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                        break;
                    }
                    case 5: {
                        if (p.c.yen >= 15000000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-15000000L);
                            p.c.upxuMessage(15000000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                        break;
                    }
                    case 6: {
                        if (p.c.yen >= 30000000 && p.c.isQuaHangDong == 0) {
                            p.c.isQuaHangDong = 1;
                            p.c.upyenMessage(-30000000L);
                            p.c.upxuMessage(30000000L);
                            Service.chatNPC(p, Short.valueOf(npcid), "Đổi xu thành công!");
                            break;
                        }
                        if (p.c.isQuaHangDong != 0) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã đổi xu rồi, xin quay lại vào ngày mai!");
                            break;
                        }
                        Service.chatNPC(p, Short.valueOf(npcid), "Không đủ yên!");
                    }
                }
                break;
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.vip < 1) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Vip 1 mới được sử dụng chức năng bật tắt exp");
                            return;
                        }
                        if (p.c.isNhanban) {
                            Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                            return;
                        }
                        if (p.c.level == 1) {
                            p.conn.sendMessageLog("Không thể thực hiện thao tác này..");
                            return;
                        }
                        if (p.c.get().exptype == 1) {
                            p.c.get().exptype = 0;
                            p.sendAddchatYellow("Đã tắt nhận exp.");
                            break block0;
                        }
                        p.c.get().exptype = 1;
                        p.sendAddchatYellow("Đã bật nhận exp.");
                        break block0;
                    }
                    case 1: {
                        if (p.vip < 2) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Vip 2 mới được sử dụng chức năng bật tắt exp");
                            return;
                        }
                        if (p.c.maxluggage >= 120) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Trùm đã lên 120 ô hành trang rồi");
                            break block0;
                        }
                        if (p.c.levelBag < 3) {
                            p.conn.sendMessageLog("Bạn cần sử dụng túi vải cấp 3 để mở thêm hành trang");
                            break block0;
                        }
                        p.c.levelBag = (byte) 4;
                        p.c.maxluggage = (byte) 120;
                        p.conn.sendMessageLog("Nâng thành công, bạn cần phải thoát game để lưu");
                    }
                }
            }
        }
    }

    public static void npcMiNuong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        block0:
        switch (menuId) {
            case 0: {
                switch (p.vxLuong) {
                    case 0: {
                        if (p.vip < 1) {
                            p.conn.sendMessageLog("Bạn cần là vip để tham gia");
                            break block0;
                        }
                        if (p.luong < 77000) {
                            p.conn.sendMessageLog("Lượt 1 cần 77k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 6, "Trùm sẽ mất 77k lượng để nhận lại ngẫu nhiên 107k đến 177k lượng?");
                        break block0;
                    }
                    case 1: {
                        if (p.vip < 2) {
                            p.conn.sendMessageLog("Bạn cần tối thiểu vip 2 để tham gia");
                            break block0;
                        }
                        if (p.luong < 277000) {
                            p.conn.sendMessageLog("Lượt 2 cần 277k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 7, "Trùm sẽ mất 277k lượng để nhận lại ngẫu nhiên 377k đến 577k lượng?");
                        break block0;
                    }
                    case 2: {
                        if (p.vip < 3) {
                            p.conn.sendMessageLog("Bạn cần tối thiểu vip 3 để tham gia");
                            break block0;
                        }
                        if (p.luong < 777000) {
                            p.conn.sendMessageLog("Lượt 3 cần 777k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 9, "Trùm sẽ mất 777k lượng để nhận lại ngẫu nhiên 1000k đến 1777k lượng?");
                        break block0;
                    }
                    case 3: {
                        if (p.vip < 4) {
                            p.conn.sendMessageLog("Bạn cần tối thiểu vip 4 để tham gia");
                            break block0;
                        }
                        if (p.luong < 2077000) {
                            p.conn.sendMessageLog("Lượt 4 cần 2077k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 10, "Trùm sẽ mất 2077k lượng để nhận lại ngẫu nhiên 2777k đến 4377k lượng?");
                        break block0;
                    }
                    case 4: {
                        if (p.vip < 5) {
                            p.conn.sendMessageLog("Bạn cần tối thiểu vip 5 để tham gia");
                            break block0;
                        }
                        if (p.luong < 5077000) {
                            p.conn.sendMessageLog("Lượt 5 cần 5077k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 11, "Trùm sẽ mất 5077k lượng để nhận lại ngẫu nhiên 6777k đến 7777k lượng?");
                        break block0;
                    }
                    case 5: {
                        if (p.vip < 6) {
                            p.conn.sendMessageLog("Bạn cần tối thiểu vip 6 để tham gia");
                            break block0;
                        }
                        if (p.luong < 10777000) {
                            p.conn.sendMessageLog("Lượt 5 cần 10777k lượng");
                            break block0;
                        }
                        Service.startYesNoDlg(p, (byte) 12, "Trùm sẽ mất 10777k lượng để nhận lại ngẫu nhiên 17777k đến 27777k lượng?");
                        break block0;
                    }
                }
                p.conn.sendMessageLog("Bạn đã quay full mốc");
                break;
            }
            case 1: {
                short[] phale = new short[]{1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
                int[] moneyUpMat = new int[]{10000000, 50000000, 70000000, 100000000, 150000000, 200000000, 300000000, 350000000, 400000000, 500000000};
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                Item itemup = p.c.ItemBody[11];
                if (p.c.ItemBody[11] == null) {
                    p.conn.sendMessageLog("Bạn cần đeo mặt nạ Ngộ Không hoặc sumimura");
                    return;
                }
                System.out.println(p.c.ItemBody[11].id);
                if (p.c.ItemBody[11].id != 786 && p.c.ItemBody[11].id != 837) {
                    p.conn.sendMessageLog("Bạn cần đeo mặt nạ Ngộ Không hoặc sumimura");
                    return;
                }
                if (itemup.upgrade > 9) {
                    p.conn.sendMessageLog("Mặt nạ đạt cấp tối đa");
                    return;
                }
                if (p.c.quantityItemyTotal(789) < phale[itemup.upgrade]) {
                    p.conn.sendMessageLog("Bạn không đủ " + phale[itemup.upgrade] + " pha lê để nâng mặt nạ!");
                    return;
                }
                if (p.c.quantityItemyTotal(789) < phale[itemup.upgrade]) {
                    p.conn.sendMessageLog("Bạn không đủ " + phale[itemup.upgrade] + " pha lê để nâng mặt nạ!");
                    return;
                }
                if (p.c.xu < moneyUpMat[p.c.ItemBody[11].upgrade]) {
                    p.conn.sendMessageLog("Bạn không đủ " + moneyUpMat[itemup.upgrade] + " xu để nâng cấp mặt nạ");
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang của trùm không đủ chỗ trống để nhận mặt nạ!");
                    return;
                }
                p.c.upxuMessage(-moneyUpMat[itemup.upgrade]);
                p.c.removeItemBags(789, phale[itemup.upgrade]);
                p.c.removeItemBody((byte) 11);
                itemup.quantity = 1;
                itemup.ncMatNa((byte) 1);
                p.c.addItemBag(false, itemup);
                break;
            }
            case 2: {
                int i;
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                if (p.c.quantityItemyTotal(838) < 1) {
                    p.conn.sendMessageLog("Bạn không đủ có Vô Môn Lệnh");
                    return;
                }
                Skill skill2 = new Skill();
                p.c.skill.add(skill2);
                p.c.nclass = 0;
                p.c.KSkill = new byte[3];
                p.c.OSkill = new byte[5];
                for (i = 0; i < p.c.KSkill.length; i = (int) ((byte) (i + 1))) {
                    p.c.clone.KSkill[i] = -1;
                }
                for (i = 0; i < p.c.OSkill.length; i = (int) ((byte) (i + 1))) {
                    p.c.OSkill[i] = -1;
                }
                p.c.CSkill = (short) -1;
                p.c.removeItemBags(838, 1);
                p.conn.sendMessageLog("Bạn chuyển về vô môn phái thành công");
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                if (p.c.isNhanban) {
                    Service.chatNPC(p, Short.valueOf(npcid), Language.NOT_FOR_PHAN_THAN);
                    return;
                }
                Item itemup = p.c.ItemBody[1];
                if (itemup == null) {
                    p.conn.sendMessageLog("Bạn chưa mang vũ khí");
                    return;
                }
                if (p.c.getBagNull() < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang không đủ chỗ trống");
                    return;
                }
                if (itemup.VKTop() == 300) {
                    p.c.removeItemBody((byte) 1);
                    Item tang = ItemTemplate.itemDefault(839);
                    tang.quantity = 300;
                    p.c.addItemBag(false, tang);
                    break;
                }
                if (itemup.VKTop() == 500) {
                    p.c.removeItemBody((byte) 1);
                    Item tang = ItemTemplate.itemDefault(839);
                    tang.quantity = 500;
                    p.c.addItemBag(false, tang);
                    break;
                }
                p.conn.sendMessageLog("Bạn không đeo đồ top");
                break;
            }
            case 5: {
                if (p.c.level < 150) {
                    Service.chatNPC(p, Short.valueOf(npcid), "anh em đã đạt cấp 150 đâu???\nKhi nào đủ tự tin hãy quay lại gặp ta nhé.! \nMày hãy cố gắng nhiều lên anh em..!");
                    return;
                }
                if (p.c.expCS < 2000000000L) {
                    p.conn.sendMessageLog("Anh em không đủ 2 tỷ exp chuyển sinh");
                    return;
                }
                if (p.c.quantityItemyTotal(843) < 4000) {
                    p.conn.sendMessageLog("Anh em không đủ 4000 bíp cải lão");
                    return;
                }
                if (p.c.quantityItemyTotal(842) < 10) {
                    p.conn.sendMessageLog("Anh em không đủ 10 chuyển sinh đan");
                    return;
                }
                if (p.c.chuyenSinh == 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "server chỉ mới cho chuyển sinh 1, sẽ cập nhật sau");
                    return;
                }
                if (p.luong < 5000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang con ko có đủ học phí 5tr lượng để ta mua cafe sáng.\nHãy đi săn boss và kiếm đủ lượng để chuyển sinh nhé anh em yêu quý của ta ơi..!");
                    return;
                }
                if (p.c.xu < 50000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang con ko có đủ học phí 100tr xu để ta mua cafe sáng.\nHãy đi săn boss và kiếm đủ lượng để chuyển sinh nhé anh em yêu quý của ta ơi..!");
                    return;
                }
                p.c.removeItemBags(843, 4000);
                p.c.removeItemBags(842, 10);
                p.c.expCS -= 2000000000L;
                p.c.chuyenSinh = (byte) (p.c.chuyenSinh + 1);
                p.updateExp(Level.getMaxExp(10) - p.c.exp);
                p.upluongMessage(-5000000L);
                p.c.upxuMessage(-50000000L);
                Manager.chatKTG("Chúc mừng anh: " + p.c.name + " đã đạt cảnh giới chuyển sinh 1. Chúng ta hãy cùng " + p.c.name + " quay lại tuổi thơ dữ dội và viết lên 1 hành trình mới đầy vẻ vang nào. Anh em nhìn " + p.c.name + " mà học hỏi nhé.!");
                break;
            }
            case 6: {
                if (p.vip < 1) {
                    p.conn.sendMessageLog("Bạn cần tối thiểu vip 1 để tham gia");
                    break;
                }
                Service.startYesNoDlg(p, (byte) 13, "Trùm sẽ xoá sạch rương đồ của chính mình?");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcHienNhan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.lvhnt != 0) {
                    p.conn.sendMessageLog("con đã học Hiền nhân rồi");
                    return;
                }
                if (p.c.exphnt < 5000000L) {
                    p.conn.sendMessageLog("Không đủ 5 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 100000) {
                    p.conn.sendMessageLog("Mày cần 100k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-100000L);
                p.c.exphnt -= 5000000L;
                p.c.lvhnt = 1;
                p.conn.sendMessageLog("con đã học thành công Hiền nhân hiện tại đang là lv1");
                break;
            }
            case 1: {
                if (p.c.lvhnt != 1) {
                    p.conn.sendMessageLog("Học Hiền nhân đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 10000000L) {
                    p.conn.sendMessageLog("Không đủ 10 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 200000) {
                    p.conn.sendMessageLog("Mày cần 200k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-200000L);
                p.c.exphnt -= 10000000L;
                p.c.lvhnt = 2;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv2");
                break;
            }
            case 2: {
                if (p.c.lvhnt != 2) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 2 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 15000000L) {
                    p.conn.sendMessageLog("Không đủ 15 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 300000) {
                    p.conn.sendMessageLog("Mày cần 300k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-300000L);
                p.c.exphnt -= 15000000L;
                p.c.lvhnt = 3;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv3");
                break;
            }
            case 3: {
                if (p.c.lvhnt != 3) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 3 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 20000000L) {
                    p.conn.sendMessageLog("Không đủ 20 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 400000) {
                    p.conn.sendMessageLog("Mày cần 400k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-400000L);
                p.c.exphnt -= 20000000L;
                p.c.lvhnt = 4;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv4");
                break;
            }
            case 4: {
                if (p.c.lvhnt != 4) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 4 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 25000000L) {
                    p.conn.sendMessageLog("Không đủ 25 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 500000) {
                    p.conn.sendMessageLog("Mày cần 500k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-500000L);
                p.c.exphnt -= 25000000L;
                p.c.lvhnt = 5;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv5");
                break;
            }
            case 5: {
                if (p.c.lvhnt != 5) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 5 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 30000000L) {
                    p.conn.sendMessageLog("Không đủ 30 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 600000) {
                    p.conn.sendMessageLog("Mày cần 600k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-600000L);
                p.c.exphnt -= 30000000L;
                p.c.lvhnt = 6;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv6");
                break;
            }
            case 6: {
                if (p.c.lvhnt != 6) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 6 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 35000000L) {
                    p.conn.sendMessageLog("Không đủ 35 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 700000) {
                    p.conn.sendMessageLog("Mày cần 700k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-700000L);
                p.c.exphnt -= 35000000L;
                p.c.lvhnt = 7;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv7");
                break;
            }
            case 7: {
                if (p.c.lvhnt != 7) {
                    p.conn.sendMessageLog("Nâng kinh mạch lên cấp 7 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 40000000L) {
                    p.conn.sendMessageLog("Không đủ 40 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 800000) {
                    p.conn.sendMessageLog("Mày cần 800k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-800000L);
                p.c.exphnt -= 40000000L;
                p.c.lvhnt = 8;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv8");
                break;
            }
            case 8: {
                if (p.c.lvhnt != 8) {
                    p.conn.sendMessageLog("Nâng Hiền nhân lên cấp 8 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.exphnt < 50000000L) {
                    p.conn.sendMessageLog("Không đủ 50 triệu EXP Hiền nhân để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 1000000) {
                    p.conn.sendMessageLog("Mày cần 1 triệu lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-1000000L);
                p.c.exphnt -= 50000000L;
                p.c.lvhnt = 9;
                p.conn.sendMessageLog("con đã nâng thành công Hiền nhân hiện tại đang là lv9");
                break;
            }
            case 11: {
                Server.manager.sendTB(p, "Hiền nhân thuật", "Bạn phải tích đủ exp kinh mạch thông qua việc đánh tinh anh, thủ lĩnh, boss\n>Hiền nhân<\n-lv1 cần 5 triệu exp Hiền nhân và 100k lượng\n-lv2 cần 10 triệu exp Hiền nhân và 200k lượng\n-lv3 cần 15 triệu exp Hiền nhân và 300k lượng\n-lv4 cần 20 triệu exp Hiền nhân và 400k lượng\n-lv5 cần 25 triệu exp Hiền nhân và 500k lượng\n-lv6 cần 30 triệu exp Hiền nhân và 600k lượng\n-lv7 cần 35 triệu exp Hiền nhân và 700k lượng\n-lv8 cần 40 triệu exp Hiền nhân và 800k lượng\n-lv9 cần 50 triệu exp Hiền nhân và 1 triệu lượng\n-thành công Hiền nhân thuật sẽ lên lv và nhận đc hiệu ứng tương ứng");
                break;
            }
            case 9: {
                p.Hiennhan();
                break;
            }
            case 10: {
                p.conn.sendMessageLog("Con đang có : " + p.c.exphnt + " Exp Hiền nhân");
            }
        }
    }

    public static void npcBachhao(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.lvbht != 0) {
                    p.conn.sendMessageLog("con đã học Bách hào thuật rồi");
                    return;
                }
                if (p.c.expbht < 5000000L) {
                    p.conn.sendMessageLog("Không đủ 5 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 100000) {
                    p.conn.sendMessageLog("Mày cần 100k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-100000L);
                p.c.expbht -= 5000000L;
                p.c.lvbht = 1;
                p.conn.sendMessageLog("con đã học thành công Bách hào thuật hiện tại đang là lv1");
                break;
            }
            case 1: {
                if (p.c.lvbht != 1) {
                    p.conn.sendMessageLog("Học Bách hào thuật đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 10000000L) {
                    p.conn.sendMessageLog("Không đủ 10 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 200000) {
                    p.conn.sendMessageLog("Mày cần 200k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-200000L);
                p.c.expbht -= 10000000L;
                p.c.lvbht = 2;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv2");
                break;
            }
            case 2: {
                if (p.c.lvbht != 2) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 2 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 15000000L) {
                    p.conn.sendMessageLog("Không đủ 15 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 300000) {
                    p.conn.sendMessageLog("Mày cần 300k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-300000L);
                p.c.expbht -= 15000000L;
                p.c.lvbht = 3;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv3");
                break;
            }
            case 3: {
                if (p.c.lvbht != 3) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 3 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 20000000L) {
                    p.conn.sendMessageLog("Không đủ 20 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 400000) {
                    p.conn.sendMessageLog("Mày cần 400k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-400000L);
                p.c.expbht -= 20000000L;
                p.c.lvbht = 4;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv4");
                break;
            }
            case 4: {
                if (p.c.lvbht != 4) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 4 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 25000000L) {
                    p.conn.sendMessageLog("Không đủ 25 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 500000) {
                    p.conn.sendMessageLog("Mày cần 500k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-500000L);
                p.c.expbht -= 25000000L;
                p.c.lvbht = 5;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv5");
                break;
            }
            case 5: {
                if (p.c.lvbht != 5) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 5 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 30000000L) {
                    p.conn.sendMessageLog("Không đủ 30 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 600000) {
                    p.conn.sendMessageLog("Mày cần 600k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-600000L);
                p.c.expbht -= 30000000L;
                p.c.lvbht = 6;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv6");
                break;
            }
            case 6: {
                if (p.c.lvbht != 6) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 6 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 35000000L) {
                    p.conn.sendMessageLog("Không đủ 35 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 700000) {
                    p.conn.sendMessageLog("Mày cần 700k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-700000L);
                p.c.expbht -= 35000000L;
                p.c.lvbht = 7;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv7");
                break;
            }
            case 7: {
                if (p.c.lvbht != 7) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 7 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 40000000L) {
                    p.conn.sendMessageLog("Không đủ 40 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 800000) {
                    p.conn.sendMessageLog("Mày cần 800k lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-800000L);
                p.c.expbht -= 40000000L;
                p.c.lvbht = 8;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv8");
                break;
            }
            case 8: {
                if (p.c.lvbht != 8) {
                    p.conn.sendMessageLog("Nâng Bách hào thuật lên cấp 8 đi rồi đến gặp tao để nâng");
                    return;
                }
                if (p.c.expbht < 50000000L) {
                    p.conn.sendMessageLog("Không đủ 50 triệu EXP Bách hào thuật để nâng, hãy đi đánh tinh anh thủ lĩnh boss rồi quay lại đây tao chỉ cho");
                    return;
                }
                if (p.luong < 1000000) {
                    p.conn.sendMessageLog("Mày cần 1 triệu lượng mới được học con nhé");
                    return;
                }
                p.upluongMessage(-1000000L);
                p.c.expbht -= 50000000L;
                p.c.lvbht = 9;
                p.conn.sendMessageLog("con đã nâng thành công Bách hào thuật hiện tại đang là lv9");
                break;
            }
            case 11: {
                Server.manager.sendTB(p, "Bách hào thuật", "Bạn phải tích đủ exp Bách hào thuật thông qua việc đánh tinh anh, thủ lĩnh, boss\n>Bách hào thuật<\n-lv1 cần 5 triệu exp Bách hào thuật và 100k lượng\n-lv2 cần 10 triệu exp Bách hào thuật và 200k lượng\n-lv3 cần 15 triệu exp Bách hào thuật và 300k lượng\n-lv4 cần 20 triệu exp Bách hào thuật và 400k lượng\n-lv5 cần 25 triệu exp Bách hào thuật và 500k lượng\n-lv6 cần 30 triệu exp Bách hào thuật và 600k lượng\n-lv7 cần 35 triệu exp Bách hào thuật và 700k lượng\n-lv8 cần 40 triệu exp Bách hào thuật và 800k lượng\n-lv9 cần 50 triệu exp Bách hào thuật và 1 triệu lượng\n-thành công Bách hào thuật sẽ lên lv và nhận đc hiệu ứng tương ứng");
                break;
            }
            case 9: {
                p.BachHaoThuat();
                break;
            }
            case 10: {
                p.conn.sendMessageLog("Con đang có : " + p.c.expbht + " Exp Bách hào thuật");
            }
        }
    }

    public static void Luyenbikiep(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.quantityItemyTotal(251) < 500) {
                    p.conn.sendMessageLog("Chuẩn bị 500 mảnh giấy vụn rồi đến gặp ta");
                    return;
                }
                if (p.c.getBagNull() == 0) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.c.level < 60) {
                    p.conn.sendMessageLog("Yêu cầu lever 60");
                    return;
                }
                if (p.luong < 5000) {
                    p.conn.sendMessageLog("Bạn không có đủ 5000 lượng");
                    return;
                }
                Item it = ItemTemplate.itemDefault(396 + p.c.nclass);
                it.setLock(true);
                p.c.addItemBag(true, it);
                p.upluongMessage(-5000L);
                p.c.removeItemBags(251, 500);
                break;
            }
            case 1: {
                if (p.c.ItemBody[15] == null) {
                    p.conn.sendMessageLog("Bạn phải đeo bí kiếp mới có thể luyện bí kiếp");
                    return;
                }
                if (p.c.ItemBody[15].upgrade >= 1) {
                    p.conn.sendMessageLog("Mày chỉ được đổi bí kíp +0 thôi nâng cấp rồi tao đéo cho đổi");
                    return;
                }
                if (p.c.getBagNull() == 0) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.luong < 5000) {
                    p.conn.sendMessageLog("Bạn không có đủ 1500 lượng");
                    return;
                }
                Item it = ItemTemplate.itemDefault(396 + p.c.nclass);
                int a = 0;
                for (int i = 0; i < GameSrc.randomOption.length; ++i) {
                    if (Util.nextInt(1, 10) >= 5) {
                        continue;
                    }
                    it.options.add(new Option(GameSrc.randomOption[i], Util.nextInt(GameSrc.randomParam[i], GameSrc.tileXuatHien[i] * 50 / 100)));
                    ++a;
                }
                it.setLock(true);
                p.c.addItemBag(true, it);
                p.c.removeItemBody((byte) 15);
                p.upluongMessage(-5000L);
                if (a > 5) {
                    p.conn.sendMessageLog("Game dễ vl chọn được bí kíp ngon nha mày");
                    break;
                }
                if (a > 2) {
                    p.conn.sendMessageLog("Cũng tàm tạm dùng cũng được đấy");
                    break;
                }
                p.conn.sendMessageLog("Tại số mày đen vãi L đấy bốc phải hàng lỏm, đéo phải tại tao đâu !");
                break;
            }
            case 2: {
                if (p.c.ItemBody[15] == null) {
                    p.conn.sendMessageLog("Bạn phải đeo bí kiếp mới có thể nâng cấp bí kiếp");
                    return;
                }
                if (p.c.getBagNull() == 0) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.luong < 25000) {
                    p.conn.sendMessageLog("Bạn không có đủ 25000 lượng");
                    return;
                }
                if (p.c.quantityItemyTotal(251) < 20) {
                    p.conn.sendMessageLog("Mang 20 mảnh giấy vụn thì tao mới viết nâng cấp được chứ.");
                    return;
                }
                Item it = p.c.ItemBody[15];
                if (it.getUpgrade() >= 16) {
                    p.conn.sendMessageLog("Bí kiếp đã đạt cấp tối đa");
                    return;
                }
                if (GameSrc.tilenang[it.getUpgrade()] >= Util.nextInt(100)) {
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param += option.param * 8 / 100;
                    }
                    it.setUpgrade(it.getUpgrade() + 1);
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("ngon lên rồi");
                    p.c.removeItemBody((byte) 15);
                } else {
                    p.conn.sendMessageLog("Mày đen vãi lồn nộp tiền ngu ra đây");
                }
                p.upluongMessage(-25000L);
                p.c.removeItemBags(251, 20);
                break;
            }
            case 3: {
                p.conn.sendMessageLog("Tao mới trộm trong trường được 1 tạ bí kíp, mày có muốn đổi không, mày chỉ cần trả cho tao ít ngân lượng và mảnh giấy vụn.");
            }
        }
    }

    public static void npcChauBac(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 50000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 50k lượng phí gia nhập đảng");
                    break;
                }
                if (p.c.quantityItemyTotal(879) < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 5000 mảnh liềm");
                    break;
                }
                if (p.c.quantityItemyTotal(880) < 5000) {
                    p.conn.sendMessageLog("Bạn cần có 5000 mảnh búa");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 100) {
                    p.conn.sendMessageLog("Bạn cần bổ sung 100 năng lượng cho não");
                    break;
                }
                p.c.removeItemBags(879, 5000);
                p.c.removeItemBags(880, 5000);
                p.c.removeItemBags(881, 100);
                p.upluongMessage(-50000L);
                p.sendAddchatYellow("Bạn nhận được chứng nhận người con của đảng");
                Item itemup = ItemTemplate.itemDefault(878);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Người chơi " + p.c.name + " vừa trở thành con dân của Đảng và nhận được " + ItemTemplate.ItemTemplateId((int) 878).name);
                break;
            }
            case 1: {
                Server.manager.sendTB(p, "Hướng dẫn nè đọc đi", "Bạn phải tích đủ mảnh thông qua việc đánh quái\n>1: Gia nhập Đảng<\n-5000 mảnh búa-up quái rơi\n-5000 mảnh liềm-up quái rơi\n-100 não-boss ldgt 1 boss rơi 10 não\n-100k lượng\n>Sau khi đổi thành công sẽ nhận được chứng nhận của đảng có hạn 2 tuần<");
                break;
            }
            case 2: {
                p.conn.sendMessageLog("Bạn có muốn gia nhập đảng cùng mình không nào?");
            }
        }
    }

    public static void npcXebeTong(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 1000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1000 lượng");
                    break;
                }
                if (p.c.xu < 4000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 4 triệu xu");
                    break;
                }
                if (p.c.quantityItemyTotal(883) < 600) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần trộn 600 phần cát");
                    break;
                }
                if (p.c.quantityItemyTotal(885) < 500) {
                    p.conn.sendMessageLog("Cần trộn 500 phần xi măng");
                    break;
                }
                if (p.c.quantityItemyTotal(886) < 1000) {
                    p.conn.sendMessageLog("Bạn cần thêm 1000 xô nước");
                    break;
                }
                if (p.c.quantityItemyTotal(889) < 100) {
                    p.conn.sendMessageLog("Bạn cần thêm 100 xe rùa");
                    break;
                }
                p.c.removeItemBags(883, 600);
                p.c.removeItemBags(885, 500);
                p.c.removeItemBags(886, 1000);
                p.c.removeItemBags(889, 100);
                p.upluongMessage(-1000L);
                p.c.upxuMessage(-4000000L);
                p.c.upyenMessage(-4000000L);
                p.c.pointBossChuot += 10;
                p.sendAddchatYellow("Bạn nhận đc 10 điểm hướng nghiệp");
                Item itemup = ItemTemplate.itemDefault(887);
                itemup.quantity = 100;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 1: {
                if (p.luong < 2000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2000 lượng");
                    break;
                }
                if (p.c.xu < 6000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 6 triệu xu");
                    break;
                }
                if (p.c.quantityItemyTotal(883) < 1200) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần trộn 1200 phần cát");
                    break;
                }
                if (p.c.quantityItemyTotal(885) < 1000) {
                    p.conn.sendMessageLog("Cần trộn 1000 phần xi măng");
                    break;
                }
                if (p.c.quantityItemyTotal(884) < 1000) {
                    p.conn.sendMessageLog("Bạn cần thêm 1000 xẻng sỏi");
                    break;
                }
                if (p.c.quantityItemyTotal(886) < 2000) {
                    p.conn.sendMessageLog("Bạn cần thêm 2000 xô nước");
                    break;
                }
                if (p.c.quantityItemyTotal(890) < 100) {
                    p.conn.sendMessageLog("Bạn cần thêm 100 Sắt làm cốt thép");
                    break;
                }
                p.c.removeItemBags(883, 1200);
                p.c.removeItemBags(885, 1000);
                p.c.removeItemBags(884, 1000);
                p.c.removeItemBags(886, 2000);
                p.c.removeItemBags(890, 100);
                p.c.upxuMessage(-6000000L);
                p.c.upyenMessage(-6000000L);
                p.upluongMessage(-2000L);
                p.c.pointBossChuot += 20;
                p.sendAddchatYellow("Bạn nhận đc 20 điểm hướng nghiệp");
                Item itemup = ItemTemplate.itemDefault(888);
                itemup.quantity = 100;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 2: {
                Server.manager.sendTB(p, "Hướng Nghiệp", "Bạn phải tích vật liệu thông qua farm quái\n>Hướng nghiệp<\n-Bạn đang có : " + p.c.pointBossChuot + " Điểm hướng nghiệp\n>Trộn vữa<\n-600 phần cát\n-500 phần xi măng\n-1000 xô nước\n-100 xe rùa\n-4 triệu xu, yên\n-1000 lượng\n>Sau khi đổi thành công sẽ nhận được 100 xe rùa vữa và 10 điểm hướng nghiệp<\n>Trộn Bê Tông<\n-1200 phần cát\n-1000 phần xi măng\n-1000 xẻng sỏi\n-2000 xô nước\n-100 sắt\n-6 triệu xu, yên\n-2000 lượng\n>Sau khi đổi thành công sẽ nhận được 100 Tấm Bê Tông và 20 điểm hướng nghiệp<\n>Thành Phẩm và Điểm Hướng Nghiệp<\n-Hãy mang thành phẩm đến gặp thầy Lộc để đổi quà\n-Khi đủ 10k điểm hướng nghiệp sẽ có thể đổi được bằng Fuho");
            }
        }
    }

    public static void npcThayLoc(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.xu < 10000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 10 triệu xu");
                    break;
                }
                if (p.c.quantityItemyTotal(887) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 10000 xe rùa vữa");
                    break;
                }
                if (p.c.quantityItemyTotal(888) < 10000) {
                    p.conn.sendMessageLog("Cần 10000 tấm bê tông");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.updateExp(200000000000L);
                p.c.removeItemBags(887, 10000);
                p.c.removeItemBags(888, 10000);
                p.upluongMessage(-100000L);
                p.c.upxuMessage(-10000000L);
                p.c.upyenMessage(-10000000L);
                Item itemup = ItemTemplate.itemDefault(828);
                p.c.addItemBag(true, itemup);
                break;
            }
            case 1: {
                if (p.c.pointBossChuot < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 10000 điểm hướng nghiệp");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.pointBossChuot -= 10000;
                Item itemup = ItemTemplate.itemDefault(891);
                itemup.quantity = 1;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 2: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-100000L);
                Item itemup = ItemTemplate.itemDefault(892);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 3: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-100000L);
                Item itemup = ItemTemplate.itemDefault(894);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 4: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-100000L);
                Item itemup = ItemTemplate.itemDefault(895);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 5: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-100000L);
                Item itemup = ItemTemplate.itemDefault(896);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 6: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-100000L);
                Item itemup = ItemTemplate.itemDefault(897);
                itemup.upgrade = (byte) 11;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 7: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-200000L);
                Item itemup = ItemTemplate.itemDefault(569);
                itemup.upgrade = (byte) 16;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 8: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-200000L);
                Item itemup = ItemTemplate.itemDefault(837);
                itemup.upgrade = (byte) 16;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 9: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200000 lượng");
                    break;
                }
                if (p.c.quantityItemyTotal(891) < 1) {
                    p.conn.sendMessageLog("Cần 1 bằng fuho");
                    break;
                }
                if (p.c.getBagNull() == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Rương còn chỗ đâu mà đổi");
                    break;
                }
                p.c.removeItemBags(891, 1);
                p.upluongMessage(-200000L);
                Item itemup = ItemTemplate.itemDefault(912);
                itemup.upgrade = (byte) 16;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 10: {
                Server.manager.sendTB(p, "Đổi Quà", "Hãy mang thành phẩm của con đến đây ta sẽ đổi cho con những mòn quà quý hiếm\n>Đổi Chim Băng<\n-10000 xe rùa vữa\n-10000 tấm bê tông\n-100k lượng\n-10 triệu xu, yên\n-Sau khi đổi sẽ nhận được phượng hoàng băng 'Vĩnh viễn'\n-Khi đổi quà sẽ nhận được 200 tỷ kinh nghiệm\n>Nhận Bằng<\n-Cần 10000 điểm hướng nghiệp\n-Đổi các loại mặt nạ mới có hạn sử dụng 14 ngày chỉ với 1 bằng fuho và 100k lượng\nChúc anh em chơi game vui vẻ");
                break;
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }

    public static void npcDeTu(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để ghép");
                    break;
                }
                if (p.c.quantityItemyTotal(903) < 9999) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 9999 tàn mảnh");
                    break;
                }
                p.c.removeItemBags(903, 9999);
                p.upluongMessage(-10000L);
                p.sendAddchatYellow("Con nhận được bí kiếp");
                Item itemup = ItemTemplate.itemDefault(904);
                itemup.quantity = 999;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 1: {
                if (p.c.philoi != 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã học chiêu này rồi");
                    break;
                }
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 củ lượng để học");
                    break;
                }
                if (p.c.quantityItemyTotal(904) < 9999) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 9999 bí kiếp");
                    break;
                }
                p.c.removeItemBags(904, 9999);
                p.upluongMessage(-2000000L);
                p.c.philoi = 1;
                p.conn.sendMessageLog("Con đã học thành công phi lôi thần thuật lv1");
                break;
            }
            case 2: {
                if (p.c.philoi != 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần học phi lôi thần thuật lv1 đã nhé");
                    break;
                }
                if (p.luong < 3000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 củ lượng để học");
                    break;
                }
                if (p.c.quantityItemyTotal(904) < 19999) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 19999 bí kiếp");
                    break;
                }
                p.c.removeItemBags(904, 19999);
                p.upluongMessage(-3000000L);
                p.c.philoi = 2;
                p.conn.sendMessageLog("Bạn đã học thành công phi lôi thần thuật lv2");
                break;
            }
            case 3: {
                if (p.c.philoi != 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần học phi lôi thần thuật lv2 đã nhé");
                    break;
                }
                if (p.luong < 5000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 3 củ lượng để học");
                    break;
                }
                if (p.c.quantityItemyTotal(904) < 29999) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 29999 bí kiếp");
                    break;
                }
                p.c.removeItemBags(904, 29999);
                p.upluongMessage(-5000000L);
                p.c.philoi = 3;
                p.conn.sendMessageLog("Bạn đã học thành công phi lôi thần thuật lv3");
                break;
            }
            case 4: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100k lượng để ghép");
                    break;
                }
                if (p.c.quantityItemyTotal(904) < 999) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 999 bí kiếp");
                    break;
                }
                p.c.removeItemBags(904, 999);
                p.upluongMessage(-100000L);
                p.sendAddchatYellow("Bạn nhận được bí kíp phi lôi");
                Item itemup = ItemTemplate.itemDefault(902);
                itemup.upgrade = (byte) 16;
                p.c.addItemBag(true, itemup);
                break;
            }
            case 5: {
                p.PhiLoi();
                break;
            }
            case 6: {
                Server.manager.sendTB(p, "Hướng dẫn nè đọc đi", "Bạn phải tích đủ tàn mảnh thông qua việc đánh quái\n>Phi Lôi Thần thuật\n-Tàn mảnh chỉ xuất hiện khi đánh quái ở làng cổ với tỷ lệ 10%\n-tiêu diệt thủ lĩnh làng cổ sẽ nhận được 2 tàn mảnh\n-Ghép 9999 tàn mảnh sẽ được 999 bí kiếp\n-Phi lôi thần thuật lv1 cần 9999 bí kiếp và 2m lượng\n-Phi lôi thần thuật lv2 cần 19999 bí kiếp và 3m lượng\n-Phi lôi thần thuật lv3 cần 29999 bí kiếp và 5m lượng\n-Bạn có thể đổi 999 bí kiếp để lấy bí kíp phi lôi\n-1 loại bí kíp tăng mạnh sự nhanh nhẹn của 1 nhẫn giả\n>Bí kíp phi lôi có hạn sử dụng 2 tuần<");
            }
        }
    }

    public static void Nangyy(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.c.ItemBody[12] == null) {
                    p.conn.sendMessageLog("Con hãy đeo Yoroi vào rồi nâng cấp nhé");
                    return;
                }
                if (p.c.getBagNull() == 0) {
                    p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
                    return;
                }
                if (p.luong < 50000) {
                    p.conn.sendMessageLog("Bạn không có đủ 50000 lượng");
                    return;
                }
                if (p.c.quantityItemyTotal(922) < 2000) {
                    p.conn.sendMessageLog("Hãy mang cho ta 2000 Đá Tanzania đến đây");
                    return;
                }
                if (p.c.quantityItemyTotal(923) < 2000) {
                    p.conn.sendMessageLog("Hãy mang cho ta 2000 vải rách đến đây");
                    return;
                }
                Item it = p.c.ItemBody[12];
                if (it.getUpgrade() >= 16) {
                    p.conn.sendMessageLog("Yoroi của con đã đạt cấp tối đa");
                    return;
                }
                if (GameSrc.tilenang[it.getUpgrade()] >= Util.nextInt(100)) {
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param += option.param * 10 / 100;
                    }
                    it.setUpgrade(it.getUpgrade() + 1);
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("Phù phép thành công yoroi được lên 1 cấp");
                    p.c.removeItemBody((byte) 12);
                } else {
                    p.conn.sendMessageLog("Con Đen Lắm bà thu 5 chục nhé");
                }
                p.upluongMessage(-50000L);
                p.c.removeItemBags(922, 2000);
                p.c.removeItemBags(923, 2000);
                break;
            }
            case 1: {
                Server.manager.sendTB(p, "Hướng dẫn nâng Yoroi", "Hãy đi đánh quái ở map đặc biệt và mang nguyên liệu về đây\n-Vải rách và đá Tanzania sẽ rơi tại map đặc biệt khi đánh quái\n-nâng cấp 1 lần tốn 2000 đá Tanzania và 2000 vải rách\n-lượng 50k/ lần\n-tỷ lệ thành công theo cấp: 100%,80%,70%,60%,50%,40%,30%,20%,10%,10%,7%,7%,5%,5%,2%,2%\n>Tối đa được nâng cấp lên 16 và mỗi cấp sẽ cộng 1% chỉ số gốc<");
            }
        }
    }

    public static void npcTaiXiu(Player p, byte npcid, byte menuId, byte b3) throws InterruptedException {
        switch (menuId) {
            case 0: {
                Server.manager.taixiu[0].InfoTaiXiu(p);
                break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 222, "Nhập tiền cược(chia hết cho 10):");
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 223, "Nhập tiền cược(chia hết cho 10):");
                break;
            }
            case 3: {
                try {
                    String a = "";
                    int i2 = 1;
                    for (SoiCau check : SoiCau.soicau) {
                        a = a + i2 + ". " + check.time + " - " + check.ketqua + " - " + check.soramdom + ".\n";
                        ++i2;
                    }
                    Server.manager.sendTB(p, "Soi Cầu", a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Số lượng đặt cược phải là số chia hết cho 10.\nKhi đã đặt cược không được thoát game, nếu thoát game sẽ bị mất số tiền cược và admin sẽ không giải quyết.\nMỗi phiên cược sẽ là 1 phút, khi thời gian còn 10s sẽ không thể đặt cược.\nKhi đã đặt tài thì không thể đặt xỉu và ngược lại.\nCó thể đặt nhiều lần trong 1 phiên.");
            }
        }
    }

    public static void npcChanLe(Player p, byte npcid, byte menuId, byte b3) throws InterruptedException {
        switch (menuId) {
            case 0: {
                Server.manager.chanle[0].InfoChanLe(p);
                break;
            }
            case 1: {
                Service.sendInputDialog(p, (short) 224, "Nhập tiền cược(chia hết cho 10):");
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 225, "Nhập tiền cược(chia hết cho 10):");
                break;
            }
            case 3: {
                try {
                    String a = "";
                    int i2 = 1;
                    for (SoiCau check : SoiCau.soicau) {
                        a = a + i2 + ". " + check.time + " - " + check.ketqua + " - " + check.soramdom + ".\n";
                        ++i2;
                    }
                    Server.manager.sendTB(p, "Soi Cầu", a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Số lượng đặt cược phải là số chia hết cho 10.\nKhi đã đặt cược không được thoát game, nếu thoát game sẽ bị mất số tiền cược và admin sẽ không giải quyết.\nMỗi phiên cược sẽ là 1 phút, khi thời gian còn 10s sẽ không thể đặt cược.\nKhi đã đặt tài thì không thể đặt xỉu và ngược lại.\nCó thể đặt nhiều lần trong 1 phiên.");
            }
        }
    }

    public static void npcNangBua(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 100) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 100 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(960) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Hiếm)");
                    break;
                }
                if (p.c.quantityItemyTotal(955) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có bùa sinh mệnh rách ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 70) {
                    p.c.removeItemBags(959, 100);
                    p.c.removeItemBags(960, 1);
                    p.upluongMessage(-100000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(955, 1);
                p.c.removeItemBags(959, 100);
                p.c.removeItemBags(960, 1);
                p.upluongMessage(-100000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được bùa sinh mệnh tinh hoa");
                Item itemup = ItemTemplate.itemDefault(956);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công bùa sinh mệnh tinh hoa. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 1: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 500 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(961) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Sử Thi)");
                    break;
                }
                if (p.c.quantityItemyTotal(956) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có bùa sinh mệnh tinh hoa ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(959, 250);
                    p.c.removeItemBags(961, 1);
                    p.upluongMessage(-200000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(959, 500);
                p.c.removeItemBags(956, 1);
                p.c.removeItemBags(961, 1);
                p.upluongMessage(-200000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được bùa sinh mệnh sử thi");
                Item itemup = ItemTemplate.itemDefault(957);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công bùa sinh mệnh sử thi. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 2: {
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 1000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1000 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(962) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Truyền Thuyết)");
                    break;
                }
                if (p.c.quantityItemyTotal(957) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có bùa sinh mệnh sử thi ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 90) {
                    p.c.removeItemBags(959, 500);
                    p.c.removeItemBags(962, 1);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(959, 1000);
                p.c.removeItemBags(957, 1);
                p.c.removeItemBags(962, 1);
                p.upluongMessage(-500000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được bùa HP Cực Hạn");
                Item itemup = ItemTemplate.itemDefault(958);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công bùa HP Cực Hạn. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 3: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 5000 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(997) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1 đá hoàng kim");
                    break;
                }
                if (p.c.quantityItemyTotal(958) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có Bùa HP Cực Hạn để tiến hóa");
                    break;
                }
                if (Util.nextInt(100) <= 95) {
                    p.c.removeItemBags(959, 2500);
                    p.c.removeItemBags(997, 1);
                    p.upluongMessage(-1000000L);
                    p.conn.sendMessageLog("Xịt rồi yên tâm nhé ta không nói cho ai biết đâu!");
                    break;
                }
                p.c.removeItemBags(959, 5000);
                p.c.removeItemBags(958, 1);
                p.c.removeItemBags(997, 1);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Bạn nhận được (Hoàng Kim) Phù LTD");
                Item itemup = ItemTemplate.itemDefault(995);
                itemup.upgrade = (byte) 17;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hệ Thống LTD: " + p.c.name + " đã thành công tiến hóa trang sức: (Hoàng Kim) Phù LTD. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn nâng cấp bùa", "Bạn phải tích đủ đá tinh hoa: rơi 5 viên/1 boss map và 10 viên/1 boss bạo chúa"
                        + "\n-Sách nâng cấp bậc Hiếm sẽ có 10% rơi ở boss bạo chúa"
                        + "\n-Sách nâng cấp bậc Hiếm còn có tỷ lệ nhỏ xuất hiện khi sử dụng dưa hấu"
                        + "\n-Ghép 10 sách hiếm sẽ nhận được 1 sách sử thi"
                        + "\n-Nâng cấp từ bùa rách lên bùa tinh hoa cần: 100 viên đá tinh hoa + 1 sách nâng cấp hiếm + 100k lượng tỷ lệ 30%"
                        + "\n-Nâng cấp từ bùa tinh hoa lên sử thi cần: 500 viên đá tinh hoa + 1 sách nâng cấp sử thi + 200k lượng tỷ lệ 20%"
                        + "\n-Nâng cấp từ bùa sử thi lên bùa HP cực hạn cần: 1000 viên đá tinh hoa + 1 sách nâng cấp truyền thuyết + 500k lượng tỷ lệ 10%"
                        + "\n-Nâng bùa rách xịt sẽ mất đá tinh hoa, sách nâng cấp và lượng"
                        + "\n-Nâng bùa sử thi và truyền thuyết xịt sẽ mất 50% đá tinh hoa, sách nâng cấp và lượng"
                        + "\n-Tiến hóa Phù cần 2m lượng, 5000 đá tinh hoa, 1 đá hoàng kim và bùa hp cực hạn"
                        + "\n-Tỷ lệ tiến hóa thành công là 5% xịt sẽ mất 1m lượng, 2500 đá tinh hoa và 1 đá hoàng kim"
                        + "\n>Bùa này ngon Vler<");
            }
        }
    }

    public static void npcNangNhan(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 100000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 100k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 100) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 100 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(960) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Hiếm)");
                    break;
                }
                if (p.c.quantityItemyTotal(964) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có nhẫn hắc hổ tinh chế ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 70) {
                    p.c.removeItemBags(959, 100);
                    p.c.removeItemBags(960, 1);
                    p.upluongMessage(-100000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(955, 1);
                p.c.removeItemBags(959, 100);
                p.c.removeItemBags(964, 1);
                p.upluongMessage(-100000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được nhẫn hiếm có");
                Item itemup = ItemTemplate.itemDefault(965);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công nhẫn hắc hổ hiếm có. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 1: {
                if (p.luong < 200000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 200k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 500 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(961) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Sử Thi)");
                    break;
                }
                if (p.c.quantityItemyTotal(965) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có nhẫn hắc hổ hiếm ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(959, 250);
                    p.c.removeItemBags(961, 1);
                    p.upluongMessage(-200000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(959, 500);
                p.c.removeItemBags(965, 1);
                p.c.removeItemBags(961, 1);
                p.upluongMessage(-200000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được nhẫn sử thi");
                Item itemup = ItemTemplate.itemDefault(966);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công nhẫn hắc hổ sử thi. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 2: {
                if (p.luong < 500000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 500k lượng để nâng cấp");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 1000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1000 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(962) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 sách nâng cấp (Truyền Thuyết)");
                    break;
                }
                if (p.c.quantityItemyTotal(966) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có nhẫn hắc hổ sử thi ở hành trang đã nhé");
                    break;
                }
                if (Util.nextInt(100) <= 90) {
                    p.c.removeItemBags(959, 500);
                    p.c.removeItemBags(962, 1);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(959, 1000);
                p.c.removeItemBags(966, 1);
                p.c.removeItemBags(962, 1);
                p.upluongMessage(-500000L);
                p.conn.sendMessageLog("Nâng cấp thành công con nhận được nhẫn truyền thuyết");
                Item itemup = ItemTemplate.itemDefault(967);
                itemup.sys = 1;
                itemup.upgrade = 16;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã nâng cấp thành công nhẫn hắc hổ truyền thuyết. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 3: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(959) < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 5000 đá tinh hoa");
                    break;
                }
                if (p.c.quantityItemyTotal(997) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1 đá hoàng kim");
                    break;
                }
                if (p.c.quantityItemyTotal(967) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có Bùa HP Cực Hạn để tiến hóa");
                    break;
                }
                if (Util.nextInt(100) <= 95) {
                    p.c.removeItemBags(959, 2500);
                    p.c.removeItemBags(997, 1);
                    p.upluongMessage(-1000000L);
                    p.conn.sendMessageLog("Xịt rồi yên tâm nhé ta không nói cho ai biết đâu!");
                    break;
                }
                p.c.removeItemBags(959, 5000);
                p.c.removeItemBags(967, 1);
                p.c.removeItemBags(997, 1);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Bạn nhận được (Hoàng Kim) Nhẫn Hắc Hổ");
                Item itemup = ItemTemplate.itemDefault(996);
                itemup.upgrade = (byte) 17;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hệ Thống LTD: " + p.c.name + " đã thành công tiến hóa trang sức: (Hoàng Kim) Nhẫn Hắc Hổ. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn nâng cấp nhẫn", "Bạn phải tích đủ đá tinh hoa: rơi 5 viên/1 boss map và 10 viên/1 boss bạo chúa"
                        + "\n-Sách nâng cấp bậc Hiếm sẽ có 10% rơi ở boss bạo chúa"
                        + "\n-Sách nâng cấp bậc Hiếm còn có tỷ lệ nhỏ xuất hiện khi sử dụng dưa hấu"
                        + "\n-Ghép 10 sách hiếm sẽ nhận được 1 sách sử thi"
                        + "\n-Nâng cấp từ nhẫn tinh chế lên hiếm có cần: 100 viên đá tinh hoa + 1 sách nâng cấp hiếm + 100k lượng tỷ lệ 30%"
                        + "\n-Nâng cấp từ nhẫn hiếm có lên sử thi cần: 500 viên đá tinh hoa + 1 sách nâng cấp sử thi + 200k lượng tỷ lệ 20%"
                        + "\n-Nâng cấp từ nhẫn sử thi lên truyền thuyết cần: 1000 viên đá tinh hoa + 1 sách nâng cấp truyền thuyết + 500k lượng tỷ lệ 10%"
                        + "\n-Nâng nhẫn tinh chế xịt sẽ mất đá tinh hoa, sách nâng cấp và lượng"
                        + "\n-Nâng nhẫn sử thi và truyền thuyết xịt sẽ mất 50% đá tinh hoa, sách nâng cấp và lượng"
                        + "\n-Tiến hóa nhẫn cần 2m lượng, 5000 đá tinh hoa, 1 đá hoàng kim và nhẫn hắc hỗ cấp truyền thuyết"
                        + "\n-Tỷ lệ tiến hóa thành công là 5% xịt sẽ mất 1m lượng, 2500 đá tinh hoa và 1 đá hoàng kim"
                        + "\n>Nhẫn này ngon Vler<");
            }
        }
    }

    public static void NangBoiDay(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(970) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 10000 đá Malachite");
                    break;
                }
                if (Util.nextInt(100) <= 70) {
                    p.c.removeItemBags(970, 999);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(970, 10000);
                p.upluongMessage(-1000000L);
                p.sendAddchatYellow("Bạn nhận được Kim Long Bội");
                Item itemup = ItemTemplate.itemDefault(969);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã hợp thành Kim Long Bội trong truyền thuyết. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 1: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(971) < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 10000 đá Opal");
                    break;
                }
                if (Util.nextInt(100) <= 70) {
                    p.c.removeItemBags(971, 999);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(971, 10000);
                p.upluongMessage(-1000000L);
                p.sendAddchatYellow("Bạn nhận được Dây chuyền nguyên tố");
                Item itemup = ItemTemplate.itemDefault(968);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Á Đù " + p.c.name + " đã hợp thành Dây Chuyền Nguyên Tố trong truyền thuyết. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 2: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(970) < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 5000 đá Malachite");
                    break;
                }
                if (p.c.quantityItemyTotal(987) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1 long cốt để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(969) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có Kim Long Bội để hợp thành");
                    break;
                }
                if (Util.nextInt(100) <= 95) {
                    p.c.removeItemBags(970, 2500);
                    p.c.removeItemBags(987, 1);
                    p.upluongMessage(-1000000L);
                    p.conn.sendMessageLog("Xịt rồi yên tâm nhé ta không nói cho ai biết đâu!");
                    break;
                }
                p.c.removeItemBags(970, 5000);
                p.c.removeItemBags(969, 1);
                p.c.removeItemBags(987, 1);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Bạn nhận được (Chân) Kim Long Bội");
                Item itemup = ItemTemplate.itemDefault(985);
                itemup.upgrade = (byte) 17;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hệ thống LTD: " + p.c.name + " đã thành công thức tỉnh trang sức hoàng kim: (Chân) Kim Long Bội trong truyền thuyết. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 3: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(971) < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 5000 đá Opal");
                    break;
                }
                if (p.c.quantityItemyTotal(986) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1 đá ngũ hành để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(968) < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có Dây chuyền nguyên tố để hợp thành");
                    break;
                }
                if (Util.nextInt(100) <= 95) {
                    p.c.removeItemBags(971, 2500);
                    p.c.removeItemBags(986, 1);
                    p.upluongMessage(-1000000L);
                    p.conn.sendMessageLog("Xịt rồi yên tâm nhé ta không nói cho ai biết đâu!");
                    break;
                }
                p.c.removeItemBags(971, 5000);
                p.c.removeItemBags(968, 1);
                p.c.removeItemBags(986, 1);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Bạn nhận được (Chân) Dây chuyền nguyên tố");
                Item itemup = ItemTemplate.itemDefault(984);
                itemup.upgrade = (byte) 17;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hệ Thống LTD: " + p.c.name + " đã thành công thức tỉnh trang sức hoàng kim: (Chân) Dây Chuyền Nguyên Tố trong truyền thuyết. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Hãy đi tìm những nguyên liệu quý"
                        + "\n-Khi săn boss và sử dụng các vật phẩm sẽ có cơ hội nhận được túi nguyên liệu quý"
                        + "\n-Mở túi nguyên liệu sẽ nhận được ngẫu nhiên các loại đá"
                        + "\n-Hợp thành dây chuyền nguyên tố tốn 10000 đá Opal"
                        + "\n-Hợp Thành Kim Long Bội tốn 10000 đá Malachite"
                        + "\n-lượng 1 củ / lần"
                        + "\n-tỷ lệ thành công: 30%"
                        + "\n>Xịt sẽ mất 500k lượng và 999 đá<"
                        + "\n-Nguyên liệu đặc biệt sẽ rớt khi tiêu diệt boss bạo chúa"
                        + "\n-Hoặc bạn có thể đổi những nguyên liệu dư thừa để lấy nguyên liệu đặc biệt"
                        + "\n-Tỷ lệ: 2500 mảnh quạ hoặc 2500 hỏa linh châu hoặc 2500 đá Malachite để lấy 1 đá ngũ hành"
                        + "\n-Tỷ lệ: 2500 mảnh Rin hoặc 2500 băng linh châu hoặc 2500 đá Opal để lấy 1 long cốt"
                        + "\n-Hợp thành (Chân) dây chuyền nguyên tố tốn 5000 đá Opal và 1 đá ngũ hành"
                        + "\n-Hợp Thành (Chân) Kim Long Bội tốn 5000 đá Malachite và 1 long cốt"
                        + "\n-lượng 2 củ / lần"
                        + "\n-tỷ lệ thành công: 10%"
                        + "\n>Xịt sẽ mất đi 1 củ lượng, 2500 đá cùng nguyên liệu đặc biệt<");
            }
        }
    }

    public static void npcdoiqua(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                if (p.c.nhiemvu == 4) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn đã hết lượt nhận nhiệm vụ hãy chờ đến ngày mai");
                    break;
                }
                if (p.c.level < 60) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần đạt đến level 60 nhé bạn!");
                    break;
                }
                if (p.c.nhiemvu > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn đã nhận nhiệm vụ rồi");
                    break;
                }
                if (p.c.level < 100) {
                    p.c.nhiemvu = 1;
                    Service.chatNPC(p, Short.valueOf(npcid), "Nhận nhiệm vụ thành công, xem chi tiết ở phần hướng dẫn");
                    break;
                }
                if (p.c.level >= 100) {
                    p.c.nhiemvu = 2;
                    Service.chatNPC(p, Short.valueOf(npcid), "Nhận nhiệm vụ thành công, xem chi tiết ở phần hướng dẫn");
                    break;
                }
                break;
            }
            case 1: {
                int coin1 = 3000;
                int coin2 = 3000;
                if (p.c.nhiemvu == 3) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn đã hết lượt nhiệm vụ");
                    break;
                }
                if (p.c.nhiemvu == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn chưa nhận nhiệm vụ");
                    break;
                }
                if (p.c.nhiemvu == 1 && p.c.numquai < 5000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần đánh 5k con quái thường");
                    break;
                }
                if (p.c.nhiemvu == 2 && p.c.numquai < 10000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần đánh 10k con quái thường");
                    break;
                }
                if (p.c.nhiemvu == 1) {
                    p.c.numquai = 0;
                    p.c.nhiemvu = 3;
                    p.coin = p.coin + coin1;
                    SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                    p.conn.sendMessageLog("Hoàn thành nhiệm vụ bạn nhận được 500 coin hãy thoát ra vào lại để cập nhật số coin");
                    break;
                }
                if (p.c.nhiemvu == 2) {
                    p.c.numquai = 0;
                    p.c.nhiemvu = 3;
                    p.coin = p.coin + coin2;
                    SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                    p.conn.sendMessageLog("Hoàn thành nhiệm vụ bạn nhận được 1500 coin hãy thoát ra vào lại để cập nhật số coin");
                    break;
                }
            }
            case 2: {
                p.NhiemVu();
                break;
            }
            case 3: {
                Service.sendInputDialog(p, (short) 255, "Nhập số lượng đan muốn làm:");
                break;
            }
        }
    }

    public static void npcDoiNgoc(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(652) < 1) {
                    p.conn.sendMessageLog("Hãy mang Huyền tinh ngọc đến đây");
                    return;
                }
                if (p.c.quantityItemyTotal(998) < 1) {
                    p.conn.sendMessageLog("Hãy mang 1 huyền dược đến đây");
                    return;
                }
                if (Util.nextInt(100) <= 40) {
                    Item it = ItemTemplate.itemDefault(652);
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param *= 2;
                    }
                    p.upluongMessage(-1000000);
                    p.c.removeItemBags(652, 1);
                    p.c.removeItemBags(998, 1);
                    it.upgrade = 11;
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("Luyện ngọc thành công");
                } else {
                    p.upluongMessage(-500000);
                    p.c.removeItemBags(652, 1);
                    p.c.removeItemBags(998, 1);
                    p.conn.sendMessageLog("Thất bại! Tài nguyên tiêu tán, chúc bạn may mắn lần sau");
                }
                break;
            }
            case 1: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(653) < 1) {
                    p.conn.sendMessageLog("Hãy mang Huyết ngọc đến đây");
                    return;
                }
                if (p.c.quantityItemyTotal(999) < 1) {
                    p.conn.sendMessageLog("Hãy mang 1 huyết dược đến đây");
                    return;
                }
                if (Util.nextInt(100) <= 40) {
                    Item it = ItemTemplate.itemDefault(653);
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param *= 2;
                    }
                    p.upluongMessage(-1000000);
                    p.c.removeItemBags(653, 1);
                    p.c.removeItemBags(999, 1);
                    it.upgrade = 11;
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("Luyện ngọc thành công");
                } else {
                    p.upluongMessage(-500000);
                    p.c.removeItemBags(653, 1);
                    p.c.removeItemBags(999, 1);
                    p.conn.sendMessageLog("Thất bại! Tài nguyên tiêu tán, chúc bạn may mắn lần sau");
                }
                break;
            }
            case 2: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(654) < 1) {
                    p.conn.sendMessageLog("Hãy mang Lam ngọc đến đây");
                    return;
                }
                if (p.c.quantityItemyTotal(1000) < 1) {
                    p.conn.sendMessageLog("Hãy mang 1 Lam dược đến đây");
                    return;
                }
                if (Util.nextInt(100) <= 40) {
                    Item it = ItemTemplate.itemDefault(654);
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param *= 2;
                    }
                    p.upluongMessage(-1000000);
                    p.c.removeItemBags(654, 1);
                    p.c.removeItemBags(1000, 1);
                    it.upgrade = 11;
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("Luyện ngọc thành công");
                } else {
                    p.upluongMessage(-500000);
                    p.c.removeItemBags(654, 1);
                    p.c.removeItemBags(1000, 1);
                    p.conn.sendMessageLog("Thất bại! Tài nguyên tiêu tán, chúc bạn may mắn lần sau");
                }
                break;
            }
            case 3: {
                if (p.luong < 1000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 1 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(655) < 1) {
                    p.conn.sendMessageLog("Hãy mang Lục ngọc đến đây");
                    return;
                }
                if (p.c.quantityItemyTotal(1001) < 1) {
                    p.conn.sendMessageLog("Hãy mang 1 Lục dược đến đây");
                    return;
                }
                if (Util.nextInt(100) <= 40) {
                    Item it = ItemTemplate.itemDefault(655);
                    for (int k = 0; k < it.options.size(); k = (int) ((byte) (k + 1))) {
                        Option option = it.options.get(k);
                        option.param *= 2;
                    }
                    p.upluongMessage(-1000000);
                    p.c.removeItemBags(655, 1);
                    p.c.removeItemBags(1001, 1);
                    it.upgrade = 11;
                    it.setLock(true);
                    p.c.addItemBag(true, it);
                    p.conn.sendMessageLog("Luyện ngọc thành công");
                } else {
                    p.upluongMessage(-500000);
                    p.c.removeItemBags(655, 1);
                    p.c.removeItemBags(1001, 1);
                    p.conn.sendMessageLog("Thất bại! Tài nguyên tiêu tán, chúc bạn may mắn lần sau");
                }
                break;
            }
            case 4: {
                Server.manager.sendTB(p, "Hướng dẫn", "Hãy mang những viên ngọc của bạn đến đây ta sẽ tiến hành tẩy luyện khiến chúng trở nên mạnh hơn"
                        + "\n-Điều kiện tẩy luyện ngọc"
                        + "\n-1 triệu lượng"
                        + "\n-1 viên ngọc 10"
                        + "\n-Cần 1 lọ tinh dược phù hợp với ngọc cần luyện"
                        + "\n-Tỷ lệ thành công là 40%, xịt sẽ mất ngọc, tinh dược và 500k lượng"
                        + "\n-Thành công sẽ nhận được ngọc sau tẩy luyện cấp 11"
                        + "\n-Chỉ số của ngọc sẽ dao động từ 1.8 đến 2 lần chỉ số gốc ngọc"
                        + "\n-Bạn có thể tiếp tục tẩy luyện ngọc cấp 11 để có được chỉ số cao hơn tùy vào độ may mắn của bạn"
                        + "\n-Tuy nhiên thất bại bạn sẽ mất luôn ngọc cấp 11"
                        + "\n-Tinh dược được ghép từ 20k huy chương tương ứng, Ấn sử dụng để ghép"
                        + "\n-Huy chương đồng = Huyền dược, Bạc = Huyết dược, Vàng = Lam dược, Bạch kim = Lục dược"
                        + "\n***Lưu ý: luyện thành công hãy cất ngọc 11 đi tránh trường hợp lò luyện nhầm mất viên ngọc cấp 11");
                break;
            }
        }
    }

    public static void npcCocLTD(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                if (p.c.numtl == 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Chào mừng bạn đến với LTD GAME, Bạn đã nhận quà này rồi nhé!");
                    break;
                }
                if (p.c.level < 60) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Chào mừng bạn đến với LTD GAME, Bạn cần đạt đến LV 60 để nhận quà");
                    break;
                } else {
                    int coinnv = 5000;
                    p.c.numtl = 0;
                    p.coin = p.coin + coinnv;
                    SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                    p.conn.sendMessageLog("Nhận quà thành công, chúc bạn năm mới vui vẻ tiếp tục gắn bó với LTD nheS");
                    break;
                }
            }
        }
    }

    public static void npcBiNgo(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                switch (b3) {
                    case 0: {
                        int coin = 3000;
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1002) < 500) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 500 quả bí ngô hắc ám");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1002, 500);
                        p.coin = p.coin + coin;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        p.conn.sendMessageLog("Bạn đã đổi thành công 500 bí ngô hắc ám ra 3000 coin hãy thoát ra vào lại để cập nhật số coin");
                        break;
                    }
                    case 1: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1002) < 300) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 300 quả bí ngô hắc ám");
                            break;
                        }
                        p.c.removeItemBags(1002, 300);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công mặt nạ Jack Hollow");
                        Item itemup = ItemTemplate.itemDefault(771);
                        itemup.upgrade = (byte) 16;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 2: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1002) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 quả bí ngô hắc ám");
                            break;
                        }
                        p.c.removeItemBags(1002, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Malachite");
                        Item itemup = ItemTemplate.itemDefault(970);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1002) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 quả bí ngô hắc ám");
                            break;
                        }
                        p.c.removeItemBags(1002, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Opal");
                        Item itemup = ItemTemplate.itemDefault(971);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 4: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần 10k lượng để đổi");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1002) < 200) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 200 quả bí ngô hắc ám");
                            break;
                        }
                        p.c.removeItemBags(1002, 200);
                        p.upluongMessage(-10000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Tinh Hoa");
                        Item itemup = ItemTemplate.itemDefault(959);
                        itemup.quantity = 100;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 5:
                        Server.manager.sendTB(p, "Top Bí Ngô Hắc Ám", Rank.getStringBXH(7));
                        return;
                    case 6: {
                        Server.manager.sendTB(p, "Bí Ngô Hắc ám", "Sau khi Drop boss sẽ rớt 3 bí ngô hắc ám, hỏa thạch vương sẽ cho kẻ hạ gục mình 10 bí ngô hắc ám"
                                + "\n>Bí ngô hắc ám sẽ được đưa thẳng vào hành trang người chơi chứ không rơi ra đất tránh trường hợp bị ks<"
                                + "\n-Đổi 3000 coin = 500 bí ngô hắc ám"
                                + "\n-Đổi mặt nạ Jack Hollow cực vip bằng 300 bí ngô hắc ám"
                                + "\n-Đổi 100 đá Malachite = 200 bí ngô hắc ám"
                                + "\n-Đổi 100 đá Opal = 200 bí ngô hắc ám"
                                + "\n-Đổi 100 đá Tinh Hoa = 200 bí ngô hắc ám"
                                + "\n-Khi nhận mỗi quả bí ngô hắc ám bạn sẽ nhận được 1 điểm"
                                + "\n-10 nhẫn giả xuất sắc có tên trên bxh mỗi tuần sẽ nhận được Coin tương ứng"
                                + "\n-Top 1-3: 30k coin"
                                + "\n-Top 4-6: 20k coin"
                                + "\n-Top 7-10: 10k coin"
                                + "\n-Chúc anh em chơi game vui vẻ");
                        break;
                    }
                }
                break;
            }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1004) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 quả bí ngô mặt quỷ");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1004, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công chim băng");
                        Item itemup = ItemTemplate.itemDefault(828);
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 1: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1004) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 quả bí ngô mặt quỷ");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1004, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Malachite");
                        Item itemup = ItemTemplate.itemDefault(970);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 2: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1004) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 quả bí ngô mặt quỷ");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1004, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Opal");
                        Item itemup = ItemTemplate.itemDefault(971);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 3: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1004) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 quả bí ngô mặt quỷ");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1004, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công đá Tinh Hoa");
                        Item itemup = ItemTemplate.itemDefault(959);
                        itemup.quantity = 500;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 4: {
                        if (p.luong < 10000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Cần có 10k lượng");
                            break;
                        }
                        if (p.c.quantityItemyTotal(1004) < 5000) {
                            Service.chatNPC(p, Short.valueOf(npcid), "Bạn phải có 5000 quả bí ngô mặt quỷ");
                            break;
                        }
                        p.upluongMessage(-10000);
                        p.c.removeItemBags(1004, 5000);
                        p.conn.sendMessageLog("Bạn đã đổi thành công mặt nạ Jack Hollow");
                        Item itemup = ItemTemplate.itemDefault(771);
                        itemup.upgrade = (byte) 16;
                        itemup.isLock = true;
                        p.c.addItemBag(true, itemup);
                        break;
                    }
                    case 5: {
                        Server.manager.sendTB(p, "Bí Ngô Mặt quỷ", "Những nhẫn giả đạt level 60 trở lên khi farm quái sẽ có tỷ lệ nhỏ rơi ra mảnh ghép bí ngô"
                                + "\n>Tinh anh thủ lĩnh sẽ có tỷ lệ cao rơi mảnh ghép bí ngô<"
                                + "\n-Khi nhấn sử dụng sẽ ghép lại với tỷ lệ 100 mảnh ghép = 1 bí ngô mặt quỷ"
                                + "\n-Đổi phượng hoàng băng = 5000 bí ngô mặt quỷ"
                                + "\n-Đổi 500 đá Malachite = 5000 bí ngô mặt quỷ"
                                + "\n-Đổi 500 đá Opal = 5000 bí ngô mặt quỷ"
                                + "\n-Đổi 500 đá Tinh Hoa = 5000 bí ngô mặt quỷ"
                                + "\n-Đổi mặt nạ vip = 5000 bí ngô mặt quỷ"
                                + "\n-Chúc anh em chơi game vui vẻ");
                        break;
                    }
                }
                break;
            }
            case 2: {
                Service.sendInputDialog(p, (short) 254, "Nhập số giỏ kẹo muốn làm");
                break;
            }
            case 3: {
                Server.manager.sendTB(p, "Hướng dẫn", "Khi đánh quái sẽ rơi những viên kẹo Halloween"
                        + "\n-Mang kẹo đến gặp ta để đổi lấy những giỏ kẹo bí ngô"
                        + "\n-Sử dụng giỏ kẹo để nhận những phần quà giá trị"
                        + "\n-Công thức làm giỏ kẹo:"
                        + "\n-5 kẹo đỏ"
                        + "\n-5 kẹo vàng"
                        + "\n-5 kẹo xanh"
                        + "\n-4 giỏ bí ngô"
                        + "\n-50k yên, 50k xu, 100 lượng"
                        + "\n-Giỏ bí ngô được bán tại npc Gosho"
                        + "\n-Mỗi khi làm giỏ kẹo sẽ được tính 1 điểm top sự kiện"
                        + "\n-Xem top tại npc trưởng làng, những nhẫn giả có tên trên bxh sẽ nhận những phần quà quý giá");
                break;
            }
        }
    }

    public static void NhanThuat(Player p, byte npcid, byte menuId, byte b3) throws IOException {
        switch (menuId) {
            case 0: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(986) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 viên đá ngũ hành");
                    break;
                }
                if (p.c.quantityItemyTotal(994) < 20) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 20 ví con cóc");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Óc chó không thể làm, bạn cần có 500 năng lượng cho não");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(986, 3);
                    p.c.removeItemBags(994, 10);
                    p.c.removeItemBags(881, 100);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(986, 15);
                p.c.removeItemBags(994, 20);
                p.c.removeItemBags(881, 500);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Thành công, bạn nhận được Nhẫn Thuật KoGeKi");
                Item itemup = ItemTemplate.itemDefault(1011);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hê Thống: " + p.c.name + " đã ghép thành công Nhẫn Thuật KoGeKi. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 1: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(986) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 viên đá ngũ hành");
                    break;
                }
                if (p.c.quantityItemyTotal(994) < 20) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 20 ví con cóc");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Óc chó không thể làm, bạn cần có 500 năng lượng cho não");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(986, 3);
                    p.c.removeItemBags(994, 10);
                    p.c.removeItemBags(881, 100);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(986, 15);
                p.c.removeItemBags(994, 20);
                p.c.removeItemBags(881, 500);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Thành công, bạn nhận được Nhẫn Thuật BoEi");
                Item itemup = ItemTemplate.itemDefault(1012);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hê Thống: " + p.c.name + " đã ghép thành công Nhẫn Thuật BoEi. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 2: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(987) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 Long Cốt");
                    break;
                }
                if (p.c.quantityItemyTotal(251) < 2000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1000 mảnh giấy vụn");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Óc chó không thể làm, bạn cần có 500 năng lượng cho não");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(987, 3);
                    p.c.removeItemBags(251, 200);
                    p.c.removeItemBags(881, 100);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(987, 15);
                p.c.removeItemBags(251, 1000);
                p.c.removeItemBags(881, 500);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Thành công, bạn nhận được Nhẫn Thuật KiBin");
                Item itemup = ItemTemplate.itemDefault(1013);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hê Thống: " + p.c.name + " đã ghép thành công Nhẫn Thuật KiBin. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 3: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(987) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 Long Cốt");
                    break;
                }
                if (p.c.quantityItemyTotal(251) < 2000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 1000 mảnh giấy vụn");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Óc chó không thể làm, bạn cần có 500 năng lượng cho não");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(987, 3);
                    p.c.removeItemBags(251, 200);
                    p.c.removeItemBags(881, 100);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(987, 15);
                p.c.removeItemBags(251, 1000);
                p.c.removeItemBags(881, 500);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Thành công, bạn nhận được Nhẫn Thuật Butsuri-Teki");
                Item itemup = ItemTemplate.itemDefault(1014);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hê Thống: " + p.c.name + " đã ghép thành công Nhẫn Thuật Butsuri-Teki. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 4: {
                if (p.luong < 2000000) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Cần 2 triệu lượng để hợp thành");
                    break;
                }
                if (p.c.quantityItemyTotal(987) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 Long Cốt");
                    break;
                }
                if (p.c.quantityItemyTotal(986) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 Đá Ngũ Hành");
                    break;
                }
                if (p.c.quantityItemyTotal(997) < 15) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Bạn cần có 15 Đá Hoàng Kim");
                    break;
                }
                if (p.c.quantityItemyTotal(881) < 500) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Óc chó không thể làm, bạn cần có 500 Năng lượng cho não");
                    break;
                }
                if (Util.nextInt(100) <= 80) {
                    p.c.removeItemBags(987, 3);
                    p.c.removeItemBags(986, 3);
                    p.c.removeItemBags(997, 3);
                    p.c.removeItemBags(881, 100);
                    p.upluongMessage(-500000L);
                    p.conn.sendMessageLog("Xịt rồi số con đen như cái bản mặt con vậy");
                    break;
                }
                p.c.removeItemBags(987, 15);
                p.c.removeItemBags(986, 15);
                p.c.removeItemBags(997, 15);
                p.c.removeItemBags(881, 500);
                p.upluongMessage(-2000000L);
                p.sendAddchatYellow("Thành công, bạn nhận được Nhẫn Thuật Baransu");
                Item itemup = ItemTemplate.itemDefault(1015);
                itemup.upgrade = (byte) 16;
                itemup.sys = 1;
                p.c.addItemBag(true, itemup);
                Manager.chatKTG("Hê Thống: " + p.c.name + " đã ghép thành công Nhẫn Thuật Baransu. Hắn đang tìm người để thử sức có ai dám nghênh chiến?");
                break;
            }
            case 5: {
                Server.manager.sendTB(p, "Hướng dẫn", "Hãy mang những nguyên liệu quý hiếm đến đây, ta sẽ đổi cho ngươi những nhẫn thuật cực kỳ mạnh mẽ"
                        + "\n-Nhẫn Thuật KoGeKi và Nhẫn Thuật BoEi cần 15 đá ngũ hành + 500 não + 20 ví con cóc và 2 triệu lượng"
                        + "\n-Xịt sẽ mất 3 đá ngũ hành, 10 ví con cóc, 100 não + 500k lượng"
                        + "\n-Nhẫn Thuật KiBin và Nhẫn Thuật Butsuri-Teki cần 15 long cốt + 500 não + 1000 mảnh giấy vụn và 2 triệu lượng"
                        + "\n-Xịt sẽ mất 3 long cốt, 200 mảnh giấy vụn, 100 não + 500k lượng"
                        + "\n-Nhẫn Thuật Baransu cần 15 long cốt + 15 Đá ngũ hành + 15 đá hoàng kim + 500 não và 2 triệu lượng"
                        + "\n-Xịt sẽ mất 3 đá ngũ hành, 3 long cốt, 3 đá hoàng kim, 100 não + 500k lượng"
                        + "\n>Tỷ lệ thành công là 20%<"
                        + "\n-Nhẫn thuật KoGeKi sẽ Gia tăng mạnh khả năng tấn công"
                        + "\n-Nhẫn Thuật BoEi sẽ Giúp người sử dụng gia tăng khả năng phòng thủ"
                        + "\n-Nhẫn Thuật KiBin sẽ Hỗ trợ tăng cường sự nhanh nhẹn của nhẫn giả sở hữu"
                        + "\n-Nhẫn Thuật Butsuri-Teki sẽ Cường hóa cơ thể gia tăng mạnh lượng HP của nhẫn giả sở hữu"
                        + "\n-Nhẫn Thuật Baransu là thứ Thích hợp với nhẫn giả hướng đến sự cân bằng hoàn hảo");
            }
        }
    }

    public static void npcshopcoin(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException, SQLException {
        switch (menuId) {
            case 0: {
            Server.manager.sendTB(p,"Coin hiện tại",
                    "\n-coin hiện tại của bạn là : "+p.coin);
            break;
                    }
            case 1: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 150000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 150000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(996);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(996).name);
                        break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Nhẫn Hoàng Kim(150k)"
                        + "\n-HP : 300k"
                        + "\n-MP : 30k"
                        + "\n-Kháng Hỏa : 1k5"
                        + "\n-Kháng Băng : 1k5"
                        + "\n-Kháng Phong : 1k5"
                        + "\n-Kháng Tất Cả : 1k5"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+4) : mỗi 5s phục hồi 1k5 hp"
                        + "\n-(+8) : tỉ lệ hp tối đa + 50%"
                        + "\n-(+12) : HP tối đa +20k"
                        + "\n-(+16) : chịu sát thương khi bị chí mạng -120%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
                case 2: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 150000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 150000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(984);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(984).name);
                         break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Dây Chuyền Hoàng Kim(150k)"
                        + "\n-Giảm trừ sát thướng : 300"
                        + "\n-Né đòn : 200"
                        + "\n-tấn công ngoại : 60k"
                        + "\n-tấn công nội : 60k"
                        + "\n-sát thương lên người : 30k"
                        + "\n-sát thương chí mạng : 30k"
                        + "\n-sát thương chuẩn : 20k"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+14) : tấn công khi đánh trí mạng +120%"
                        + "\n-(+16) : sát thương đánh hệ hỏa +60%"
                        + "\n-(+16) : sát thương đánh hệ băng +60%"
                        + "\n-(+16) : sát thương đánh hệ phong +60%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 150000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 150000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(985);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(985).name);
                          break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Hoàng Kim Bội(150k)"
                        + "\n-Giảm trừ sát thướng : 300"
                        + "\n-Né đòn : 200"
                        + "\n-tấn công ngoại : 60k"
                        + "\n-tấn công nội : 60k"
                        + "\n-sát thương lên người : 30k"
                        + "\n-sát thương chí mạng : 30k"
                        + "\n-sát thương chuẩn : 20k"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+14) : tấn công khi đánh trí mạng +120%"
                        + "\n-(+16) : sát thương đánh hệ hỏa +60%"
                        + "\n-(+16) : sát thương đánh hệ băng +60%"
                        + "\n-(+16) : sát thương đánh hệ phong +60%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 4: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 150000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 150000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(995);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(995).name);
                         break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Hoàng Kim Phù(150k)"
                        + "\n-giảm trừ sát thương : 6k"
                        + "\n-né đồn : 1k5k"
                        + "\n-HP : 300k"
                        + "\n-MP : 30k"
                        + "\n-Kháng Hỏa : 1k5"
                        + "\n-Kháng Băng : 1k5"
                        + "\n-Kháng Phong : 1k5"
                        + "\n-Kháng Tất Cả : 1k5"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+4) : mỗi 5s phục hồi 1k5 hp"
                        + "\n-(+8) : tỉ lệ hp tối đa + 50%"
                        + "\n-(+12) : HP tối đa +20k"
                        + "\n-(+16) : chịu sát thương khi bị chí mạng -120%"
                        + "\n-miễn giảm hoàn toàn sát thương từ người chơi : 10%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 5: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(968);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(968).name);
                          break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Dây Chuyền Truyền Thuyết(100k)"
                        + "\n-Giảm trừ sát thướng : 100"
                        + "\n-Né đòn : 100"
                        + "\n-tấn công ngoại : 40k"
                        + "\n-tấn công nội : 40k"
                        + "\n-sát thương lên người : 20k"
                        + "\n-sát thương chí mạng : 20k"
                        + "\n-sát thương chuẩn : 10k"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+14) : tấn công khi đánh trí mạng +100%"
                        + "\n-(+16) : sát thương đánh hệ hỏa +40%"
                        + "\n-(+16) : sát thương đánh hệ băng +40%"
                        + "\n-(+16) : sát thương đánh hệ phong +40%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 6: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(967);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(967).name);
                         break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Nhẫn Truyền Thuyết(100k)"
                        + "\n-giảm trừ sát thương : 4k"
                        + "\n-né đồn : 1k3"
                        + "\n-HP : 240k"
                        + "\n-MP : 20k"
                        + "\n-Kháng Hỏa : 1k3"
                        + "\n-Kháng Băng : 1k3"
                        + "\n-Kháng Phong : 1k3"
                        + "\n-Kháng Tất Cả : 1k3"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+4) : mỗi 5s phục hồi 1k3 hp"
                        + "\n-(+8) : tỉ lệ hp tối đa + 40%"
                        + "\n-(+12) : HP tối đa +10k"
                        + "\n-(+14) : kháng tất cả 1k3"
                        + "\n-(+16) : chịu sát thương khi bị chí mạng -120%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 7: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(969);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(969).name);
                         break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "Bội Truyền Thuyết(100k)"
                        + "\n-Giảm trừ sát thướng : 100"
                        + "\n-Né đòn : 100"
                        + "\n-tấn công ngoại : 40k"
                        + "\n-tấn công nội : 40k"
                        + "\n-sát thương lên người : 20k"
                        + "\n-sát thương chí mạng : 20k"
                        + "\n-sát thương chuẩn : 10k"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+14) : tấn công khi đánh trí mạng +100%"
                        + "\n-(+16) : sát thương đánh hệ hỏa +40%"
                        + "\n-(+16) : sát thương đánh hệ băng +40%"
                        + "\n-(+16) : sát thương đánh hệ phong +40%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            case 8: {
                switch (b3) {
                    case 0: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 150k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item itemup = ItemTemplate.itemDefault(958);
                         itemup.quantity = 1;
                            itemup.upgrade = 17;
                            itemup.isLock = true;
                         p.c.addItemBag(true, itemup);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 150k.và mua món đồ"+ItemTemplate.ItemTemplateId(967).name);
                          break;
                    }
                    case 1: {
                Server.manager.sendTB(p, "Thông Tin Đồ", "bùa Truyền Thuyết(100k)"
                        + "\n-giảm trừ sát thương : 4k"
                        + "\n-né đồn : 1k3"
                        + "\n-HP : 240k"
                        + "\n-MP : 20k"
                        + "\n-Kháng Hỏa : 1k3"
                        + "\n-Kháng Băng : 1k3"
                        + "\n-Kháng Phong : 1k3"
                        + "\n-Kháng Tất Cả : 1k3"
                        + "\n> CÁC DÒNG ẨN "
                        + "\n-(+4) : mỗi 5s phục hồi 1k3 hp"
                        + "\n-(+8) : tỉ lệ hp tối đa + 40%"
                        + "\n-(+12) : HP tối đa +10k"
                        + "\n-(+14) : kháng tất cả 1k3"
                        + "\n-(+16) : chịu sát thương khi bị chí mạng -120%"
                        + "\n-hãy mua đi pro");
            }
                    }
                break;
                    }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }
    
      public static void npcHacAm(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 2) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 2 ô trống mới có thể nhập học!");
                    break;
                }
                p.coin -= 10000;
                SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                p.restSpoint();
                Admission.Admission(p, (byte) 7);
                Service.chatKTG( "LỚP HĂC ÁM " +"Chào mừng "+ p.c.name + " Đã gia nhập lớp hắc ám . nơi đào tạo nhân tài ninja");
               
            }
            case 1: {
                if (p.c.get().nclass != 7) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải Ninja hắc ám, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm tiềm năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvbang != 0) {
                            p.conn.sendMessageLog("Băng nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvbang == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV2\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV3\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 7) {
                            p.conn.sendMessageLog("Cậu không phải Ninja hắc ám không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 băng linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvbang = 1;
                        p.c.removeItemBags(856, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong thiên lý");
                        break;
                    }
                    case 1: {
                        if (p.c.lvbang != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 2;
                        p.c.removeItemBags(856, 2500);
                        p.c.removeItemBags(914, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvbang != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 3;
                        p.c.removeItemBags(856, 5000);
                        p.c.removeItemBags(914, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV3");
                        break;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvhoa != 0) {
                            p.conn.sendMessageLog("Hỏa nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvhoa == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV2\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV3\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 7) {
                            p.conn.sendMessageLog("Cậu không phải Ninja hắc ám không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 hỏa linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvhoa = 1;
                        p.c.removeItemBags(855, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo");
                        break;
                    }
                    case 1: {
                        if (p.c.lvhoa != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 2;
                        p.c.removeItemBags(855, 2500);
                        p.c.removeItemBags(915, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvhoa != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 3;
                        p.c.removeItemBags(855, 5000);
                        p.c.removeItemBags(915, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV3");
                        break;
                    }
                }
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }
   
     public static void npcshopjarai(Player p, byte npcid, byte menuId, byte b3) throws IOException, InterruptedException, SQLException {
        switch (menuId) { 
      case 0: {
            Server.manager.sendTB(p,"Coin hiện tại",
                    "\n-coin hiện tại của bạn là : "+p.coin);
            break;
                    }
            
            case 1: {
                switch (b3) {
                    case 0: {//nón
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(746, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(95, 220);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(746).name);
                        break;
                    }
                    case 1: {//áo
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(712, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(91, 220);
                            item.options.add(op);
                            op = new Option(80, 300);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(712).name);
                        break;
                    }
                    case 2: {//găng
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(747, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(94, 400);
                            item.options.add(op);
                            op = new Option(86, 800);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(747).name);
                        break;
                    }
                    case 3: {//quần
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(713, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(82, 2500);
                            item.options.add(op);
                            op = new Option(83, 2500);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(713).name);
                        break;
                    }
                    case 4: {//giày
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(748, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(82, 2500);
                            item.options.add(op);
                            op = new Option(84, 900);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(748).name);
                        break;
                    }
                    case 5: {//phù
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(750, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(83, 2500);
                            item.options.add(op);
                            op = new Option(84, 900);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(750).name);
                        break;
                    }
                    case 6: {//bội
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(751, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(87, 2000);
                            item.options.add(op);
                            op = new Option(96, 220);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(751).name);
                        break;
                    }
                    case 7: {//quần
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(749, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(92, 220);
                            item.options.add(op);
                            op = new Option(96, 220);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(749).name);
                        break;
                    }
                    case 8: {//chuyền
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(752, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            op = new Option(81, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(752).name);
                        break;
                    }
                    case 9: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 100k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item item = ItemTemplate.itemDefault(711, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                            op = new Option(87, 30000);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            op = new Option(81, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 100k.và mua món đồ"+ItemTemplate.ItemTemplateId(711).name);
                        break;
                    }
                    }
                break;
                    }
            
            
            case 2: {
                switch (b3) {
                    case 0: {//nón
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(753, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(95, 220);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(753).name);
                        break;
                    }
                    case 1: {//áo
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(715, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(91, 220);
                            item.options.add(op);
                            op = new Option(80, 300);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(715).name);
                        break;
                    }
                    case 2: {//găng
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(754, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(94, 400);
                            item.options.add(op);
                            op = new Option(86, 800);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(754).name);
                        break;
                    }
                    case 3: {//quần
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(716, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(82, 2500);
                            item.options.add(op);
                            op = new Option(83, 2500);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(716).name);
                        break;
                    }
                    case 4: {//giày
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(755, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(82, 2500);
                            item.options.add(op);
                            op = new Option(84, 900);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(755).name);
                        break;
                    }
                    case 5: {//phù
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(757, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(83, 2500);
                            item.options.add(op);
                            op = new Option(84, 900);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(757).name);
                        break;
                    }
                    case 6: {//bội
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(758, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(87, 2000);
                            item.options.add(op);
                            op = new Option(96, 220);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(758).name);
                        break;
                    }
                    case 7: {//quần
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(756, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(92, 220);
                            item.options.add(op);
                            op = new Option(96, 220);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(756).name);
                        break;
                    }
                    case 8: {//chuyền
                        if (p.coin < 20000) {
                            p.conn.sendMessageLog("bạn ko có 20k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 20000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                       Item item = ItemTemplate.itemDefault(759, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            op = new Option(81, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 20k.và mua món đồ"+ItemTemplate.ItemTemplateId(759).name);
                        break;
                    }
                    case 9: {
                        if (p.coin < 100000) {
                            p.conn.sendMessageLog("bạn ko có 100k coin.hãy nạp thêm coin đi bạn");
                            return;
                        }
                        p.coin -= 100000;
                        SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                        Item item = ItemTemplate.itemDefault(714, true);
                            item.quantity = 1;
                            item.upgrade = 17;
                            item.isLock = true;
                            Option op = new Option(47, 1000);
                            item.options.add(op);
                            op = new Option(3, 500);
                            item.options.add(op);
                            op = new Option(87, 30000);
                            item.options.add(op);
                             op = new Option(6, 2000);
                            item.options.add(op);
                             op = new Option(7, 2000);
                            item.options.add(op);
                             op = new Option(12, 200);
                            item.options.add(op);
                            op = new Option(18, 200);
                            item.options.add(op);
                            op = new Option(27, 18);
                            item.options.add(op);
                            op = new Option(28, 10);
                            item.options.add(op);
                            op = new Option(29, 2000);
                            item.options.add(op);
                            op = new Option(33, 200);
                            item.options.add(op);
                            op = new Option(48, 1000);
                            item.options.add(op);
                            op = new Option(85, 9);
                            item.options.add(op);
                            op = new Option(79, 35);
                            item.options.add(op);
                            op = new Option(81, 35);
                            item.options.add(op);
                            p.c.addItemBag(false, item);
                          Service.chatKTG( "Cảm ơn " + p.c.name + " đã ủng hộ ad 100k.và mua món đồ"+ItemTemplate.ItemTemplateId(714).name);
                        break;
                    }
                    }
                break;
                    }
        }}
     public static void npcdaide(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                 if (p.coin < 2000000000) {
                      p.sendAddchatYellow("đây là quà top 1 lv mà");
                            break;
                }
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 7) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 7 ô trống mới có thể nhập học!");
                    break;
                }
                p.coin -= 100000;
                SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                p.restSpoint();
                Admission.Admission(p, (byte) 8);
                Service.chatKTG( "LỚP ĐẠI ĐẾ " +"Chào mừng "+ p.c.name + " Đã gia nhập lớp hắc ám . nơi đào tạo nhân tài ninja");
               
            }
            case 1: {
                if (p.c.get().nclass != 8) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải Ninja hắc ám, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm tiềm năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvbang != 0) {
                            p.conn.sendMessageLog("Băng nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvbang == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV2\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV3\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 8) {
                            p.conn.sendMessageLog("Cậu không phải Ninja ĐẠI ĐẾ không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 băng linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvbang = 1;
                        p.c.removeItemBags(856, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong thiên lý");
                        break;
                    }
                    case 1: {
                        if (p.c.lvbang != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 2;
                        p.c.removeItemBags(856, 2500);
                        p.c.removeItemBags(914, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvbang != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 3;
                        p.c.removeItemBags(856, 5000);
                        p.c.removeItemBags(914, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV3");
                        break;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvhoa != 0) {
                            p.conn.sendMessageLog("Hỏa nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvhoa == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV2\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV3\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 8) {
                            p.conn.sendMessageLog("Cậu không phải Ninja ĐẠI ĐẾ không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 hỏa linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvhoa = 1;
                        p.c.removeItemBags(855, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo");
                        break;
                    }
                    case 1: {
                        if (p.c.lvhoa != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 2;
                        p.c.removeItemBags(855, 2500);
                        p.c.removeItemBags(915, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvhoa != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 3;
                        p.c.removeItemBags(855, 5000);
                        p.c.removeItemBags(915, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV3");
                        break;
                    }
                }
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }
     
       public static void npcloidien(Player p, byte npcid, byte menuId, byte b3) throws IOException, SQLException {
        switch (menuId) {
            case 0: {
                 if (p.coin < 2000000000) {
                      p.sendAddchatYellow("đây là quà top 1 lv mà");
                            break;
                }
                if (p.c.get().nclass > 0) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con đã vào lớp từ trước rồi mà.");
                    break;
                }
                if (p.c.get().ItemBody[1] != null) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con cần có 1 tâm hồn trong trắng mới có thể nhập học, hãy tháo vũ khí trên người ra!");
                    break;
                }
                if (p.c.getBagNull() < 7) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Hành trang cần phải có ít nhất 7 ô trống mới có thể nhập học!");
                    break;
                }
                p.coin -= 100000;
                SQLManager.stat.executeUpdate("UPDATE `player` SET `coin`=" + p.coin + " WHERE `id`=" + p.id + " LIMIT 1;");
                p.restSpoint();
                Admission.Admission(p, (byte) 9);
                Service.chatKTG( "LỚP Lôi Diện " +"Chào mừng "+ p.c.name + " Đã gia nhập lớp hắc ám . nơi đào tạo nhân tài ninja");
               
            }
            case 1: {
                if (p.c.get().nclass != 9) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Con không phải Ninja hắc ám, ta không thể giúp con tẩy điểm dược rồi.");
                    break;
                }
                if (b3 == 0) {
                    if (p.c.get().countTayTiemNang < 1) {
                        Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm tiềm năng của con đã hết.");
                        return;
                    }
                    p.restPpoint();
                    --p.c.get().countTayTiemNang;
                    Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm tiềm năng, hãy nâng điểm thật hợp lý nha.");
                    p.sendAddchatYellow("Tẩy điểm tiềm năng thành công");
                    break;
                }
                if (b3 != 1) {
                    break;
                }
                if (p.c.get().countTayKyNang < 1) {
                    Service.chatNPC(p, Short.valueOf(npcid), "Số lần tẩy điểm kỹ năng của con đã hết.");
                    return;
                }
                p.restSpoint();
                --p.c.get().countTayKyNang;
                Service.chatNPC(p, Short.valueOf(npcid), "Ta đã giúp con tẩy điểm kỹ năng, hãy nâng điểm thật hợp lý nha.");
                p.sendAddchatYellow("Tẩy điểm kỹ năng thành công");
                break;
            }
            case 2: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvbang != 0) {
                            p.conn.sendMessageLog("Băng nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvbang == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV2\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.lvbang == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động băng phong thiên lý LV3\n-Con nhận được hiệu ứng băng phong: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng đóng băng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 9) {
                            p.conn.sendMessageLog("Cậu không phải Ninja ĐẠI ĐẾ không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 băng linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvbang = 1;
                        p.c.removeItemBags(856, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong thiên lý");
                        break;
                    }
                    case 1: {
                        if (p.c.lvbang != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 2;
                        p.c.removeItemBags(856, 2500);
                        p.c.removeItemBags(914, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvbang != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(856) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 băng linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(914) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp băng phong đến đây");
                            return;
                        }
                        p.c.lvbang = 3;
                        p.c.removeItemBags(856, 5000);
                        p.c.removeItemBags(914, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công băng phong LV3");
                        break;
                    }
                }
            }
            case 3: {
                switch (b3) {
                    case 0: {
                        if (p.c.lvhoa != 0) {
                            p.conn.sendMessageLog("Hỏa nội của mày là bn!");
                            return;
                        }
                        if (p.c.lvhoa == 1) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 5%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 2) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV2\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 7%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.lvhoa == 3) {
                            p.conn.sendMessageLog("con đã học chiêu bị động thiên hỏa đại đạo LV3\n-Con nhận được hiệu ứng hỏa vũ: Tỷ lệ kích hoạt 10%\n-Đối tượng: người, quái và boss\n-Đòn tấn công khiến mục tiêu chịu hiệu ứng bỏng trong 2 giây");
                            return;
                        }
                        if (p.c.get().nclass != 9) {
                            p.conn.sendMessageLog("Cậu không phải Ninja ĐẠI ĐẾ không thể học");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 10000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 10000 hỏa linh châu và 1 triệu lượng tao mới dạy cho");
                            return;
                        }
                        p.c.lvhoa = 1;
                        p.c.removeItemBags(855, 10000);
                        p.upluongMessage(-1000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo");
                        break;
                    }
                    case 1: {
                        if (p.c.lvhoa != 1) {
                            p.conn.sendMessageLog("Mày phải học lv1 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 2500) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 2500 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 1) {
                            p.conn.sendMessageLog("mày phải mang 1 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 2;
                        p.c.removeItemBags(855, 2500);
                        p.c.removeItemBags(915, 1);
                        p.upluongMessage(-2000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV2");
                        break;
                    }
                    case 2: {
                        if (p.c.lvhoa != 2) {
                            p.conn.sendMessageLog("Mày phải học lv2 đã nhé con");
                            return;
                        }
                        if (p.c.quantityItemyTotal(855) < 5000) {
                            p.conn.sendMessageLog("mày phải thu thập đủ 5000 hỏa linh châu");
                            return;
                        }
                        if (p.c.quantityItemyTotal(915) < 2) {
                            p.conn.sendMessageLog("mày phải mang 2 sách nâng cấp thiên hỏa đến đây");
                            return;
                        }
                        p.c.lvhoa = 3;
                        p.c.removeItemBags(855, 5000);
                        p.c.removeItemBags(915, 2);
                        p.upluongMessage(-3000000L);
                        p.conn.sendMessageLog("Con đã học thành công thiên hỏa đại đạo LV3");
                        break;
                    }
                }
            }
            default: {
                Service.chatNPC(p, Short.valueOf(npcid), "Chức năng này đang cập nhật!");
            }
        }
    }
     
     
}
