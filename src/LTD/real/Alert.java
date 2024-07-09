package LTD.real;

import LTD.real.Player;
import java.net.Socket;
import com.sun.management.OperatingSystemMXBean;
import LTD.stream.Client;
import LTD.server.Server;
import java.lang.management.ManagementFactory;

public class Alert
{
    private String alert;
   private String clientIpAddress;
    public Alert() {
    }
    public Alert(final String alert) {
        this.alert = alert;
    }
    
    public String getAlert() {
        return this.alert;
    }
    
    public void setAlert(final String alert) {
        this.alert = alert;
    }
    
    public void sendAlert(final Player player) {
        final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        final long memoryusage = osBean.getTotalPhysicalMemorySize() / 1000000L + -osBean.getFreePhysicalMemorySize() / 1000000L;
        final long totalmemory = osBean.getTotalPhysicalMemorySize() / 1000000L;
        final int songuoi = Client.gI().ninja_size() + 200;
        Server.manager.sendTB(player, "Server", ((this.getAlert() == null) ? "" : this.getAlert()) + "\n- S\u1ed1 ng\u01b0\u1eddi online: " + songuoi + "\n\n- CPU Usgae: " + String.format("%.2f", osBean.getSystemCpuLoad() * 100.0) + "%" + "\n\n - Free Memory : " + osBean.getFreePhysicalMemorySize() / 1000000L + "MB" + "\n\n - Memory Usage : " + memoryusage + "MB" + "\n\n - Total Memory : " + totalmemory + "MB");
    }
}

