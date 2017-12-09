package it.unifi.rc.httpserver.m5951907;

import it.unifi.rc.httpserver.*;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

@SuppressWarnings("unused")
public class MyHTTPFactory implements HTTPFactory {
    @Override
    public HTTPInputStream getHTTPInputStream(InputStream is) {
        return null;
    }

    @Override
    public HTTPOutputStream getHTTPOutputStream(OutputStream os) {
        return null;
    }

    @Override
    public HTTPServer getHTTPServer(int port, int backlog, InetAddress address, HTTPHandler... handlers) {
        return null;
    }

    @Override
    public HTTPHandler getFileSystemHandler1_0(File root) {
        return null;
    }

    @Override
    public HTTPHandler getFileSystemHandler1_0(String host, File root) {
        return null;
    }

    @Override
    public HTTPHandler getFileSystemHandler1_1(File root) {
        return null;
    }

    @Override
    public HTTPHandler getFileSystemHandler1_1(String host, File root) {
        return null;
    }

    @Override
    public HTTPHandler getProxyHandler() {
        return null;
    }
}
