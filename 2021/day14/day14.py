#!/usr/bin/env python3

from collections import Counter
from functools import reduce

f = open("sample.txt","r")
sample = [ x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]

# part 1

def parse(input):
    template = None
    rules = {}
    for l in input:
        if template is None:
            template = l
        elif l != '':
            (elA,elB) = l.split(' -> ')
            rules[elA] = elB
    return (template,rules)

def apply(input,rules):
    out = []
    elements = list(input)
    for x in range(0,len(elements)-1):
        (a,b) = elements[x:x+2]
        out.append(a)
        k = ''.join([a,b])
        if k in rules:
            out.append(rules[k])
    out.append(elements[-1])
    return ''.join(out)

def part1(input,iterate):
    (template,rules) = parse(input)
    val = template
    for i in range(0,iterate):
        val = apply(val,rules)
    calc = Counter(list(val))
    return calc.most_common()[0][1] - calc.most_common()[-1][1]

print ('Part 1')
print (part1(sample,10))
print (part1(puzzle,10))
