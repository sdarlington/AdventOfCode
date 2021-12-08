#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [[a.split(' ') for a in x.replace('\n','').split(' | ')] for x in f.readlines()]

f = open("input.txt","r")
puzzle = [[a.split(' ') for a in x.replace('\n','').split(' | ')] for x in f.readlines()]

# part 1
def part1(input):
    def process_segment(input):
        return [ len(x) for x in input ]
    count = 0
    for r in input:
        signal = [x for x in process_segment(r[0]) if x in (2,4,3,7)]
        output = [x for x in process_segment(r[1]) if x in (2,4,3,7)]
        count = count + len(output)
    return count

print ("Part 1")
print(part1(sample))
print(part1(puzzle))