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
    return None

def part1(input):
    results = [parse(x) for x in input]
    cal = [{')':3, ']':57, '}':1197, '>': 25137}[x[0]] for x in results if x is not None]
    return sum(cal)

print(part1(sample))
print(part1(puzzle))
