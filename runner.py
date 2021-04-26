#!/usr/bin/env python3
import subprocess
import sys

# remember to export the jar

def runOnce(f):
    s = subprocess.run(['java', '-jar', 'snakes-game-tutorial.jar', 'students.SampleBot', 'students.QLearningBot'], capture_output=True)
    s.check_returncode()
    f.write(s.stdout.decode())
    f.write(s.stderr.decode())


if __name__ == "__main__":
    print(sys.argv)
    n = 1
    if len(sys.argv) > 1:
        n = int(sys.argv[1])
    
    print(f"Running {n} batch{'es' if n > 1 else ''} of 5")
    
    with open('autorunner.log', 'w') as f:
        for _ in range(n):
            runOnce(f)
