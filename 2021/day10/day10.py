#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [x.replace('\n','') for x in f.readlines()]

# Part 1
print ('Part 1')
def parse(line):
    elem = list(line)
    stack = []
    for e in elem:
        if e in ['(', '[', '{', '<']:
            stack.append(e)
        else:
            match = stack.pop()
            opposite = { ')':'(', ']':'[', '}':'{', '>':'<' }[e]
            if match != opposite:
                return (e,match)
    return (None,stack)

def part1(input):
    results = [parse(x) for x in input]
    cal = [{')':3, ']':57, '}':1197, '>': 25137}[x[0]] for x in results if x[0] is not None]
    return sum(cal)

print(part1(sample))
print(part1(puzzle))

# Part 2
from functools import reduce

def part2(input):
    results = []
    for l in input:
        (last,stack) = parse(l)
        if last is None:
            stack.reverse()
            score = [ {'(':1, '[':2, '{':3, '<':4}[x] for x in stack]
            total = reduce(lambda x,y: x*5 + y, score)
            results.append(total)
    results.sort()
    return results[int(len(results) / 2)]

print ('\nPart 2')
print(part2(sample))
print(part2(puzzle))
