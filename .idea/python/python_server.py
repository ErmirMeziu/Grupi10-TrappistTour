import socket
import json
import numpy as np
from trappist_tour import trappist_tour

udp = trappist_tour()

s = socket.socket()
s.bind(("localhost", 5555))
s.listen(1)

while True:
    conn, addr = s.accept()
    data = conn.recv(16384).decode()
    vec = np.array(json.loads(data), dtype=float)

    dv, t, eq, iq = udp.fitness(vec)
    conn.send(f"{dv},{t}".encode())
    conn.close()