import sys
import json
import numpy as np
from trappist_tour import trappist_tour

vec = json.loads(sys.argv[1])
x = np.array(vec, float)

perm = [int(round(v)) for v in x[-7:]]
perm = [min(6, max(0, p)) for p in perm]

cont = x[:-7]

udp = trappist_tour()

dv, time, eq, iq = udp.fitness(cont, perm)

print(f"{dv},{time}")
