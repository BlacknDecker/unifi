#!/usr/bin/python

import my_server


def get(url):
    req = "GET " + url + " HTTP/1.0%s" % my_server.end_line
    res = my_server.send_this(req)

    print('--- RECEIVED:\n', res.decode())
    print('--- END\n')


get('/stuff_I_do_not_have.html')
get('/stuff.html')
