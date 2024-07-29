package LTD.real;

import LTD.io.Session;
import LTD.io.Util;
import LTD.real.Char;
import LTD.real.ItemLeave;
import LTD.real.TileMap;
import LTD.server.Server;
import LTD.server.Service;
import LTD.stream.ChienTruong;
import LTD.stream.Client;
import LTD.template.ItemTemplate;
import LTD.template.MobTemplate;
import java.util.ArrayList;
import java.util.HashMap;

public class Mob {

    public boolean isFire;
    public boolean isIce;
    public boolean isWind;
    public long timeFire;
    public long timeIce;
    public long timeWind;
    public int id;
    public byte sys;
    public int hp;
    public int level;
    public int hpmax;
    public short x;
    public short y;
    public byte status;
    public int lvboss;
    public int dameFire;
    public long timeDameFire;
    public boolean isboss;
    public boolean isDie;
    public boolean isRefresh = true;
    public long xpup;
    public long timeRefresh;
    public long timeFight;
    public long timeDisable;
    public long timeDontMove;
    public boolean isDisable;
    public boolean isDontMove;
    public TileMap tileMap;
    public int idCharSkill25;
    public int yenmin = 100;
    public int yenmax = 200;
    public MobTemplate templates;
    private HashMap<Integer, Integer> nFight;
    private ArrayList<Character> sortFight;
    private static int[] arrMobLangCoId = new int[]{148, 146, 147, 148, 149, 151, 152, 154, 155, 156, 157, 159};
    private static int[] arrMobLTD = new int[]{75, 81, 91, 93, 94, 96, 95, 206, 108, 109, 110, 79, 106, 107, 112};
    private static int[] arrMobChienTruongId = new int[]{90, 91, 92, 93, 94, 95, 96, 97, 98, 99};

    public Mob(int id, int idtemplate, int level, TileMap tileMap) {
        int hp;
        this.id = id;
        this.templates = MobTemplate.entrys.get(idtemplate);
        this.level = level;
        this.hpmax = hp = this.templates.hp;
        this.hp = hp;
        this.xpup = 10000L;
        this.isDie = false;
        this.dameFire = 0;
        this.timeDameFire = -1L;
        this.timeFire = -1L;
        this.nFight = new HashMap();
        this.sortFight = new ArrayList();
        this.timeDisable = -1L;
        this.timeDontMove = -1L;
        this.isDisable = false;
        this.isDontMove = false;
        this.tileMap = tileMap;
        this.idCharSkill25 = -1;
    }

    public void setSkill25() {
        this.timeDameFire = -1L;
        this.dameFire = 0;
        this.timeFire = -1L;
        this.idCharSkill25 = -1;
    }

    public boolean checkMobLTD() {
        for (int i = 0; i < arrMobLTD.length; ++i) {
            if (this.templates.id != arrMobLTD[i]) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean checkMobLangCo() {
        for (int i = 0; i < arrMobLangCoId.length; ++i) {
            if (this.templates.id != arrMobLangCoId[i]) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean checkMobChienTruong() {
        return this.templates.id >= 90 && this.templates.id <= 99;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateHP(int num, int _charId, boolean liveAttack) {
        this.hp += num;
        Char _char = this.tileMap.getNinja(_charId);
        if (!liveAttack && _char != null) {
            this.Fight(_char.p.conn.id, Math.abs(num));
        }
        if (this.hp <= 0) {
            this.hp = 0;
            this.status = 0;
            this.isDie = true;
            if (this.isRefresh) {
                this.timeRefresh = System.currentTimeMillis() + 7500L;
            }
            if (this.isRefresh && this.checkMobLangCo()) {
                this.timeRefresh = System.currentTimeMillis() + 20000L;
            } else if (this.isRefresh && this.checkMobChienTruong()) {
                this.timeRefresh = System.currentTimeMillis() + 30000L;
            } else if (this.isRefresh && this.tileMap.map.getXHD() == 9) {
                this.timeRefresh = System.currentTimeMillis() + 20000L;
            }
            if (this.isboss) {
                if (this.templates.id != 198 && this.templates.id != 199 && this.templates.id != 200) {
                    this.isRefresh = false;
                    this.timeRefresh = -1L;
                } else {
                    this.timeRefresh = System.currentTimeMillis() + 60000L;
                }
            }
            if (_char != null) {
                Mob mob = this;
                synchronized (mob) {
                    this.handleAfterCharFight(_char);
                }
            }
        }
    }

    public void ClearFight() {
        this.nFight.clear();
    }

    public int sortNinjaFight() {
        int idN = -1;
        int dameMax = 0;
        for (int value : this.nFight.keySet()) {
            int dame = this.nFight.get(value);
            Session conn = Client.gI().getConn(value);
            if (conn == null || conn.player == null || conn.player.c == null || dame <= dameMax) {
                continue;
            }
            dameMax = this.nFight.get(value);
            idN = conn.player.c.id;
        }
        return idN;
    }

    public void Fight(int id, int dame) {
        if (!this.nFight.containsKey(id)) {
            this.nFight.put(id, dame);
        } else {
            int damenew = this.nFight.get(id);
            this.nFight.replace(id, damenew += dame);
        }
    }

    public void removeFight(int id) {
        if (this.nFight.containsKey(id)) {
            this.nFight.remove(id);
        }
    }

    public boolean isFight(int id) {
        return this.nFight.containsKey(id);
    }

    public void setDisable(boolean isDisable, long timeDisable) {
        this.isDisable = isDisable;
        this.timeDisable = timeDisable;
    }

    public void setDonteMove(boolean isDontMove, long timeDontMove) {
        this.isDontMove = isDontMove;
        this.timeDontMove = timeDontMove;
    }

    public boolean isDisable() {
        return this.isDisable;
    }

    public boolean isDonteMove() {
        return this.isDontMove;
    }

    private void handlerUpExpAndEvent(Char _char,int master){
        
        if(_char.p.c.get().level<=150){
            if(!this.tileMap.map.LTDMap()&&!this.tileMap.map.LTDMap2()){
                ItemLeave.randomLeave(this.tileMap, this, master, Util.nextInt(1, 3), 1);
            }       
            if (this.lvboss == 1) {
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 200;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(1000);
                _char.p.sendAddchatYellow("Bạn nhận được 1000 lượng");
            }
            if (this.lvboss == 2) {
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 20;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(2000);
                _char.p.sendAddchatYellow("Bạn nhận được 2000 lượng");
                if(!this.tileMap.map.LTDMap()&&!this.tileMap.map.LTDMap2()){
                    ItemLeave.leaveTTTT(this.tileMap, this, master);
                }  
                
            }
            if(this.lvboss != 1&&this.lvboss != 2){
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 199;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(10);
                _char.p.sendAddchatYellow("Bạn nhận được 10 lượng");
            }
        }
        if(_char.p.c.get().level>150){
            if(!this.tileMap.map.LTDMap()&&!this.tileMap.map.LTDMap2()){
                ItemLeave.randomLeave(this.tileMap, this, master, Util.nextInt(1, 3), 1);
            }        
            if (this.lvboss == 1) {
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 400;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(1000);
                _char.p.sendAddchatYellow("Bạn nhận được 1000 lượng");
            }
            if (this.lvboss == 2) {
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 60;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(2000);
                _char.p.sendAddchatYellow("Bạn nhận được 2000 lượng");
                if(!this.tileMap.map.LTDMap()&&!this.tileMap.map.LTDMap2()){
                    ItemLeave.leaveTTTT(this.tileMap, this, master);
                } 
            }
            if(this.lvboss != 1&&this.lvboss != 2){
                long expup = (Level.getMaxExp(_char.p.c.get().level + 1) - Level.getMaxExp(_char.p.c.get().level)) / 1200;
                if(_char.p.c.get().exptype == 1){
                    _char.p.updateExp(expup);
                }
                _char.p.upluongMessage(10);
                _char.p.sendAddchatYellow("Bạn nhận được 10 lượng");
            }
            
        }
        short idI1=0;
        idI1 = ItemLeave.arrSuKienLangCo[Util.nextInt(ItemLeave.arrSuKienLangCo.length)];
        Item itemup1 = ItemTemplate.itemDefault(idI1);
        _char.addItemBag(true, itemup1);
    }

    public void handleAfterCharFight(Char _char) {
        if (this.level > 1) {
            ++this.tileMap.numMobDie;
        }
        if (this.templates.id == 0 && _char.isTaskDanhVong == 1 && _char.taskDanhVong[0] == 6) {
            _char.taskDanhVong[1] = _char.taskDanhVong[1] + 1;
            if (_char.c.taskDanhVong[1] == _char.c.taskDanhVong[2]) {
                _char.p.upluongMessage(500);
                _char.p.sendAddchatYellow("Bạn nhận được 500 lượng");
                _char.p.sendAddchatYellow("Bạn đã hoàn thành nhiệm vụ danh vọng.");
            }
        }
        if (this.templates.id == 230 && this.tileMap.map.bossTuanLoc != null) {
            this.isRefresh = false;
            ItemLeave.leaveItemBOSSTuanLoc(this.tileMap, this, -1);
            _char.upyenMessage(100000000);
            _char.p.sendAddchatYellow("Bạn nhận được 100000000 yên");
            _char.p.upluongMessage(30000);
            _char.p.sendAddchatYellow("Bạn nhận được 30000 lượng");
            this.tileMap.mobs.clear();
        } else if (this.tileMap.map.mapLDGT()) {
            if (this.lvboss == 0 && this.templates.id != 81) {
                this.isRefresh = false;
                switch (this.tileMap.map.id) {
                    case 81:
                    case 82:
                    case 83:
                    case 84:
                    case 85:
                    case 86: {
                        if (this.tileMap.mobs.size() - this.tileMap.numMobDie != 1) {
                            break;
                        }
                        this.tileMap.refreshMob(this.tileMap.mobs.size() - 1);
                        break;
                    }
                    case 87:
                    case 88:
                    case 89: {
                        if (this.tileMap.mobs.size() - this.tileMap.numMobDie != 5) {
                            break;
                        }
                        this.tileMap.refreshMob(this.tileMap.mobs.size() - 5);
                    }
                }
                this.tileMap.map.lanhDiaGiaToc.plusPoint(1);
                this.tileMap.map.lanhDiaGiaToc.clanManager.upExp(50);
            } else if (this.lvboss == 1 && this.templates.id != 81) {
                this.isRefresh = false;
                _char.p.upluongMessage(500);
                _char.p.sendAddchatYellow("Bạn nhận được 500 lượng");
                ItemLeave.leaveChiaKhoa(this.tileMap, this, -1);
                this.tileMap.map.lanhDiaGiaToc.plusPoint(2);
                this.tileMap.map.lanhDiaGiaToc.clanManager.upExp(100);
                if (this.tileMap.map.id >= 87 && this.tileMap.map.id <= 89) {
                    for (int i2 = this.tileMap.mobs.size() - 4; i2 < this.tileMap.mobs.size() - 1; ++i2) {
                        this.tileMap.refreshMob(i2);
                    }
                }
            } else if (this.lvboss == 0 && this.templates.id == 81) {
                this.isRefresh = true;
                ItemLeave.leaveLDGT(this.tileMap, this, -1);
            } else if (this.lvboss == 2 && this.templates.id == 82) {
                this.isRefresh = false;
                ItemLeave.leaveYen(this.tileMap, this, -1);
                ItemLeave.leaveYen(this.tileMap, this, -1);
                ItemLeave.leaveYen(this.tileMap, this, -1);
                ItemLeave.leaveYen(this.tileMap, this, -1);
                ItemLeave.leaveYen(this.tileMap, this, -1);
                ItemLeave.leaveLDGT(this.tileMap, this, -1);
                _char.p.upluongMessage(1000);
                _char.p.sendAddchatYellow("Bạn nhận được 1000 lượng");
                this.tileMap.map.lanhDiaGiaToc.finish();
                this.tileMap.map.lanhDiaGiaToc.clanManager.upExp(300);
                this.tileMap.map.lanhDiaGiaToc.plusPoint(3);
            }
        } else if ((this.templates.id == 98 || this.templates.id == 99) && ChienTruong.chienTruong != null && this.tileMap.map.mapChienTruong()) {
            if (this.templates.id == 98) {
                ChienTruong.pheWin = 1;
            } else if (this.templates.id == 99) {
                ChienTruong.pheWin = 0;
            }
            ChienTruong.chienTruong.finish();
        } else if (this.level > 1) {
            if (this.tileMap.map.cave != null) {
                if (this.isboss) {
                    this.tileMap.map.cave.updatePoint(100);
                } else if (this.lvboss == 2) {
                    this.tileMap.map.cave.updatePoint(20);
                } else if (this.lvboss == 1) {
                    this.tileMap.map.cave.updatePoint(10);
                } else {
                    this.tileMap.map.cave.updatePoint(1);
                }
            } else if (ChienTruong.chienTruong != null && this.tileMap.map.mapChienTruong()) {
                ++_char.pointCT;
                if (_char.pointCT > 14000) {
                    _char.pointCT = 14000;
                }
                Service.updatePointCT(_char, 1);
            } else if (this.tileMap.map.giaTocChien != null && this.tileMap.map.mapGTC()) {
                ++_char.pointGTC;
                if (_char.pointGTC > 14000) {
                    _char.pointGTC = 14000;
                }
                Service.sendPointGTC(_char, 1);
            }
            if (_char.isTaskHangNgay == 1 && this.templates.id == _char.taskHangNgay[3] && _char.taskHangNgay[0] == 0 && _char.taskHangNgay[1] < _char.taskHangNgay[2]) {
                _char.taskHangNgay[1] = _char.taskHangNgay[1] + 1;
                Service.updateTaskOrder(_char, (byte) 0);
            }
            if (_char.isTaskTaThu == 1 && this.templates.id == _char.taskTaThu[3] && this.lvboss == 3 && _char.taskTaThu[0] == 1 && _char.taskTaThu[1] < _char.taskTaThu[2]) {
                if (_char.get().party != null) {
                    Player pl;
                    byte i;
                    int miss;
                    for (miss = tileMap.players.size() - 1; miss >= 0; miss--) {
                        pl = tileMap.players.get(miss);
                        if (pl != null && pl.c != null && pl.c.id != _char.id && pl.c.party == _char.party) {
                            _char.taskTaThu[1] = _char.taskTaThu[1] + 1;
                            Service.updateTaskOrder(_char, (byte) 1);
                            pl.c.taskTaThu[1] = pl.c.taskTaThu[1] + 1;
                            _char.p.upluongMessage(100);
                            Service.updateTaskOrder(pl.c, (byte) 1);
                        }
                    }
                } else {
                    _char.taskTaThu[1] = _char.taskTaThu[1] + 1;
                    _char.p.upluongMessage(100);
                    Service.updateTaskOrder(_char, (byte) 1);
                }
            }
            if (_char.isTaskDanhVong == 1 && _char.taskDanhVong[0] == 7 && Math.abs(this.level - _char.get().level) <= 10) {
                _char.taskDanhVong[1] = _char.taskDanhVong[1] + 1;
                if (_char.c.taskDanhVong[1] == _char.c.taskDanhVong[2]) {
                    _char.p.upluongMessage(500);
                    _char.p.sendAddchatYellow("Bạn đã hoàn thành nhiệm vụ danh vọng.");
                }
            }
            int master = this.sortNinjaFight();
            if (this.lvboss == 1) {
                --this.tileMap.numTA;
                if (_char.isTaskDanhVong == 1 && _char.taskDanhVong[0] == 8 && Math.abs(this.level - _char.get().level) <= 10) {
                    _char.taskDanhVong[1] = _char.taskDanhVong[1] + 1;
                    if (_char.c.taskDanhVong[1] == _char.c.taskDanhVong[2]) {
                        _char.p.upluongMessage(500);
                        _char.p.sendAddchatYellow("Bạn đã hoàn thành nhiệm vụ danh vọng.");
                    }
                }
            } else if (this.lvboss == 2) {
                --this.tileMap.numTL;
                if (_char.isTaskDanhVong == 1 && _char.taskDanhVong[0] == 9 && Math.abs(this.level - _char.get().level) <= 10) {
                    _char.taskDanhVong[1] = _char.taskDanhVong[1] + 1;
                    if (_char.c.taskDanhVong[1] == _char.c.taskDanhVong[2]) {
                        _char.p.upluongMessage(500);
                        _char.p.sendAddchatYellow("Bạn đã hoàn thành nhiệm vụ danh vọng.");
                    }
                }
            }
            if (Math.abs(this.level - _char.get().level) <= 10 || this.tileMap.map.LangCo() || this.tileMap.map.mapChienTruong() || this.tileMap.map.LTDMap()) {
                byte tile = (byte) Util.nextInt(1, 100);
                int yenup = Util.nextInt(yenmin * _char.get().level, yenmax * _char.get().level);
                if (this.lvboss == 0) {
                    if (tile <= 2 && _char.level >= 60) {
                        Item itemup = ItemTemplate.itemDefault(236);
                        itemup.isLock = itemup.isLock;
                        _char.addItemBag(true, itemup);
                    }
                    if (_char.nhiemvu == 1 || _char.nhiemvu == 2) {
                        _char.numquai++;
                    }
                    if (tile <= 2) {
                        _char.upyenMessage(yenup);
                        _char.p.sendAddchatYellow("Bạn nhận được " + yenup + " yên");
                    }
                } else if (this.lvboss == 1) {
                    if (tile <= 50 && _char.level >= 60) {
                        Item itemup = ItemTemplate.itemDefault(236);
                        itemup.isLock = itemup.isLock;
                        _char.addItemBag(true, itemup);
                    }
                    if (_char.lvhnt < 9) {
                        _char.exphnt += 2000L;
                    }
                    if (_char.lvbht < 9) {
                        _char.expbht += 2000L;
                    }
                    _char.p.upluongMessage(500);
                    _char.p.sendAddchatYellow("Bạn nhận được 500 lượng");
                    _char.upyenMessage(10000);
                    _char.p.sendAddchatYellow("Bạn nhận được 10000 yên");
                } else if (this.lvboss == 2) {
                    if (tile <= 70 && _char.level >= 60) {
                        Item itemup = ItemTemplate.itemDefault(236);
                        itemup.isLock = itemup.isLock;
                        _char.addItemBag(true, itemup);
                    }
                    if (_char.lvhnt < 9) {
                        _char.exphnt += 4000L;
                    }
                    if (_char.lvbht < 9) {
                        _char.expbht += 4000L;
                    }
                    _char.p.upluongMessage(1000);
                    _char.p.sendAddchatYellow("Bạn nhận được 1000 lượng");
                    _char.upyenMessage(20000);
                    _char.p.sendAddchatYellow("Bạn nhận được 20000 yên");
                }
                if (Server.manager.event != 0 && this.tileMap.map.id <= 164 && tile <= 30) {
                    short idI = ItemLeave.arrItemskAll[Util.nextInt(ItemLeave.arrItemskAll.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    short idI1 =ItemLeave.itemDropEvent();
                    if(idI1 !=0){
                        Item itemup1 = ItemTemplate.itemDefault(idI1);
                        itemup1.isLock = true;
                        _char.addItemBag(true, itemup1);
                    }
                    _char.addItemBag(true, itemup); 
                    _char.p.upluongMessage(5);
                    _char.p.sendAddchatYellow("Bạn nhận được 5 lượng");
                    _char.upxuMessage(50);
                    _char.p.sendAddchatYellow("Bạn nhận được 50 xu");
                    _char.upyenMessage(50);
                    _char.p.sendAddchatYellow("Bạn nhận được 50 yên");
                }
            }
            byte tile = (byte) Util.nextInt(1, 100);
            if (this.lvboss == 1) {
                short idI1 =0;
                idI1 = ItemLeave.arrItemThuLinh[Util.nextInt(ItemLeave.arrItemThuLinh.length)];
                Item itemup1 = ItemTemplate.itemDefault(idI1);
                _char.addItemBag(true, itemup1);
                _char.p.upluongMessage(100);
                _char.upyenMessage(1000);
                _char.p.sendAddchatYellow("Bạn nhận được 100 lượng");
                _char.p.sendAddchatYellow("Bạn nhận được 1000 yên");
            } else if (this.lvboss == 2) {
                short idI1 =0;
                idI1 = ItemLeave.arrItemThuLinh[Util.nextInt(ItemLeave.arrItemThuLinh.length)];
                Item itemup1 = ItemTemplate.itemDefault(idI1);
                _char.addItemBag(true, itemup1);
                _char.p.upluongMessage(400);
                _char.upyenMessage(2000);
                _char.p.sendAddchatYellow("Bạn nhận được 400 lượng");
                _char.p.sendAddchatYellow("Bạn nhận được 2000 yên");
            }
            if (this.tileMap.map.VDMQ() && (_char.get().getEffId(40) != null || _char.get().getEffId(41) != null) && Math.abs(this.level - _char.get().level) <= 10) {
                ItemLeave.randomLeave(this.tileMap, this, master, Util.nextInt(1, 2), 0);
            } else if (this.tileMap.map.LangCo()) {
                //drop và exp làng cổ
                handlerUpExpAndEvent(_char,master);
                if(_char.p.c.level >=150){
                    _char.p.c.expCS +=_char.p.c.level;
                }
            } else if (this.tileMap.map.TruyenThuyet()) {
                // ItemLeave.randomLeave(this.tileMap, this, master, Util.nextInt(1, 3), 1);
                // if (this.lvboss == 2) {
                //     _char.p.upluongMessage(800);
                //     ItemLeave.leaveTTTT(this.tileMap, this, master);
                // }
                handlerUpExpAndEvent(_char,master);
                if(_char.p.c.level >=150){
                    if (this.lvboss == 2) {
                         _char.p.c.expCS +=_char.p.c.level*40;
                        _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*40+" exp chuyển sinh");
                    }else{
                          _char.p.c.expCS +=_char.p.c.level*2;
                        _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*2+" exp chuyển sinh");
                    }   
                }
            } else if (this.tileMap.map.LTDMap()) {
                if (tile <= 5) {
                    Item itemup = ItemTemplate.itemDefault(236);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (tile <= 15) {
                    short idI = ItemLeave.Ltd[Util.nextInt(ItemLeave.Ltd.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (tile <= 10) {
                    short idI = ItemLeave.Dadv[Util.nextInt(ItemLeave.Dadv.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (this.lvboss == 2) {
                    if (tile <= 50) {
                        short idI = ItemLeave.Ltd2[Util.nextInt(ItemLeave.Ltd2.length)];
                        Item itemup = ItemTemplate.itemDefault(idI);
                        itemup.isLock = itemup.isLock;
                        _char.p.upluongMessage(400);
                        _char.addItemBag(true, itemup);
                    }
                 
                    ItemLeave.leaveTLTT(this.tileMap, this, master);
                }
                if(_char.p.c.level >=150){
                    if (this.lvboss == 2) {
                         _char.p.c.expCS +=_char.p.c.level*60;
                         _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*60+" exp chuyển sinh");
                        }else{
                            _char.p.c.expCS +=_char.p.c.level*2;
                            _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*2+" exp chuyển sinh");
                    }   
                    
                }
                handlerUpExpAndEvent(_char,master);
            } else if (this.tileMap.map.LTDMap2()) {
                
                _char.p.c.expCS +=_char.p.c.level*2;
                if (tile <= 10) {
                    Item itemup = ItemTemplate.itemDefault(236);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (tile <= 15) {
                    short idI = ItemLeave.Ltd[Util.nextInt(ItemLeave.Ltd.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (tile <= 20) {
                    short idI = ItemLeave.Dadv[Util.nextInt(ItemLeave.Dadv.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                }
                if (this.lvboss == 2) {
                    short idI = ItemLeave.Ltd2[Util.nextInt(ItemLeave.Ltd2.length)];
                    Item itemup = ItemTemplate.itemDefault(idI);
                    itemup.isLock = itemup.isLock;
                    _char.addItemBag(true, itemup);
                    _char.p.upluongMessage(400);
                    Item itemup1 = ItemTemplate.itemDefault(704);
                    itemup1.isLock = itemup1.isLock;
                    _char.addItemBag(true, itemup1);
                    
                }
                if(_char.p.c.level >=150){
                    long exp ;
                    if (this.lvboss == 2) {
                        exp= _char.p.c.expCS +=_char.p.c.level*60;
                        _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*60+" exp chuyển sinh");
                    }else{
                        exp=  _char.p.c.expCS +=_char.p.c.level*2;
                    }   
                    _char.p.sendAddchatYellow("Bạn nhận được "+_char.p.c.level*2+" exp chuyển sinh");
                }
                handlerUpExpAndEvent(_char,master);
            }
            if (this.isboss) {
                if (this.templates.id == 228 && this.tileMap.map.id == 4) {
                    Service.chatKTG(_char.name + " đã đập chết " + this.templates.name + " thật quá là ghê gớm!");
                    _char.p.upluongMessage(100000);
                    _char.upxuMessage(5000000);
                    _char.bingo += 15;
                    ItemLeave.leaveBossTo(this.tileMap, this, -1);
                } else if (this.tileMap.map.cave == null) {
                    if (_char.lvhnt < 9 && _char.lvbht < 9) {
                        _char.exphnt += 6000L;
                        _char.expbht += 6000L;
                    }
                    _char.p.upluongMessage(50000);
                    _char.upxuMessage(2500000);
                    _char.bingo += 2;
                    Service.chatKTG(_char.name + " đã đập chết " + this.templates.name);
                    _char.bingo += 2;
                    for (int i = 0; i < 10; ++i) {
                        ItemLeave.leaveYen(this.tileMap, this, master);
                    }
                    ItemLeave.leaveItemBOSS(this.tileMap, this, master);
                } else if (this.tileMap.map.cave != null && this.tileMap.map.getXHD() == 9 && (this.tileMap.map.id == 157 && this.tileMap.map.cave.level == 0 || this.tileMap.map.id == 158 && this.tileMap.map.cave.level == 1 || this.tileMap.map.id == 159 && this.tileMap.map.cave.level == 2) && Util.nextInt(3) < 3) {
                    ItemLeave.leaveYen(this.tileMap, this, master);
                    ItemLeave.leaveYen(this.tileMap, this, master);
                    this.tileMap.map.cave.updatePoint(this.tileMap.mobs.size());
                    _char.p.upluongMessage(10);
                    for (int k2 = 0; k2 < this.tileMap.mobs.size(); k2 = (int) ((short) (k2 + 1))) {
                        if (this.tileMap.mobs.get(k2) == null) {
                            continue;
                        }
                        Mob var12 = this.tileMap.mobs.get(k2);
                        if (!var12.isDie) {
                            var12.updateHP(-var12.hpmax, _char.id, true);
                        }
                        var12.isRefresh = false;
                        for (int h = 0; h < this.tileMap.players.size(); h = (int) ((short) (h + 1))) {
                            Service.setHPMob(this.tileMap.players.get((int) h).c, var12.id, 0);
                        }
                    }
                    ++this.tileMap.map.cave.level;
                }
            }
            if (this.tileMap.map.cave != null && this.tileMap.map.getXHD() < 9) {
                this.isRefresh = false;
                if (this.tileMap.numMobDie == this.tileMap.mobs.size()) {
                    if (this.tileMap.map.getXHD() == 5) {
                        if (this.tileMap.map.id == 105) {
                            this.tileMap.map.cave.openMap();
                            this.tileMap.map.cave.openMap();
                            this.tileMap.map.cave.openMap();
                        } else if (this.tileMap.map.id != 106 && this.tileMap.map.id != 107 && this.tileMap.map.id != 108) {
                            this.tileMap.map.cave.openMap();
                        } else {
                            this.tileMap.map.cave.finsh = (byte) (this.tileMap.map.cave.finsh + 1);
                            if (this.tileMap.map.cave.finsh >= 3) {
                                this.tileMap.map.cave.openMap();
                            }
                        }
                    } else if (this.tileMap.map.getXHD() == 6 && this.tileMap.map.id == 116) {
                        if (this.tileMap.map.cave.finsh == 0) {
                            this.tileMap.map.cave.openMap();
                        } else {
                            this.tileMap.map.cave.finsh = (byte) (this.tileMap.map.cave.finsh + 1);
                        }
                        this.tileMap.numMobDie = 0;
                        for (int l2 = 0; l2 < this.tileMap.mobs.size(); l2 = (int) ((short) (l2 + 1))) {
                            this.tileMap.refreshMob(l2);
                        }
                    } else {
                        this.tileMap.map.cave.openMap();
                    }
                }
            }
        }
    }
}
