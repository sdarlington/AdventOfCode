#!/usr/bin/env python3

sample = [ 16,1,2,0,4,2,7,1,2,14 ]

f = open("input.txt","r")
puzzle = [[int(a) for a in x.replace('\n','').split(',')] for x in f.readlines()][0]

# part 1

def alignTo(positions,align):
    return [ abs(x - align) for x in positions ]

def calcMinFuel(positions, fuelCalc = alignTo):
    minFuel = (None,None)
    for p in range(min(positions),max(positions)+1):
        fuel = sum(fuelCalc(positions,p))
        if minFuel[0] is None or fuel < minFuel[1]:
            minFuel = (p,fuel)
    return minFuel

print (sum(alignTo(sample,2)))
print (calcMinFuel(sample))

print (calcMinFuel(puzzle))

# Part 2
def alignTo2(positions,align):
    return [ (lambda x: int((x * (x+1)) / 2))(abs(x - align)) for x in positions]

print (sum(alignTo2(sample,5)))
print (calcMinFuel(sample,alignTo2))

print (calcMinFuel(puzzle, alignTo2))
