#!/usr/bin/python

import socket

end_line = "\r\n\r\n"
_host = socket.gethostname()
_port = 8080


def send_this(req):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    s.connect((_host, _port))
    s.send(req.encode())

    data = s.recv(1024 * 1024)  # status line and headers
    data += s.recv(1024 * 1024)  # body

    s.close()

    return data
