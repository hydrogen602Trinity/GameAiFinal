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


def getWidth():
    s = subprocess.run(['tput', 'cols'], capture_output=True)
    s.check_returncode()
    return int(s.stdout.decode().strip())


if __name__ == "__main__":
    print(sys.argv)
    n = 1
    if len(sys.argv) > 1:
        n = int(sys.argv[1])
    
    #print(f"Running {n} batch{'es' if n > 1 else ''} of 5")
    print(f"Running for {n} minute{'s' if n > 1 else ''}")

    width = getWidth()

    print('-' * width)
    
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

                #width = getWidth()

                data = f'Time elapsed: {int(time.time() - startTime):5} seconds, runs completed: {runs:10} '
                spacing = len(data) + 3

                spaceLeft = width - spacing
                timeFracDone = int((time.time() - startTime) / seconds * spaceLeft)
                bar = f'[{("=" * timeFracDone)}>{(" " * (spaceLeft - timeFracDone))}]'
                print(f'\r{data}{bar}', end='', flush=True)
        except KeyboardInterrupt:
            pass
    print()
    print(f'Ran {runs} times')
    
