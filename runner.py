#!/usr/bin/env python3
import subprocess
import sys
import time

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
    
    #print(f"Running {n} batch{'es' if n > 1 else ''} of 5")
    print(f"Running for {n} minute{'s' if n > 1 else ''}")
    
    seconds = n * 60
    startTime = time.time()
    runs = 0
    counter = 0
    with open('autorunner.log', 'w') as f:
        try:
            while time.time() - startTime < seconds:
                runOnce(f)
                runs += 5
                counter += 1
                print(f'\r{int(time.time() - startTime)}: {("x" * counter)}', end='', flush=True)
        except KeyboardInterrupt:
            pass
    
    print(f'ran {runs} times')
    
