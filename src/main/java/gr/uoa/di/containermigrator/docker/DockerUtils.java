package gr.uoa.di.containermigrator.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import gr.uoa.di.containermigrator.global.Global;

/**
 * @author kyriakos
 */
public class DockerUtils {
	private static final DockerClient dockerClient = Global.getDockerClient();

	public static Container cloneContainer(Container srcContainer) {
		Container container = null;

		return container;

//		{
//			"Id": "9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f",
//			"Path": "catalina.sh",
//			"Args": [
//				"run"
//			],
//			"Image": "0a68ef0843121aa4408327f8f555c97abe0c54ef2c0a31e03620bc3080cee070",
//				"NetworkSettings": {
//					"Bridge": "",
//					"EndpointID": "5ad6b42134295fb312885d5a6c81fb9c9eb380335c7bfb5be4cc8898cfb94da4",
//					"Gateway": "172.17.42.1",
//					"GlobalIPv6Address": "",
//					"GlobalIPv6PrefixLen": 0,
//					"HairpinMode": false,
//					"IPAddress": "172.17.0.4",
//					"IPPrefixLen": 16,
//					"IPv6Gateway": "",
//					"LinkLocalIPv6Address": "",
//					"LinkLocalIPv6PrefixLen": 0,
//					"MacAddress": "02:42:ac:11:00:04",
//					"NetworkID": "c2da8179789854215a1f70943ab4f115e129286bd73973d1934a16baaf1a5057",
//					"PortMapping": null,
//					"Ports": {
//				"8080/tcp": null
//			},
//			"SandboxKey": "/var/run/docker/netns/9734159c3d93",
//			"SecondaryIPAddresses": null,
//			"SecondaryIPv6Addresses": null
//		},
//			"ResolvConfPath": "/var/lib/docker/containers/9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f/resolv.conf",
//				"HostnamePath": "/var/lib/docker/containers/9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f/hostname",
//				"HostsPath": "/var/lib/docker/containers/9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f/hosts",
//				"LogPath": "/var/lib/docker/containers/9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f/9734159c3d93780d5b5a527b5325a3114c399bb1623e3c0b44949082118d003f-json.log",
//				"Name": "/tomcat1",
//				"RestartCount": 0,
//				"Driver": "aufs",
//				"ExecDriver": "native-0.2",
//				"MountLabel": "",
//				"ProcessLabel": "",
//				"AppArmorProfile": "",
//				"ExecIDs": null,
//				"HostConfig": {
//			"Binds": null,
//					"ContainerIDFile": "",
//					"LxcConf": [],
//			"Memory": 0,
//					"MemorySwap": 0,
//					"CpuShares": 0,
//					"CpuPeriod": 0,
//					"CpusetCpus": "",
//					"CpusetMems": "",
//					"CpuQuota": 0,
//					"BlkioWeight": 0,
//					"OomKillDisable": false,
//					"MemorySwappiness": -1,
//					"Privileged": false,
//					"PortBindings": {},
//			"Links": null,
//					"PublishAllPorts": false,
//					"Dns": null,
//					"DnsSearch": null,
//					"ExtraHosts": null,
//					"VolumesFrom": null,
//					"Devices": [],
//			"NetworkMode": "default",
//					"IpcMode": "",
//					"PidMode": "",
//					"UTSMode": "",
//					"CapAdd": null,
//					"CapDrop": null,
//					"GroupAdd": null,
//					"RestartPolicy": {
//				"Name": "no",
//						"MaximumRetryCount": 0
//			},
//			"SecurityOpt": null,
//					"ReadonlyRootfs": false,
//					"Ulimits": null,
//					"LogConfig": {
//				"Type": "json-file",
//						"Config": {}
//			},
//			"CgroupParent": "",
//					"ConsoleSize": [
//				0,
//					0
//			]
//		},
//			"GraphDriver": {
//			"Name": "aufs",
//					"Data": null
//		},
//			"Mounts": [],
//			"Config": {
//			"Hostname": "9734159c3d93",
//					"Domainname": "",
//					"User": "",
//					"AttachStdin": false,
//					"AttachStdout": false,
//					"AttachStderr": false,
//					"ExposedPorts": {
//				"8080/tcp": {}
//			},
//			"PublishService": "",
//					"Tty": false,
//					"OpenStdin": false,
//					"StdinOnce": false,
//					"Env": [
//			"PATH=/usr/local/tomcat/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin",
//					"LANG=C.UTF-8",
//					"JAVA_VERSION=7u79",
//					"JAVA_DEBIAN_VERSION=7u79-2.5.5-1~deb8u1",
//					"CATALINA_HOME=/usr/local/tomcat",
//					"TOMCAT_MAJOR=8",
//					"TOMCAT_VERSION=8.0.24",
//					"TOMCAT_TGZ_URL=https://www.apache.org/dist/tomcat/tomcat-8/v8.0.24/bin/apache-tomcat-8.0.24.tar.gz"
//			],
//			"Cmd": [
//			"catalina.sh",
//					"run"
//			],
//			"Image": "my-tomcat",
//					"Volumes": null,
//					"VolumeDriver": "",
//					"WorkingDir": "/usr/local/tomcat",
//					"Entrypoint": null,
//					"NetworkDisabled": false,
//					"MacAddress": "",
//					"OnBuild": null,
//					"Labels": {}
//		}
//		}

	}

	public static boolean isRunning(String containerName) {
		Iterable<Container> containers = dockerClient.listContainersCmd().exec();
		for(Container c : containers) {
			if (c.getNames()[0].contains(containerName)) return true;
		}
		return false;
	}

	public static boolean exists(String containerName) {
		Iterable<Container> containers = dockerClient.listContainersCmd()
				.withShowAll(true)
				.exec();
		for(Container c : containers) {
			if (c.getNames()[0].contains(containerName)) return true;
		}
		return false;
	}
}
