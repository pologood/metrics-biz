package com.codahale.metrics.biz.health;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import com.codahale.metrics.health.HealthCheck;
/**
 * 
 * @className	： ServiceOnlineCheck
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 * @date		： 2017年9月12日 下午10:46:35
 * @version 	V1.0
 * http://blog.csdn.net/paullmq/article/details/9032631
 */
public class ServiceOnlineCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {

		return null;
	}

	void isAddressAvailable(String ip,int timeout) {
		try {
			InetAddress address = InetAddress.getByName(ip);// ping this IP

			if (address instanceof java.net.Inet4Address) {
				System.out.println(ip + " is ipv4 address");
			} else if (address instanceof java.net.Inet6Address) {
				System.out.println(ip + " is ipv6 address");
			} else {
				System.out.println(ip + " is unrecongized");
			}
			
			if (address.isReachable(timeout)) {
				System.out.println("SUCCESS - ping " + ip + " with no interface specified");
			} else {
				System.out.println("FAILURE - ping " + ip + " with no interface specified");
			}

			System.out.println("\n-------Trying different interfaces--------\n");

			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				System.out.println("Checking interface, DisplayName:" + ni.getDisplayName() + ", Name:" + ni.getName());
				if (address.isReachable(ni, 0, 5000)) {
					System.out.println("SUCCESS - ping " + ip);
				} else {
					System.out.println("FAILURE - ping " + ip);
				}

				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					System.out.println("IP: " + ips.nextElement().getHostAddress());
				}
				System.out.println("-------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("error occurs.");
			e.printStackTrace();
		}
	}

}
