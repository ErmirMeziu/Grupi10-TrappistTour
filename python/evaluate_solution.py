import json
import numpy as np
from trappist_tour import trappist_tour, combine_scores


def load_solution_vector(filename):
    with open(filename, "r") as f:
        data = json.load(f)
    return np.array(data, dtype=float)


# ESA SINGLE POINT SCORE
def single_score(udp, vector):
    dv, time, eq, iq = udp.fitness(vector)

    # ESA scoring rules
    if eq != 0:
        print("Invalid permutation.")
        return 0.0
    if iq > 0:
        print("Unsafe approach to star.")
        return 0.0
    if dv >= 2500 or time >= 4000:
        print("Outside ESA DV/time thresholds.")
        return 0.0


    points = [[dv, time]]

    score = combine_scores(points)
    return score

def main():

    x = load_solution_vector("solution_vector.json")
    udp = trappist_tour()

    dv, time, eq, iq = udp.fitness(x, logging=True)

    print("\n----- Mission Summary -----")
    print(f"Total DV: {dv:.3f} m/s")
    print(f"Total Time: {time:.3f} days ({time/365.25:.3f} years)")

    if eq == 0:
        print("Permutation: VALID")
    else:
        print("Permutation: INVALID (duplicate or missing planet index)")

    if iq < 0:
        print("Safe-distance: OK (Stayed outside star’s danger zone)")
    else:
        print(f"Safe-distance VIOLATION: periapsis inside danger zone, iq = {iq}")


    score = single_score(udp, x)
    print("\nESA Single Score:", score)



if __name__ == "__main__":
    main()