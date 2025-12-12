# 🚀 TRAPPIST Tour – Java + Python Hybrid Mission Optimization

[![Java](https://img.shields.io/badge/Java-17+-007396.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.10+-3776AB.svg?logo=python&logoColor=white)](https://www.python.org/)
[![PyKEP](https://img.shields.io/badge/PyKEP-Orbital%20Mechanics-FF6F00.svg)](https://esa.github.io/pykep/)
[![NumPy](https://img.shields.io/badge/NumPy-Scientific-013243.svg?logo=numpy&logoColor=white)](https://numpy.org/)
[![SciPy](https://img.shields.io/badge/SciPy-Optimization%20Tools-8CAAE6.svg?logo=scipy&logoColor=white)](https://scipy.org/)
[![Anaconda](https://img.shields.io/badge/Anaconda-Env%20Manager-44A833.svg?logo=anaconda&logoColor=white)](https://www.anaconda.com/)
[![Git](https://img.shields.io/badge/Git-Version%20Control-F05032.svg?logo=git&logoColor=white)](https://git-scm.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)


This project evaluates multi-leg interplanetary transfers in the **TRAPPIST-1** system using a combined Java + Python architecture.

- **Java** handles trajectory vector generation, feasibility filters, orchestration, iteration loops, and pipeline control.
- **Python** evaluates each vector using **PyKEP**, solving Lambert transfers, propagating spacecraft motion, and computing ΔV (mission cost).

This structure allows Java’s speed and control flow to work together with Python’s scientific astrodynamics libraries.

---
## 📁 Project Structure
```
├── src/ 
│ └── trappist/
│   ├── FeasibleInitializer.java
│   ├── PythonEvaluator.java 
│   ├── DifferentialEvolutionOptimizer.java 
│   ├── Main.java
│   ├── TrappistTour.py
│   ├── SolutionExplorer.java
│   ├── TrajectoryEvaluator.java
│── python/
│   ├── evaluate_single_vector.py 
│   ├── trappist_tour.py 
│   ├── evaluate_solution.py
│   ├── python_server.py
│ ├── .gitignore 
└── README.md

```
## 🧠 Overview of How It Works

### **Java responsibilities**
- Generates feasible trajectory vectors
- Applies range constraints and sanity checks
- Detects and filters out “garbage” vectors
- Calls Python using `ProcessBuilder`
- Reads Python’s output (ΔV or penalty)
- Can run many evaluations in loops or threaded schedules

### **Python responsibilities**
- Parses the vector sent from Java
- Computes:
    - Lambert transfers
    - Leg propagation
    - Orbital mechanics
    - Total mission ΔV
- Prints a single number back to Java
- Returns large penalty values for unphysical missions

---

## 🐍 Python Environment Setup (Anaconda + Required Libraries)

This section explains how to install Anaconda, create a Python environment, and install all required packages for the Python side of the TRAPPIST Tour project.

---

### 📥 1. Install Anaconda (or Miniconda)

Download and install:

- **Anaconda:** https://www.anaconda.com/products/distribution
- **Miniconda:** https://docs.conda.io/en/latest/miniconda.html

During installation:

✔ Check **“Add Anaconda/Miniconda to PATH”**  
✔ Check **“Register Anaconda as default Python”**

After installation, open **Anaconda Prompt** (not CMD).

---

### 🆕 2. Create a New Conda Environment

Run this inside Anaconda Prompt:

```bash
conda create -n trappist_env python=3.10
conda activate trappist_env
```
A successful activation looks like:

```bash
(trappist_env) C:\Users\YourName>
```

Install core scientific libraries:

```bash
pip install numpy scipy matplotlib
pip install pykep
```

Run this command to verify installation:

```bash
python -c "import numpy; import scipy; import matplotlib; import pykep; print('Environment OK')"
```

If everything is correct, you will see:

```bash
Environment OK
```
Now locate the python folder where you've cloned it and then use:
```bash
cd <your location>
```
You can run the python server with:
```bash
python python_server.py
```
Now you can run main in java and wait for a solution vector to be made.<br>
After u have the solution vector, copy it and send it to the solution vector inside python.<br>
Now you can run the evaluate_solution.py using:<br>
```bash
python evaluate_solution.py
```

## 👥 Project Team

**Developed by:**
- [Eron Berisha](https://github.com/eronberishaa)
- [Enkel Berisha](https://github.com/enkelberisha)
- [Ermir Meziu](https://github.com/ErmirMeziu)
- [Vlera Galica](https://github.com/vleragalica)


FIEK - Universiteti i Prishtines

---

## 📄 License

This project is intended for educational purposes.  
You may freely use and modify it for learning and non-commercial use.  
(Consider adding **MIT** or another license if you plan to open-source.)





