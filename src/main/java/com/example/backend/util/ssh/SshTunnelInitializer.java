package com.example.backend.util.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.database.host", havingValue = "local1host")
public class SshTunnelInitializer {

    private Session session;
    @Value("${ssh.port}")
    private int sshPort;
    @Value("${ssh.ip}")
    private String sshIp;
    @Value("${ssh.user}")
    private String sshUser;
    @Value("${db.local.port}")
    private int dbLocalPort;
    @Value("${db.remote.host}")
    private String dbRemoteHost;
    @Value("${db.remote.port}")
    private int dbRemotePort;
    @Value("${ssh.key.location}")
    private String sshKeyLocation;

    @PostConstruct
    public void createSshTunnel() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(sshKeyLocation);
            session = jsch.getSession(sshUser, sshIp, sshPort);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            // Port forwarding
            session.setPortForwardingL(dbLocalPort, dbRemoteHost, dbRemotePort);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create SSH tunnel.");
        }
    }

    @PreDestroy
    public void stopSshTunnel() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
