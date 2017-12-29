#!/usr/bin/python

import socket

end_line = "\r\n\r\n"
host = socket.gethostname()
port = 8080


def get(url):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    req = "GET " + url + " HTTP/1.0%s" % end_line

    s.connect((host, port))
    s.send(req.encode())

    data = s.recv(1024 * 1024)  # status line and headers
    data += s.recv(1024 * 1024)  # body

    print('--- RECEIVED:\n', data.decode())

    s.close()
    print('--- END\n')


get('/stuff_I_do_not_have.html')

get('/stuff.html')
