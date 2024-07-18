package LTD.real;

import LTD.io.Util;
import LTD.template.ItemTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {
    public short id;
    public boolean isLock;
    public byte upgrade;
    public boolean isExpires;
    public int quantity;
    public long expires;
    public int saleCoinLock;
    public int buyCoin;
    public int buyCoinLock;
    public int buyGold;
    public byte sys;
    public ArrayList<Option> options;
    public List<Item> ngocs;
    private static final short[] DEFAULT_RANDOM_ITEM_IDS = new short[]{7, 8, 9, 436, 437, 438, 695};

    public Item() {
        this.id = -1;
        this.isLock = false;
        this.setUpgrade(0);
        this.isExpires = false;
        this.quantity = 1;
        this.expires = -1L;
        this.saleCoinLock = 0;
        this.buyCoin = 0;
        this.buyCoinLock = 0;
        this.buyGold = 0;
        this.sys = 0;
        this.options = new ArrayList<Option>();
        this.ngocs = new ArrayList();
    }
public void upBKnext(byte next) {
        this.setUpgrade(this.getUpgrade() + next);
        if (this.options != null) {
        short i;
        Option op;
        for (i = 0; i<this.options.size(); ++i) {
            op = this.options.get(i);
            if (op.id == 82 || op.id == 83) {
                Option ops = op;
                ops.param += 400 * next;
            }
           else if (op.id == 84 || op.id == 81 || op.id == 95 || op.id == 96 | op.id == 97) {
                Option ops2 = op;
                ops2.param += 50 * next;
            }
           else if (op.id == 87 || op.id == 88 || op.id == 89 || op.id == 90) {
                Option ops3 = op;
                ops3.param += 250 * next;
            }
           else if (op.id == 92 || op.id == 80 || op.id == 94 || op.id == 86) {
                Option ops4 = op;
                ops4.param += 15 * next;
            }
        }
    }
    }
    public Item clone() {
        Item item = new Item();
        item.id = this.id;
        item.isLock = this.isLock;
        item.setUpgrade(this.getUpgrade());
        item.isExpires = this.isExpires;
        item.quantity = this.quantity;
        item.expires = this.expires;
        item.saleCoinLock = this.saleCoinLock;
        item.buyCoin = this.buyCoin;
        item.buyCoinLock = this.buyCoinLock;
        item.buyGold = this.buyGold;
        item.sys = this.sys;
        for (int i = 0; i < this.options.size(); ++i) {
            item.options.add(new Option(this.options.get(i).id, this.options.get(i).param));
        }
        item.ngocs.addAll(this.ngocs);
        return item;
    }

    public int getUpMax() {
        ItemTemplate data = ItemTemplate.ItemTemplateId(this.id);
        if (data.level >= 1 && data.level < 20) {
            return 4;
        }
        if (data.level >= 20 && data.level < 40) {
            return 8;
        }
        if (data.level >= 40 && data.level < 50) {
            return 12;
        }
        if (data.level >= 50 && data.level < 60) {
            return 14;
        }
        return 16;
    }
    
    public void upgradeNext(byte next) {
        this.setUpgrade(this.getUpgrade() + next);
        if (this.options != null) {
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 6 || itemOption.id == 7) {
                    Option option = itemOption;
                    option.param += 15 * next;
                }
                else if (itemOption.id == 8 || itemOption.id == 9 || itemOption.id == 19) {
                    Option option2 = itemOption;
                    option2.param += 10 * next;
                }
                else if (itemOption.id == 10 || itemOption.id == 11 || itemOption.id == 12 || itemOption.id == 13 || itemOption.id == 14 || itemOption.id == 15 || itemOption.id == 17 || itemOption.id == 18 || itemOption.id == 20) {
                    Option option3 = itemOption;
                    option3.param += 5 * next;
                }
                else if (itemOption.id == 21 || itemOption.id == 22 || itemOption.id == 23 || itemOption.id == 24 || itemOption.id == 25 || itemOption.id == 26) {
                    Option option4 = itemOption;
                    option4.param += 150 * next;
                }
                else if (itemOption.id == 16) {
                    Option option5 = itemOption;
                    option5.param += 3 * next;
                }
            }
        }
    }
    
    public void ncMatNa(byte next) {
        this.setUpgrade(this.getUpgrade() + next);
        if (this.options != null) {
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 87){
                    Option option6 = itemOption;
                    option6.param += 10000 * next;
                }               
                else if (itemOption.id == 94){
                    Option option8 = itemOption;
                    option8.param += next;
                }
                else if (itemOption.id == 113){
                    Option option9 = itemOption;
                    option9.param += 200 * next;
                }              
                else if (itemOption.id == 114){
                    Option option9 = itemOption;
                    option9.param += 50 * next;
                }
            }
        }
    }
    
    public void ncYoroi(byte next) {
        this.setUpgrade(this.getUpgrade() + next);
        if (this.options != null) {
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 82 || itemOption.id == 83){
                    Option option = itemOption;
                    option.param += 3126 * next;
                }
                else if (itemOption.id == 84){
                    Option option2 = itemOption;
                    option2.param += 10 * next;
                }
                else if (itemOption.id == 81|| itemOption.id == 79){
                    Option option3 = itemOption;
                    option3.param += 1 * next;
                }
                else if (itemOption.id == 80){
                    Option option3 = itemOption;
                    option3.param += 50 * next;
                }
            }
        }
    }
    
    public void NhanVKTop(int param){
        if (this.options != null) {          
            short i;
            Option itemOption;
            if(param == 300) {
                for (i = 0; i < this.options.size(); ++i) {
                    itemOption = this.options.get(i);
                    if (itemOption.id == 0 || itemOption.id == 1) {
                        Option option = itemOption;
                        option.param = 1000;
                    }
                    if (itemOption.id == 8 || itemOption.id == 9) {                 
                       Option option2 = itemOption;
                       option2.param = 300;
                    }
                }
            }
            else if (param == 500){
                for (i = 0; i < this.options.size(); ++i) {
                    itemOption = this.options.get(i);
                    if (itemOption.id == 0 || itemOption.id == 1) {
                        Option option = itemOption;
                        option.param = 2000;
                    }
                    if (itemOption.id == 8 || itemOption.id == 9) {                 
                       Option option2 = itemOption;
                       option2.param = 500;
                    }
                }
            }
        }
    }
    
    public void NhanUngLong(){
        if (this.options != null) {          
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 133 || itemOption.id == 134) {
                    Option option = itemOption;
                    option.param = Util.nextInt(1, 12);
                }
                if (itemOption.id == 6) {
                    Option option2 = itemOption;
                    option2.param = Util.nextInt(10000, 68000);
                }
            }
        }
    }
    
    public int VKTop(){
        if (this.options != null) {
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 8 || itemOption.id == 9) {
                    Option option = itemOption;
                    if(option.param >= 300){
                        return option.param;
                    }
                    else if(option.param >= 500){
                        return option.param;
                    }
                    return option.param;
                }
            }
        }
        return 0;
    }

    public int getOptionShopMin(int opid, int param) {
        if (opid == 0 || opid == 1 || opid == 21 || opid == 22 || opid == 23 || opid == 24 || opid == 25 || opid == 26) {
            return param - 50 + 1;
        }
        if (opid == 6 || opid == 7 || opid == 8 || opid == 9 || opid == 19) {
            return param - 10 + 1;
        }
        if (opid == 2 || opid == 3 || opid == 4 || opid == 5 || opid == 10 || opid == 11 || opid == 12 || opid == 13 || opid == 14 || opid == 15 || opid == 17 || opid == 18 || opid == 20) {
            return param - 5 + 1;
        }
        if (opid == 16) {
            return param - 3 + 1;
        }
        return param;
    }

    public boolean isTypeBody() {
        return ItemTemplate.isTypeBody(this.id);
    }

    public boolean isTypeNgocKham() {
        return ItemTemplate.isTypeNgocKham(this.id);
    }

    public ItemTemplate getData() {
        return ItemTemplate.ItemTemplateId(this.id);
    }

    public byte getUpgrade() {
        return this.upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = (byte)upgrade;
    }

    protected boolean isTypeTask() {
        ItemTemplate data = this.getData();
        return data.type == 23 || data.type == 24 || data.type == 25;
    }

    public boolean isLock() {
        return this.isLock;
    }
    
    /*public boolean isTL() {
        boolean check = false;
        if (this.options != null) {
            short i;
            Option itemOption;
            for (i = 0; i < this.options.size(); ++i) {
                itemOption = this.options.get(i);
                if (itemOption.id == 85){
                    check = true;
                    break;                
                }
            }
        }
        return check;
    }*/

    public void setLock(boolean lock) {
        this.isLock = lock;
    }

    public static void rollItemVIP(Player p,int i){
        if(p.c.quantityItemyTotal(672)>=5000&&p.c.quantityItemyTotal(671)>=5000&&p.c.quantityItemyTotal(434)>=5000&&p.c.quantityItemyTotal(433)>=5000&&p.c.quantityItemyTotal(302)>=5000&&p.c.quantityItemyTotal(303)>=5000&&p.c.quantityItemyTotal(643)>=5000&&p.c.quantityItemyTotal(644)>=5000&&p.c.quantityItemyTotal(592)>=5000&&p.c.quantityItemyTotal(593)>=5000&&p.c.quantityItemyTotal(987)>=5000&&p.c.quantityItemyTotal(927)>=5000&&p.c.quantityItemyTotal(926)>=5000){
            // 672,673,434,433,302,303,643,644,592,593,987,927,926
            if (p.c.getBagNull() == 0) {
                p.conn.sendMessageLog("Hành trang không đủ chỗ trống");
            } else {
                short[] arrPet = new short[]{951,952,953,954,950,931,932,934,935,936,937,938,939,941,940,942,943,944,945,946,947,948,949,419,583,584,585,586,587,588,589,742,772,781,825,826,832,833,834,835,836,837,930};
                short[] arrBK = new short[]{397,398,399,400,401,402};
                short[] arrNTGT = new short[]{423,424,425,426,427};
                short[] arrEye = new short[]{685,686,687,688,689,690,691,692,693,694,840,846,841,838};
                short[] arrCustom = new short[]{799,800,568,569,570,571,795,796};
                short[] arrMounts = new short[]{828, 443, 523,524,776,777,801,802,803,798,831};
                short[] arrMask = new short[]{911,346,344,337,338,542,541,594,919,912,616,615,614,613,513,512,406,405,354,353,352,351,345,343,287,286,274,273,259,258,406,407,408,898,897,896,895,830,813,814,815,816,817,771,745};
                p.c.removeItemBags(672, 5000);
                p.c.removeItemBags(673, 5000);
                p.c.removeItemBags(434, 5000);
                p.c.removeItemBags(433, 5000);
                p.c.removeItemBags(302, 5000);
                p.c.removeItemBags(303, 5000);
                p.c.removeItemBags(643, 5000);
                p.c.removeItemBags(644, 5000);
                p.c.removeItemBags(592, 5000);
                p.c.removeItemBags(593, 5000);
                p.c.removeItemBags(987, 5000);
                p.c.removeItemBags(927, 5000);
                p.c.removeItemBags(926, 5000);
                Option critDmg = new Option(67, Util.nextInt(150,250));
                Option critRate = new Option(69, Util.nextInt(150,250));
                Option dame = new Option(73, Util.nextInt(4000,5000));
                Option damePercent = new Option(94, Util.nextInt(40,50));
                Option dmgTankerMounts = new Option(74, Util.nextInt(1500,2500));
                Option startPoint = new Option(57, Util.nextInt(250,500));
                Option startPointPercent = new Option(58, Util.nextInt(20,40));
                Option maxHP = new Option(125, Util.nextInt(20000,50000));
                Option dmgRef = new Option(126, Util.nextInt(1000,2500));
                Option exp = new Option(100, Util.nextInt(20,100));
                Option eva = new Option(115, Util.nextInt(500,1000));
                Option acc = new Option(116, Util.nextInt(500,1000));
                Option resAll = new Option(118, Util.nextInt(1000,1500));
                Option dmgMob = new Option(102, Util.nextInt(5000,8000));
                Option resFire = new Option(95, Util.nextInt(2000,3000));
                Option resIce = new Option(96, Util.nextInt(2000,3000));
                Option resWind = new Option(97, Util.nextInt(2000,3000));
                Option resFirePercent = new Option(127, Util.nextInt(20,30));
                Option resIcePercent = new Option(128, Util.nextInt(20,30));
                Option resWindPercent = new Option(129, Util.nextInt(20,30));
                Option dmgFire = new Option(88, Util.nextInt(4000,5000));
                Option dmgIce = new Option(89, Util.nextInt(4000,5000));
                Option dmgWind = new Option(90, Util.nextInt(4000,5000));
                Option regenMP = new Option(119, Util.nextInt(10000,15000));
                Option regenHP = new Option(120, Util.nextInt(40000,50000));
                Option resBaseDmg = new Option(124, Util.nextInt(2000,3000));
                switch (i) {
                    //roll thú cưỡi
                    case 0:
                        short idMount = arrMounts[Util.nextInt(arrMounts.length)];
                        Item mount = ItemTemplate.itemDefault(idMount);
                        mount.options.add(critDmg);
                        mount.options.add(critRate);
                        mount.options.add(dame);
                        mount.options.add(dmgTankerMounts);
                        mount.options.add(exp);
                        mount.options.add(maxHP);
                        mount.options.add(resAll);
                        mount.isExpires= false;
                        mount.expires = -1;
                        mount.isLock= false;  
                        p.c.addItemBag(true, mount);
                        break;
                    //roll mặt nạ
                    case 1:
                        short idMask = arrMounts[Util.nextInt(arrMask.length)];
                        Item mask = ItemTemplate.itemDefault(idMask);
                        mask.options.add(startPointPercent);
                        mask.options.add(damePercent);
                        mask.options.add(dame);
                        mask.options.add(dmgFire);
                        mask.options.add(dmgWind);
                        mask.options.add(dmgIce);
                        mask.options.add(resAll);
                        mask.options.add(dmgMob);
                        mask.options.add(dmgRef);
                        if(Util.nextInt(3)<1){
                            mask.options.add(regenHP); 
                            mask.options.add(regenMP); 
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(startPoint);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resFire);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resIce);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resWind);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resWindPercent);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resFirePercent);
                        }
                        if(Util.nextInt(3)<1){
                            mask.options.add(resIcePercent);
                        }
                        mask.isExpires= false;
                        mask.expires = -1;
                        mask.isLock= false;  
                        p.c.addItemBag(true, mask);
                        break; 
                    //roll mắt
                    case 2:                
                        int indexEye = Util.nextInt(arrEye.length);
                        short idEye = arrEye[indexEye];
                        Option eyeHP = new Option(125, 20000+((indexEye+1)*1000));
                        Option eyeDame = new Option(73, 5000+((indexEye+1)*1000));
                        Option eyeCritDmg = new Option(67, 50+((indexEye+1)*10));
                        Option eyeExp = new Option(100, 20+((indexEye+1)*10));
                        Option eyeDmgMob = new Option(102, 10000+((indexEye+1)*1000));
                        Option eyeResFire = new Option(95, 1000+((indexEye+1)*100));
                        Option eyeResIce = new Option(96, 1000+((indexEye+1)*100));
                        Option eyeResWind = new Option(97, 1000+((indexEye+1)*100));
                        Option eyeDamePercent = new Option(94, 42+((indexEye+1)*2));
                        Option[][] eyeOptions = new Option[][]{
                            {},{eyeResFire},{ eyeResIce},{ eyeResWind},
                            {},{},{},{},{},{},
                            { eyeDame},{ eyeCritDmg},{ eyeCritDmg},{ eyeDamePercent},
                        };
                        Item eye = ItemTemplate.itemDefault(idEye);
                        eye.options.add(eyeHP);
                        eye.options.add(eyeDame);
                        eye.options.add(eyeCritDmg);
                        eye.options.add(eyeExp);
                        eye.options.add(eyeDmgMob);
                        eye.upgrade = (byte) (indexEye+1);
                        
                        for (int j = 0; j <= indexEye; j++) {
                            eye.options.addAll(Arrays.asList(eyeOptions[j]));
                        }
                        eye.isExpires= false;
                        eye.expires = -1;
                        eye.isLock= false;  
                        p.c.addItemBag(true, eye);
                        break;
                    //roll NTGT
                    case 3:
                        int indexNTGT = Util.nextInt(arrNTGT.length);
                        Item NTGT = ItemTemplate.itemDefault(arrNTGT[indexNTGT]);
                        Option[][] ntgtOptions = new Option[][]{
                            {startPoint},  
                            {startPointPercent},
                            {regenHP},
                            {dame},
                            {damePercent},
                        };
                        NTGT.options.add(critRate);
                        NTGT.options.add(maxHP);
                        NTGT.options.add(dame);
                        NTGT.options.add(eva);
                        NTGT.options.add(acc);
                        NTGT.options.add(exp);
                        for (int j = 0; j <= indexNTGT; j++) {
                            NTGT.options.addAll(Arrays.asList(ntgtOptions[j]));
                        }
                        NTGT.isExpires= false;
                        NTGT.expires = -1;
                        NTGT.isLock= false;  
                        p.c.addItemBag(true, NTGT);

                        break;  
                    //roll bí kíp    
                    case 4:
                        int indexBK = Util.nextInt(arrBK.length);
                        Item bk = ItemTemplate.itemDefault(arrBK[indexBK]);
                        bk.options.add(critDmg);
                        bk.options.add(critRate);
                        bk.options.add(dmgFire);
                        bk.options.add(dmgWind);
                        bk.options.add(dmgIce);
                        bk.options.add(exp);
                        bk.options.add(maxHP);
                        bk.options.add(eva);
                        bk.options.add(damePercent);
                        bk.isExpires= false;
                        bk.expires = -1;
                        bk.isLock= false;  
                        p.c.addItemBag(true, bk);
                        break;
                    //roll pet    
                    case 5:
                        int indexPet = Util.nextInt(arrPet.length);
                        Item pet = ItemTemplate.itemDefault(arrBK[indexPet]);
                        pet.options.add(critDmg);
                        pet.options.add(dame);
                        pet.options.add(dmgFire);
                        pet.options.add(dmgIce);
                        pet.options.add(dmgWind);
                        if(Util.nextInt(1,2)==1){
                            pet.options.add(damePercent);
                        }else{
                            pet.options.add(resBaseDmg);
                        }
                        if(Util.nextInt(1,2)==1){
                            pet.options.add(regenHP);
                        }else{
                            pet.options.add(regenMP);
                        }
                        if(Util.nextInt(1,2)==1){
                            pet.options.add(startPoint);
                        }else{
                            pet.options.add(startPointPercent);
                        }
                        if(Util.nextInt(1,2)==1){
                            pet.options.add(dmgMob);
                        }else{
                            pet.options.add(resAll);
                        }
                        pet.isExpires= false;
                        pet.expires = -1;
                        pet.isLock= false;  
                        p.c.addItemBag(true, pet);
                        break;
                    case 6:
                        int indexCustom = Util.nextInt(arrCustom.length);
                        Item custom = ItemTemplate.itemDefault(arrCustom[indexCustom]);
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(damePercent);
                        }else{
                            custom.options.add(resBaseDmg);
                        }
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(regenHP);
                        }else{
                            custom.options.add(regenMP);
                        }
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(startPoint);
                        }else{
                            custom.options.add(startPointPercent);
                        }
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(dmgMob);
                        }else{
                            custom.options.add(resAll);
                        }
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(dame);
                        }else{
                            custom.options.add(eva);
                        }
                        if(Util.nextInt(1,2)==1){
                            custom.options.add(dame);
                        }else{
                            custom.options.add(eva);
                        }
                        custom.options.add(exp);
                        custom.options.add(maxHP);
                        custom.isExpires= false;
                        custom.expires = -1;
                        custom.isLock= false;  
                        p.c.addItemBag(true, custom);
                        break;
                }  
            }
        }else{
            p.conn.sendMessageLog("Hành trang của con không có đủ nguyên liệu");
        }
    }
}
