#!/usr/bin/env python3

from collections import Counter
# from functools import reduce
# from collections import deque 
import cProfile

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
            el = list(elA)
            rules[(el[0],el[1])] = elB
    return (template,rules)

def apply(elements,rules):
    out = [ 0 ] * (len(elements) * 2 - 1) # deque()
    for x in range(0,len(elements)-1):
        ab = (elements[x],elements[x+1])
        out[x*2],out[x*2+1] = ab[0], rules[ab]
    out[len(elements) * 2 - 2] = elements[-1]
    return out

def part1(input,iterate):
    (template,rules) = parse(input)
    val = template # deque(template)
    for i in range(0,iterate):
        if i % 5 == 0:
            print (i, ' ', len(val))
        val = apply(val,rules)
    calc = Counter(val)
    return calc.most_common()[0][1] - calc.most_common()[-1][1]

print ('Part 1')
print (part1(sample,10))
print (part1(puzzle,10))

# cProfile.run("part1(puzzle,20)")

print ('Part 2')
print (part1(sample,40))
# print (part1(puzzle,40))