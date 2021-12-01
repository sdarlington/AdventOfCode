#!/usr/bin/env python3

sample = [ 199, 200, 208, 210, 200, 207, 240, 269, 260,263 ]

def compareElements(input):
    last = None
    for current in input:
        if last is not None:
            yield current > last
        last = current

def part1(list):
    return len([ x for x in  compareElements(list) if x is True])

print ("Sample:")
print (part1(sample))

f = open("input.txt","r")
puzzle = [ int(x) for x in f.readlines()]

print("Part 1")
print (part1(puzzle))

def compareElements2(input):
    elements = []
    for current in input:
        if len(elements) < 3:
            elements.append(current)
        else:
            old = sum(elements)
            elements.append(current)
            del elements[0]
            new = sum(elements)
            yield old < new 

def part2(list):
    return len([ x for x in  compareElements2(list) if x is True])

print("Part 2")
print (part2(sample))
print (part2(puzzle))
