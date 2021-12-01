#!/usr/bin/env python3

sample = [ 199, 200, 208, 210, 200, 207, 240, 269, 260,263 ]

def compareElements(input):
    last = None
    for current in sample:
        if last is not None:
            if current > last:
                yield True
            else:
                yield False
        last = current

def part1(list):
    return len([ x for x in  compareElements(list) if x is True])

print ("Sample:")
print (part1(sample))

f = open("input.txt","r")
sample = [ int(x) for x in f.readlines()]

print("Part 1")
print (part1(sample))